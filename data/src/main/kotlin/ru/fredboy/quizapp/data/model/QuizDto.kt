package ru.fredboy.quizapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("passing_score") val passingScore: Int,
    @SerialName("number_of_questions") val numberOfQuestions: Int,
)
