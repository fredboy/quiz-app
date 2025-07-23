package ru.fredboy.quizapp.presentation.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fredboy.quizapp.R

@Composable
fun CommonErrorBox(
    message: String,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp),
            )

            Button(
                onClick = onRetry,
            ) {
                Text(
                    text = stringResource(R.string.common_error_retry),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun PreviewCommonErrorBox() {
    CommonErrorBox(
        message = "An error occurred",
        onRetry = { },
    )
}
