package ru.fredboy.quizapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.fredboy.quizapp.data.android.di.dataAndroidModule
import ru.fredboy.quizapp.data.di.dataModule
import ru.fredboy.quizapp.di.applicationModule
import ru.fredboy.quizapp.domain.di.domainModule

class QuizApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@QuizApplication)

            modules(
                dataAndroidModule,
                dataModule,
                domainModule,
                applicationModule,
            )
        }
    }
}
