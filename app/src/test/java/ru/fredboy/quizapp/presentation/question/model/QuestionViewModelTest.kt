package ru.fredboy.quizapp.presentation.question.model

import androidx.navigation3.runtime.NavBackStack
import app.cash.turbine.test
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.fredboy.quizapp.domain.model.Answer
import ru.fredboy.quizapp.domain.model.Question
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.domain.usecase.InvalidateCachedQuizUseCase
import ru.fredboy.quizapp.domain.usecase.SaveQuizStatusUseCase
import kotlin.test.assertEquals

class QuestionViewModelTest {

    private val getQuizUseCase = mock<GetQuizUseCase>()
    private val invalidateCachedQuizUseCase = mock<InvalidateCachedQuizUseCase>()
    private val saveQuizStatusUseCase = mock<SaveQuizStatusUseCase>()
    private val navBackStack = mock<NavBackStack>()

    private lateinit var viewModel: QuestionViewModel

    private val quizId = 123
    private val passingScore = 2

    private val questions = listOf(
        Question(
            id = 1,
            text = "Q1",
            answers = listOf(Answer(1, "A"), Answer(2, "C"), Answer(3, "B")),
            correctAnswerId = 1,
            imageUrl = null,
        ),
        Question(
            id = 2,
            text = "Q2",
            answers = listOf(Answer(1, "A"), Answer(2, "C"), Answer(3, "B")),
            correctAnswerId = 2,
            imageUrl = null,
        ),
    )

    private val quizDetails = QuizDetails(
        id = quizId,
        title = "Sample Quiz",
        description = "Desc",
        imageUrl = "https://placehold.co/600x400.png",
        passingScore = passingScore,
        questions = questions,
    )

    @BeforeEach
    fun setUp() = runTest {
        whenever(getQuizUseCase.invoke(quizId)).thenReturn(quizDetails)
        viewModel = QuestionViewModel(
            getQuizUseCase = getQuizUseCase,
            invalidateCachedQuizUseCase = invalidateCachedQuizUseCase,
            saveQuizStatusUseCase = saveQuizStatusUseCase,
            navBackStack = navBackStack,
            params = QuestionViewModelParams(quizId),
        )
    }

    @Test
    fun `emits loading then question on successful quiz load`() = runTest {
        viewModel.questionState.test {
            assertEquals(QuestionState.Loading, awaitItem())
            val state = awaitItem() as QuestionState.Success
            assertEquals("Q1", state.questionVo.question.text)
            assertEquals(1, state.questionVo.current)
            assertEquals(2, state.questionVo.total)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits error state on quiz load failure`() = runTest {
        whenever(getQuizUseCase.invoke(quizId)).doThrow(RuntimeException("API failure"))

        viewModel = QuestionViewModel(
            getQuizUseCase,
            invalidateCachedQuizUseCase,
            saveQuizStatusUseCase,
            navBackStack,
            QuestionViewModelParams(quizId),
        )

        viewModel.questionState.test {
            assertEquals(QuestionState.Loading, awaitItem())
            val errorState = awaitItem() as QuestionState.Error
            assertEquals("API failure", errorState.message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updates selected answer on user selection`() = runTest {
        viewModel.onAnswerSelected(1)

        viewModel.questionState.test {
            awaitItem()
            val state = awaitItem() as QuestionState.Success
            assertEquals(1, state.questionVo.selectedAnswerId)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @Disabled("flaky due to coroutine launch")
    fun `navigates to next question and calculates final result`() = runTest {
        viewModel.questionState.test {
            awaitItem()

            viewModel.onAnswerSelected(1)
            awaitItem()
            viewModel.onNextClicked()
            awaitItem()

            viewModel.onAnswerSelected(2)
            awaitItem()
            viewModel.onNextClicked()
        }

        verify(saveQuizStatusUseCase).invoke(quizId, QuizStatus.PASSED)
        verify(navBackStack).removeLastOrNull()
    }

    @Test
    @Disabled("flaky due to coroutine launch")
    fun `calculates failed result if score below passing`() = runTest {
        viewModel.questionState.test {
            awaitItem()

            viewModel.onAnswerSelected(2)
            awaitItem()
            viewModel.onNextClicked()
            awaitItem()

            viewModel.onAnswerSelected(2)
            awaitItem()
            viewModel.onNextClicked()
        }

        advanceUntilIdle()
        verify(saveQuizStatusUseCase).invoke(quizId, QuizStatus.FAILED)
        verify(navBackStack).removeLastOrNull()
    }

    @Test
    fun `reload emits invalidation and refetches quiz`() = runTest {
        viewModel.questionState.test {
            awaitItem()
            awaitItem()

            viewModel.onReload()

            awaitItem()
            awaitItem()

            inOrder(invalidateCachedQuizUseCase, getQuizUseCase) {
                verify(invalidateCachedQuizUseCase).invoke(quizId)
                verify(getQuizUseCase, times(1)).invoke(quizId)
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}
