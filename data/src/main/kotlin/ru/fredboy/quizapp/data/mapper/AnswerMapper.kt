package ru.fredboy.quizapp.data.mapper

import ru.fredboy.quizapp.data.model.AnswerDto
import ru.fredboy.quizapp.domain.model.Answer

internal class AnswerMapper {

    fun map(answerDto: AnswerDto): Answer {
        return Answer(
            id = answerDto.id,
            text = answerDto.text,
        )
    }
}
