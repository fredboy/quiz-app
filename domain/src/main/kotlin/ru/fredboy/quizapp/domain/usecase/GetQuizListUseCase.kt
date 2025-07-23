package ru.fredboy.quizapp.domain.usecase

import co.touchlab.kermit.Logger
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GetQuizListUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(): Quizzes {
        val cachedQuizzes = try {
            quizRepository.getQuizzesFromCache()
                ?.takeIf { (_, timestamp) ->
                    Clock.System.now().epochSeconds - timestamp < QUIZ_CACHE_TTL_SEC
                }
        } catch (e: Exception) {
            logger.w(e) { "Exception when reading quizzes from cache" }
            null
        }

        return cachedQuizzes ?: quizRepository.getQuizzesFromServer()
            .also { quizzes ->
                try {
                    quizRepository.saveQuizzesToCache(quizzes)
                } catch (e: Exception) {
                    logger.w(e) { "Couldn't cache quizzes" }
                }
            }
    }

    companion object {
        private const val QUIZ_CACHE_TTL_SEC = 60 * 1000L

        private val logger = Logger.withTag("GetQuizListUseCase")
    }
}
