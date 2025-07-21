package ru.fredboy.quizapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository

internal class QuizRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource,
    private val quizzesMapper: QuizzesMapper,
    private val quizDetailsMapper: QuizDetailsMapper,
) : QuizRepository {

    override suspend fun getQuizzes(): Quizzes {
        val quizzesDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuizzes()
        }

        return withContext(Dispatchers.Default) {
            quizzesMapper.map(quizzesDto)
        }
    }

    override suspend fun getQuiz(id: Int): QuizDetails {
        val quizDetailsDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuiz(id)
        }

        return withContext(Dispatchers.Default) {
            quizDetailsMapper.map(quizDetailsDto)
        }
    }
}
