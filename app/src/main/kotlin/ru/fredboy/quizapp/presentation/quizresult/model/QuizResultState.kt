package ru.fredboy.quizapp.presentation.quizresult.model

sealed interface QuizResultState {

    data object Loading : QuizResultState

    data class Success(
        val quizResultVo: QuizResultVo,
    ) : QuizResultState
}
