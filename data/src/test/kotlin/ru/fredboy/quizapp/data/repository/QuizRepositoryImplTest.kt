package ru.fredboy.quizapp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.model.Quizzes

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class QuizRepositoryImplTest {

    private lateinit var repo: QuizRepositoryImpl

    private val remote = mock<RemoteQuizDataSource>()
    private val local = mock<LocalQuizDataSource>()
    private val quizzesMapper = mock<QuizzesMapper>()
    private val detailsMapper = mock<QuizDetailsMapper>()

    @BeforeEach
    fun setup() {
        repo = QuizRepositoryImpl(remote, local, quizzesMapper, detailsMapper)
    }

    @Test
    fun `getQuizzesFromServer fetches and maps quizzes`() = runTest {
        val dto = mock<QuizzesDto>()
        val mapped = mock<Quizzes>()

        whenever(remote.getQuizzes()) doReturn dto
        whenever(quizzesMapper.map(dto)) doReturn mapped

        val result = repo.getQuizzesFromServer()

        assert(result === mapped)
        verify(remote).getQuizzes()
        verify(quizzesMapper).map(dto)
    }

    @Test
    fun `getQuizFromServer fetches and maps details`() = runTest {
        val dto = mock<QuizDetailsDto>()
        val mapped = mock<QuizDetails>()
        val id = 42

        whenever(remote.getQuiz(id)) doReturn dto
        whenever(detailsMapper.map(dto)) doReturn mapped

        val result = repo.getQuizFromServer(id)

        assert(result === mapped)
        verify(remote).getQuiz(id)
        verify(detailsMapper).map(dto)
    }

    @Test
    fun `getQuizzesFromCache returns cached quizzes`() = runTest {
        val quizzes = mock<Quizzes>()

        whenever(local.getQuizzes()) doReturn quizzes

        val result = repo.getQuizzesFromCache()

        assert(result === quizzes)
        verify(local).getQuizzes()
    }

    @Test
    fun `getQuizFromCache returns cached quiz`() = runTest {
        val quiz = mock<QuizDetails>()
        val id = 7

        whenever(local.getQuiz(id)) doReturn quiz

        val result = repo.getQuizFromCache(id)

        assert(result === quiz)
        verify(local).getQuiz(id)
    }

    @Test
    fun `saveQuizzesToCache delegates to local source`() = runTest {
        val quizzes = mock<Quizzes>()

        repo.saveQuizzesToCache(quizzes)

        verify(local).saveQuizzes(quizzes)
    }

    @Test
    fun `saveQuizToCache delegates to local source`() = runTest {
        val quiz = mock<QuizDetails>()

        repo.saveQuizToCache(quiz)

        verify(local).saveQuiz(quiz)
    }

    @Test
    fun `saveQuizStatusToCache calls localQuizDataSource saveQuizStatus`() = runTest {
        val quizId = 1
        val status = QuizStatus.PASSED

        repo.saveQuizStatusToCache(quizId, status)

        verify(local).saveQuizStatus(quizId, status)
    }

    @Test
    fun `getQuizStatusFromCache returns status from localQuizDataSource`() = runTest {
        val quizId = 5
        val expectedStatus = QuizStatus.PASSED

        whenever(local.getQuizStatus(quizId)).thenReturn(expectedStatus)

        val actualStatus = repo.getQuizStatusFromCache(quizId)

        assertEquals(expectedStatus, actualStatus)
        verify(local).getQuizStatus(quizId)
    }

    @Test
    fun `observeQuizStatus returns flow from localQuizDataSource`() = runTest {
        val quizId = 7
        val expectedStatus = QuizStatus.FAILED

        val flow = flowOf(expectedStatus)

        whenever(local.getQuizStatusFlow(quizId)).thenReturn(flow)

        val resultFlow = repo.observeQuizStatus(quizId)

        val firstValue = resultFlow.first()

        assertEquals(expectedStatus, firstValue)
        verify(local).getQuizStatusFlow(quizId)
    }


    @Test
    fun `clearCache calls local clear`() = runTest {
        repo.clearCache()

        verify(local).clear()
    }

    @Test
    fun `clears single quiz by id`() = runTest {
        repo.clearCachedQuiz(123)

        verify(local).clearQuiz(123)
    }
}
