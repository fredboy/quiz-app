package ru.fredboy.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fredboy.quizapp.presentation.common.theme.QuizAppTheme
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel
import ru.fredboy.quizapp.presentation.quizlist.ui.QuizListScreen
import kotlin.getValue

class MainActivity : ComponentActivity() {

    val quizListViewModel: QuizListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                QuizListScreen(
                    viewModel = quizListViewModel,
                )
            }
        }
    }
}
