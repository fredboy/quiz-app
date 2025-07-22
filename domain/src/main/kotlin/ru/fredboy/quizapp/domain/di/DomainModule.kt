package ru.fredboy.quizapp.domain.di

import org.koin.dsl.module
import ru.fredboy.quizapp.domain.usecase.GetQuizListUseCase
import ru.fredboy.quizapp.domain.usecase.GetQuizUseCase

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
}
