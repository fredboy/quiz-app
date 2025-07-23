package ru.fredboy.quizapp.presentation.quizdetails.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.presentation.common.component.CommonErrorBox
import ru.fredboy.quizapp.presentation.quizdetails.component.QuizPage
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsReloadEvent
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsState
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModel
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsVo

@Composable
fun QuizDetailsScreen(
    contentPadding: PaddingValues,
    viewModel: QuizDetailsViewModel,
    modifier: Modifier = Modifier,
) {
    val quizDetailsState by viewModel.quizDetailsState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp),
    ) {
        QuizDetailsScreen(
            state = quizDetailsState,
            onStartQuizClick = { },
            onReload = { viewModel.onReload(QuizDetailsReloadEvent.Reload) },
            modifier = modifier,
            contentPadding = contentPadding,
        )
    }
}

@Composable
fun QuizDetailsScreen(
    state: QuizDetailsState,
    onStartQuizClick: (QuizDetailsVo) -> Unit,
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
                contentPadding = contentPadding,
            )
        }

        is QuizDetailsState.Refreshing -> {
            QuizDetailsScreen(
                state = state.lastState,
                onStartQuizClick = onStartQuizClick,
                onReload = onReload,
                modifier = modifier,
                contentPadding = contentPadding,
            )
        }
    }
}
