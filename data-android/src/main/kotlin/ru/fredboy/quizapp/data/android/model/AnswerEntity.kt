package ru.fredboy.quizapp.data.android.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "answers",
    primaryKeys = ["id", "question_id", "quiz_id" ],
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = [ "id", "quiz_id" ],
            childColumns = [ "question_id", "quiz_id" ],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("question_id"),
        Index("quiz_id"),
    ],
)
internal data class AnswerEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "question_id") val questionId: Int,
    @ColumnInfo(name = "quiz_id") val quizId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
)
