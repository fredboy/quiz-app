package ru.fredboy.quizapp.presentation.quizlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.domain.model.QuizStatus

@Composable
fun QuizStatusLabel(status: QuizStatus?) {
    val (label, color) = when (status) {
        QuizStatus.PASSED -> {
            stringResource(R.string.quiz_status_passed) to MaterialTheme.colorScheme.primary
        }

        QuizStatus.FAILED -> {
            stringResource(R.string.quiz_status_failed) to MaterialTheme.colorScheme.error
        }

        null -> {
            stringResource(R.string.quiz_status_untaken) to MaterialTheme.colorScheme.secondary
        }
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = label,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                ),
        )
    }
}

@Preview
@Composable
private fun QuizStatusLabelPreview() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        QuizStatusLabel(
            status = null,
        )
        Spacer(
            modifier = Modifier.height(8.dp),
        )
        QuizStatusLabel(
            status = QuizStatus.PASSED,
        )
        Spacer(
            modifier = Modifier.height(8.dp),
        )
        QuizStatusLabel(
            status = QuizStatus.FAILED,
        )
    }
}
