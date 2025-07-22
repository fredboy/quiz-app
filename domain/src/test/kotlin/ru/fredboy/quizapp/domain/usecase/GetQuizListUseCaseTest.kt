package ru.fredboy.quizapp.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
@ExtendWith(MockitoExtension::class)
class GetQuizListUseCaseTest {

    @Test
    fun `returns cached quizzes when cache is fresh`() = runTest {
        val freshTimestamp = Clock.System.now().epochSeconds
        val quizzes = Quizzes(mockQuizList(), freshTimestamp)
        val serverQuizzes =
            Quizzes(mockQuizList().map { it.copy(id = it.id + 100) }, freshTimestamp)

        val repo = mock<QuizRepository>(lenient = true) {
            onBlocking { getQuizzesFromCache() } doReturn quizzes
            onBlocking { getQuizzesFromServer() } doReturn serverQuizzes
        }

        val useCase = GetQuizListUseCase(repo)

        val result = useCase.invoke()

        assertEquals(quizzes, result)
        verify(repo, never()).getQuizzesFromServer()
    }

    @Test
    fun `fetches from server when cache is stale`() = runTest {
        val staleTimestamp = Instant.DISTANT_PAST.epochSeconds
        val freshTimestamp = Clock.System.now().epochSeconds
        val cached = Quizzes(mockQuizList().take(1), staleTimestamp)
        val fromServer = Quizzes(mockQuizList(), freshTimestamp)

        val repo = mock<QuizRepository> {
            onBlocking { getQuizzesFromCache() } doReturn cached
            onBlocking { getQuizzesFromServer() } doReturn fromServer
        }

        val useCase = GetQuizListUseCase(repo)

        val result = useCase.invoke()

        assertEquals(fromServer, result)
        verify(repo).saveQuizzesToCache(fromServer)
    }

    @Test
    fun `fetches from server when cache is missing`() = runTest {
        val freshTimestamp = Clock.System.now().epochSeconds
        val fromServer = Quizzes(mockQuizList(), freshTimestamp)

        val repo = mock<QuizRepository> {
            onBlocking { getQuizzesFromCache() } doReturn null
            onBlocking { getQuizzesFromServer() } doReturn fromServer
        }

        val useCase = GetQuizListUseCase(repo)

        val result = useCase.invoke()

        assertEquals(fromServer, result)
        verify(repo).saveQuizzesToCache(fromServer)
    }

    @Test
    fun `does not crash if caching fails`() = runTest {
        val freshTimestamp = Clock.System.now().epochSeconds
        val fromServer = Quizzes(mockQuizList(), freshTimestamp)

        val repo = mock<QuizRepository> {
            onBlocking { getQuizzesFromCache() } doReturn null
            onBlocking { getQuizzesFromServer() } doReturn fromServer
            onBlocking { saveQuizzesToCache(any()) } doThrow RuntimeException("Disk full")
        }

        val useCase = GetQuizListUseCase(repo)

        val result = useCase.invoke()

        assertEquals(fromServer, result)
    }

    companion object {
        private fun mockQuizList(): List<Quiz> {
            return listOf(
                Quiz(
                    id = 1,
                    title = "title 1",
                    imageUrl = "http://example.com/image_1.png",
                    passingScore = 2,
                    numberOfQuestions = 4,
                ),
                Quiz(
                    id = 2,
                    title = "title 2",
                    imageUrl = "http://example.com/image_2.png",
                    passingScore = 2,
                    numberOfQuestions = 4,
                )
            )
        }
    }
}
