package ru.fredboy.quizapp.presentation.quizresult.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.fredboy.quizapp.presentation.common.navigation.QuizAppNavKey
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultViewModelParams
import ru.fredboy.quizapp.presentation.quizresult.ui.QuizResultScreen

@Serializable
data class QuizResultNavKey(
    val params: QuizResultViewModelParams,
) : QuizAppNavKey {

    override fun getNavEntry(navBackStack: NavBackStack): NavEntry<NavKey> {
        return NavEntry(this) {
            QuizResultScreen(
                viewModel = koinViewModel {
                    parametersOf(params)
                },
                navBackStack = navBackStack,
            )
        }
    }
}
