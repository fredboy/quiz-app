package ru.fredboy.quizapp.data.di

import org.koin.dsl.module
import ru.fredboy.quizapp.data.mapper.AnswerMapper
import ru.fredboy.quizapp.data.mapper.QuestionMapper
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.repository.QuizRepositoryImpl
import ru.fredboy.quizapp.domain.repository.QuizRepository

val dataModule = module {

    single<QuizRepository> {
        QuizRepositoryImpl(
            remoteQuizDataSource = get(),
            quizzesMapper = get(),
            quizDetailsMapper = get(),
        )
    }

    factory<QuizzesMapper> {
        QuizzesMapper()
    }

    factory<QuizDetailsMapper> {
        QuizDetailsMapper(
            questionMapper = get(),
        )
    }

    factory<QuestionMapper> {
        QuestionMapper(
            answerMapper = get(),
        )
    }

    factory<AnswerMapper> {
        AnswerMapper()
    }
}
