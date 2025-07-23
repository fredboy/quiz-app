package ru.fredboy.quizapp.data.android.mapper

import ru.fredboy.quizapp.data.android.model.QuizEntity
import ru.fredboy.quizapp.data.android.model.relation.QuestionWithAnswers
import ru.fredboy.quizapp.data.android.model.relation.QuizWithQuestions
import ru.fredboy.quizapp.domain.model.QuizDetails

internal class QuizMapper(
    private val questionMapper: QuestionMapper,
) {

    fun map(quiz: QuizDetails): QuizEntity {
        return QuizEntity(
            id = quiz.id,
            title = quiz.title,
            description = quiz.description,
            imageUrl = quiz.imageUrl,
            passingScore = quiz.passingScore,
            numberOfQuestions = quiz.questions.size,
        )
    }

    fun map(
        quizWithQuestions: QuizWithQuestions,
        questionsWithAnswers: List<QuestionWithAnswers>,
    ): QuizDetails {
        return QuizDetails(
            id = quizWithQuestions.quiz.id,
            title = quizWithQuestions.quiz.title,
            description = quizWithQuestions.quiz.description,
            imageUrl = quizWithQuestions.quiz.imageUrl,
            passingScore = quizWithQuestions.quiz.passingScore,
            questions = questionsWithAnswers.map { questionMapper.map(it) },
        )
    }
}
