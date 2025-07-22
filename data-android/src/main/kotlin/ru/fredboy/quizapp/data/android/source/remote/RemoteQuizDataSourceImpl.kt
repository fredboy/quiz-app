package ru.fredboy.quizapp.data.android.source.remote

import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.data.model.QuizzesDto
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource

internal class RemoteQuizDataSourceImpl(
    private val quizApiService: QuizApiService,
) : RemoteQuizDataSource {

    override suspend fun getQuizzes(): QuizzesDto {
        return quizApiService.getQuizzes()
    }

    override suspend fun getQuiz(id: Int): QuizDetailsDto {
        return quizApiService.getQuiz(id)
    }
}
