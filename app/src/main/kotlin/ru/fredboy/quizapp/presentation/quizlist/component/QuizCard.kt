package ru.fredboy.quizapp.presentation.quizlist.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.presentation.quizlist.model.QuizVo

@Composable
fun QuizCard(
    quizVo: QuizVo,
    onClick: (QuizVo) -> Unit,
) {
    val quiz = quizVo.quiz
    val status = quizVo.status
    val passingScorePercents =
        (quiz.passingScore.toFloat() / quiz.numberOfQuestions.toFloat() * 100f).toInt()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = { onClick(quizVo) },
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(quiz.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(16.dp),
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(
                    modifier = Modifier
                        .height(4.dp),
                )

                Text(
                    text = stringResource(
                        R.string.quiz_questions_and_score,
                        quiz.numberOfQuestions.toString(),
                        passingScorePercents.toString(),
                    ),
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp),
                )

                QuizStatusLabel(status)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewQuizCard() {
    QuizCard(
        quizVo = QuizVo(
            quiz = Quiz(
                id = 1,
                title = "Quiz Title",
                imageUrl = "https://placehold.co/600x400.png",
                passingScore = 6,
                numberOfQuestions = 10,
            ),
            status = QuizStatus.PASSED,
        ),
        onClick = { },
    )
}
