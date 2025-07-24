package ru.fredboy.quizapp.presentation.question.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.fredboy.quizapp.presentation.common.navigation.QuizAppNavKey
import ru.fredboy.quizapp.presentation.question.model.QuestionViewModelParams
import ru.fredboy.quizapp.presentation.question.ui.QuestionScreen

@Serializable
data class QuestionNavKey(
    val params: QuestionViewModelParams,
) : QuizAppNavKey {

    override fun getNavEntry(
        navBackStack: NavBackStack,
    ): NavEntry<NavKey> {
        return NavEntry(this) {
            QuestionScreen(
                viewModel = koinViewModel {
                    parametersOf(params)
                },
                navBackStack = navBackStack,
            )
        }
    }
}
