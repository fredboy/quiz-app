package ru.fredboy.quizapp.data.source.remote

import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto

interface RemoteQuizDataSource {

    suspend fun getQuizzes(): QuizzesDto

    suspend fun getQuiz(id: Int): QuizDetailsDto
}
