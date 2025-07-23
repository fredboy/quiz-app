package ru.fredboy.quizapp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel

val applicationModule = module {

    viewModel<QuizListViewModel> {
        QuizListViewModel(
            savedStateHandle = get(),
            getQuizListUseCase = get(),
            observeQuizStatusUseCase = get(),
            invalidateCachedQuizzesUseCase = get(),
        )
    }
}
