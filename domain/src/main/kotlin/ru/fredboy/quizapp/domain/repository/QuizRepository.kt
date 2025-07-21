package ru.fredboy.quizapp.domain.repository

import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizDetails

interface QuizRepository {

    suspend fun getQuizzes(): List<Quiz>

    suspend fun getQuiz(id: Int): QuizDetails
}
