package ru.fredboy.quizapp.data.mapper

import ru.fredboy.quizapp.data.model.QuizzesDto
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus

internal class QuizListMapper {

    fun map(quizzesDto: QuizzesDto): List<Quiz> {
        return quizzesDto.quizzes.map { quizDto ->
            Quiz(
                id = quizDto.id,
                title = quizDto.title,
                imageUrl = quizDto.imageUrl,
                status = QuizStatus.UNTAKEN,
            )
        }
    }
}
