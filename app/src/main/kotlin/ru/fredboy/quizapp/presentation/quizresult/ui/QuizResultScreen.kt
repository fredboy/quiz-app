package ru.fredboy.quizapp.presentation.quizresult.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import ru.fredboy.quizapp.presentation.common.component.CommonScaffold
import ru.fredboy.quizapp.presentation.common.navigation.ListenNavBackStackEvent
import ru.fredboy.quizapp.presentation.quizresult.component.QuizResultPage
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultState
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QuizResultScreen(
    viewModel: QuizResultViewModel,
    navBackStack: NavBackStack,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val quizResultState by viewModel.state.collectAsStateWithLifecycle()

    ListenNavBackStackEvent(viewModel, navBackStack)

    CommonScaffold(
        scrollBehavior = scrollBehavior,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            QuizResultScreen(
                state = quizResultState,
                onRetakeQuiz = viewModel::onRetakeQuiz,
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun QuizResultScreen(
    state: QuizResultState,
    onRetakeQuiz: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (state) {
        is QuizResultState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is QuizResultState.Success -> {
            QuizResultPage(
                quizResultVo = state.quizResultVo,
                onRetakeQuiz = onRetakeQuiz,
                modifier = modifier,
                contentPadding = contentPadding,
            )
        }
    }
}
