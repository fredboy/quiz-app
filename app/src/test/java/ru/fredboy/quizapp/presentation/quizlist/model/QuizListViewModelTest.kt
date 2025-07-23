package ru.fredboy.quizapp.presentation.quizlist.model

import androidx.navigation3.runtime.NavBackStack
import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizzesUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams
import ru.fredboy.quizapp.presentation.quizdetails.navigation.QuizDetailsNavKey

class QuizListViewModelTest {

    private val getQuizListUseCase = mock<GetQuizListUseCase>()

    private val observeQuizStatusUseCase = mock<ObserveQuizStatusUseCase>()

    private val invalidateCachedQuizzesUseCase = mock<InvalidateCachedQuizzesUseCase>()

    private val navBackStack = mock<NavBackStack>()

    private lateinit var viewModel: QuizListViewModel

    @BeforeEach
    fun setViewModel() {
        viewModel = QuizListViewModel(
            getQuizListUseCase = getQuizListUseCase,
            observeQuizStatusUseCase = observeQuizStatusUseCase,
            invalidateCachedQuizzesUseCase = invalidateCachedQuizzesUseCase,
            navBackStack = navBackStack,
        )
    }

    @Test
    fun `successfully emits a list of quizzes with status`() = runTest {
        val quizzes = mockQuizVos()
        whenever(getQuizListUseCase.invoke()).doReturn(Quizzes(quizzes.map { it.quiz }, 0L))
        quizzes.forEach { quizVo ->
            whenever(observeQuizStatusUseCase.invoke(quizVo.quiz.id))
                .doReturn(flowOf(quizVo.status))
        }

        viewModel.quizListState.test {
            assertEquals(QuizListState.Loading, awaitItem())

            assertEquals(
                QuizListState.Success(mockQuizVos()),
                awaitItem(),
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits error state on load error`() = runTest {
        whenever(getQuizListUseCase.invoke()).doThrow(RuntimeException("Random exception"))

        viewModel.quizListState.test {
            assertEquals(QuizListState.Loading, awaitItem())

            assertEquals(QuizListState.Error("Random exception"), awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invalidates caches before reload`() = runTest {
        val quizzes = mockQuizVos()
        whenever(getQuizListUseCase.invoke()).doReturn(Quizzes(quizzes.map { it.quiz }, 0L))
        quizzes.forEach {
            whenever(observeQuizStatusUseCase(it.quiz.id)).doReturn(flowOf(it.status))
        }

        viewModel.quizListState.test {
            awaitItem()
            awaitItem()

            viewModel.onReload(QuizListReloadEvent.Reload)

            val loading = awaitItem()
            assertEquals(QuizListState.Loading, loading)
            awaitItem()

            inOrder(invalidateCachedQuizzesUseCase, getQuizListUseCase) {
                verify(invalidateCachedQuizzesUseCase).invoke()
                verify(getQuizListUseCase).invoke()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `pushes quiz details nav key to back stack on click`() = runTest {
        val quizVo = mockQuizVos().last()

        viewModel.onQuizClick(quizVo)

        verify(navBackStack, times(1))
            .add(QuizDetailsNavKey(QuizDetailsViewModelParams(quizVo.quiz.id)))
    }

    companion object {

        private fun mockQuizVos(): List<QuizVo> {
            return listOf(
                QuizVo(
                    quiz = Quiz(
                        id = 1,
                        title = "Quiz Title 1",
                        description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                                "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                                "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                                "Consequatur ut exercitationem ut beatae soluta officiis." +
                                "Repellat et possimus doloremque molestiae.",
                        imageUrl = "https://placehold.co/600x400.png",
                        passingScore = 12,
                        numberOfQuestions = 20,
                    ),
                    status = QuizStatus.FAILED,
                ),
                QuizVo(
                    quiz = Quiz(
                        id = 2,
                        title = "Quiz Title 2",
                        description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                                "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                                "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                                "Consequatur ut exercitationem ut beatae soluta officiis." +
                                "Repellat et possimus doloremque molestiae.",
                        imageUrl = "https://placehold.co/600x400.png",
                        passingScore = 6,
                        numberOfQuestions = 10,
                    ),
                    status = QuizStatus.PASSED,
                ),
                QuizVo(
                    quiz = Quiz(
                        id = 3,
                        title = "Quiz Title 3",
                        description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                                "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                                "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                                "Consequatur ut exercitationem ut beatae soluta officiis." +
                                "Repellat et possimus doloremque molestiae.",
                        imageUrl = "https://placehold.co/600x400.png",
                        passingScore = 38,
                        numberOfQuestions = 40,
                    ),
                    status = null,
                ),
            )
        }
    }
}
