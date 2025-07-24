package ru.fredboy.quizapp.data.android.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.fredboy.quizapp.domain.model.QuizStatus

@Entity(
    tableName = "quiz_status",
    indices = [
        Index("quiz_id"),
    ],
)
internal data class QuizStatusEntity(
    @PrimaryKey @ColumnInfo(name = "quiz_id") val quizId: Int,
    @ColumnInfo(name = "status") val status: QuizStatus,
)
