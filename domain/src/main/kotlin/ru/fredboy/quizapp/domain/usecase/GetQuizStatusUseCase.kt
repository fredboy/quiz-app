package ru.fredboy.quizapp.domain.usecase

import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizStatusUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(quizId: Int): QuizStatus? {
        return quizRepository.getQuizStatusFromCache(quizId)
    }
}
