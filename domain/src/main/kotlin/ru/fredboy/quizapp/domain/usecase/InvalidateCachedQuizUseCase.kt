package ru.fredboy.quizapp.domain.usecase

import co.touchlab.kermit.Logger
import ru.fredboy.quizapp.domain.repository.QuizRepository

class InvalidateCachedQuizUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(quizId: Int) {
        try {
            quizRepository.clearCachedQuiz(quizId)
        } catch (e: Exception) {
            logger.w(e) { "Couldn't delete quiz with id $quizId from cache" }
        }
    }

    companion object {
        private val logger = Logger.withTag("InvalidateCachedQuizUseCase")
    }
}
