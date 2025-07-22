package ru.fredboy.quizapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.ui.theme.QuizAppTheme

class MainActivity : ComponentActivity() {

    val getQuizListUseCase: GetQuizListUseCase by inject()

    val getQuizUseCase: GetQuizUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }

        lifecycleScope.launch {
            val quizzes = getQuizListUseCase.invoke()
            Log.d("MainActivity", quizzes.quizzes.toString())

            quizzes.quizzes.forEach { quiz ->
                val details = getQuizUseCase.invoke(quiz.id)
                Log.d("MainActivity", details.toString())
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QuizAppTheme {
        Greeting("Android")
    }
}
