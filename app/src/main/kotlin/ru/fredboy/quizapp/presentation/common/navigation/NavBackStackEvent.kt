package ru.fredboy.quizapp.presentation.common.navigation

import androidx.navigation3.runtime.NavKey

sealed interface NavBackStackEvent {

    data object Pop : NavBackStackEvent

    data class Push(
        val navKey: NavKey,
    ) : NavBackStackEvent

    data class ReplaceTop(
        val navKey: NavKey,
    ) : NavBackStackEvent
}
