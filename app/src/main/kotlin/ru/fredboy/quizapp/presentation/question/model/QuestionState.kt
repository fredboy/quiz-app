package ru.fredboy.quizapp.presentation.question.model

sealed interface QuestionState {

    data object Loading : QuestionState

    data class Error(
        val message: String?,
    ) : QuestionState

    data class Success(
        val questionVo: QuestionVo,
    ) : QuestionState
}
