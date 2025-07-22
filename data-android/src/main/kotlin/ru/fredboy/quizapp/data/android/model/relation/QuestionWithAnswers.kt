package ru.fredboy.quizapp.data.android.model.relation

import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.data.android.model.QuestionEntity

internal data class QuestionWithAnswers(
    val question: QuestionEntity,
    val answers: List<AnswerEntity>,
)
