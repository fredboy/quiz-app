package ru.fredboy.quizapp.ui.compose

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.fredboy.quizapp.view.quizlist.model.QuizVo

@Composable
fun QuizCard(
    quizVo: QuizVo,
    onClick: () -> Unit,
) {
    val quiz = quizVo.quiz
    val status = quizVo.status

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = onClick,
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(quiz.imageUrl),
                contentDescription = "Quiz Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Questions: ${quiz.numberOfQuestions}, Passing Score: ${quiz.passingScore}%",
                    style = MaterialTheme.typography.bodySmall,
                )

                Spacer(Modifier.height(8.dp))

                StatusLabel(status)
            }
        }
    }
}
