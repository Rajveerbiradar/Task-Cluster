package com.journeytix.taskcluster.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeytix.taskcluster.data.preferences.Settings
import com.journeytix.taskcluster.data.preferences.UserPreferences
import com.journeytix.taskcluster.data.repository.TaskRepository
import com.journeytix.taskcluster.data.export.TaskExporter
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/* Settings options — stored value to display label, in menu order. */
enum class SettingKey(val options: List<Pair<String, String>>) {
    Theme(listOf("light" to "Light", "dark" to "Dark", "system" to "System")),
    DefaultPriority(listOf("none" to "None", "low" to "Low", "medium" to "Medium", "high" to "High")),
    DefaultSort(listOf("date_added" to "Date added", "due_date" to "Due date", "priority" to "Priority")),
    AutoHideCompleted(listOf("off" to "Off", "1" to "1 day", "3" to "3 days", "7" to "7 days")),
    Reminders(listOf("on" to "On", "off" to "Off")),
    ReminderOffset(
        listOf(
            "0" to "At the time",
            "10" to "10 min before",
            "30" to "30 min before",
            "60" to "1 hour before",
            "morning" to "Morning of (9:00)",
        )
    ),
    TrashAutoDelete(listOf("7" to "7 days", "14" to "14 days", "30" to "30 days", "never" to "Never"));

    fun labelFor(value: String): String = options.firstOrNull { it.first == value }?.second ?: value
}

sealed interface SettingsIntent {
    data class SetOption(val key: SettingKey, val value: String) : SettingsIntent
    data object ClearCompleted : SettingsIntent
    data object Reset : SettingsIntent
}

class SettingsViewModel(
    private val preferences: UserPreferences,
    private val repository: TaskRepository,
) : ViewModel() {

    val settings: StateFlow<Settings> = preferences.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Settings())

    fun onIntent(intent: SettingsIntent) {
        viewModelScope.launch {
            when (intent) {
                is SettingsIntent.SetOption -> when (intent.key) {
                    SettingKey.Theme -> preferences.setTheme(intent.value)
                    SettingKey.DefaultPriority -> preferences.setDefaultPriority(intent.value)
                    SettingKey.DefaultSort -> preferences.setDefaultSort(intent.value)
                    SettingKey.AutoHideCompleted -> preferences.setAutoHideCompleted(intent.value)
                    SettingKey.Reminders -> preferences.setRemindersEnabled(intent.value == "on")
                    SettingKey.ReminderOffset -> preferences.setReminderOffset(intent.value)
                    SettingKey.TrashAutoDelete -> preferences.setTrashAutoDelete(intent.value)
                }
                SettingsIntent.ClearCompleted -> repository.trashAllCompleted()
                SettingsIntent.Reset -> preferences.resetToDefaults()
            }
        }
    }

    suspend fun exportTasks(): String {
        val parents = repository.observeParents().first()
        val sections = repository.observeSections().first()
        val tasks = repository.observeActiveTasks().first()
        return TaskExporter.export(parents, sections, tasks)
    }
}
