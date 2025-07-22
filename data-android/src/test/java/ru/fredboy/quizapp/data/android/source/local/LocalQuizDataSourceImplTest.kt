package ru.fredboy.quizapp.data.android.source.local

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import ru.fredboy.quizapp.data.android.mapper.AnswerMapper
import ru.fredboy.quizapp.data.android.mapper.QuestionMapper
import ru.fredboy.quizapp.data.android.mapper.QuizMapper
import ru.fredboy.quizapp.data.android.mapper.QuizStatusMapper
import ru.fredboy.quizapp.data.android.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.data.android.model.QuizStatusEntity
import ru.fredboy.quizapp.data.android.model.relation.QuestionWithAnswers
import ru.fredboy.quizapp.data.android.model.relation.QuizWithQuestions
import ru.fredboy.quizapp.data.android.source.local.prefs.QuizCachePrefsDataStore
import ru.fredboy.quizapp.data.android.source.local.room.QuizDao
import ru.fredboy.quizapp.domain.model.Answer
import ru.fredboy.quizapp.domain.model.Question
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.model.Quizzes
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
@ExtendWith(MockitoExtension::class)
class LocalQuizDataSourceImplTest {

    private lateinit var dataSource: LocalQuizDataSourceImpl

    private val quizDao = mock<QuizDao>()
    private val quizzesMapper = mock<QuizzesMapper>()
    private val quizMapper = mock<QuizMapper>()
    private val questionMapper = mock<QuestionMapper>()
    private val answerMapper = mock<AnswerMapper>()
    private val quizStatusMapper = mock<QuizStatusMapper>()
    private val quizCachePrefsDataStore = mock<QuizCachePrefsDataStore>()

    @BeforeEach
    fun setup() {
        dataSource = LocalQuizDataSourceImpl(
            quizDao = quizDao,
            quizzesMapper = quizzesMapper,
            quizMapper = quizMapper,
            questionMapper = questionMapper,
            answerMapper = answerMapper,
            quizStatusMapper = quizStatusMapper,
            quizCachePrefsDataStore = quizCachePrefsDataStore,
        )
    }

    @Test
    fun `getQuizzes returns null if dao returns empty`() = runTest {
        whenever(quizDao.getAllQuizzes()).thenReturn(emptyList())

        val result = dataSource.getQuizzes()

        assert(result == null)
    }

    @Test
    fun `getQuizzes maps quizzes if dao returns list`() = runTest {
        val timestamp = Clock.System.now().epochSeconds
        val entities = listOf(mock<QuizEntity>())
        val quizzes = mock<Quizzes>()

        whenever(quizDao.getAllQuizzes()).thenReturn(entities)
        whenever(quizzesMapper.map(entities, timestamp)).thenReturn(quizzes)
        whenever(quizCachePrefsDataStore.getLastUpdateTimestamp()).thenReturn(timestamp)

        val result = dataSource.getQuizzes()

        assert(result === quizzes)
        verify(quizzesMapper).map(entities, timestamp)
    }

    @Test
    fun `getQuiz returns null if quiz not found`() = runTest {
        whenever(quizDao.getQuizWithQuestions(1)).thenReturn(null)

        val result = dataSource.getQuiz(1)

        assert(result == null)
    }

    @Test
    fun `getQuiz returns null if no questions with answers`() = runTest {
        val quiz = mock<QuizWithQuestions>()
        whenever(quizDao.getQuizWithQuestions(1)).thenReturn(quiz)
        whenever(quiz.questions).thenReturn(listOf(mock()))
        whenever(quizDao.getQuestionWithAnswers(any(), any())).thenReturn(null)

        val result = dataSource.getQuiz(1)

        assert(result == null)
    }

    @Test
    fun `getQuiz maps and returns QuizDetails when data is available`() = runTest {
        val quiz = mock<QuizWithQuestions>()
        val questionId = 123
        val question = mock<QuestionEntity>().apply {
            whenever(this.id).thenReturn(questionId)
        }
        val questionsList = listOf(question)
        val questionWithAnswers = mock<QuestionWithAnswers>()
        val expected = mock<QuizDetails>()

        whenever(quizDao.getQuizWithQuestions(1)).thenReturn(quiz)
        whenever(quiz.questions).thenReturn(questionsList)
        whenever(quizDao.getQuestionWithAnswers(questionId, 1)).thenReturn(questionWithAnswers)
        whenever(quizMapper.map(quiz, listOf(questionWithAnswers))).thenReturn(expected)

        val result = dataSource.getQuiz(1)

        assert(result === expected)
    }

    @Test
    fun `saveQuizzes maps and inserts`() = runTest {
        val quizzes = mock<Quizzes>()
        val entities = listOf(mock<QuizEntity>())

        whenever(quizzesMapper.map(quizzes)).thenReturn(entities)

        dataSource.saveQuizzes(quizzes)

        verify(quizzesMapper).map(quizzes)
        verify(quizDao).insertQuizzes(entities)
    }

    @Test
    fun `saveQuiz maps and updates dao`() = runTest {
        val quiz = mock<QuizDetails>()
        val quizEntity = mock<QuizEntity>()
        val questionEntity = mock<QuestionEntity>()
        val answerEntity = mock<AnswerEntity>()

        val answer = mock<Answer>().apply {
            whenever(id).thenReturn(20)
        }

        val question = mock<Question>().apply {
            whenever(id).thenReturn(10)
            whenever(correctAnswerId).thenReturn(20)
            whenever(answers).thenReturn(listOf(answer))
        }

        whenever(quiz.id).thenReturn(1)
        whenever(quiz.questions).thenReturn(listOf(question))

        whenever(quizMapper.map(quiz)).thenReturn(quizEntity)
        whenever(questionMapper.map(question, 1)).thenReturn(questionEntity)
        whenever(answerMapper.map(answer, 1, 10, true)).thenReturn(answerEntity)

        dataSource.saveQuiz(quiz)

        verify(quizDao).updateQuiz(quizEntity)
        verify(quizDao).insertQuestions(listOf(questionEntity))
        verify(quizDao).insertAnswers(listOf(answerEntity))
    }

    @Test
    fun `saveQuizStatus maps and inserts quiz status`() = runTest {
        val quizId = 42
        val status = mock<QuizStatus>()
        val entity = mock<QuizStatusEntity>()

        whenever(quizStatusMapper.map(quizId, status)).thenReturn(entity)

        dataSource.saveQuizStatus(quizId, status)

        verify(quizDao).insertStatus(entity)
    }

    @Test
    fun `getQuizStatus returns mapped status if present`() = runTest {
        val quizId = 99
        val statusEntity = mock<QuizStatusEntity>()
        val status = mock<QuizStatus>()

        val flow = flowOf(statusEntity)

        whenever(quizDao.observeStatus(quizId)).thenReturn(flow)
        whenever(quizStatusMapper.map(statusEntity)).thenReturn(status)

        val result = dataSource.getQuizStatus(quizId)

        assertEquals(status, result)
    }

    @Test
    fun `getQuizStatusFlow maps status entity to domain`() = runTest {
        val quizId = 123
        val statusEntity = mock<QuizStatusEntity>()
        val status = mock<QuizStatus>()

        val flow = flowOf(statusEntity)

        whenever(quizDao.observeStatus(quizId)).thenReturn(flow)
        whenever(quizStatusMapper.map(statusEntity)).thenReturn(status)

        val result = dataSource.getQuizStatusFlow(quizId).first()

        assertEquals(status, result)
    }

    @Test
    fun `clear deletes all data`() = runTest {
        dataSource.clear()

        verify(quizDao).clearQuizzes()
    }
}
