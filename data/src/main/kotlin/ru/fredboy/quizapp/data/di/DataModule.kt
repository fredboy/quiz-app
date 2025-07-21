package ru.fredboy.quizapp.data.di

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.fredboy.quizapp.data.mapper.AnswerMapper
import ru.fredboy.quizapp.data.mapper.QuestionMapper
import ru.fredboy.quizapp.data.mapper.QuizDetailsMapper
import ru.fredboy.quizapp.data.mapper.QuizListMapper
import ru.fredboy.quizapp.data.repository.QuizRepositoryImpl
import ru.fredboy.quizapp.data.source.remote.QuizApiService
import ru.fredboy.quizapp.domain.repository.QuizRepository

val dataModule = module {

    single<Converter.Factory> {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json; charset=UTF8".toMediaType())
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(get())
            .build()
    }

    single<QuizApiService> {
        get<Retrofit>().create(QuizApiService::class.java)
    }

    single<QuizRepository> {
        QuizRepositoryImpl(
            quizDataSource = get(),
            quizListMapper = get(),
            quizDetailsMapper = get(),
        )
    }

    factory<QuizListMapper> {
        QuizListMapper()
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
