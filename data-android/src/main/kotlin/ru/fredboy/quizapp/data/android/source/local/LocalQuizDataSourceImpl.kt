package ru.fredboy.quizapp.data.android.source.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
) : LocalQuizDataSource {

    override suspend fun getQuizzes(): Quizzes {
        val quizList = withContext(Dispatchers.IO) {
            quizDao.getAllQuizzes()
        }

        return withContext(Dispatchers.Default) {
            quizzesMapper.map(quizList)
        }
    }

    override suspend fun getQuiz(id: Int): QuizDetails {
        val quiz = withContext(Dispatchers.IO) {
            quizDao.getQuizWithQuestions(id)
        }

        val questionsWithAnswers = withContext(Dispatchers.IO) {
            quiz.questions.map { question ->
                quizDao.getQuestionWithAnswers(question.id, id)
            }
        }

        return withContext(Dispatchers.Default) {
            quizMapper.map(quiz, questionsWithAnswers)
        }
    }
}
