package ru.fredboy.quizapp.domain.usecase

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.repository.QuizRepository

@ExtendWith(MockitoExtension::class)
class GetQuizUseCaseTest {

    @Test
    fun `returns cached quiz if available`() = runTest {
        val quizId = 1
        val cachedQuiz = mock<QuizDetails>()
        val serverQuiz = mock<QuizDetails>()

        val repo = mock<QuizRepository>(lenient = true) {
            onBlocking { getQuizFromCache(quizId) } doReturn cachedQuiz
            onBlocking { getQuizFromServer(quizId) } doReturn serverQuiz
        }

        val useCase = GetQuizUseCase(repo)

        val result = useCase.invoke(quizId)

        assertEquals(cachedQuiz, result)
        verify(repo, never()).getQuizFromServer(any())
    }

    @Test
    fun `fetches from server and caches if cache is missing`() = runTest {
        val quizId = 2
        val serverQuiz = mock<QuizDetails>()

        val repo = mock<QuizRepository> {
            onBlocking { getQuizFromCache(quizId) } doReturn null
            onBlocking { getQuizFromServer(quizId) } doReturn serverQuiz
        }

        val useCase = GetQuizUseCase(repo)

        val result = useCase.invoke(quizId)

        assertEquals(serverQuiz, result)
        verify(repo).saveQuizToCache(serverQuiz)
    }

    @Test
    fun `fetches from server on cache error`() = runTest {
        val quizId = 2
        val serverQuiz = mock<QuizDetails>()

        val repo = mock<QuizRepository> {
            onBlocking { getQuizFromCache(quizId) } doThrow RuntimeException("Cache exception")
            onBlocking { getQuizFromServer(quizId) } doReturn serverQuiz
        }

        val useCase = GetQuizUseCase(repo)

        val result = useCase.invoke(quizId)

        assertEquals(serverQuiz, result)
        verify(repo).saveQuizToCache(serverQuiz)
    }

    @Test
    fun `does not crash if caching throws after fetching from server`() = runTest {
        val quizId = 3
        val serverQuiz = mock<QuizDetails>()

        val repo = mock<QuizRepository> {
            onBlocking { getQuizFromCache(quizId) } doReturn null
            onBlocking { getQuizFromServer(quizId) } doReturn serverQuiz
            onBlocking { saveQuizToCache(serverQuiz) } doThrow RuntimeException("Disk error")
        }

        val useCase = GetQuizUseCase(repo)

        val result = useCase.invoke(quizId)

        assertEquals(serverQuiz, result)

        verify(repo).saveQuizToCache(serverQuiz)
    }
}
