package ru.fredboy.quizapp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fredboy.quizapp.data.android.di.dataAndroidModule
import ru.fredboy.quizapp.data.di.dataModule
import ru.fredboy.quizapp.domain.di.domainModule
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModel
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel

val applicationModule = module {

    includes(
        domainModule,
        dataModule,
        dataAndroidModule,
    )

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
            invalidateCachedQuizUseCase = get(),
            params = parameters.get(),
        )
    }
}
