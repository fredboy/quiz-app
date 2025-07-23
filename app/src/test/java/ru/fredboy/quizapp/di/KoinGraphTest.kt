package ru.fredboy.quizapp.di

import androidx.navigation3.runtime.NavBackStack
import org.junit.jupiter.api.Test
import org.koin.test.verify.verify
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams

class KoinGraphTest {

    @Test
    fun `check koin graph`() {
        applicationModule.verify(
            extraTypes = listOf(
                NavBackStack::class,
                QuizDetailsViewModelParams::class,
            ),
        )
    }
}
