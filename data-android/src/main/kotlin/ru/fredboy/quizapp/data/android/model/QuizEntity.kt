package ru.fredboy.quizapp.data.android.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "quizzes",
    primaryKeys = [ "id" ],
)
internal data class QuizEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo(name = "passing_score") val passingScore: Int,
    @ColumnInfo(name = "number_of_questions") val numberOfQuestions: Int,
)
