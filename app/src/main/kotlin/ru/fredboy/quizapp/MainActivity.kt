package ru.fredboy.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.fredboy.quizapp.presentation.common.component.CommonTopAppBar
import ru.fredboy.quizapp.presentation.common.theme.QuizAppTheme
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel
import ru.fredboy.quizapp.presentation.quizlist.ui.QuizListScreen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    val quizListViewModel: QuizListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

            QuizAppTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        CommonTopAppBar(
                            scrollBehavior = scrollBehavior,
                            title = stringResource(R.string.app_name),
                        )
                    },
                    contentWindowInsets = WindowInsets.safeDrawing,
                ) { contentPadding ->
                    QuizListScreen(
                        contentPadding = contentPadding,
                        viewModel = quizListViewModel,
                        modifier = Modifier
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                    )
                }
            }
        }
    }
}
