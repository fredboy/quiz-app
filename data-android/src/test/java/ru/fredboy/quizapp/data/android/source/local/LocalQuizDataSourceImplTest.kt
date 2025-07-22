package ru.fredboy.quizapp.data.android.source.local

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.*
import org.mockito.junit.jupiter.MockitoExtension
import ru.fredboy.quizapp.data.android.mapper.*
import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.data.android.model.relation.QuestionWithAnswers
import ru.fredboy.quizapp.data.android.model.relation.QuizWithQuestions
import ru.fredboy.quizapp.data.android.source.local.room.QuizDao
import ru.fredboy.quizapp.domain.model.*

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class)
class LocalQuizDataSourceImplTest {

    private lateinit var dataSource: LocalQuizDataSourceImpl

    private val quizDao = mock<QuizDao>()
    private val quizzesMapper = mock<QuizzesMapper>()
    private val quizMapper = mock<QuizMapper>()
    private val questionMapper = mock<QuestionMapper>()
    private val answerMapper = mock<AnswerMapper>()

    @BeforeEach
    fun setup() {
        dataSource = LocalQuizDataSourceImpl(
            quizDao,
            quizzesMapper,
            quizMapper,
            questionMapper,
            answerMapper
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
        val entities = listOf(mock<QuizEntity>())
        val quizzes = mock<Quizzes>()

        whenever(quizDao.getAllQuizzes()).thenReturn(entities)
        whenever(quizzesMapper.map(entities)).thenReturn(quizzes)

        val result = dataSource.getQuizzes()

        assert(result === quizzes)
        verify(quizzesMapper).map(entities)
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
    fun `clear deletes all data`() = runTest {
        dataSource.clear()

        verify(quizDao).clearQuizzes()
    }
}
