package ru.fredboy.quizapp.domain.usecase

import ru.fredboy.quizapp.domain.repository.QuizRepository

class InvalidateCachedQuizUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(quizId: Int) {
        quizRepository.clearCachedQuiz(quizId)
    }
}
