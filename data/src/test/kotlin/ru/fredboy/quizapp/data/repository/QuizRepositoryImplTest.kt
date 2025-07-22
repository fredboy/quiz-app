package ru.fredboy.quizapp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto

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
    fun `clearCache calls local clear`() = runTest {
        repo.clearCache()

        verify(local).clear()
    }
}
