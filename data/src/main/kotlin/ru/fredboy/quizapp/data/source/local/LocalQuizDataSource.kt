package ru.fredboy.quizapp.data.source.local

import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes

interface LocalQuizDataSource {

    suspend fun getQuizzes(): Quizzes?

    suspend fun getQuiz(id: Int): QuizDetails?

    suspend fun saveQuizzes(quizzes: Quizzes)

    suspend fun saveQuiz(quiz: QuizDetails)

    suspend fun clear()
}
