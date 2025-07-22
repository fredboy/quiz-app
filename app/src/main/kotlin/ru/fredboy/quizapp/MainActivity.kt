package ru.fredboy.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fredboy.quizapp.ui.compose.QuizListScreen
import ru.fredboy.quizapp.ui.theme.QuizAppTheme
import ru.fredboy.quizapp.view.quizlist.QuizListViewModel

class MainActivity : ComponentActivity() {

    val quizListViewModel: QuizListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(quizListViewModel)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: QuizListViewModel) {
    val state by viewModel.quizzes.collectAsState()

    QuizListScreen(
        state = state,
        onRetry = {  },
        onQuizClick = { quizVo ->
            viewModel.onQuizClick(quizVo)
        }
    )
}
