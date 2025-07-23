package ru.fredboy.quizapp.presentation.quizdetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.domain.model.Question
import ru.fredboy.quizapp.domain.model.QuizDetails
import ru.fredboy.quizapp.presentation.common.component.CommonQuizStatusLabel
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsVo

@Composable
fun QuizPage(
    quizDetailsVo: QuizDetailsVo,
) {
    val quizDetails = quizDetailsVo.quizDetails
    val status = quizDetailsVo.status

    val buttonText = status?.let {
        stringResource(R.string.quiz_retake)
    } ?: stringResource(R.string.quiz_start)

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Image(
            painter = rememberAsyncImagePainter(quizDetails.imageUrl),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(120.dp, 240.dp),
            contentScale = ContentScale.Fit,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        Text(
            text = quizDetails.title,
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        Text(
            text = quizDetails.description,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 8,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        CommonQuizStatusLabel(
            status = status,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        Text(
            text = stringResource(
                R.string.quiz_number_of_questions,
                quizDetails.questions.size.toString()
            ),
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(
            modifier = Modifier
                .height(3.dp),
        )

        Text(
            text = stringResource(
                R.string.quiz_passing_score_percent,
                (quizDetails.passingScore.toFloat() / quizDetails.questions.size.toFloat() * 100f)
                    .toInt().toString()
            ),
            style = MaterialTheme.typography.bodyMedium,
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { },
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuizPage() {
    QuizPage(
        quizDetailsVo = QuizDetailsVo(
            quizDetails = QuizDetails(
                id = 1,
                title = "Quiz Title",
                description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                        "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                        "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                        "Consequatur ut exercitationem ut beatae soluta officiis." +
                        "Repellat et possimus doloremque molestiae.",
                imageUrl = "https://placehold.co/600x400.png",
                passingScore = 2,
                questions = listOf(
                    Question(
                        id = 1,
                        text = "Question text 1",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                    Question(
                        id = 2,
                        text = "Question text 2",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                    Question(
                        id = 3,
                        text = "Question text 3",
                        answers = emptyList(),
                        correctAnswerId = 0,
                        imageUrl = null,
                    ),
                ),
            ),
            status = null,
        )
    )
}
