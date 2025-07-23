package ru.fredboy.quizapp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModel
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel

val applicationModule = module {

    viewModel<QuizListViewModel> { parameters ->
        QuizListViewModel(
            getQuizListUseCase = get(),
            observeQuizStatusUseCase = get(),
            invalidateCachedQuizzesUseCase = get(),
            navBackStack = parameters.get(),
        )
    }

    viewModel<QuizDetailsViewModel> { parameters ->
        QuizDetailsViewModel(
            getQuizUseCase = get(),
            observeQuizStatusUseCase = get(),
            params = parameters.get(),
        )
    }
}
