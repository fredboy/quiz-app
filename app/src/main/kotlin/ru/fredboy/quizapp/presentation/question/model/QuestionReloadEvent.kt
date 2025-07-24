package ru.fredboy.quizapp.presentation.question.model

sealed interface QuestionReloadEvent {

    data object Initial : QuestionReloadEvent

    data object Reload : QuestionReloadEvent
}
