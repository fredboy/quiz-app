package ru.fredboy.quizapp.data.android.mapper

import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.relation.QuestionWithAnswers
import ru.fredboy.quizapp.domain.model.Question

internal class QuestionMapper(
    private val answerMapper: AnswerMapper,
) {

    fun map(question: Question, quizId: Int): QuestionEntity {
        return QuestionEntity(
            id = question.id,
            quizId = quizId,
            text = question.text,
            imageUrl = question.imageUrl,
        )
    }

    fun map(questionWithAnswers: QuestionWithAnswers): Question {
        return Question(
            id = questionWithAnswers.question.id,
            text = questionWithAnswers.question.text,
            answers = questionWithAnswers.answers.map { answer ->
                answerMapper.map(answer)
            },
            correctAnswerId = questionWithAnswers.answers.first { it.isCorrect }.id,
            imageUrl = questionWithAnswers.question.imageUrl,
        )
    }
}
