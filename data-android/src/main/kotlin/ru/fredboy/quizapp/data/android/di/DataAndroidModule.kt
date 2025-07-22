package ru.fredboy.quizapp.data.android.di

import android.content.Context
import androidx.room.Room
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.fredboy.quizapp.data.android.BuildConfig
import ru.fredboy.quizapp.data.android.mapper.AnswerMapper
import ru.fredboy.quizapp.data.android.mapper.QuestionMapper
import ru.fredboy.quizapp.data.android.mapper.QuizMapper
import ru.fredboy.quizapp.data.android.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.android.source.local.LocalQuizDataSourceImpl
import ru.fredboy.quizapp.data.android.source.local.room.QuizDao
import ru.fredboy.quizapp.data.android.source.local.room.QuizDatabase
import ru.fredboy.quizapp.data.android.source.remote.QuizApiService
import ru.fredboy.quizapp.data.android.source.remote.RemoteQuizDataSourceImpl
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource

val dataAndroidModule = module {

    single<Converter.Factory> {
        Json { ignoreUnknownKeys = true }
            .asConverterFactory("application/json; charset=UTF8".toMediaType())
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single<QuizApiService> {
        get<Retrofit>().create(QuizApiService::class.java)
    }

    single<RemoteQuizDataSource> {
        RemoteQuizDataSourceImpl(
            quizApiService = get(),
        )
    }

    single<QuizDatabase> {
        Room.databaseBuilder(
            context = get<Context>(),
            klass = QuizDatabase::class.java,
            QuizDatabase::class.simpleName,
        ).build()
    }

    single<QuizDao> {
        val database = get<QuizDatabase>()
        database.quizDao()
    }

    single<LocalQuizDataSource> {
        LocalQuizDataSourceImpl(
            quizDao = get(),
            quizzesMapper = get(),
            quizMapper = get(),
            questionMapper = get(),
            answerMapper = get(),
        )
    }

    factory<QuizzesMapper> {
        QuizzesMapper()
    }

    factory<QuizMapper> {
        QuizMapper(
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
