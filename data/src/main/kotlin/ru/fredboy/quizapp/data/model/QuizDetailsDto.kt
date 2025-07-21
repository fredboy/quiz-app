package ru.fredboy.quizapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QuizDetailsDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("image_url") val imageUrl: String,
    @SerialName("passing_score") val passingScore: Int,
    @SerialName("questions") val questions: List<QuestionDto>,
)
