package ru.fredboy.quizapp.presentation.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey

interface QuizAppNavKey : NavKey {

    fun getNavEntry(
        scaffoldContentPadding: PaddingValues,
        modifier: Modifier,
        navBackStack: NavBackStack,
    ): NavEntry<NavKey>
}
