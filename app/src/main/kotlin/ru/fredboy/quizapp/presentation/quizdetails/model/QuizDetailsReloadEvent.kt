package ru.fredboy.quizapp.presentation.quizdetails.model

sealed interface QuizDetailsReloadEvent {

    data object Initial : QuizDetailsReloadEvent

    data object Reload : QuizDetailsReloadEvent
}
