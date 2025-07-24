package ru.fredboy.quizapp.presentation.quizdetails.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavBackStack
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.presentation.common.component.CommonErrorBox
import ru.fredboy.quizapp.presentation.common.component.CommonScaffold
import ru.fredboy.quizapp.presentation.common.navigation.ListenNavBackStackEvent
import ru.fredboy.quizapp.presentation.quizdetails.component.QuizPage
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsReloadEvent
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsState
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun QuizDetailsScreen(
    viewModel: QuizDetailsViewModel,
    navBackStack: NavBackStack,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val quizDetailsState by viewModel.quizDetailsState.collectAsStateWithLifecycle()

    ListenNavBackStackEvent(viewModel, navBackStack)

    CommonScaffold(
        scrollBehavior = scrollBehavior,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            QuizDetailsScreen(
                state = quizDetailsState,
                onStartQuizClick = viewModel::onStartQuiz,
                onReload = { viewModel.onReload(QuizDetailsReloadEvent.Reload) },
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun QuizDetailsScreen(
    state: QuizDetailsState,
    onStartQuizClick: () -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (state) {
        is QuizDetailsState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is QuizDetailsState.Error -> {
            CommonErrorBox(
                message = state.message ?: stringResource(R.string.common_error_message_default),
                onRetry = onReload,
            )
        }

        is QuizDetailsState.Success -> {
            QuizPage(
                quizDetailsVo = state.quizDetails,
                modifier = modifier,
                contentPadding = contentPadding,
                onStartQuizClick = onStartQuizClick,
            )
        }
    }
}
