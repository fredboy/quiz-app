package ru.fredboy.quizapp.data.mapper

import ru.fredboy.quizapp.data.model.QuizDetailsDto
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.domain.model.QuizStatus

internal class QuizDetailsMapper(
    private val questionMapper: QuestionMapper,
) {

    fun map(quizDetailsDto: QuizDetailsDto): QuizDetails {
        return QuizDetails(
            id = quizDetailsDto.id,
            title = quizDetailsDto.title,
            imageUrl = quizDetailsDto.imageUrl,
            passingScore = quizDetailsDto.passingScore,
            questions = quizDetailsDto.questions.map(questionMapper::map),
        )
    }
}
