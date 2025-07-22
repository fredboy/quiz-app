package ru.fredboy.quizapp.domain.usecase

import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizListUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(): Quizzes {
        return quizRepository.getQuizzes()
    }
}
