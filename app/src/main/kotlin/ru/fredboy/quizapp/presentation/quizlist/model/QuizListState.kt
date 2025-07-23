package ru.fredboy.quizapp.presentation.quizlist.model

sealed interface QuizListState {

    data object Loading: QuizListState

    data class Error(
        val throwable: Throwable,
    ): QuizListState

    data class Success(
        val quizzes: List<QuizVo>,
    ): QuizListState

    data class Refreshing(
        val lastState: QuizListState,
    ): QuizListState
}
