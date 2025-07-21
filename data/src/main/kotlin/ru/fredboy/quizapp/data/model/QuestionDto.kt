package ru.fredboy.quizapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDto(
    @SerialName("id") val id: Int,
    @SerialName("text") val text: String,
    @SerialName("image_url") val imageUrl: String?,
    @SerialName("answers") val answers: List<AnswerDto>,
    @SerialName("correct_answer_id") val correctAnswerId: Int,
)
