package ru.fredboy.quizapp.presentation.quizdetails.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.fredboy.quizapp.presentation.common.navigation.QuizAppNavKey
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModelParams
import ru.fredboy.quizapp.presentation.quizdetails.ui.QuizDetailsScreen

@Serializable
data class QuizDetailsNavKey(
    val params: QuizDetailsViewModelParams,
) : QuizAppNavKey {

    override fun getNavEntry(
        navBackStack: NavBackStack,
    ): NavEntry<NavKey> {
        return NavEntry(this) {
            QuizDetailsScreen(
                viewModel = koinViewModel {
                    parametersOf(params)
                },
                navBackStack = navBackStack,
            )
        }
    }
}
