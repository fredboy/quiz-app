package ru.fredboy.quizapp.domain.usecase

import ru.fredboy.quizapp.domain.repository.QuizRepository

class InvalidateCachedQuizzesUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke() {
        quizRepository.clearCache()
    }
}
