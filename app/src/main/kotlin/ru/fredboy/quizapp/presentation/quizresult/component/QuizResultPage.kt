package ru.fredboy.quizapp.presentation.quizresult.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultVo
import java.util.concurrent.TimeUnit

@Composable
fun QuizResultPage(
    quizResultVo: QuizResultVo,
    onRetakeQuiz: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        Spacer(
            modifier = Modifier
                .height(64.dp),
        )

        Text(
            text = quizResultVo.title,
            style = MaterialTheme.typography.displaySmall,
        )

        Spacer(
            modifier = Modifier
                .height(64.dp),
        )

        Text(
            text = if (quizResultVo.status == QuizStatus.PASSED) {
                stringResource(R.string.quiz_passed_emoji)
            } else {
                stringResource(R.string.quiz_failed_emoji)
            },
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(
            modifier = Modifier
                .height(64.dp),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            KonfettiView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                parties = quizResultVo.parties,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(
                    modifier = Modifier
                        .height(32.dp),
                )

                Text(
                    text = stringResource(
                        R.string.quiz_result_answers,
                        quizResultVo.correctAnswers,
                        quizResultVo.totalQuestions,
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                )

                Spacer(
                    modifier = Modifier
                        .height(16.dp),
                )

                Text(
                    text = if (quizResultVo.status == QuizStatus.PASSED) {
                        stringResource(R.string.quiz_passed_text)
                    } else {
                        stringResource(R.string.quiz_failed_text)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onRetakeQuiz,
            ) {
                Text(
                    text = stringResource(R.string.quiz_retake),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun PreviewQuizResultPage() {
    QuizResultPage(
        quizResultVo = QuizResultVo(
            status = QuizStatus.PASSED,
            title = "Quiz Title",
            correctAnswers = 9,
            totalQuestions = 10,
            passingScore = 8,
            parties = listOf(
                Party(
                    speed = 0f,
                    maxSpeed = 15f,
                    damping = 0.9f,
                    angle = Angle.BOTTOM,
                    spread = Spread.ROUND,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(100),
                    position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0)),
                ),
            ),
        ),
        onRetakeQuiz = { },
    )
}
