package ru.fredboy.quizapp.domain.repository

import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes

interface QuizRepository {

    suspend fun getQuizzesFromServer(): Quizzes

    suspend fun getQuizFromServer(id: Int): QuizDetails

    suspend fun getQuizzesFromCache(): Quizzes?

    suspend fun getQuizFromCache(id: Int): QuizDetails?

    suspend fun saveQuizToCache(quiz: QuizDetails)

    suspend fun saveQuizzesToCache(quizzes: Quizzes)

    suspend fun clearCache()
}
