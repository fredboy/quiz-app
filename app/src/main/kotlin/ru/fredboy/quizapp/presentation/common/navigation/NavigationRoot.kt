package ru.fredboy.quizapp.presentation.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import ru.fredboy.quizapp.presentation.quizlist.navigation.QuizListNavKey

@Composable
fun NavigationRoot(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val navBackStack = rememberNavBackStack(QuizListNavKey)

    NavDisplay(
        backStack = navBackStack,
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = { navKey ->
            when (navKey) {
                is QuizAppNavKey -> navKey.getNavEntry(contentPadding, modifier, navBackStack)
                else -> throw RuntimeException("Unknown NavKey: $navKey")
            }
        },
    )
}
