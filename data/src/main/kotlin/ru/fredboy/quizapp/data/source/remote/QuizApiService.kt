package ru.fredboy.quizapp.data.source.remote

import retrofit2.http.GET
import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto

internal interface QuizApiService {

    @GET("/quizzes")
    suspend fun getQuizzes(): QuizzesDto

    @GET("/quizzes/{id}")
    suspend fun getQuiz(id: Int): QuizDetailsDto
}
