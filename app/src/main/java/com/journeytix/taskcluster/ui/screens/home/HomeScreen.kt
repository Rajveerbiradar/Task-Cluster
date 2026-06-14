package com.journeytix.taskcluster.ui.screens.home

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.launch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
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
import com.journeytix.taskcluster.data.export.TaskExporter
import com.journeytix.taskcluster.data.image.ParentImage
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

// Highlighted task id for the search "reveal & blink" flow (null = none).
private val LocalBlinkTaskId = androidx.compose.runtime.compositionLocalOf<Long?> { null }

// Which entity to scroll into view, e.g. "task:12" / "section:4" / "parent:7".
private val LocalRevealKey = androidx.compose.runtime.compositionLocalOf<String?> { null }

// Centers a target in the scroll viewport: (targetTopInRoot, targetHeightPx).
private val LocalRevealScroll = androidx.compose.runtime.compositionLocalOf<(Float, Int) -> Unit> { { _, _ -> } }

/* A top-level row — either a parent or a standalone section — so the two can be
   interleaved in pure created/imported order instead of all parents then all
   sections. */
/* Where an import should land. null = global (new top-level items). */
private sealed interface ImportScope {
    data class IntoParent(val parentId: Long?, val isDaily: Boolean) : ImportScope
    data class IntoSection(val sectionId: Long) : ImportScope
}

private sealed interface TopLevelEntry {
    val createdAt: Long
    data class ParentEntry(val data: ParentWithSections) : TopLevelEntry {
        override val createdAt get() = data.parent.createdAt
    }
    data class SectionEntry(val data: SectionWithTasks) : TopLevelEntry {
        override val createdAt get() = data.section.createdAt
    }
}

/* When [isTarget] is the searched entity, measure it and center it in the scroll
   viewport (after a short settle for expand animations). */
@Composable
private fun revealModifier(isTarget: Boolean): Modifier {
    val scroll = LocalRevealScroll.current
    var top by remember { mutableStateOf(0f) }
    var heightPx by remember { mutableStateOf(0) }
    LaunchedEffect(isTarget, heightPx) {
        if (isTarget && heightPx > 0) {
            kotlinx.coroutines.delay(300) // let expand/collapse settle
            scroll(top, heightPx)
        }
    }
    return Modifier.onGloballyPositioned { c ->
        top = c.positionInRoot().y
        heightPx = c.size.height
    }
}

// Opens the full-detail dialog for a tapped task (titles/descriptions truncate in the row).
private val LocalOpenTask = androidx.compose.runtime.compositionLocalOf<(com.journeytix.taskcluster.data.model.Task) -> Unit> { {} }

private fun weekOf(date: LocalDate): List<DateStripDay> {
    val monday = date.minusDays((date.dayOfWeek.value - 1).toLong())
    // Always seven real days (Mon–Sun), even when the week spans two months.
    return (0..6).map { i ->
        val day = monday.plusDays(i.toLong())
        DateStripDay(
            key = day.toString(),
            weekday = day.dayOfWeek.getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH),
            date = day.dayOfMonth,
        )
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
    val context = LocalContext.current
    var showImport by remember { mutableStateOf(false) }
    var importConflict by remember { mutableStateOf<TaskExporter.ImportData?>(null) }
    var showAddMethod by remember { mutableStateOf(false) }
    var pendingManualAdd by remember { mutableStateOf<(() -> Unit)?>(null) }
    var importScope by remember { mutableStateOf<ImportScope?>(null) }
    var showCalendar by remember { mutableStateOf(false) }
    var showSearch by remember { mutableStateOf(false) }
    var taskMenuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var menuTaskId by remember { mutableStateOf<Long?>(null) }
    var dailyMenuAnchor by remember { mutableStateOf<IntOffset?>(null) }
    var creatingDailySection by remember { mutableStateOf(false) }
    var blinkTaskId by remember { mutableStateOf<Long?>(null) }
    var blinkVisible by remember { mutableStateOf(false) }
    var detailTask by remember { mutableStateOf<com.journeytix.taskcluster.data.model.Task?>(null) }
    var showDownloadSkill by remember { mutableStateOf(false) }

    // Scroll + reveal plumbing.
    val scrollState = rememberScrollState()
    val revealScope = androidx.compose.runtime.rememberCoroutineScope()
    var columnTopPx by remember { mutableStateOf(0f) }
    var viewportPx by remember { mutableStateOf(0) }
    var revealKey by remember { mutableStateOf<String?>(null) }

    // Blink the revealed task twice, then clear.
    LaunchedEffect(blinkTaskId) {
        if (blinkTaskId != null) {
            repeat(2) {
                blinkVisible = true
                kotlinx.coroutines.delay(280)
                blinkVisible = false
                kotlinx.coroutines.delay(220)
            }
            blinkTaskId = null
        }
    }
    // Clear the reveal key once the scroll has had time to happen (so re-searching
    // the same item triggers it again).
    LaunchedEffect(revealKey) {
        if (revealKey != null) {
            kotlinx.coroutines.delay(1500)
            revealKey = null
        }
    }

    // Custom parent image: pick → resize/compress (PNG, keeps transparency) → store
    // in the parent's emoji slot as "img:<path>".
    var imagePickTarget by remember { mutableStateOf<Long?>(null) }
    val imageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        val target = imagePickTarget
        imagePickTarget = null
        if (uri != null && target != null) {
            revealScope.launch {
                val value = ParentImage.importAndCompress(context, uri)
                if (value != null) {
                    val old = if (target == DAILY_PARENT_ID) state.dailyEmoji
                        else state.parents.firstOrNull { it.parent.id == target }?.parent?.emoji
                    ParentImage.deleteIfImage(old)
                    if (target == DAILY_PARENT_ID) viewModel.onIntent(HomeIntent.SetDailyEmoji(value))
                    else viewModel.onIntent(HomeIntent.SetParentEmoji(target, value))
                } else {
                    Toast.makeText(context, "Couldn't load that image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val revealScroll: (Float, Int) -> Unit = { rowTop, rowH ->
        revealScope.launch {
            val target = (scrollState.value + (rowTop - columnTopPx) - viewportPx / 2f + rowH / 2f)
                .toInt()
                .coerceIn(0, scrollState.maxValue)
            scrollState.animateScrollTo(target)
        }
    }

    androidx.compose.runtime.CompositionLocalProvider(
        LocalBlinkTaskId provides blinkTaskId.takeIf { blinkVisible },
        LocalRevealKey provides revealKey,
        LocalRevealScroll provides revealScroll,
        LocalOpenTask provides { task -> detailTask = task },
    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Canvas)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    columnTopPx = it.positionInRoot().y
                    viewportPx = it.size.height
                }
                .verticalScroll(scrollState)
                .padding(horizontal = Gutter)
                .widthIn(max = ScreenMax)
                .align(Alignment.TopCenter)
                .padding(top = 26.dp, bottom = 220.dp),
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
                state.page == HomePage.Home -> Column(
                    modifier = Modifier.padding(top = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(Space3),
                ) {
                    // Daily — pinned, collapsible, emoji editable, never deletable.
                    val (dailyStatus, dailyTime) = aggregatePill(state.daily.flatMap { it.tasks }, now)
                    ParentSection(
                        title = "daily",
                        expanded = state.dailyExpanded,
                        onToggle = { viewModel.onIntent(HomeIntent.ToggleDaily) },
                        pinned = true,
                        emoji = state.dailyEmoji,
                        status = dailyStatus,
                        time = dailyTime,
                        onMenu = { anchor -> dailyMenuAnchor = anchor },
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
                    // On a future date, show everything scheduled for that day (not just
                    // favourites) so items added while planning are visible. Parents and
                    // standalone sections interleave in created order.
                    val favourites = state.parents.filter { it.parent.isFavourite }
                    val parentsToShow = if (state.isPlanning) state.parents else favourites
                    val homeEntries = (
                        parentsToShow.map { TopLevelEntry.ParentEntry(it) } +
                            (if (state.isPlanning) state.standalone else emptyList())
                                .map { TopLevelEntry.SectionEntry(it) }
                        ).sortedBy { it.createdAt }
                    homeEntries.forEach { entry ->
                        when (entry) {
                            is TopLevelEntry.ParentEntry -> ParentBlock(
                                entry.data.parent, entry.data.sections, viewModel, now,
                                onMenu = { p, anchor -> menuParent = p; parentMenuAnchor = anchor },
                                onEmojiClick = { pId, anchor -> emojiTarget = pId to anchor },
                                onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                                onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                                onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                            )
                            is TopLevelEntry.SectionEntry -> SectionBlock(
                                entry.data, viewModel, now,
                                onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                                onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                                onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                            )
                        }
                    }
                    val planningEmpty = state.isPlanning &&
                        parentsToShow.isEmpty() && state.standalone.isEmpty()
                    if (planningEmpty || (!state.isPlanning && favourites.isEmpty())) {
                        Text(
                            text = if (state.isPlanning) {
                                "Nothing planned for this day yet. Use add to plan ahead."
                            } else {
                                "Pin parents from the Tasks tab to see them here."
                            },
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
                    // Parents and standalone sections interleave in created/imported order.
                    val entries = (
                        state.parents.map { TopLevelEntry.ParentEntry(it) } +
                            state.standalone.map { TopLevelEntry.SectionEntry(it) }
                        ).sortedBy { it.createdAt }
                    entries.forEach { entry ->
                        when (entry) {
                            is TopLevelEntry.ParentEntry -> ParentBlock(
                                entry.data.parent, entry.data.sections, viewModel, now,
                                onMenu = { p, anchor -> menuParent = p; parentMenuAnchor = anchor },
                                onEmojiClick = { pId, anchor -> emojiTarget = pId to anchor },
                                onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                                onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                                onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                            )
                            is TopLevelEntry.SectionEntry -> SectionBlock(
                                entry.data, viewModel, now,
                                onSectionMenu = { sId, anchor -> menuSectionId = sId; sectionMenuAnchor = anchor },
                                onIconClick = { sId, anchor -> iconTarget = sId to anchor },
                                onTaskMenu = { tId, anchor -> menuTaskId = tId; taskMenuAnchor = anchor },
                            )
                        }
                    }
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
                ContextMenuItem(label = "Download SKILL", icon = TaskIcons.Import, onClick = { showDownloadSkill = true }),
            ),
        )

        TaskContextMenu(
            open = parentMenuAnchor != null,
            anchor = parentMenuAnchor ?: IntOffset.Zero,
            onClose = { parentMenuAnchor = null },
            items = menuParent?.let { p ->
                listOf(
                    ContextMenuItem(label = "Add section", icon = TaskIcons.Plus, onClick = {
                        pendingManualAdd = { nameFormParentId = p.id; showNameForm = NameFormMode.Section }
                        importScope = ImportScope.IntoParent(p.id, isDaily = false)
                        showAddMethod = true
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
                val sectionTitle = state.daily.find { it.section.id == sId }?.section?.title
                    ?: state.parents.flatMap { it.sections }.find { it.section.id == sId }?.section?.title
                    ?: state.standalone.find { it.section.id == sId }?.section?.title
                    ?: ""
                listOf(
                    ContextMenuItem(label = "Add task", icon = TaskIcons.Plus, onClick = {
                        pendingManualAdd = { addTaskSectionId = sId }
                        importScope = ImportScope.IntoSection(sId)
                        showAddMethod = true
                    }),
                    ContextMenuItem(label = "Icon", icon = TaskIcons.Image, onClick = {
                        iconTarget = sId to (sectionMenuAnchor ?: IntOffset.Zero)
                    }),
                    ContextMenuItem(label = "Rename", icon = TaskIcons.Pencil, onClick = {
                        renameSectionId = sId
                        renameInitialTitle = sectionTitle
                        showNameForm = NameFormMode.RenameSection
                    }),
                    // Daily's sections are deletable even though the daily parent isn't.
                    ContextMenuItem(label = "Delete", icon = TaskIcons.Trash, danger = true, onClick = {
                        viewModel.onIntent(HomeIntent.DeleteSection(sId))
                    }),
                )
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

        // Daily parent context menu (pinned — add section / emoji only)
        TaskContextMenu(
            open = dailyMenuAnchor != null,
            anchor = dailyMenuAnchor ?: IntOffset.Zero,
            onClose = { dailyMenuAnchor = null },
            items = listOf(
                ContextMenuItem(label = "Add section", icon = TaskIcons.Plus, onClick = {
                    pendingManualAdd = {
                        creatingDailySection = true
                        nameFormParentId = null
                        showNameForm = NameFormMode.Section
                    }
                    importScope = ImportScope.IntoParent(parentId = null, isDaily = true)
                    showAddMethod = true
                }),
                ContextMenuItem(label = "Emoji", icon = TaskIcons.Smile, onClick = {
                    emojiTarget = DAILY_PARENT_ID to (dailyMenuAnchor ?: IntOffset.Zero)
                }),
            ),
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
                        AddChooserOption.Import -> { importScope = null; showImport = true }
                    }
                },
                onDismiss = { showAddChooser = false },
            )
        }

        // Add-method chooser — manual entry, or import (file/paste).
        if (showAddMethod) {
            AddMethodChooser(
                onManual = {
                    val action = pendingManualAdd
                    showAddMethod = false
                    pendingManualAdd = null
                    importScope = null // manual entry isn't an import
                    action?.invoke()
                },
                onImport = {
                    showAddMethod = false
                    pendingManualAdd = null
                    showImport = true // keep importScope so the import lands in the right place
                },
                onDismiss = {
                    showAddMethod = false
                    pendingManualAdd = null
                    importScope = null
                },
            )
        }

        // Import overlay — paste JSON or pick a .txt file. The scope decides whether
        // it creates new top-level items or lands inside a parent/section.
        if (showImport) {
            ImportSheet(
                onImport = { raw ->
                    val data = runCatching { TaskExporter.parse(raw) }.getOrNull()
                    if (data != null && (data.parents.isNotEmpty() || data.standaloneSections.isNotEmpty())) {
                        when (val scope = importScope) {
                            is ImportScope.IntoSection -> {
                                viewModel.onIntent(HomeIntent.ImportTasksInto(scope.sectionId, data))
                                Toast.makeText(context, "Imported", Toast.LENGTH_SHORT).show()
                            }
                            is ImportScope.IntoParent -> {
                                viewModel.onIntent(HomeIntent.ImportSectionsInto(scope.parentId, scope.isDaily, data))
                                Toast.makeText(context, "Imported", Toast.LENGTH_SHORT).show()
                            }
                            null -> {
                                val dupes = data.parents
                                    .map { it.title.trim() }
                                    .filter { t -> state.parents.any { it.parent.title.equals(t, ignoreCase = true) } }
                                if (dupes.isEmpty()) {
                                    viewModel.onIntent(HomeIntent.Import(data))
                                    Toast.makeText(context, "Imported", Toast.LENGTH_SHORT).show()
                                } else {
                                    importConflict = data // resolve via dialog
                                }
                            }
                        }
                    } else {
                        Toast.makeText(context, "Couldn't read that — check the JSON", Toast.LENGTH_SHORT).show()
                    }
                    showImport = false
                    if (importConflict == null) importScope = null
                },
                onDismiss = { showImport = false; importScope = null },
            )
        }

        // Duplicate-parent resolution for an import.
        importConflict?.let { data ->
            val dupes = data.parents
                .map { it.title.trim() }
                .filter { t -> state.parents.any { it.parent.title.equals(t, ignoreCase = true) } }
            ImportConflictDialog(
                duplicateTitles = dupes,
                onResolve = { strategy ->
                    viewModel.onIntent(HomeIntent.Import(data, strategy))
                    Toast.makeText(context, "Imported", Toast.LENGTH_SHORT).show()
                    importConflict = null
                },
                onDismiss = { importConflict = null },
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
                            viewModel.onIntent(HomeIntent.CreateSection(title, nameFormParentId, null, scheduledDate, isDaily = creatingDailySection))
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
                    creatingDailySection = false
                },
                onDismiss = {
                    showNameForm = null
                    nameFormParentId = null
                    renameParentId = null
                    renameSectionId = null
                    renameInitialTitle = ""
                    creatingDailySection = false
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
                    // Replacing/removing an existing custom image — drop its file.
                    val old = if (parentId == DAILY_PARENT_ID) state.dailyEmoji
                        else state.parents.firstOrNull { it.parent.id == parentId }?.parent?.emoji
                    ParentImage.deleteIfImage(old)
                    if (parentId == DAILY_PARENT_ID) {
                        viewModel.onIntent(HomeIntent.SetDailyEmoji(emoji))
                    } else {
                        viewModel.onIntent(HomeIntent.SetParentEmoji(parentId, emoji))
                    }
                    emojiTarget = null
                },
                onAddImage = {
                    imagePickTarget = parentId
                    emojiTarget = null
                    imageLauncher.launch("image/*")
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

        // SearchOverlay — tasks, sections, and parents are all searchable.
        if (showSearch) {
            val searchItems = remember(state) {
                buildList {
                    // Tasks
                    state.daily.forEach { s -> s.tasks.forEach { t ->
                        val (st, tm) = timePillFor(t, now)
                        add(SearchItem("task:${t.id}", t.title, "Daily · ${s.section.title}", "${t.title} ${t.description}", st, tm))
                    } }
                    state.parents.forEach { p -> p.sections.forEach { s -> s.tasks.forEach { t ->
                        val (st, tm) = timePillFor(t, now)
                        add(SearchItem("task:${t.id}", t.title, "${p.parent.title} · ${s.section.title}", "${t.title} ${t.description}", st, tm))
                    } } }
                    state.standalone.forEach { s -> s.tasks.forEach { t ->
                        val (st, tm) = timePillFor(t, now)
                        add(SearchItem("task:${t.id}", t.title, s.section.title, "${t.title} ${t.description}", st, tm))
                    } }
                    // Sections
                    state.daily.forEach { s -> add(SearchItem("section:${s.section.id}", s.section.title, "Section · Daily", s.section.title)) }
                    state.parents.forEach { p -> p.sections.forEach { s -> add(SearchItem("section:${s.section.id}", s.section.title, "Section · ${p.parent.title}", s.section.title)) } }
                    state.standalone.forEach { s -> add(SearchItem("section:${s.section.id}", s.section.title, "Section · standalone", s.section.title)) }
                    // Parents
                    state.parents.forEach { p -> add(SearchItem("parent:${p.parent.id}", p.parent.title, "Parent", p.parent.title)) }
                }
            }
            SearchOverlay(
                items = searchItems,
                onDismiss = { showSearch = false },
                onSelect = { item ->
                    showSearch = false
                    val parts = item.id.split(":")
                    val kind = parts[0]
                    val id = parts.getOrNull(1)?.toLongOrNull()
                    when (kind) {
                        "task" -> if (id != null) {
                            val isDaily = state.daily.any { s -> s.tasks.any { it.id == id } }
                            val sectionId = (state.daily + state.standalone +
                                state.parents.flatMap { it.sections })
                                .firstOrNull { s -> s.tasks.any { it.id == id } }?.section?.id
                            val parentId = state.parents.firstOrNull { p ->
                                p.sections.any { s -> s.tasks.any { it.id == id } }
                            }?.parent?.id
                            viewModel.onIntent(HomeIntent.SetPage(if (isDaily) HomePage.Home else HomePage.Tasks))
                            viewModel.onIntent(HomeIntent.RevealTask(parentId, sectionId))
                            revealKey = "task:$id"
                            blinkTaskId = id
                        }
                        "section" -> if (id != null) {
                            val isDaily = state.daily.any { it.section.id == id }
                            val parentId = state.parents.firstOrNull { p ->
                                p.sections.any { it.section.id == id }
                            }?.parent?.id
                            viewModel.onIntent(HomeIntent.SetPage(if (isDaily) HomePage.Home else HomePage.Tasks))
                            viewModel.onIntent(HomeIntent.RevealTask(parentId, id))
                            revealKey = "section:$id"
                        }
                        "parent" -> if (id != null) {
                            val isFav = state.parents.firstOrNull { it.parent.id == id }?.parent?.isFavourite == true
                            viewModel.onIntent(HomeIntent.SetPage(if (isFav) HomePage.Home else HomePage.Tasks))
                            viewModel.onIntent(HomeIntent.RevealTask(id, null))
                            revealKey = "parent:$id"
                        }
                    }
                },
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

        // Task detail — full title + description (rows truncate).
        detailTask?.let { task ->
            TaskDetailDialog(
                task = task,
                now = now,
                onDismiss = { detailTask = null },
            )
        }

        // Download SKILL — instructions an AI uses to write an import file.
        if (showDownloadSkill) {
            DownloadSkillSheet(onDismiss = { showDownloadSkill = false })
        }
    }
    }
}

@Composable
private fun TaskDetailDialog(
    task: com.journeytix.taskcluster.data.model.Task,
    now: Long,
    onDismiss: () -> Unit,
) {
    val (status, time) = timePillFor(task, now)
    com.journeytix.taskcluster.ui.components.feedback.PopupShell(onDismiss = onDismiss) {
        Text(
            text = task.title,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
            ),
            color = Ink900,
        )
        if (task.description.isNotBlank()) {
            Text(
                text = task.description,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 22.sp,
                ),
                color = Ink700,
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        if (time != null && status != com.journeytix.taskcluster.ui.components.planner.TimePillStatus.Calm) {
            Box(modifier = Modifier.padding(top = 16.dp)) {
                com.journeytix.taskcluster.ui.components.planner.TimePill(status = status, label = time)
            }
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
    val (parentStatus, parentTime) = aggregatePill(sections.flatMap { it.tasks }, now)
    ParentSection(
        title = parent.title,
        expanded = parent.id in state.expandedParents,
        onToggle = { viewModel.onIntent(HomeIntent.ToggleParent(parent.id)) },
        count = sections.size.toString(),
        favourite = parent.isFavourite,
        emoji = parent.emoji,
        status = parentStatus,
        time = parentTime,
        modifier = revealModifier(LocalRevealKey.current == "parent:${parent.id}"),
        onMenu = { anchor -> onMenu(parent, anchor) },
        onEmojiClick = { anchor -> onEmojiClick(parent.id, anchor) },
    ) {
        if (sections.isEmpty()) {
            Text(
                text = "No sections yet — long press to add one.",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                ),
                color = Ink400,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            )
        } else {
            sections.forEach { SectionBlock(it, viewModel, now, onSectionMenu, onIconClick, onTaskMenu) }
        }
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
    val openTask = LocalOpenTask.current
    val section = sectionWithTasks.section
    val (sectionStatus, sectionTime) = aggregatePill(sectionWithTasks.tasks, now)
    SectionCard(
        title = section.title,
        iconResId = SectionIcons.resIdOrDefault(section.iconKey),
        expanded = section.id in state.expandedSections,
        onToggle = { viewModel.onIntent(HomeIntent.ToggleSection(section.id)) },
        done = sectionWithTasks.done,
        total = sectionWithTasks.total,
        status = sectionStatus,
        time = sectionTime,
        pinned = section.isDaily,
        modifier = revealModifier(LocalRevealKey.current == "section:${section.id}"),
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
            Text(
                text = "Tap a task to see its full detail.",
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                ),
                color = Ink400,
                modifier = Modifier.padding(top = 6.dp, bottom = 2.dp),
            )
            sectionWithTasks.tasks.forEachIndexed { index, task ->
                val (status, time) = timePillFor(task, now)
                TaskRow(
                    title = task.title,
                    description = task.description,
                    checked = task.isCompleted,
                    status = status,
                    time = time,
                    divider = index > 0,
                    highlighted = task.id == LocalBlinkTaskId.current,
                    modifier = revealModifier(LocalRevealKey.current == "task:${task.id}"),
                    onClick = { openTask(task) },
                    onToggle = { checked ->
                        viewModel.onIntent(HomeIntent.ToggleTask(task, checked))
                    },
                    onMenu = if (onTaskMenu != null) { { anchor -> onTaskMenu(task.id, anchor) } } else null,
                )
            }
        }
    }
}
