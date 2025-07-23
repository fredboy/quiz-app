package ru.fredboy.quizapp.presentation.quizlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.fredboy.quizapp.domain.model.Quiz
import ru.fredboy.quizapp.domain.model.QuizStatus
import ru.fredboy.quizapp.presentation.quizlist.model.QuizVo

@Composable
fun QuizList(
    quizzes: List<QuizVo>,
    onQuizClick: (QuizVo) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(quizzes) { quizVo ->
            QuizCard(
                quizVo = quizVo,
                onClick = onQuizClick,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewQuizList() {
    QuizList(
        quizzes = listOf(
            QuizVo(
                quiz = Quiz(
                    id = 1,
                    title = "Quiz Title 1",
                    description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                        "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                        "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                        "Consequatur ut exercitationem ut beatae soluta officiis." +
                        "Repellat et possimus doloremque molestiae.",
                    imageUrl = "https://placehold.co/600x400.png",
                    passingScore = 12,
                    numberOfQuestions = 20,
                ),
                status = QuizStatus.FAILED,
            ),
            QuizVo(
                quiz = Quiz(
                    id = 2,
                    title = "Quiz Title 2",
                    description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                        "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                        "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                        "Consequatur ut exercitationem ut beatae soluta officiis." +
                        "Repellat et possimus doloremque molestiae.",
                    imageUrl = "https://placehold.co/600x400.png",
                    passingScore = 6,
                    numberOfQuestions = 10,
                ),
                status = QuizStatus.PASSED,
            ),
            QuizVo(
                quiz = Quiz(
                    id = 3,
                    title = "Quiz Title 3",
                    description = "Pariatur quam vero est. Vel non sapiente quam tempora quisquam" +
                        "aliquid voluptas voluptas. Est quisquam reprehenderit consequatur." +
                        "Quidem quo dolores laudantium praesentium aliquid harum rerum." +
                        "Consequatur ut exercitationem ut beatae soluta officiis." +
                        "Repellat et possimus doloremque molestiae.",
                    imageUrl = "https://placehold.co/600x400.png",
                    passingScore = 38,
                    numberOfQuestions = 40,
                ),
                status = null,
            ),
        ),
        onQuizClick = { },
    )
}
