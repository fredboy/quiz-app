package ru.fredboy.quizapp.presentation.question.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import ru.fredboy.quizapp.R
import ru.fredboy.quizapp.domain.model.Answer
import ru.fredboy.quizapp.domain.model.Question
import ru.fredboy.quizapp.presentation.question.model.QuestionVo

@Composable
fun QuestionPage(
    questionVo: QuestionVo,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val question = questionVo.question

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(
                R.string.quiz_question_number,
                questionVo.current,
                questionVo.total,
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(
            modifier = Modifier
                .height(12.dp),
        )

        questionVo.question.imageUrl?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
        }

        Spacer(
            modifier = Modifier
                .height(12.dp),
        )

        Text(
            text = question.text,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp),
        )

        question.answers.forEach { answer ->

            QuestionAnswerOption(
                answer = answer,
                isSelected = questionVo.selectedAnswerId == answer.id,
                onSelect = { onAnswerSelected(answer.id) },
            )

            Spacer(
                modifier = Modifier
                    .height(8.dp),
            )
        }

        Spacer(
            modifier = Modifier
                .height(24.dp),
        )

        Button(
            onClick = onNext,
            enabled = questionVo.selectedAnswerId != null,
        ) {
            Text(
                if (questionVo.current < questionVo.total) {
                    stringResource(R.string.quiz_next)
                } else {
                    stringResource(R.string.quiz_finish)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewQuestionPage() {
    QuestionPage(
        questionVo = QuestionVo(
            question = Question(
                id = 1,
                text = "Question text",
                answers = listOf(
                    Answer(
                        id = 1,
                        text = "Option 1",
                    ),
                    Answer(
                        id = 2,
                        text = "Option 2",
                    ),
                    Answer(
                        id = 3,
                        text = "Option 3",
                    ),
                ),
                correctAnswerId = 2,
                imageUrl = "https://placehold.co/600x400.png",
            ),
            current = 2,
            total = 3,
            selectedAnswerId = 2,
            passingScore = 2,
        ),
        onAnswerSelected = { },
        onNext = { },
    )
}
