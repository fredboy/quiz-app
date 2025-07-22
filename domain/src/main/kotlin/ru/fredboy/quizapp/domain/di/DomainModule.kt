package ru.fredboy.quizapp.domain.di

import org.koin.dsl.module
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.GetQuizStatusUseCase
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase
import ru.fredboy.quizapp.domain.usecase.ObserveQuizStatusUseCase
import ru.fredboy.quizapp.domain.usecase.SaveQuizStatusUseCase

val domainModule = module {

    factory<GetQuizListUseCase> {
        GetQuizListUseCase(
            quizRepository = get(),
        )
    }

    factory<GetQuizUseCase> {
        GetQuizUseCase(
            quizRepository = get(),
        )
    }

    factory<SaveQuizStatusUseCase> {
        SaveQuizStatusUseCase(
            quizRepository = get(),
        )
    }

    factory<GetQuizStatusUseCase> {
        GetQuizStatusUseCase(
            quizRepository = get(),
        )
    }

    factory<ObserveQuizStatusUseCase> {
        ObserveQuizStatusUseCase(
            quizRepository = get(),
        )
    }
}
