package ru.fredboy.quizapp.data.android.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.quizapp.data.android.mapper.AnswerMapper
import ru.fredboy.quizapp.data.android.mapper.QuestionMapper
import ru.fredboy.quizapp.data.android.mapper.QuizMapper
import ru.fredboy.quizapp.data.android.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.android.source.local.room.QuizDao
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes

internal class LocalQuizDataSourceImpl(
    private val quizDao: QuizDao,
    private val quizzesMapper: QuizzesMapper,
    private val quizMapper: QuizMapper,
    private val questionMapper: QuestionMapper,
    private val answerMapper: AnswerMapper,
) : LocalQuizDataSource {

    override suspend fun getQuizzes(): Quizzes? {
        val quizList = withContext(Dispatchers.IO) {
            quizDao.getAllQuizzes()
                ?.takeIf { it.isNotEmpty() }
        } ?: return null

        return withContext(Dispatchers.Default) {
            quizzesMapper.map(quizList)
        }
    }

    override suspend fun getQuiz(id: Int): QuizDetails? {
        val quiz = withContext(Dispatchers.IO) {
            quizDao.getQuizWithQuestions(id)
        } ?: return null

        val questionsWithAnswers = withContext(Dispatchers.IO) {
            quiz.questions
                .mapNotNull { question ->
                    quizDao.getQuestionWithAnswers(question.id, id)
                }
                .takeIf { it.isNotEmpty() }
        } ?: return null

        return withContext(Dispatchers.Default) {
            quizMapper.map(quiz, questionsWithAnswers)
        }
    }

    override suspend fun saveQuizzes(quizzes: Quizzes) {
        val entities = withContext(Dispatchers.Default) {
            quizzesMapper.map(quizzes)
        }

        withContext(Dispatchers.IO) {
            quizDao.insertQuizzes(entities)
        }
    }

    override suspend fun saveQuiz(quiz: QuizDetails) {
        val quizEntity = quizMapper.map(quiz)
        val questionEntities = quiz.questions.map { question ->
            questionMapper.map(question, quiz.id)
        }
        val answerEntities = quiz.questions.flatMap { question ->
            question.answers.map { answer ->
                answerMapper.map(
                    answer = answer,
                    quizId = quiz.id,
                    questionId = question.id,
                    isCorrect = question.correctAnswerId == answer.id,
                )
            }
        }

        quizDao.updateQuiz(quizEntity)
        quizDao.insertQuestions(questionEntities)
        quizDao.insertAnswers(answerEntities)
    }
}
