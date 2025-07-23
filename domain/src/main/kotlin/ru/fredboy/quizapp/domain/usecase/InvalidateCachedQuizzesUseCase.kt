package ru.fredboy.quizapp.domain.usecase

import co.touchlab.kermit.Logger
import ru.fredboy.quizapp.domain.repository.QuizRepository

class InvalidateCachedQuizzesUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke() {
        try {
            quizRepository.clearCache()
        } catch (e: Exception) {
            logger.w(e) { "Couldn't delete quizzes from cache" }
        }
    }

    companion object {
        private val logger = Logger.withTag("InvalidateCachedQuizzesUseCase")
    }
}
