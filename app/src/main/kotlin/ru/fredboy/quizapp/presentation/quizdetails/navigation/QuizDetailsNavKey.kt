package ru.fredboy.quizapp.presentation.quizdetails.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams

@Serializable
data class QuizDetailsNavKey(
    val params: QuizDetailsViewModelParams,
) : NavKey
