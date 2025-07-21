package ru.fredboy.quizapp.domain.repository

import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes

interface QuizRepository {

    suspend fun getQuizzes(): Quizzes

    suspend fun getQuiz(id: Int): QuizDetails
}
