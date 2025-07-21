package ru.fredboy.quizapp.data.android.mapper

import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.domain.model.Answer

internal class AnswerMapper {

    fun map(answer: Answer, quizId: Int, questionId: Int, isCorrect: Boolean): AnswerEntity {
        return AnswerEntity(
            id = answer.id,
            quizId = quizId,
            questionId = questionId,
            text = answer.text,
            isCorrect = isCorrect,
        )
    }

    fun map(answer: AnswerEntity): Answer {
        return Answer(
            id = answer.id,
            text = answer.text,
        )
    }
}
