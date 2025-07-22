package ru.fredboy.quizapp.domain.usecase

import co.touchlab.kermit.Logger
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(id: Int): QuizDetails {
        val cachedQuiz = quizRepository.getQuizFromCache(id)

        return cachedQuiz ?: quizRepository.getQuizFromServer(id).also { quiz ->
            try {
                quizRepository.saveQuizToCache(quiz)
            } catch (e: Exception) {
                logger.w(e) { "Couldn't cache quiz. id: ${quiz.id}" }
            }
        }
    }

    companion object {
        private val logger = Logger.withTag("GetQuizUseCase")
    }
}
