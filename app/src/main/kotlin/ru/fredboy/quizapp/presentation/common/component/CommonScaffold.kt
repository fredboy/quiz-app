package ru.fredboy.quizapp.presentation.common.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.fredboy.quizapp.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CommonScaffold(
    scrollBehavior: TopAppBarScrollBehavior,
    content: @Composable ((PaddingValues) -> Unit),
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CommonTopAppBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(R.string.app_name),
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        content = content,
    )
}
