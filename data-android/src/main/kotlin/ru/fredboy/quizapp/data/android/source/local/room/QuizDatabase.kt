package ru.fredboy.quizapp.data.android.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.fredboy.quizapp.data.android.converter.QuizStatusConverter
import ru.fredboy.quizapp.data.android.model.AnswerEntity
import ru.fredboy.quizapp.data.android.model.QuestionEntity
import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.data.android.model.QuizStatusEntity

@Database(
    entities = [
        AnswerEntity::class,
        QuestionEntity::class,
        QuizEntity::class,
        QuizStatusEntity::class,
    ],
    version = 1,
)
@TypeConverters(QuizStatusConverter::class)
internal abstract class QuizDatabase : RoomDatabase() {

    abstract fun quizDao(): QuizDao
}
