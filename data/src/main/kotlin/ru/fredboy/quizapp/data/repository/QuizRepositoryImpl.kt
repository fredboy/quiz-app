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

    override suspend fun getQuizzes(): Quizzes {
        val localQuizzes = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuizzes()
        }

        if (localQuizzes != null) {
            return localQuizzes
        }

        val quizzesDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuizzes()
        }

        val quizzes = withContext(Dispatchers.Default) {
            quizzesMapper.map(quizzesDto)
        }

        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuizzes(quizzes)
        }

        return quizzes
    }

    override suspend fun getQuiz(id: Int): QuizDetails {
        val localQuiz = withContext(Dispatchers.IO) {
            localQuizDataSource.getQuiz(id)
        }

        if (localQuiz != null) {
            return localQuiz
        }

        val quizDetailsDto = withContext(Dispatchers.IO) {
            remoteQuizDataSource.getQuiz(id)
        }

        val quiz = withContext(Dispatchers.Default) {
            quizDetailsMapper.map(quizDetailsDto)
        }

        withContext(Dispatchers.IO) {
            localQuizDataSource.saveQuiz(quiz)
        }

        return quiz
    }
}
