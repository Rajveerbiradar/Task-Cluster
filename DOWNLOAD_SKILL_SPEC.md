# TaskCluster — "Download SKILL" Feature Spec

The three-dot menu's "Export" item is REPLACED with "Download SKILL".

Purpose: instead of exporting the user's data, this gives the user a spec file they
hand to any AI (Claude, ChatGPT, etc). The AI reads it and writes a valid TaskCluster
import JSON for them — because the import format is precise, an AI with the right
instructions produces a perfect file, while hand-writing is error-prone.

================================================================================
## 1 — TWO-FILE ARCHITECTURE
================================================================================

Tapping "Download SKILL" produces TWO files (zipped together, or offered as two
share actions):

1. `taskcluster-import-skill.md`  — STATIC. The full instruction manual for the AI.
   Bundled as a raw asset in the app (res/raw or assets/). Never changes at runtime.
   Shipping it static means it can never break, even if icon reading fails.

2. `taskcluster-icons.md`  — LIVE. The list of icon keys currently installed on THIS
   device, generated at download time by scanning SectionIcons.keys. If the scan
   fails for any reason, this file is still written with a safe fallback note (see
   Section 4) — it never blocks the download.

The static SKILL references the icon file by name and tells the AI to read it for
valid icon keys, and to use the default icon for anything not listed.

================================================================================
## 2 — DELIVERY
================================================================================

- Menu item: "Download SKILL" (icon: TaskIcons.Import or a download glyph).
- On tap, show a small popup: "Download SKILL" with two actions:
  • "Save files" — writes both .md files via the Android document/share picker.
  • "Copy SKILL" — copies the static SKILL.md text to clipboard (for quick paste
    into an AI chat). Toast "SKILL copied".
- The icon list file is always included alongside when saving.
- Daily is never mentioned as creatable (it's a built-in parent).

================================================================================
## 3 — STATIC FILE: taskcluster-import-skill.md  (ship this verbatim as an asset)
================================================================================

Create `app/src/main/assets/taskcluster-import-skill.md` with EXACTLY this content:

---BEGIN ASSET---
# TaskCluster Import File — Generation Guide

You are generating an import file for the TaskCluster Android app. Produce ONE JSON
object that exactly matches the schema below. Output ONLY the JSON — no prose, no
markdown fences, no commentary.

## Hierarchy
Parent → Section → Task. A file may contain many parents (each with sections and
tasks) and standalone sections (sections with no parent). Tasks live only inside
sections.

## Top-level shape
{
  "formatVersion": 1,
  "parents": [ ... ],
  "standaloneSections": [ ... ]
}
- formatVersion: always the integer 1.
- parents: array (may be empty).
- standaloneSections: array (may be empty).
- At least one parent or standalone section must exist.

## Parent
{
  "title": "Work",
  "emoji": "💼",
  "favourite": true,
  "sections": [ ... ]
}
- title: required, 1–80 chars.
- emoji: optional, exactly ONE emoji character, or omit it.
- favourite: optional bool, default false (pins it to the Home screen).
- sections: array (may be empty).
- The title "daily" is reserved — never create a parent named "daily".

## Section (same shape whether under a parent or standalone)
{
  "title": "Launch prep",
  "icon": "rocket",
  "tasks": [ ... ]
}
- title: required, 1–80 chars.
- icon: optional. MUST be one of the icon keys listed in the companion file
  "taskcluster-icons.md". If you want an icon that isn't in that list, OR if you
  have no icon list, simply omit the icon field — the app will show its default
  icon. NEVER invent icon names; an unknown icon falls back to the default.
- tasks: array (may be empty).

## Task
{
  "title": "Write release notes",
  "description": "Summarize the 2.2 changes",
  "priority": "high",
  "due": "2026-06-20T17:00:00Z",
  "completed": false
}
- title: required, 1–200 chars.
- description: optional string, default empty.
- priority: optional, one of "none" | "low" | "medium" | "high". Default "none".
- due: optional ISO-8601 datetime (UTC, e.g. 2026-06-20T17:00:00Z), or omit for
  no deadline.
- completed: optional bool, default false.

## Emoji rules (parents only)
- Emoji are for PARENTS only. Sections use icons, tasks have neither.
- Use exactly one emoji per parent, or omit.

## Icon rules (sections only)
- Icons are for SECTIONS only, by key string (e.g. "rocket", "dumbbell").
- Valid keys are in the companion file "taskcluster-icons.md".
- If an icon key is not in that list, the app automatically uses its default icon —
  it will NOT error. When unsure, omit the icon field.

## Hard rules
- Output valid JSON only. No trailing commas. No comments. No markdown fences.
- Every task MUST have a non-empty title.
- priority must be exactly one of the four allowed values, lowercase.
- Never create a parent named "daily".
- Dates must be ISO-8601 or omitted.

## Minimal valid example
{
  "formatVersion": 1,
  "parents": [
    {
      "title": "Health",
      "emoji": "🏃",
      "favourite": false,
      "sections": [
        {
          "title": "Morning routine",
          "icon": "dumbbell",
          "tasks": [
            { "title": "Stretch 10 min", "completed": true },
            { "title": "Log breakfast", "priority": "low" }
          ]
        }
      ]
    }
  ],
  "standaloneSections": [
    {
      "title": "Quick tasks",
      "icon": "star",
      "tasks": [
        { "title": "Buy groceries", "description": "Milk, eggs, bread" }
      ]
    }
  ]
}

After you generate the JSON, the user will paste it into TaskCluster → + →
Import. If anything is wrong, the app reports the exact problem and imports nothing,
so make sure every rule above is followed.
---END ASSET---

================================================================================
## 4 — LIVE FILE: taskcluster-icons.md  (generated at download time)
================================================================================

At the moment the user taps "Download SKILL", generate this file from the installed
icons. Pseudocode:

```kotlin
fun buildIconListMarkdown(): String {
    val keys = try {
        SectionIcons.keys.filter { it != "default" }.sorted()
    } catch (e: Exception) {
        emptyList()
    }
    return buildString {
        appendLine("# TaskCluster — Available Section Icons")
        appendLine()
        appendLine("Use any ONE of these keys as a section's \"icon\" value.")
        appendLine("If you want an icon not listed here, omit the icon field —")
        appendLine("the app will use its default icon (it will not error).")
        appendLine()
        if (keys.isEmpty()) {
            appendLine("_No custom icons detected on this device._")
            appendLine("Omit the \"icon\" field on every section; the app will use")
            appendLine("its default icon for all sections.")
        } else {
            keys.forEach { appendLine("- $it") }
        }
    }
}
```

Key points:
- The list EXCLUDES "default" (it's the fallback, not a selectable key).
- If the scan throws OR returns nothing, the file STILL generates with the
  "no custom icons detected" note telling the AI to omit icons. The download never
  fails because of icon reading — that's the whole reason the icon list is a
  separate file from the static SKILL.

================================================================================
## 5 — WHY TWO FILES
================================================================================

The SKILL.md is static and bundled, so it is always correct and can never break.
The icon list is dynamic and isolated, so if folder-scanning ever fails, only the
icon file degrades (to "omit icons / use default") while the SKILL stays intact.
The fallback-to-default rule is stated in BOTH files so the AI handles unknown or
missing icons gracefully no matter what.

================================================================================
## 6 — REPLACES EXPORT?
================================================================================

This REPLACES the "Export" item in the three-dot menu with "Download SKILL".
NOTE: the round-trip JSON Export (export your real data as JSON) is a SEPARATE
feature that lives in Settings → Data → "Export tasks". Do not remove that. The
three-dot menu item specifically becomes "Download SKILL"; Settings keeps the
data export. They are different features:
  • Three-dot "Download SKILL" → gives an AI the instructions to WRITE an import file.
  • Settings "Export tasks"     → dumps the user's ACTUAL data as import JSON.
