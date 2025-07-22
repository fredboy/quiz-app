package ru.fredboy.quizapp.data.android.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "questions",
    primaryKeys = [ "id", "quiz_id" ],
    foreignKeys = [
        ForeignKey(
            entity = QuizEntity::class,
            parentColumns = [ "id" ],
            childColumns = [ "quiz_id" ],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index("quiz_id"),
    ],
)
internal data class QuestionEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "quiz_id") val quizId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
)
