package ru.fredboy.quizapp.data.mapper

import ru.fredboy.quizapp.data.model.QuizzesDto
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.domain.model.Quizzes

internal class QuizzesMapper {

    fun map(quizzesDto: QuizzesDto): Quizzes {
        val quizList = quizzesDto.quizzes.map { quizDto ->
            Quiz(
                id = quizDto.id,
                title = quizDto.title,
                imageUrl = quizDto.imageUrl,
                passingScore = quizDto.passingScore,
                numberOfQuestions = quizDto.numberOfQuestions,
            )
        }

        return Quizzes(
            quizzes = quizList,
            timestamp = quizzesDto.timestamp,
        )
    }
}
