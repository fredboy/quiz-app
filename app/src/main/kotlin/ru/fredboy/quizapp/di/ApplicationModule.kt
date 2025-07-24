package ru.fredboy.quizapp.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.fredboy.quizapp.data.android.di.dataAndroidModule
import ru.fredboy.quizapp.data.di.dataModule
import ru.fredboy.quizapp.domain.di.domainModule
import ru.fredboy.quizapp.presentation.question.model.QuestionViewModel
import ru.fredboy.quizapp.presentation.quizdetails.model.QuizDetailsViewModel
import ru.fredboy.quizapp.presentation.quizlist.model.QuizListViewModel
import ru.fredboy.quizapp.presentation.quizresult.model.QuizResultViewModel

val applicationModule = module {

    includes(
        domainModule,
        dataModule,
        dataAndroidModule,
    )

    viewModel<QuizListViewModel> {
        QuizListViewModel(
            getQuizListUseCase = get(),
            observeQuizStatusUseCase = get(),
            invalidateCachedQuizzesUseCase = get(),
        )
    }

    viewModel<QuizDetailsViewModel> {
        QuizDetailsViewModel(
            getQuizUseCase = get(),
            observeQuizStatusUseCase = get(),
            invalidateCachedQuizUseCase = get(),
            params = get(),
        )
    }

    viewModel<QuestionViewModel> {
        QuestionViewModel(
            getQuizUseCase = get(),
            invalidateCachedQuizUseCase = get(),
            saveQuizStatusUseCase = get(),
            params = get(),
        )
    }

    viewModel<QuizResultViewModel> {
        QuizResultViewModel(
            params = get(),
        )
    }
}
