package ru.fredboy.quizapp.presentation.quizlist.model

sealed interface QuizListReloadEvent {

    data class Initial(
        val lastSuccessState: QuizListState.Success?,
    ) : QuizListReloadEvent

    data object Reload : QuizListReloadEvent

    data class Refresh(
        val backgroundState: QuizListState,
    ) : QuizListReloadEvent
}
