package com.journeytix.taskcluster.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.data.model.Parent
import com.journeytix.taskcluster.ui.components.core.SectionIcons
import com.journeytix.taskcluster.ui.components.core.TaskIconButton
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.components.feedback.ContextMenuItem
import com.journeytix.taskcluster.ui.components.feedback.TaskBanner
import com.journeytix.taskcluster.ui.components.feedback.TaskBannerVariant
import com.journeytix.taskcluster.ui.components.feedback.TaskContextMenu
import com.journeytix.taskcluster.ui.components.planner.BottomBar
import com.journeytix.taskcluster.ui.components.planner.BottomBarMode
import com.journeytix.taskcluster.ui.components.planner.DateStrip
import com.journeytix.taskcluster.ui.components.planner.DateStripDay
import com.journeytix.taskcluster.ui.components.planner.ParentSection
import com.journeytix.taskcluster.ui.components.planner.SectionCard
import com.journeytix.taskcluster.ui.components.planner.TaskRow
import com.journeytix.taskcluster.ui.theme.Canvas
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Gutter
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.ScreenMax
import com.journeytix.taskcluster.ui.theme.Space3
import com.journeytix.taskcluster.ui.theme.TrackTight
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import org.koin.androidx.compose.koinViewModel

private val HeaderDateFormat = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)
private const val DAILY_PARENT_ID = -1L

private fun weekOf(date: LocalDate): List<DateStripDay> {
    val monday = date.minusDays((date.dayOfWeek.value - 1).toLong())
    return (0..6).map { i ->
        val day = monday.plusDays(i.toLong())
        if (day.month != date.month) {
            DateStripDay(key = "p$i", weekday = "", date = 0, isPlaceholder = true)
        } else {
            DateStripDay(
                key = day.toString(),
                weekday = day.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH),
                date = day.dayOfMonth,
            )
        }
    }
}

@Composable
fun HomeScreen(
    onOpenSettings: () -> Unit,
    onOpenTrash: () -> Unit,
    onOpenAbout: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var menuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var menuButtonOrigin by remember { mutableStateOf(Offset.Zero) }
    val now = remember { System.currentTimeMillis() }
    var parentMenuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var menuParent by remember { mutableStateOf<Parent?>(null) }
    var sectionMenuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var menuSectionId by remember { mutableStateOf<Long?>(null) }
    var emojiTarget by remember { mutableStateOf<Pair<Long, IntOffset>?>(null) }
    var iconTarget by remember { mutableStateOf<Pair<Long, IntOffset>?>(null) }
    var showAddChooser by remember { mutableStateOf(false) }
    var showNameForm by remember { mutableStateOf<NameFormMode?>(null) }
    var nameFormParentId by remember { mutableStateOf<Long?>(null) }
    var renameParentId by remember { mutableStateOf<Long?>(null) }
    var renameSectionId by remember { mutableStateOf<Long?>(null) }
    var renameInitialTitle by remember { mutableStateOf("") }
    var addTaskSectionId by remember { mutableStateOf<Long?>(null) }
    var showImportPicker by remember { mutableStateOf(false) }
    var showCalendar by remember { mutableStateOf(false) }
    var showSearch by remember { mutableStateOf(false) }
    var taskMenuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var menuTaskId by remember { mutableStateOf<Long?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = Gutter)
                .widthIn(max = ScreenMax)
                .align(Alignment.TopCenter)
                .padding(top = 26.dp, bottom = 140.dp),
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Task Cluster",
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W500,
                        fontSize = 32.sp,
                        letterSpacing = TrackTight,
                    ),
                    color = Ink900,
                )
                Box(
                    modifier = Modifier.onGloballyPositioned {
                        menuButtonOrigin = it.positionInRoot()
                    },
                ) {
                    TaskIconButton(
                        icon = TaskIcons.MoreVertical,
                        label = "Open menu",
                        size = 36.dp,
                        onClick = {
                            menuAnchor = IntOffset(
                                menuButtonOrigin.x.toInt(),
                                menuButtonOrigin.y.toInt() + 36,
                            )
                        },
                    )
                }
            }
            Text(
                text = when {
                    state.page == HomePage.Tasks -> "Tasks"
                    state.isToday -> "Today"
                    else -> state.selected.format(HeaderDateFormat)
                },
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                ),
                color = Ink500,
                modifier = Modifier.padding(top = 4.dp, bottom = 18.dp),
            )

            if (state.page == HomePage.Home) {
                DateStrip(
                    days = weekOf(state.selected),
                    selectedKey = state.selected.toString(),
                    todayKey = state.today.toString(),
                    onSelect = { key ->
                        viewModel.onIntent(HomeIntent.SelectDate(LocalDate.parse(key)))
                    },
                    onCalendar = { showCalendar = true },
                )
                if (state.isReadOnly || state.isPlanning) {
                    TaskBanner(
                        variant = if (state.isReadOnly) {
                            TaskBannerVariant.ReadOnly
                        } else {
                            TaskBannerVariant.Planning
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp),
                    )
                }
            }

            when {
                state.isPlanning -> Text(
                    text = "Nothing planned for this day yet.",
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W400,
                        fontSize = 15.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center,
                    ),
                    color = Ink400,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 40.dp),
                )
                state.page == HomePage.Home -> Column(
                    modifier = Modifier.padding(top = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(Space3),
                ) {
                    // Daily — pinned, collapsible, emoji editable, never deletable.
                    ParentSection(
                        title = "daily",
                        expanded = state.dailyExpanded,
                        onToggle = { viewModel.onIntent(HomeIntent.ToggleDaily) },
                        pinned = true,
                        emoji = state.dailyEmoji,
                        onEmojiClick = { anchor -> emojiTarget = DAILY_PARENT_ID to anchor },
                        count = state.daily.size.toString(),
                        modifier = Modifier.testTag("daily-parent"),
                    ) {
                        state.daily.forEach { SectionBlock(
                            it, viewModel, now,
                            onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                            onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                            onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                        ) }
                    }
                    val favourites = state.parents.filter { it.parent.isFavourite }
                    favourites.forEach { ParentBlock(
                        it.parent, it.sections, viewModel, now,
                        onMenu = { p, anchor -> menuParent = p; parentMenuAnchor = anchor },
                        onEmojiClick = { pId, anchor -> emojiTarget = pId to anchor },
                        onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                        onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                        onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                    ) }
                    if (favourites.isEmpty()) {
                        Text(
                            text = "Pin parents from the Tasks tab to see them here.",
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                textAlign = TextAlign.Center,
                            ),
                            color = Ink400,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 24.dp),
                        )
                    }
                }
                else -> Column(
                    modifier = Modifier.padding(top = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(Space3),
                ) {
                    state.parents.forEach { ParentBlock(
                        it.parent, it.sections, viewModel, now,
                        onMenu = { p, anchor -> menuParent = p; parentMenuAnchor = anchor },
                        onEmojiClick = { pId, anchor -> emojiTarget = pId to anchor },
                        onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                        onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                        onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                    ) }
                    state.standalone.forEach { SectionBlock(
                        it, viewModel, now,
                        onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                        onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                        onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                    ) }
                }
            }
        }

        BottomBar(
            mode = if (state.page == HomePage.Home) BottomBarMode.Home else BottomBarMode.Tasks,
            onModeChange = { mode ->
                when (mode) {
                    BottomBarMode.Home -> viewModel.onIntent(HomeIntent.SetPage(HomePage.Home))
                    BottomBarMode.Tasks -> viewModel.onIntent(HomeIntent.SetPage(HomePage.Tasks))
                    BottomBarMode.Search -> { showSearch = true }
                    BottomBarMode.Add -> { showAddChooser = true }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
        )

        TaskContextMenu(
            open = menuAnchor != null,
            anchor = menuAnchor ?: IntOffset.Zero,
            onClose = { menuAnchor = null },
            items = listOf(
                ContextMenuItem(label = "Settings", icon = TaskIcons.Settings, onClick = onOpenSettings),
                ContextMenuItem(label = "Trash", icon = TaskIcons.Trash, onClick = onOpenTrash),
                ContextMenuItem(label = "About", icon = TaskIcons.Info, onClick = onOpenAbout),
                ContextMenuItem(label = "Export", icon = TaskIcons.Import, onClick = { /* export sheet — TODO */ }),
            ),
        )

        TaskContextMenu(
            open = parentMenuAnchor != null,
            anchor = parentMenuAnchor ?: IntOffset.Zero,
            onClose = { parentMenuAnchor = null },
            items = menuParent?.let { p ->
                listOf(
                    ContextMenuItem(label = "Add section", icon = TaskIcons.Plus, onClick = {
                        nameFormParentId = p.id
                        showNameForm = NameFormMode.Section
                    }),
                    ContextMenuItem(label = "Rename", icon = TaskIcons.Pencil, onClick = {
                        renameParentId = p.id
                        renameInitialTitle = p.title
                        showNameForm = NameFormMode.RenameParent
                    }),
                    ContextMenuItem(label = "Emoji", icon = TaskIcons.Smile, onClick = {
                        emojiTarget = p.id to (parentMenuAnchor ?: IntOffset.Zero)
                    }),
                    ContextMenuItem(
                        label = if (p.isFavourite) "Unpin" else "Pin",
                        icon = TaskIcons.Pin,
                        onClick = { viewModel.onIntent(HomeIntent.ToggleFavourite(p.id)) },
                    ),
                    ContextMenuItem(label = "Delete", icon = TaskIcons.Trash, danger = true, onClick = {
                        viewModel.onIntent(HomeIntent.DeleteParent(p.id))
                    }),
                )
            } ?: emptyList(),
        )

        // Section context menu
        TaskContextMenu(
            open = sectionMenuAnchor != null,
            anchor = sectionMenuAnchor ?: IntOffset.Zero,
            onClose = { sectionMenuAnchor = null },
            items = menuSectionId?.let { sId ->
                val isDaily = state.daily.any { it.section.id == sId }
                val sectionTitle = state.daily.find { it.section.id == sId }?.section?.title
                    ?: state.parents.flatMap { it.sections }.find { it.section.id == sId }?.section?.title
                    ?: state.standalone.find { it.section.id == sId }?.section?.title
                    ?: ""
                buildList {
                    add(ContextMenuItem(label = "Add task", icon = TaskIcons.Plus, onClick = {
                        addTaskSectionId = sId
                    }))
                    add(ContextMenuItem(label = "Icon", icon = TaskIcons.Image, onClick = {
                        iconTarget = sId to (sectionMenuAnchor ?: IntOffset.Zero)
                    }))
                    add(ContextMenuItem(label = "Rename", icon = TaskIcons.Pencil, onClick = {
                        renameSectionId = sId
                        renameInitialTitle = sectionTitle
                        showNameForm = NameFormMode.RenameSection
                    }))
                    if (!isDaily) {
                        add(ContextMenuItem(label = "Delete", icon = TaskIcons.Trash, danger = true, onClick = {
                            viewModel.onIntent(HomeIntent.DeleteSection(sId))
                        }))
                    }
                }
            } ?: emptyList(),
        )

        // Task context menu
        TaskContextMenu(
            open = taskMenuAnchor != null,
            anchor = taskMenuAnchor ?: IntOffset.Zero,
            onClose = { taskMenuAnchor = null; menuTaskId = null },
            items = menuTaskId?.let { tId ->
                listOf(
                    ContextMenuItem(label = "Edit", icon = TaskIcons.Pencil, onClick = {
                        addTaskSectionId = state.daily.flatMap { it.tasks }.find { it.id == tId }?.sectionId
                            ?: state.parents.flatMap { p -> p.sections.flatMap { it.tasks } }.find { it.id == tId }?.sectionId
                            ?: state.standalone.flatMap { it.tasks }.find { it.id == tId }?.sectionId
                    }),
                    ContextMenuItem(label = "Delete", icon = TaskIcons.Trash, danger = true, onClick = {
                        viewModel.onIntent(HomeIntent.TrashTask(tId))
                    }),
                )
            } ?: emptyList(),
        )

        // AddChooser overlay
        if (showAddChooser) {
            AddChooser(
                onSelect = { option ->
                    showAddChooser = false
                    when (option) {
                        AddChooserOption.NewParent -> showNameForm = NameFormMode.Parent
                        AddChooserOption.NewSection -> {
                            nameFormParentId = null
                            showNameForm = NameFormMode.Section
                        }
                        AddChooserOption.Import -> { showImportPicker = true }
                    }
                },
                onDismiss = { showAddChooser = false },
            )
        }

        // NameForm overlay
        showNameForm?.let { mode ->
            NameForm(
                mode = mode,
                initialTitle = renameInitialTitle,
                onConfirm = { title ->
                    when (mode) {
                        NameFormMode.Parent -> {
                            val scheduledDate = if (state.selected.isAfter(state.today)) state.selected.toString() else null
                            viewModel.onIntent(HomeIntent.CreateParent(title, null, scheduledDate))
                        }
                        NameFormMode.Section -> {
                            val scheduledDate = if (state.selected.isAfter(state.today)) state.selected.toString() else null
                            viewModel.onIntent(HomeIntent.CreateSection(title, nameFormParentId, null, scheduledDate))
                        }
                        NameFormMode.RenameParent -> {
                            renameParentId?.let { viewModel.onIntent(HomeIntent.RenameParent(it, title)) }
                        }
                        NameFormMode.RenameSection -> {
                            renameSectionId?.let { viewModel.onIntent(HomeIntent.RenameSection(it, title)) }
                        }
                    }
                    showNameForm = null
                    nameFormParentId = null
                    renameParentId = null
                    renameSectionId = null
                    renameInitialTitle = ""
                },
                onDismiss = {
                    showNameForm = null
                    nameFormParentId = null
                    renameParentId = null
                    renameSectionId = null
                    renameInitialTitle = ""
                },
            )
        }

        // AddTaskSheet
        addTaskSectionId?.let { sectionId ->
            AddTaskSheet(
                open = true,
                sectionId = sectionId,
                onDismiss = { addTaskSectionId = null },
                onSave = { title, description, dueDate, dueTime ->
                    viewModel.onIntent(
                        HomeIntent.AddTask(
                            sectionId = sectionId,
                            title = title,
                            description = description,
                            dueDate = dueDate,
                            dueTime = dueTime,
                        )
                    )
                    addTaskSectionId = null
                },
            )
        }

        // EmojiPicker
        emojiTarget?.let { (parentId, _) ->
            EmojiPicker(
                onSelect = { emoji ->
                    if (parentId == DAILY_PARENT_ID) {
                        viewModel.onIntent(HomeIntent.SetDailyEmoji(emoji))
                    } else {
                        viewModel.onIntent(HomeIntent.SetParentEmoji(parentId, emoji))
                    }
                    emojiTarget = null
                },
                onDismiss = { emojiTarget = null },
            )
        }

        // IconPicker
        iconTarget?.let { (sectionId, _) ->
            IconPicker(
                onSelect = { iconKey ->
                    viewModel.onIntent(HomeIntent.SetSectionIcon(sectionId, iconKey))
                    iconTarget = null
                },
                onDismiss = { iconTarget = null },
            )
        }

        // SearchOverlay
        if (showSearch) {
            val allTasks = remember(state) {
                val list = mutableListOf<Pair<com.journeytix.taskcluster.data.model.Task, String>>()
                state.daily.forEach { s -> s.tasks.forEach { t -> list.add(t to "Daily · ${s.section.title}") } }
                state.parents.forEach { p -> p.sections.forEach { s -> s.tasks.forEach { t -> list.add(t to "${p.parent.title} · ${s.section.title}") } } }
                state.standalone.forEach { s -> s.tasks.forEach { t -> list.add(t to s.section.title) } }
                list
            }
            SearchOverlay(
                allTasks = allTasks,
                onDismiss = { showSearch = false },
                onTaskClick = { showSearch = false },
            )
        }

        // CalendarPopup
        if (showCalendar) {
            CalendarPopup(
                selectedDate = state.selected,
                today = state.today,
                onDateSelected = { date ->
                    viewModel.onIntent(HomeIntent.SelectDate(date))
                    showCalendar = false
                },
                onDismiss = { showCalendar = false },
            )
        }
    }
}

@Composable
private fun ParentBlock(
    parent: Parent,
    sections: List<SectionWithTasks>,
    viewModel: HomeViewModel,
    now: Long,
    onMenu: (Parent, IntOffset) -> Unit,
    onEmojiClick: (Long, IntOffset) -> Unit,
    onSectionMenu: (Long, IntOffset) -> Unit,
    onIconClick: (Long, IntOffset) -> Unit,
    onTaskMenu: (Long, IntOffset) -> Unit,
) {
    val state by viewModel.state.collectAsState()
    ParentSection(
        title = parent.title,
        expanded = parent.id in state.expandedParents,
        onToggle = { viewModel.onIntent(HomeIntent.ToggleParent(parent.id)) },
        count = sections.size.toString(),
        favourite = parent.isFavourite,
        emoji = parent.emoji,
        onMenu = { anchor -> onMenu(parent, anchor) },
        onEmojiClick = { anchor -> onEmojiClick(parent.id, anchor) },
    ) {
        sections.forEach { SectionBlock(it, viewModel, now, onSectionMenu, onIconClick, onTaskMenu) }
    }
}

@Composable
private fun SectionBlock(
    sectionWithTasks: SectionWithTasks,
    viewModel: HomeViewModel,
    now: Long,
    onSectionMenu: ((Long, IntOffset) -> Unit)? = null,
    onIconClick: ((Long, IntOffset) -> Unit)? = null,
    onTaskMenu: ((Long, IntOffset) -> Unit)? = null,
) {
    val state by viewModel.state.collectAsState()
    val section = sectionWithTasks.section
    SectionCard(
        title = section.title,
        iconResId = SectionIcons.resIdOrDefault(section.iconKey),
        expanded = section.id in state.expandedSections,
        onToggle = { viewModel.onIntent(HomeIntent.ToggleSection(section.id)) },
        done = sectionWithTasks.done,
        total = sectionWithTasks.total,
        pinned = section.isDaily,
        onMenu = if (onSectionMenu != null) { { anchor -> onSectionMenu(section.id, anchor) } } else null,
        onIconClick = if (onIconClick != null) { { anchor -> onIconClick(section.id, anchor) } } else null,
    ) {
        if (sectionWithTasks.tasks.isEmpty()) {
            Text(
                text = "No tasks yet — press and hold the title to add one.",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                color = Ink400,
                modifier = Modifier.padding(top = 10.dp, bottom = 6.dp),
            )
        } else {
            sectionWithTasks.tasks.forEachIndexed { index, task ->
                val (status, time) = timePillFor(task, now)
                TaskRow(
                    title = task.title,
                    description = task.description,
                    checked = task.isCompleted,
                    status = status,
                    time = time,
                    divider = index > 0,
                    onToggle = { checked ->
                        viewModel.onIntent(HomeIntent.ToggleTask(task, checked))
                    },
                    onMenu = if (onTaskMenu != null) { { anchor -> onTaskMenu(task.id, anchor) } } else null,
                )
            }
        }
    }
}
