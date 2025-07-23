package ru.fredboy.quizapp.presentation.quizlist.model

sealed interface QuizListReloadEvent {

    data object Initial : QuizListReloadEvent

    data object Reload : QuizListReloadEvent

    data class Refresh(
        val backgroundState: QuizListState,
    ) : QuizListReloadEvent
}
