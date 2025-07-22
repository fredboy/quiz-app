package ru.fredboy.quizapp.domain.usecase

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(id: Int): QuizDetails = coroutineScope {
        val cachedQuiz = quizRepository.getQuizFromCache(id)

        return@coroutineScope cachedQuiz ?: quizRepository.getQuizFromServer(id).also { quiz ->
            launch {
                quizRepository.saveQuizToCache(quiz)
            }
        }
    }
}
