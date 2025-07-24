package ru.fredboy.quizapp.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.NavBackStack
import ru.fredboy.quizapp.presentation.common.model.BaseViewModel

@Composable
fun ListenNavigationEvents(
    viewModel: BaseViewModel,
    navBackStack: NavBackStack,
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEventFlow.collect { event ->
            when (event) {
                is NavigationEvent.PushToBackStack -> {
                    navBackStack.add(event.navKey)
                }
                is NavigationEvent.PopBackStack -> {
                    navBackStack.removeLastOrNull()
                }
            }
        }
    }
}
