package ru.fredboy.quizapp.presentation.question.ui

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
import ru.fredboy.quizapp.presentation.common.navigation.ListenNavigationEvents
import ru.fredboy.quizapp.presentation.question.component.QuestionPage
import ru.fredboy.quizapp.presentation.question.model.QuestionState
import ru.fredboy.quizapp.presentation.question.model.QuestionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(
    viewModel: QuestionViewModel,
    navBackStack: NavBackStack,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val questionState by viewModel.questionState.collectAsStateWithLifecycle()

    ListenNavigationEvents(viewModel, navBackStack)

    CommonScaffold(
        scrollBehavior = scrollBehavior,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp),
        ) {
            QuestionScreen(
                state = questionState,
                onAnswerSelected = viewModel::onAnswerSelected,
                onNext = viewModel::onNextClicked,
                onReload = viewModel::onReload,
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = contentPadding,
            )
        }
    }
}

@Composable
fun QuestionScreen(
    state: QuestionState,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onReload: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (state) {
        is QuestionState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        is QuestionState.Error -> {
            CommonErrorBox(
                message = state.message ?: stringResource(R.string.common_error_message_default),
                onRetry = onReload,
            )
        }

        is QuestionState.Success -> {
            QuestionPage(
                questionVo = state.questionVo,
                onAnswerSelected = onAnswerSelected,
                onNext = onNext,
                modifier = modifier,
                contentPadding = contentPadding,
            )
        }
    }
}
