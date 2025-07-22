package ru.fredboy.quizapp.view.quizlist

import ru.fredboy.quizapp.domain.model.Quizzes
import ru.fredboy.quizapp.view.quizlist.model.QuizVo

sealed interface QuizListState {

    data object Loading: QuizListState

    data class Error(val throwable: Throwable): QuizListState

    data class Success(
        val quizzes: List<QuizVo>,
    ): QuizListState
}
