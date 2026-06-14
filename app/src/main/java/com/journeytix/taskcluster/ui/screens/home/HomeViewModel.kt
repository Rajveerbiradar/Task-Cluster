package com.journeytix.taskcluster.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journeytix.taskcluster.data.export.TaskExporter
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.data.model.Priority
import com.journeytix.taskcluster.data.model.Section
import com.journeytix.taskcluster.data.model.Task
import com.journeytix.taskcluster.data.repository.TaskRepository
import com.journeytix.taskcluster.ui.components.core.SectionIcons
import com.journeytix.taskcluster.ui.components.planner.TimePillStatus
import java.time.Instant
import java.time.LocalDate
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class HomePage { Home, Tasks }

/* How to resolve an imported parent whose title already exists. */
enum class ImportStrategy { Default, Rename, Replace, Skip }

data class SectionWithTasks(
    val section: Section,
    val tasks: List<Task>,
) {
    val done: Int get() = tasks.count { it.isCompleted }
    val total: Int get() = tasks.size
}

data class ParentWithSections(
    val parent: Parent,
    val sections: List<SectionWithTasks>,
)

data class HomeUiState(
    val today: LocalDate = LocalDate.now(),
    val selected: LocalDate = LocalDate.now(),
    val page: HomePage = HomePage.Home,
    val daily: List<SectionWithTasks> = emptyList(),
    val parents: List<ParentWithSections> = emptyList(),
    val standalone: List<SectionWithTasks> = emptyList(),
    val expandedSections: Set<Long> = emptySet(),
    val expandedParents: Set<Long> = emptySet(),
    val dailyExpanded: Boolean = true,
    val dailyEmoji: String? = null,
) {
    val isToday: Boolean get() = selected == today
    val isReadOnly: Boolean get() = selected.isBefore(today) // past — viewing only
    val isPlanning: Boolean get() = selected.isAfter(today) // future — hidden until then
}

sealed interface HomeIntent {
    data class SelectDate(val date: LocalDate) : HomeIntent
    data class SetPage(val page: HomePage) : HomeIntent
    data class ToggleTask(val task: Task, val checked: Boolean) : HomeIntent
    data class ToggleSection(val id: Long) : HomeIntent
    data class ToggleParent(val id: Long) : HomeIntent
    data class RevealTask(val parentId: Long?, val sectionId: Long?) : HomeIntent
    data class AddTask(
        val sectionId: Long,
        val title: String,
        val description: String,
        val dueDate: Long?,
        val dueTime: Long?,
    ) : HomeIntent

    // Creation
    data class CreateParent(val title: String, val emoji: String?, val scheduledDate: String?) : HomeIntent
    data class CreateSection(val title: String, val parentId: Long?, val iconKey: String?, val scheduledDate: String?, val isDaily: Boolean = false) : HomeIntent
    data class Import(
        val data: TaskExporter.ImportData,
        val strategy: ImportStrategy = ImportStrategy.Default,
    ) : HomeIntent
    // Scoped import: drop the file's sections under one parent (or daily), or its
    // tasks under one section — instead of creating new top-level items.
    data class ImportSectionsInto(
        val parentId: Long?,
        val isDaily: Boolean,
        val data: TaskExporter.ImportData,
        val strategy: ImportStrategy = ImportStrategy.Default,
    ) : HomeIntent
    data class ImportTasksInto(
        val sectionId: Long,
        val data: TaskExporter.ImportData,
    ) : HomeIntent

    // Parent actions
    data class AddSectionToParent(val parentId: Long) : HomeIntent
    data class RenameParent(val parentId: Long, val title: String) : HomeIntent
    data class ToggleFavourite(val parentId: Long) : HomeIntent
    data class SetParentEmoji(val parentId: Long, val emoji: String?) : HomeIntent
    data class DeleteParent(val parentId: Long) : HomeIntent

    // Section actions
    data class RenameSection(val sectionId: Long, val title: String) : HomeIntent
    data class SetSectionIcon(val sectionId: Long, val iconKey: String?) : HomeIntent
    data class DeleteSection(val sectionId: Long) : HomeIntent

    // Task actions
    data class TrashTask(val taskId: Long) : HomeIntent

    // Daily
    data object ToggleDaily : HomeIntent
    data class SetDailyEmoji(val emoji: String?) : HomeIntent
}

private data class LocalUi(
    val selected: LocalDate,
    val page: HomePage = HomePage.Home,
    val expandedSections: Set<Long> = emptySet(),
    val expandedParents: Set<Long> = emptySet(),
    val dailyExpanded: Boolean = true,
)

class HomeViewModel(
    private val repository: TaskRepository,
    private val userPreferences: com.journeytix.taskcluster.data.preferences.UserPreferences,
    private val today: LocalDate = LocalDate.now(),
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (repository.sectionCount() == 0) {
                repository.addSection(
                    Section(title = "morning", isDaily = true, iconKey = "coffee")
                )
            }
        }
    }

    private val localUi = MutableStateFlow(LocalUi(selected = today))

    val state: StateFlow<HomeUiState> = combine(
        repository.observeSections(),
        repository.observeActiveTasks(),
        repository.observeParents(),
        localUi,
        userPreferences.settings,
    ) { sections, tasks, parents, local, settings ->
        val selectedDateStr = local.selected.toString()
        val tasksBySection = tasks.groupBy { it.sectionId }
        fun withTasks(section: Section) =
            SectionWithTasks(section, tasksBySection[section.id].orEmpty())

        // Filter by date: show if scheduledDate is null (always visible) or matches selected date
        fun Section.matchesDate() = scheduledDate == null || scheduledDate == selectedDateStr
        fun Parent.matchesDate() = scheduledDate == null || scheduledDate == selectedDateStr

        val filteredSections = sections.filter { it.matchesDate() }
        val filteredParents = parents.filter { it.matchesDate() }

        HomeUiState(
            today = today,
            selected = local.selected,
            page = local.page,
            daily = filteredSections.filter { it.isDaily }.map(::withTasks),
            parents = filteredParents.map { parent ->
                ParentWithSections(
                    parent = parent,
                    sections = filteredSections
                        .filter { !it.isDaily && it.parentId == parent.id }
                        .map(::withTasks),
                )
            },
            standalone = filteredSections
                .filter { !it.isDaily && it.parentId == null }
                .map(::withTasks),
            expandedSections = local.expandedSections,
            expandedParents = local.expandedParents,
            dailyExpanded = local.dailyExpanded,
            dailyEmoji = settings.dailyEmoji,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        HomeUiState(today = today, selected = today),
    )

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectDate -> localUi.update { it.copy(selected = intent.date) }
            is HomeIntent.SetPage -> localUi.update { it.copy(page = intent.page) }
            is HomeIntent.ToggleSection -> localUi.update {
                it.copy(expandedSections = it.expandedSections.toggled(intent.id))
            }
            is HomeIntent.ToggleParent -> localUi.update {
                it.copy(expandedParents = it.expandedParents.toggled(intent.id))
            }
            is HomeIntent.RevealTask -> localUi.update {
                val sid = intent.sectionId
                it.copy(
                    dailyExpanded = if (intent.parentId == null && sid != null && state.value.daily.any { s -> s.section.id == sid }) true else it.dailyExpanded,
                    expandedParents = intent.parentId?.let { pid -> it.expandedParents + pid } ?: it.expandedParents,
                    expandedSections = sid?.let { s -> it.expandedSections + s } ?: it.expandedSections,
                )
            }
            is HomeIntent.ToggleTask -> {
                if (state.value.isReadOnly) return
                viewModelScope.launch {
                    repository.updateTask(
                        intent.task.copy(
                            isCompleted = intent.checked,
                            completedAt = if (intent.checked) System.currentTimeMillis() else null,
                        )
                    )
                }
            }
            is HomeIntent.AddTask -> {
                viewModelScope.launch {
                    repository.addTask(
                        Task(
                            sectionId = intent.sectionId,
                            title = intent.title,
                            description = intent.description,
                            dueDate = intent.dueDate,
                            dueTime = intent.dueTime,
                        )
                    )
                }
            }
            is HomeIntent.CreateParent -> viewModelScope.launch {
                repository.addParent(Parent(title = intent.title, emoji = intent.emoji, scheduledDate = intent.scheduledDate))
            }
            is HomeIntent.CreateSection -> viewModelScope.launch {
                repository.addSection(Section(title = intent.title, parentId = intent.parentId, iconKey = intent.iconKey, scheduledDate = intent.scheduledDate, isDaily = intent.isDaily))
            }
            is HomeIntent.Import -> viewModelScope.launch {
                val existing = state.value.parents.map { it.parent }
                val usedTitles = existing.map { it.title }.toMutableList()
                intent.data.parents
                    .filter { it.title.trim().lowercase() != "daily" } // "daily" is reserved
                    .forEach { p ->
                        val title = p.title.trim().take(80)
                        val dup = existing.firstOrNull { it.title.equals(title, ignoreCase = true) }
                        when {
                            dup == null -> insertImportedParent(p, title, usedTitles)
                            intent.strategy == ImportStrategy.Skip -> Unit
                            intent.strategy == ImportStrategy.Replace -> {
                                repository.deleteParentPermanently(dup.id)
                                insertImportedParent(p, title, usedTitles)
                            }
                            else -> insertImportedParent(p, uniqueTitle(title, usedTitles), usedTitles)
                        }
                    }
                intent.data.standaloneSections.forEach { insertImportedSection(it, null) }
            }
            is HomeIntent.ImportSectionsInto -> viewModelScope.launch {
                val incoming = intent.data.standaloneSections +
                    intent.data.parents.flatMap { it.sections }
                val existing = if (intent.isDaily) {
                    state.value.daily.map { it.section }
                } else {
                    state.value.parents.firstOrNull { it.parent.id == intent.parentId }
                        ?.sections?.map { it.section } ?: emptyList()
                }
                val used = existing.map { it.title }.toMutableList()
                incoming.forEach { s ->
                    val title = s.title.trim().take(80)
                    val dup = existing.firstOrNull { it.title.equals(title, ignoreCase = true) }
                    when {
                        dup == null -> {
                            used.add(title)
                            insertImportedSection(s, intent.parentId, intent.isDaily)
                        }
                        intent.strategy == ImportStrategy.Skip -> Unit
                        intent.strategy == ImportStrategy.Replace -> {
                            repository.deleteSectionPermanently(dup.id)
                            insertImportedSection(s, intent.parentId, intent.isDaily)
                        }
                        else -> {
                            val unique = uniqueTitle(title, used)
                            used.add(unique)
                            insertImportedSection(s.copy(title = unique), intent.parentId, intent.isDaily)
                        }
                    }
                }
            }
            is HomeIntent.ImportTasksInto -> viewModelScope.launch {
                val tasks = (intent.data.standaloneSections + intent.data.parents.flatMap { it.sections })
                    .flatMap { it.tasks }
                tasks.forEach { insertImportedTask(it, intent.sectionId) }
            }
            is HomeIntent.AddSectionToParent -> viewModelScope.launch {
                repository.addSection(Section(title = "new section", parentId = intent.parentId))
            }
            is HomeIntent.RenameParent -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(title = intent.title))
            }
            is HomeIntent.ToggleFavourite -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(isFavourite = !parent.isFavourite))
            }
            is HomeIntent.SetParentEmoji -> viewModelScope.launch {
                val parent = repository.getParent(intent.parentId) ?: return@launch
                repository.updateParent(parent.copy(emoji = intent.emoji))
            }
            is HomeIntent.DeleteParent -> viewModelScope.launch {
                repository.deleteParent(intent.parentId)
            }
            is HomeIntent.RenameSection -> viewModelScope.launch {
                val section = repository.getSection(intent.sectionId) ?: return@launch
                repository.updateSection(section.copy(title = intent.title))
            }
            is HomeIntent.SetSectionIcon -> viewModelScope.launch {
                val section = repository.getSection(intent.sectionId) ?: return@launch
                repository.updateSection(section.copy(iconKey = intent.iconKey))
            }
            is HomeIntent.DeleteSection -> viewModelScope.launch {
                repository.deleteSection(intent.sectionId)
            }
            is HomeIntent.TrashTask -> viewModelScope.launch {
                repository.trashTask(intent.taskId)
            }
            is HomeIntent.ToggleDaily -> localUi.update { it.copy(dailyExpanded = !it.dailyExpanded) }
            is HomeIntent.SetDailyEmoji -> viewModelScope.launch {
                userPreferences.setDailyEmoji(intent.emoji)
            }
        }
    }

    private fun Set<Long>.toggled(id: Long): Set<Long> =
        if (id in this) this - id else this + id

    // Imported parents are never auto-pinned — the user pins from the menu.
    private suspend fun insertImportedParent(
        p: TaskExporter.ImportParent,
        title: String,
        usedTitles: MutableList<String>,
    ) {
        usedTitles.add(title)
        val parentId = repository.addParent(
            Parent(title = title, emoji = p.emoji, isFavourite = false)
        )
        p.sections.forEach { insertImportedSection(it, parentId) }
    }

    private fun uniqueTitle(base: String, used: List<String>): String {
        if (used.none { it.equals(base, ignoreCase = true) }) return base
        var n = 2
        while (used.any { it.equals("$base ($n)", ignoreCase = true) }) n++
        return "$base ($n)".take(80)
    }

    private suspend fun insertImportedSection(
        s: TaskExporter.ImportSection,
        parentId: Long?,
        isDaily: Boolean = false,
    ) {
        val iconKey = s.icon?.takeIf { SectionIcons.resId(it) != null } // unknown icon -> default
        val sectionId = repository.addSection(
            Section(title = s.title.trim().take(80), parentId = parentId, iconKey = iconKey, isDaily = isDaily)
        )
        s.tasks.forEach { insertImportedTask(it, sectionId) }
    }

    private suspend fun insertImportedTask(t: TaskExporter.ImportTask, sectionId: Long) {
        val due = t.due?.let { runCatching { Instant.parse(it).toEpochMilli() }.getOrNull() }
        repository.addTask(
            Task(
                sectionId = sectionId,
                title = t.title.trim().take(200),
                description = t.description,
                isCompleted = t.completed,
                completedAt = if (t.completed) System.currentTimeMillis() else null,
                priority = parseImportPriority(t.priority),
                dueDate = due,
                dueTime = due,
            )
        )
    }

    private fun parseImportPriority(p: String?): Priority = when (p?.lowercase()) {
        "low" -> Priority.LOW
        "medium" -> Priority.MEDIUM
        "high" -> Priority.HIGH
        else -> Priority.NONE
    }
}

/* --- Time pill derivation ---
   calm (>50% of the window remaining) → nothing · on-track (>25%) → blue ·
   close (>10%) → amber · due → red · past due → overdue capsule counting up. */
internal fun timePillFor(task: Task, nowMs: Long): Pair<TimePillStatus, String?> {
    val due = task.dueTime ?: task.dueDate ?: return TimePillStatus.Calm to null
    val remaining = due - nowMs
    if (remaining < 0) {
        return TimePillStatus.Overdue to "−${formatDuration(-remaining)}"
    }
    val window = (due - task.createdAt).coerceAtLeast(1)
    val fraction = remaining.toDouble() / window
    // Always show remaining time when a deadline exists; colour ramps with urgency.
    val status = when {
        fraction > 0.5 -> TimePillStatus.OnTrack
        fraction > 0.25 -> TimePillStatus.OnTrack
        fraction > 0.1 -> TimePillStatus.Close
        else -> TimePillStatus.Due
    }
    return status to formatDuration(remaining)
}

/* Aggregate pill for a group of tasks — surfaces the single most urgent
   (soonest) deadline among the group's active tasks. */
internal fun aggregatePill(tasks: List<Task>, nowMs: Long): Pair<TimePillStatus, String?> {
    val soonest = tasks
        .filter { !it.isCompleted }
        .mapNotNull { t -> (t.dueTime ?: t.dueDate)?.let { it to t } }
        .minByOrNull { it.first }
        ?.second
        ?: return TimePillStatus.Calm to null
    return timePillFor(soonest, nowMs)
}

internal fun formatDuration(ms: Long): String {
    val minutes = ms / 60_000
    val hours = minutes / 60
    val days = hours / 24
    return when {
        days > 0 -> "${days}d ${hours % 24}h"
        hours > 0 -> "${hours}h ${minutes % 60}m"
        else -> "${minutes}m"
    }
}
