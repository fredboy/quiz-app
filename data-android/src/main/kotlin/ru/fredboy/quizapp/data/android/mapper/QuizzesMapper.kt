package ru.fredboy.quizapp.data.android.mapper

import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.Quizzes

internal class QuizzesMapper {

    fun map(quizzes: Quizzes): List<QuizEntity> {
        return quizzes.quizzes.map { quiz ->
            QuizEntity(
                id = quiz.id,
                title = quiz.title,
                description = quiz.description,
                imageUrl = quiz.imageUrl,
                passingScore = quiz.passingScore,
                numberOfQuestions = quiz.numberOfQuestions,
            )
        }
    }

    fun map(quizzes: List<QuizEntity>, lastUpdateTimestamp: Long): Quizzes {
        return Quizzes(
            quizzes = quizzes.map { quiz ->
                Quiz(
                    id = quiz.id,
                    title = quiz.title,
                    description = quiz.description,
                    imageUrl = quiz.imageUrl,
                    passingScore = quiz.passingScore,
                    numberOfQuestions = quiz.numberOfQuestions,
                )
            },
            timestamp = lastUpdateTimestamp,
        )
    }
}
