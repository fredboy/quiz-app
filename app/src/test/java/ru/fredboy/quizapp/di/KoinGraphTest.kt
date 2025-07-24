package ru.fredboy.quizapp.di

import org.junit.jupiter.api.Test
import org.koin.test.verify.verify
import ru.fredboy.quizapp.presentation.question.model.QuestionViewModelParams
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultViewModelParams

class KoinGraphTest {

    @Test
    fun `check koin graph`() {
        applicationModule.verify(
            extraTypes = listOf(
                QuizDetailsViewModelParams::class,
                QuestionViewModelParams::class,
                QuizResultViewModelParams::class,
            ),
        )
    }
}
