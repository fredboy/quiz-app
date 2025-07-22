package ru.fredboy.quizapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository

internal class QuizRepositoryImpl(
    private val remoteQuizDataSource: RemoteQuizDataSource,
    private val localQuizDataSource: LocalQuizDataSource,
    private val quizzesMapper: QuizzesMapper,
    private val quizDetailsMapper: QuizDetailsMapper,
) : QuizRepository {

    override suspend fun getQuizzesFromServer(): Quizzes {
        val quizzesDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuizzes()
        }

        val quizzes = withContext(Dispatchers.Default) {
            quizzesMapper.map(quizzesDto)
        }

        return quizzes
    }

    override suspend fun getQuizFromServer(id: Int): QuizDetails {
        val quizDetailsDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuiz(id)
        }

        val quiz = withContext(Dispatchers.Default) {
            quizDetailsMapper.map(quizDetailsDto)
        }

        return quiz
    }

    override suspend fun getQuizzesFromCache(): Quizzes? {
        val quizzes = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuizzes()
        }

        return quizzes
    }

    override suspend fun getQuizFromCache(id: Int): QuizDetails? {
        val quiz = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuiz(id)
        }

        return quiz
    }

    override suspend fun saveQuizzesToCache(quizzes: Quizzes) {
        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuizzes(quizzes)
        }
    }

    override suspend fun saveQuizToCache(quiz: QuizDetails) {
        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuiz(quiz)
        }
    }

    override suspend fun clearCache() {
        withContext(Dispatchers.IO) {
            localQuizDataSource.clear()
        }
    }
}
