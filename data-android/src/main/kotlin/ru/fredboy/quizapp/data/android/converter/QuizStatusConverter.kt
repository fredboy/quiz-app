package ru.fredboy.quizapp.data.android.converter

import androidx.room.TypeConverter
import ru.fredboy.quizapp.domain.model.QuizStatus

internal class QuizStatusConverter {

    @TypeConverter
    fun fromStatus(status: QuizStatus): String {
        return status.name
    }

    @TypeConverter
    fun toStatus(name: String): QuizStatus {
        return QuizStatus.valueOf(name)
    }
}
