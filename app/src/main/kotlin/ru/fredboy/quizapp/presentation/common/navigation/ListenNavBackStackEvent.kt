package ru.fredboy.quizapp.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.NavBackStack
import ru.fredboy.quizapp.presentation.common.model.BaseViewModel

@Composable
fun ListenNavBackStackEvent(
    viewModel: BaseViewModel,
    navBackStack: NavBackStack,
) {
    LaunchedEffect(Unit) {
        viewModel.navBackStackEventFlow.collect { event ->
            when (event) {
                is NavBackStackEvent.Push -> {
                    navBackStack.add(event.navKey)
                }

                is NavBackStackEvent.Pop -> {
                    navBackStack.removeLastOrNull()
                }

                is NavBackStackEvent.ReplaceTop -> {
                    navBackStack[navBackStack.lastIndex] = event.navKey
                }
            }
        }
    }
}
