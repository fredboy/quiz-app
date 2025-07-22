package ru.fredboy.quizapp.data.android.source.local.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class QuizCachePrefsDataStore(
    private val dataStore: DataStore<Preferences>,
) {

    suspend fun getLastUpdateTimestamp(): Long {
        return dataStore.data.map { preferences ->
            preferences[KEY_QUIZ_LAST_UPDATE] ?: 0L
        }.first()
    }

    suspend fun setLastUpdateTimestamp(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_QUIZ_LAST_UPDATE] = timestamp
        }
    }

    companion object {
        private val KEY_QUIZ_LAST_UPDATE = longPreferencesKey("quiz_cache_last_update")
    }
}
