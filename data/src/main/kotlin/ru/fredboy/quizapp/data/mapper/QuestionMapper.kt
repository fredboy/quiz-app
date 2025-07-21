package ru.fredboy.quizapp.data.mapper

import ru.fredboy.quizapp.data.model.QuestionDto
import ru.fredboy.quizapp.domain.model.Question

internal class QuestionMapper(
    private val answerMapper: AnswerMapper,
) {

    fun map(questionDto: QuestionDto): Question {
        return Question(
            id = questionDto.id,
            text = questionDto.text,
            answers = questionDto.answers.map(answerMapper::map),
            correctAnswerId = questionDto.correctAnswerId,
        )
    }
}
