package ru.fredboy.quizapp.data.android.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto

internal interface QuizApiService {

    @GET("/quizzes")
    suspend fun getQuizzes(): QuizzesDto

    @GET("/quizzes/{id}")
    suspend fun getQuiz(@Path("id") id: Int): QuizDetailsDto
}
