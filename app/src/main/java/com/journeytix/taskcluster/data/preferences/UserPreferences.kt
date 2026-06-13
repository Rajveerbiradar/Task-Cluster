package com.journeytix.taskcluster.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object PreferenceKeys {
    val THEME = stringPreferencesKey("theme") // "light"|"dark"|"system"
    val DEFAULT_PRIORITY = stringPreferencesKey("default_priority") // "none"|"low"|"medium"|"high"
    val DEFAULT_SORT = stringPreferencesKey("default_sort") // "date_added"|"due_date"|"priority"
    val AUTO_HIDE_COMPLETED = stringPreferencesKey("auto_hide") // "off"|"1"|"3"|"7"
    val REMINDERS_ENABLED = booleanPreferencesKey("reminders")
    val REMINDER_OFFSET = stringPreferencesKey("reminder_offset") // "0"|"10"|"30"|"60"|"morning"
    val TRASH_AUTO_DELETE = stringPreferencesKey("trash_ttl") // "7"|"14"|"30"|"never"
}

data class Settings(
    val theme: String = "system",
    val defaultPriority: String = "none",
    val defaultSort: String = "date_added",
    val autoHideCompleted: String = "off",
    val remindersEnabled: Boolean = false,
    val reminderOffset: String = "0",
    val trashAutoDelete: String = "30",
)

private val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferences(private val context: Context) {

    val settings: Flow<Settings> = context.dataStore.data.map { prefs ->
        Settings(
            theme = prefs[PreferenceKeys.THEME] ?: "system",
            defaultPriority = prefs[PreferenceKeys.DEFAULT_PRIORITY] ?: "none",
            defaultSort = prefs[PreferenceKeys.DEFAULT_SORT] ?: "date_added",
            autoHideCompleted = prefs[PreferenceKeys.AUTO_HIDE_COMPLETED] ?: "off",
            remindersEnabled = prefs[PreferenceKeys.REMINDERS_ENABLED] ?: false,
            reminderOffset = prefs[PreferenceKeys.REMINDER_OFFSET] ?: "0",
            trashAutoDelete = prefs[PreferenceKeys.TRASH_AUTO_DELETE] ?: "30",
        )
    }

    suspend fun setTheme(value: String) = set(PreferenceKeys.THEME, value)
    suspend fun setDefaultPriority(value: String) = set(PreferenceKeys.DEFAULT_PRIORITY, value)
    suspend fun setDefaultSort(value: String) = set(PreferenceKeys.DEFAULT_SORT, value)
    suspend fun setAutoHideCompleted(value: String) = set(PreferenceKeys.AUTO_HIDE_COMPLETED, value)
    suspend fun setReminderOffset(value: String) = set(PreferenceKeys.REMINDER_OFFSET, value)
    suspend fun setTrashAutoDelete(value: String) = set(PreferenceKeys.TRASH_AUTO_DELETE, value)

    suspend fun setRemindersEnabled(value: Boolean) {
        context.dataStore.edit { it[PreferenceKeys.REMINDERS_ENABLED] = value }
    }

    suspend fun resetToDefaults() {
        context.dataStore.edit { it.clear() }
    }

    private suspend fun set(
        key: androidx.datastore.preferences.core.Preferences.Key<String>,
        value: String,
    ) {
        context.dataStore.edit { it[key] = value }
    }
}
