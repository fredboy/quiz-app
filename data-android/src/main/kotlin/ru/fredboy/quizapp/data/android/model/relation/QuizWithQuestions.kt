package ru.fredboy.quizapp.data.android.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.QuizEntity

internal data class QuizWithQuestions(
    @Embedded val quiz: QuizEntity,
    @Relation(parentColumn = "id", entityColumn = "quiz_id") val questions: List<QuestionEntity>,
)
