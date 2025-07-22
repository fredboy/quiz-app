package ru.fredboy.quizapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerDto(
    @SerialName("id") val id: Int,
    @SerialName("text") val text: String,
)
