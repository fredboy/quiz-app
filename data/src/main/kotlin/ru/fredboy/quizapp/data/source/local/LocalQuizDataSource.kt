package ru.fredboy.quizapp.data.source.local

import kotlinx.coroutines.flow.Flow
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.model.Quizzes

interface LocalQuizDataSource {

    suspend fun getQuizzes(): Quizzes?

    suspend fun getQuiz(id: Int): QuizDetails?

    suspend fun saveQuizzes(quizzes: Quizzes)

    suspend fun saveQuiz(quiz: QuizDetails)

    suspend fun saveQuizStatus(quizId: Int, status: QuizStatus)

    suspend fun getQuizStatus(quizId: Int): QuizStatus?

    fun getQuizStatusFlow(quizId: Int): Flow<QuizStatus?>

    suspend fun clear()

    suspend fun clearQuiz(quizId: Int)
}
