# CLAUDE.md — TaskCluster Android

> Read this entire file before writing a single line of code or modifying any file.
> This is the authoritative reference for the project. If anything contradicts this file, this file wins.

---

## Coding philosophy (Karpathy guidelines — enforced)

These are not suggestions. Follow them on every task, every file, every response.

**1. Think before coding. Surface assumptions. Never hide confusion.**
- State your assumptions before implementing. If uncertain, stop and ask.
- If multiple interpretations exist, list them — don't silently pick one.
- If a simpler approach exists, say so. Push back when the spec allows it.

**2. Goals and tests, not imperative instructions. Loop relentlessly.**
- Every task is a verifiable goal, not a step-by-step order.
- Before writing code, define what "done" looks like — a passing test, a visible behavior, a constraint being satisfied.
- Loop on that goal until it is verified. Don't stop at "looks about right."
- Transform tasks into test-first goals:
  - "Implement TaskRow" → "TaskRow renders title truncated at 1 line, description at 2 lines, correct priority color, and correct time pill state — verified by screenshot and logic test"
  - "Add confirm dialog" → "Every destructive action triggers ConfirmDialog before execution — verified by instrumented test"

**3. Minimum code. Nothing speculative.**
- No features beyond what was asked.
- No abstractions for single-use code.
- No "configurability" that wasn't requested.
- If you wrote 200 lines and it could be 50, rewrite it.
- Ask yourself: would a senior Android engineer say this is overcomplicated? If yes, simplify.

**4. Surgical changes. Touch only what you must.**
- Don't "improve" adjacent code, formatting, or comments.
- Don't refactor things that aren't broken.
- Match existing style, even if you'd do it differently.
- Every changed line must trace directly to the task.
- If your changes create unused imports or orphaned functions, remove them. Don't remove pre-existing dead code unless asked.

---

## What this project is

TaskCluster is a focused, single-screen Android task manager built in Kotlin Jetpack Compose.
It is a calm, sectioned planner — not a gamified productivity app. The emotional baseline is stillness.
Urgency communicates only through time pills, never through badges, sounds, or clutter.

The design system lives in the `design-reference/` folder. It is the source of truth for every
visual decision — colors, typography, spacing, component anatomy, motion, copy tone.

---

## Tools active in this project

### ECC (Everything Claude Code)
ECC is installed globally. Use the following automatically:

**Auto-fires (no invocation needed):**
- All 28 hooks fire automatically — GateGuard blocks file edits until the file is investigated, quality gate runs after every edit, session context saves on stop.
- `kotlin-patterns`, `kotlin-testing`, `android-clean-architecture`, `compose-multiplatform-patterns` skills auto-activate when relevant.

**Invoke explicitly as quality gates:**
- `/kotlin-review` — after completing any component or screen
- `/kotlin-test` — after any new feature (Kotest, 80%+ Kover coverage target)
- `/kotlin-build` — when Gradle/Kotlin compilation fails
- `/code-review` — before marking any step done
- `/plan` — at the start of any new screen or multi-file task (WAIT for CONFIRM before coding)

**ECC rules active:**
- `rules/common/` — always loaded (git, testing standards, security)
- `rules/kotlin/` — always loaded (Kotlin idioms, null safety, coroutines)

### Graphify
Graphify is installed. After the project skeleton is set up, run:
```
/graphify .
```
This builds a knowledge graph of the entire codebase. After that:
- Use `graphify query "..."` to find relationships between components before editing them
- Use `graphify explain "ClassName"` to understand a class before modifying it
- Graphify's hook auto-fires before search/read operations and nudges toward graph-first lookup
- Run `/graphify . --update` after any major structural change

### Karpathy Guidelines (SKILL.md)
The Karpathy guidelines SKILL.md is in `.claude/skills/karpathy-guidelines/SKILL.md`.
It auto-activates for any coding task. Its rules are already encoded at the top of this file.

### RTK (Token Optimization)
RTK is active via PreToolUse hook. It transparently compresses bash output.
- `rtk gain` — check token savings
- `rtk git status`, `rtk ls .`, `rtk grep` — use these explicitly if needed

---

## Project structure

```
app/
  src/main/
    java/com/yourname/taskcluster/
      ui/
        theme/           ← Color.kt, Type.kt, Theme.kt, Shape.kt
        screens/
          home/          ← HomeScreen.kt + HomeViewModel.kt
          settings/      ← SettingsScreen.kt
          trash/         ← TrashScreen.kt
          about/         ← AboutScreen.kt
          legal/         ← LegalWebViewScreen.kt
        components/
          core/          ← TaskButton.kt, TaskIconButton.kt, TaskBadge.kt
          planner/       ← TaskRow.kt, SectionCard.kt, ParentSection.kt,
                            DateStrip.kt, BottomBar.kt, TimePill.kt
          forms/         ← TaskCheckbox.kt, TaskTextField.kt, TaskTimeInput.kt
          feedback/      ← TaskBanner.kt, TaskToast.kt, TaskSnackbar.kt,
                            TaskContextMenu.kt
        navigation/      ← NavGraph.kt, Screen.kt
      data/
        db/              ← AppDatabase.kt, TaskDao.kt, SectionDao.kt, ParentDao.kt
        model/           ← Task.kt, Section.kt, Parent.kt, TrashItem.kt
        repository/      ← TaskRepository.kt
        preferences/     ← UserPreferences.kt (DataStore)
      di/                ← AppModule.kt (Koin)
design-reference/
  tcApp.jsx              ← Full UI reference (read this for every screen)
  tokens/                ← colors.css, typography.css, spacing.css
  components/            ← All component JSX files with exact values
  remaining-pages.md     ← Full feature spec for Settings, Trash, About, Legal
  readme.md              ← Design system rules and hard constraints
.claude/
  skills/
    karpathy-guidelines/ ← SKILL.md (Karpathy coding guidelines)
```

---

## Tech stack

| Layer | Choice |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Architecture | MVI (UiState + Intent + ViewModel) |
| Navigation | Jetpack Navigation Compose |
| Local DB | Room |
| Preferences | DataStore (Preferences) |
| DI | Koin |
| Min SDK | 26 |
| Target SDK | 35 |
| Kotlin | 2.3.20 |
| AGP | 9.2.0 |
| Compose BOM | 2026.04.01 |

---

## Design tokens → Compose mapping

Map these exactly. No approximations.

### Colors

```kotlin
val Canvas        = Color(0xFFF1EFE8)
val Surface       = Color(0x8CF1EFE8)   // 55% opacity
val SurfaceRaised = Color(0xFFFFFFFF)
val SurfaceSunken = Color(0xFFECEAE2)
val ParentFill    = Color(0xFFFFFFFF)

val Ink900 = Color(0xFF2C2C2A)
val Ink700 = Color(0xFF4A4843)
val Ink600 = Color(0xFF6E6B63)
val Ink500 = Color(0xFF8C887E)
val Ink400 = Color(0xFFA8A498)
val Ink300 = Color(0xFFC6C2B6)
val Ink200 = Color(0xFFDAD6CB)

val Hairline       = Color(0x162C2C2A)  // ~8.5%
val HairlineStrong = Color(0x242C2C2A)  // ~14%
val GuideLine      = Color(0x292C2C2A)  // ~16%

val Primary      = Color(0xFF2C2C2A)
val PrimaryPress = Color(0xFF1A1A18)
val PrimaryTint  = Color(0xFFE8E5DB)
val PrimaryOn    = Color(0xFFFFFFFF)

// Blue — time pill on-track ONLY
val Blue      = Color(0xFF378ADD)
val BluePress = Color(0xFF2C72BC)
val BlueTint  = Color(0xFFEAF2FB)

// Urgency — time pills ONLY
val Amber = Color(0xFFB26C12)
val Red   = Color(0xFFCF3030)

// Overdue capsule
val OverdueBg     = Color(0xFFFCEBEB)
val OverdueBorder = Color(0xFFE24B4A)
val OverdueText   = Color(0xFF791F1F)

val ToastBg   = Color(0xFF2C2C2A)
val ToastText = Color(0xFFFBFAF6)
val WarnBg    = Color(0xFFF6F1E4)
val Scrim     = Color(0x52211F1B)  // ~32%
```

### Typography

Typeface: **General Sans** (Fontshare CDN). Weights: **400 and 500 only.**

```kotlin
Display = 40.sp   // day header
Title   = 20.sp   // section title
Body    = 16.sp   // task title
Sub     = 14.sp   // description
Meta    = 13.sp   // progress, time, date
Caption = 12.sp   // weekday, banner

TrackTight = -0.02.em  // display, title
TrackSnug  = -0.01.em  // body
```

### Spacing (8pt grid)

```kotlin
Space1=4.dp  Space2=8.dp  Space3=12.dp  Space4=16.dp  Space5=20.dp
Space6=24.dp  Space8=32.dp  Space10=40.dp  Space12=48.dp  Space16=64.dp
```

### Corner radii

```kotlin
RadiusXs=6.dp   RadiusSm=8.dp   RadiusMd=12.dp
RadiusLg=16.dp  RadiusXl=22.dp  RadiusPill=999.dp
```

### Motion

```kotlin
DurFast=120ms  DurBase=200ms  DurSlow=320ms
// spring(dampingRatio=0.7f, stiffness=300f) for expand/collapse, popups, checkbox
// FastOutSlowInEasing for standard transitions
```

---

## Hard design rules

Non-negotiable. Every screen. Every component.

1. **Hierarchy is strict:** Parent → Section → Task. Daily is pinned top, always expanded, never deletable.
2. **Blue is accent-only.** Time pill on-track state only. Never a surface, never text, never decoration.
3. **Red and amber are urgency-only.** Time pills only. Nowhere else.
4. **Newest items on top.** Completion never reorders — a finished task greys out in place.
5. **Hairlines, not boxes.** Tasks are separated by 0.5dp dividers. No cards within cards.
6. **Truncation always.** Titles: `maxLines=1`. Descriptions: `maxLines=2`. Always `TextOverflow.Ellipsis`.
7. **Two weights only (400/500). Sentence case everywhere.** No bold. No italic. No all-caps.
8. **No gradients. No drop-shadow soup. No decorative icons.** Every visual element must mean something.
9. **No emoji. No unicode glyph icons.** Lucide icons only.
10. **Screen gutter: 16dp. Max column width: 440dp.**

---

## Copy and voice rules

- Tone: Quiet, plain. Terse, never peppy. Never exclamation points.
- Casing: Sentence case. `today`, `daily`, `add`, `search` are intentionally lowercase.
- Toast: past-tense fact only — `saved`, `task deleted`
- Snackbar: fact + single verb — `undo`
- Banner: mode statement — `viewing — read only`
- Forbidden: "Great job!", "You crushed it!", "TODAY'S TASKS", gamified praise of any kind.
- Numbers as facts: `3 / 8`, `2h 14m`, `−2h 14m`. No fuzzy phrasing.

---

## Icons

Library: Lucide. Style: stroke, never filled, 24dp controls / 20dp inline, 1.75dp stroke. Inherit ink color.

In-product set: `chevron-down`, `chevron-right`, `chevron-left`, `check`, `search`, `pencil`, `plus`, `more-vertical`, `x`, `calendar`, `clock`, `import`, `undo`, `info`, `settings`, `trash`, `star`

---

## Screen inventory

| Screen | Status | Reference |
|---|---|---|
| Home / Main List | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Settings | ✅ Fully designed | `design-reference/tcApp.jsx` + `remaining-pages.md §1` |
| Trash | ✅ Fully designed | `design-reference/tcApp.jsx` + `remaining-pages.md §2` |
| About | ✅ Fully designed | `design-reference/tcApp.jsx` + `remaining-pages.md §3` |
| Add Task form | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Import preview | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Search overlay | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Calendar popup | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Rate popup | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Context menu | ✅ Fully designed | `design-reference/tcApp.jsx` |
| Legal WebView screen | ⬜ Not designed | `remaining-pages.md §4` — simple wrapper |
| Confirmation dialogs | ⬜ Not designed | `remaining-pages.md` — 5 destructive actions |
| Empty state (main list) | ⬜ Not designed | Text only, no illustration |

---

## Navigation structure

```
NavGraph
├── HomeScreen (start)
│   ├── ContextMenu overlay (three-dot)
│   ├── SettingsScreen → LegalWebViewScreen(title, url)
│   ├── TrashScreen
│   └── AboutScreen → LegalWebViewScreen(title, url)
```

Transitions: `slideInHorizontally` enter, `slideOutHorizontally` exit. Duration: 200ms. Fades only on overlays.

---

## Data model

```kotlin
@Entity data class Task(
    @PrimaryKey(autoGenerate=true) val id: Long = 0,
    val sectionId: Long,
    val title: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.NONE,
    val dueDate: Long? = null,
    val dueTime: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    val isTrashed: Boolean = false,
    val trashedAt: Long? = null
)

enum class Priority { NONE, LOW, MEDIUM, HIGH }

@Entity data class Section(
    @PrimaryKey(autoGenerate=true) val id: Long = 0,
    val parentId: Long? = null,
    val title: String,
    val isDaily: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val colorKey: String = "default"
)

@Entity data class Parent(
    @PrimaryKey(autoGenerate=true) val id: Long = 0,
    val title: String,
    val emoji: String? = null,
    val colorKey: String = "default",
    val isFavourite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
```

---

## MVI pattern

Each screen: `UiState` data class + `Intent` sealed class + `ViewModel` with `StateFlow<UiState>`.
ViewModel accepts `Intent`, emits `UiState`. No business logic in composables.

---

## Settings DataStore keys

```kotlin
object PreferenceKeys {
    val THEME = stringPreferencesKey("theme")               // "light"|"dark"|"system"
    val DEFAULT_PRIORITY = stringPreferencesKey("default_priority") // "none"|"low"|"medium"|"high"
    val DEFAULT_SORT = stringPreferencesKey("default_sort") // "date_added"|"due_date"|"priority"
    val AUTO_HIDE_COMPLETED = stringPreferencesKey("auto_hide") // "off"|"1"|"3"|"7"
    val REMINDERS_ENABLED = booleanPreferencesKey("reminders")
    val REMINDER_OFFSET = stringPreferencesKey("reminder_offset") // "0"|"10"|"30"|"60"|"morning"
    val TRASH_AUTO_DELETE = stringPreferencesKey("trash_ttl")    // "7"|"14"|"30"|"never"
}
```

---

## Confirmation dialogs (required for all destructive actions)

```kotlin
@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmLabel: String,
    confirmDanger: Boolean = true,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
)
```

Required before: clear all completed, delete permanently, empty trash, restore all, reset settings.

---

## Legal page URLs

```kotlin
object LegalUrls {
    const val PRIVACY_POLICY  = "https://journeytix.com/legal/privacy"
    const val TERMS_OF_SERVICE = "https://journeytix.com/legal/terms"
    const val EULA            = "https://journeytix.com/legal/eula"
    const val DATA_DELETION   = "https://journeytix.com/legal/data-deletion"
}
```

---

## Key dependencies (build.gradle.kts)

```kotlin
implementation(platform("androidx.compose:compose-bom:2026.04.01"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.material3:material3")
implementation("androidx.navigation:navigation-compose:2.8.9")
implementation("androidx.room:room-runtime:2.7.1")
implementation("androidx.room:room-ktx:2.7.1")
ksp("androidx.room:room-compiler:2.7.1")
implementation("androidx.datastore:datastore-preferences:1.1.3")
implementation("io.insert-koin:koin-android:4.0.0")
implementation("io.insert-koin:koin-androidx-compose:4.0.0")
implementation("androidx.webkit:webkit:1.12.1")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
// Lucide icons — verify latest version before using
implementation("com.github.YarikSOffice:lucide-compose:0.1.0")
```

---

## What NOT to do

- Do not use Material 3 default colors anywhere. Every color from the token table above.
- Do not use `TopAppBar` from Material 3 — build the custom header from `tcApp.jsx`.
- Do not use `FontWeight.Bold`. Only `400` and `500`.
- Do not reorder tasks on completion. Ever.
- Do not add emoji, illustration, or decoration to empty states. Text only.
- Do not add any feature not in this file or `remaining-pages.md`.
- Do not play sounds or show badges on task completion. Task greys out silently.
- Do not use `BottomNavigation` — the app has a custom floating pill bottom bar.
- Do not cap `fillMaxWidth` without capping at `440.dp`.

---

## Before you start any task

1. Run `graphify query "<what you're about to build>"` — understand existing connections first
2. Read the relevant component JSX in `design-reference/components/`
3. Read `design-reference/tcApp.jsx` for the screen containing the component
4. Define the verifiable success criteria for this task (Karpathy rule #4)
5. State your plan — steps + verification for each step
6. Wait for CONFIRM before coding if using `/plan`
7. Build → run → compare against reference → loop until verified
8. Run `/kotlin-review` before marking done

<!-- BEGIN @agent-native/skills -->
## Quick Recap Status Block

Every response that completes a unit of work must end with one final status line:

```md
🟢 Actual concise status sentence
```

Use 🟢 when the requested work is finished. Use 🟡 when non-routine follow-up work or a manual step remains, and name that pending item. Use 🔴 only when blocked on user input. Keep the status line under 100 characters. Put the status line at the very end of the response. Do not add `---`, spacer lines, or any content after it.

Examples:

```md
🟢 Updated quick recap docs with output examples
```

```md
🟡 Code updated, set PROVIDER_WEBHOOK_SECRET before testing webhooks
```

```md
🔴 Need the production API key to continue
```
<!-- END @agent-native/skills -->
