package ru.fredboy.quizapp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fredboy.quizapp.view.quizlist.QuizListViewModel

val applicationModule = module {

    viewModel<QuizListViewModel> {
        QuizListViewModel(
            savedStateHandle = get(),
            getQuizListUseCase = get(),
            saveQuizStatusUseCase = get(),
            observeQuizStatusUseCase = get(),
        )
    }
}
