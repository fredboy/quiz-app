package ru.fredboy.quizapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.repository.QuizRepository

class ObserveQuizStatusUseCase(
    private val quizRepository: QuizRepository,
) {

    operator fun invoke(quizId: Int): Flow<QuizStatus?> {
        return quizRepository.observeQuizStatus(quizId)
    }
}
