package ru.fredboy.quizapp.data.repository

import co.touchlab.kermit.Logger
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
        logger.i { "Requesting quiz list from server" }

        val quizzesDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuizzes()
        }

        val quizzes = withContext(Dispatchers.Default) {
            quizzesMapper.map(quizzesDto)
        }

        return quizzes
    }

    override suspend fun getQuizFromServer(id: Int): QuizDetails {
        logger.i { "Requesting quiz with id $id from server" }

        val quizDetailsDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuiz(id)
        }

        val quiz = withContext(Dispatchers.Default) {
            quizDetailsMapper.map(quizDetailsDto)
        }

        return quiz
    }

    override suspend fun getQuizzesFromCache(): Quizzes? {
        logger.d { "Attempting to read quiz list from cache" }

        val quizzes = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuizzes()
        }

        logger.d { quizzes?.let { "Cache hit (size ${quizzes.quizzes.size})" } ?: "Cache miss" }

        return quizzes
    }

    override suspend fun getQuizFromCache(id: Int): QuizDetails? {
        logger.d { "Attempting to read quiz with id $id from cache" }

        val quiz = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuiz(id)
        }

        logger.d { quiz?.let { "Cache hit" } ?: "Cache miss" }

        return quiz
    }

    override suspend fun saveQuizzesToCache(quizzes: Quizzes) {
        logger.d { "Saving quiz list (size ${quizzes.quizzes.size}) to cache" }

        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuizzes(quizzes)
        }
    }

    override suspend fun saveQuizToCache(quiz: QuizDetails) {
        logger.d { "Saving quiz details with id ${quiz.id} to cache" }

        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuiz(quiz)
        }
    }

    override suspend fun clearCache() {
        logger.d { "Clear local cache" }

        withContext(Dispatchers.IO) {
            localQuizDataSource.clear()
        }
    }

    companion object {
        private val logger = Logger.withTag("QuizRepositoryImpl")
    }
}
