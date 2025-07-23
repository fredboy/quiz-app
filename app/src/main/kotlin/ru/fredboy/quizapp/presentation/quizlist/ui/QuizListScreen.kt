package ru.fredboy.quizapp.presentation.quizlist.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.presentation.common.component.CommonErrorBox
import ru.fredboy.quizapp.presentation.quizlist.component.QuizList
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListReloadEvent
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListState
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel
import ru.fredboy.quizapp.presentation.quizlist.model.QuizVo

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QuizListScreen(
    contentPadding: PaddingValues,
    viewModel: QuizListViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.quizListState.collectAsState()

    PullToRefreshBox(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        isRefreshing = state is QuizListState.Refreshing,
        onRefresh = { viewModel.onReload(QuizListReloadEvent.Refresh(state)) },
    ) {
        QuizListScreen(
            state = state,
            onQuizClick = viewModel::onQuizClick,
            onRetry = { viewModel.onReload(QuizListReloadEvent.Reload) },
            modifier = modifier,
            contentPadding = contentPadding,
        )
    }
}

@Composable
fun QuizListScreen(
    state: QuizListState,
    onQuizClick: (QuizVo) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (state) {
        is QuizListState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is QuizListState.Error -> {
            CommonErrorBox(
                message = state.message ?: stringResource(R.string.common_error_message_default),
                onRetry = onRetry,
            )
        }

        is QuizListState.Success -> {
            QuizList(
                quizzes = state.quizzes,
                onQuizClick = onQuizClick,
                modifier = modifier,
                contentPadding = contentPadding,
            )
        }

        is QuizListState.Refreshing -> {
            QuizListScreen(
                state = state.lastState,
                onQuizClick = onQuizClick,
                onRetry = onRetry,
                modifier = modifier,
                contentPadding = contentPadding,
            )
        }
    }
}
