package ru.fredboy.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import ru.fredboy.quizapp.presentation.common.navigation.NavigationRoot
import ru.fredboy.quizapp.presentation.common.theme.QuizAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizAppTheme {
                NavigationRoot()
            }
        }
    }
}
