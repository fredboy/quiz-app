package ru.fredboy.quizapp.presentation.quizdetails.model

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.whenever
import ru.fredboy.quizapp.domain.model.Question
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase

class QuizDetailsViewModelTest {

    private val getQuizUseCase = mock<GetQuizUseCase>()

    private val observeQuizStatusUseCase = mock<ObserveQuizStatusUseCase>()

    private val invalidateCachedQuizUseCase = mock<InvalidateCachedQuizUseCase>()

    private lateinit var viewModel: QuizDetailsViewModel

    @BeforeEach
    fun setUp() {
        viewModel = QuizDetailsViewModel(
            getQuizUseCase = getQuizUseCase,
            observeQuizStatusUseCase = observeQuizStatusUseCase,
            invalidateCachedQuizUseCase = invalidateCachedQuizUseCase,
            params = QuizDetailsViewModelParams(QUIZ_DETAILS_VO.quizDetails.id),
        )
    }

    @Test
    fun `successfully emits a quiz view object`() = runTest {
        val quizDetailsVo = QUIZ_DETAILS_VO

        whenever(getQuizUseCase.invoke(quizDetailsVo.quizDetails.id))
            .thenReturn(quizDetailsVo.quizDetails)
        whenever(observeQuizStatusUseCase.invoke(quizDetailsVo.quizDetails.id))
            .thenReturn(flowOf(quizDetailsVo.status))

        viewModel.quizDetailsState.test {
            assertEquals(QuizDetailsState.Loading, awaitItem())
            assertEquals(QuizDetailsState.Success(quizDetailsVo), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits error state on load error`() = runTest {
        whenever(getQuizUseCase.invoke(any()))
            .doThrow(RuntimeException("Random exception"))

        viewModel.quizDetailsState.test {
            assertEquals(QuizDetailsState.Loading, awaitItem())
            assertEquals(QuizDetailsState.Error("Random exception"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invalidates caches before reload`() = runTest {
        val quizDetailsVo = QUIZ_DETAILS_VO

        whenever(getQuizUseCase.invoke(quizDetailsVo.quizDetails.id))
            .thenReturn(quizDetailsVo.quizDetails)
        whenever(observeQuizStatusUseCase.invoke(quizDetailsVo.quizDetails.id))
            .thenReturn(flowOf(quizDetailsVo.status))

        viewModel.quizDetailsState.test {
            awaitItem()
            awaitItem()

            viewModel.onReload(QuizDetailsReloadEvent.Reload)

            val loading = awaitItem()
            assertEquals(QuizDetailsState.Loading, loading)
            awaitItem()

            inOrder(invalidateCachedQuizUseCase, getQuizUseCase) {
                verify(invalidateCachedQuizUseCase).invoke(quizDetailsVo.quizDetails.id)
                verify(getQuizUseCase).invoke(quizDetailsVo.quizDetails.id)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        private val QUIZ_DETAILS_VO = QuizDetailsVo(
            quizDetails = QuizDetails(
                id = 1234,
                title = "Quiz Title",
                description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                    "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                    "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                    "Consequatur ut exercitationem ut beatae soluta officiis." +
                    "Repellat et possimus doloremque molestiae.",
                imageUrl = "https://placehold.co/600x400.png",
                passingScore = 2,
                questions = listOf(
                    Question(
                        id = 1,
                        text = "Question text 1",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                    Question(
                        id = 2,
                        text = "Question text 2",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                    Question(
                        id = 3,
                        text = "Question text 3",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                ),
            ),
            status = null,
        )
    }
}
