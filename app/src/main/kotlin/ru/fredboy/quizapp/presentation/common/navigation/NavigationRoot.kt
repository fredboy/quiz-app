package ru.fredboy.quizapp.presentation.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.fredboy.quizapp.presentation.quizdetails.navigation.QuizDetailsNavKey
import ru.fredboy.quizapp.presentation.quizdetails.ui.QuizDetailsScreen
import ru.fredboy.quizapp.presentation.quizlist.navigation.QuizListNavKey
import ru.fredboy.quizapp.presentation.quizlist.ui.QuizListScreen

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
                is QuizListNavKey -> {
                    NavEntry(
                        key = navKey,
                    ) {
                        QuizListScreen(
                            contentPadding = contentPadding,
                            viewModel = koinViewModel {
                                parametersOf(navBackStack)
                            },
                            modifier = modifier,
                        )
                    }
                }

                is QuizDetailsNavKey -> {
                    NavEntry(
                        key = navKey,
                    ) {
                        QuizDetailsScreen(
                            contentPadding = contentPadding,
                            viewModel = koinViewModel {
                                parametersOf(navKey.params)
                            },
                            modifier = modifier,
                        )
                    }
                }

                else -> throw RuntimeException("Unknown NavKey: $navKey")
            }
        },
    )
}
