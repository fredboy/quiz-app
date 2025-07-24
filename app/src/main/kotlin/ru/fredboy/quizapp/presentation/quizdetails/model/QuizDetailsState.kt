package ru.fredboy.quizapp.presentation.quizdetails.model

sealed interface QuizDetailsState {

    data object Loading : QuizDetailsState

    data class Error(
        val message: String?,
    ) : QuizDetailsState

    data class Success(
        val quizDetails: QuizDetailsVo,
    ) : QuizDetailsState
}
