package ru.fredboy.quizapp.domain.usecase

import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.repository.QuizRepository

class GetQuizUseCase(
    private val quizRepository: QuizRepository,
) {

    suspend operator fun invoke(id: Int): QuizDetails {
        return quizRepository.getQuiz(id)
    }
}
