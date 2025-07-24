package ru.fredboy.quizapp.presentation.common.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

interface QuizAppNavKey : NavKey {

    fun getNavEntry(
        navBackStack: NavBackStack,
    ): NavEntry<NavKey>
}
