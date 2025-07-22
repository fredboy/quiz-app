package ru.fredboy.quizapp.domain.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizListUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(): Quizzes = coroutineScope {
        val cachedQuizzes = quizRepository.getQuizzesFromCache()
            ?.takeIf { (_, timestamp) ->
                System.currentTimeMillis() - timestamp < QUIZ_CACHE_TTL_MS
            }

        return@coroutineScope cachedQuizzes ?: quizRepository.getQuizzesFromServer()
            .also { quizzes ->
                launch {
                    quizRepository.saveQuizzesToCache(quizzes)
                }
            }
    }

    companion object {
        private const val QUIZ_CACHE_TTL_MS = 60 * 60 * 1000L
    }
}
