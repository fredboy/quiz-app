package ru.fredboy.quizapp.data.android.mapper

import ru.fredboy.quizapp.data.android.model.QuizStatusEntity
import ru.fredboy.quizapp.domain.model.QuizStatus

internal class QuizStatusMapper {

    fun map(quizId: Int, quizStatus: QuizStatus): QuizStatusEntity {
        return QuizStatusEntity(
            quizId = quizId,
            status = quizStatus,
        )
    }

    fun map(quizStatusEntity: QuizStatusEntity): QuizStatus {
        return quizStatusEntity.status
    }
}
