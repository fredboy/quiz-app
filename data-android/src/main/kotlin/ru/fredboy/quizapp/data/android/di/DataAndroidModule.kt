package ru.fredboy.quizapp.data.android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
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
import ru.fredboy.quizapp.data.android.mapper.QuizStatusMapper
import ru.fredboy.quizapp.data.android.mapper.QuizzesMapper
import ru.fredboy.quizapp.data.android.source.local.LocalQuizDataSourceImpl
import ru.fredboy.quizapp.data.android.source.local.prefs.QuizCachePrefsDataStore
import ru.fredboy.quizapp.data.android.source.local.room.QuizDao
import ru.fredboy.quizapp.data.android.source.local.room.QuizDatabase
import ru.fredboy.quizapp.data.android.source.remote.QuizApiService
import ru.fredboy.quizapp.data.android.source.remote.RemoteQuizDataSourceImpl
import ru.fredboy.quizapp.data.source.local.LocalQuizDataSource
import ru.fredboy.quizapp.data.source.remote.RemoteQuizDataSource

private const val PREFERENCES_DATA_STORE_FILE_NAME = "quiz-app-data-store"

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
            quizStatusMapper = get(),
            quizCachePrefsDataStore = get(),
        )
    }

    single<DataStore<Preferences>> {
        val context = get<Context>()

        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(PREFERENCES_DATA_STORE_FILE_NAME)
        }
    }

    single<QuizCachePrefsDataStore> {
        QuizCachePrefsDataStore(
            dataStore = get(),
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

    factory<QuizStatusMapper> {
        QuizStatusMapper()
    }
}
