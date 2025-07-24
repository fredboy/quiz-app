package ru.fredboy.quizapp.presentation.common.navigation

import androidx.navigation3.runtime.NavKey

sealed interface NavigationEvent {

    data object PopBackStack : NavigationEvent

    data class PushToBackStack(
        val navKey: NavKey,
    ) : NavigationEvent
}
