package ru.fredboy.quizapp.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.fredboy.quizapp.domain.model.QuizStatus

@Composable
fun StatusLabel(status: QuizStatus) {
    val (label, color) = when (status) {
        QuizStatus.UNTAKEN -> "Not Attempted" to MaterialTheme.colorScheme.secondary
        QuizStatus.PASSED -> "Passed" to MaterialTheme.colorScheme.primary
        QuizStatus.FAILED -> "Failed" to MaterialTheme.colorScheme.error
    }

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = label,
            color = color,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
