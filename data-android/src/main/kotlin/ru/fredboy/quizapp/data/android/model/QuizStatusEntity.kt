package ru.fredboy.quizapp.data.android.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ru.fredboy.quizapp.domain.model.QuizStatus

@Entity(
    tableName = "quiz_status",
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
internal data class QuizStatusEntity(
    @PrimaryKey @ColumnInfo(name = "quiz_id") val quizId: Int,
    @ColumnInfo(name = "status") val status: QuizStatus,
)
