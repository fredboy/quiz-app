package ru.fredboy.quizapp.data.source.remote

import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto

internal class QuizDataSource(
    private val quizApiService: QuizApiService,
) {

    suspend fun getQuizzes(): QuizzesDto {
        return quizApiService.getQuizzes()
    }

    suspend fun getQuiz(id: Int): QuizDetailsDto {
        return quizApiService.getQuiz(id)
    }
}
