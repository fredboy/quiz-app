package ru.fredboy.quizapp.presentation.quizlist.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import ru.fredboy.quizapp.presentation.common.navigation.QuizAppNavKey
import ru.fredboy.quizapp.presentation.quizlist.ui.QuizListScreen

@Serializable
data object QuizListNavKey : QuizAppNavKey {

    override fun getNavEntry(
        navBackStack: NavBackStack,
    ): NavEntry<NavKey> {
        return NavEntry(this) {
            QuizListScreen(
                viewModel = koinViewModel(),
                navBackStack = navBackStack,
            )
        }
    }
}
