package ru.fredboy.quizapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizListMapper
import ru.fredboy.quizapp.data.source.remote.QuizDataSource
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.repository.QuizRepository

internal class QuizRepositoryImpl(
    private val quizDataSource: QuizDataSource,
    private val quizListMapper: QuizListMapper,
    private val quizDetailsMapper: QuizDetailsMapper,
) : QuizRepository {

    override suspend fun getQuizzes(): List<Quiz> {
        val quizzesDto = withContext(Dispatchers.IO) {
            quizDataSource.getQuizzes()
        }

        return withContext(Dispatchers.Default) {
            quizListMapper.map(quizzesDto)
        }
    }

    override suspend fun getQuiz(id: Int): QuizDetails {
        val quizDetailsDto = withContext(Dispatchers.IO) {
            quizDataSource.getQuiz(id)
        }

        return withContext(Dispatchers.Default) {
            quizDetailsMapper.map(quizDetailsDto)
        }
    }
}
