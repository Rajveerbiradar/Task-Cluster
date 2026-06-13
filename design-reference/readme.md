# TaskCluster — Design System

> A single-screen Android planner where your whole day lives as collapsible
> sections of tasks, and the only thing that ever raises its voice is
> *time running out*.

TaskCluster is a todo app built around **sections**, not loose lists. A pinned,
recurring **Daily** block sits at the top; below it, project-style sections
(created by hand or imported from a `.txt`) hold tasks. Every task can carry a
countdown, and a quiet colour system escalates from calm to alarm as a deadline
approaches. One screen does everything: a date strip to time-travel, a three-mode
bottom bar, and the section tree.

**Who it's for & the feeling.** A focused person who wants *calm command* over
their day — not a gamified, badge-throwing productivity toy. The app should feel
like a well-set desk: ordered, quiet, precise. Pressure is communicated only
through the time pills, never through clutter or noise. The default emotional
state is **stillness**; urgency is the exception that earns colour.

---

## Sources

- **Figma file** (mounted read-only as `canvas.fig`): a large "Sigma" iOS/Material
  UI kit — Foundation (Colors, Type, Material, Elevation, Shapes), a full Components
  library, and a ~600-icon **Sigma Icons** set. We used it to confirm the typeface
  (**General Sans**) and the icon family (Feather/Lucide-style line icons), and as
  a reference for component anatomy. TaskCluster's own warm-paper aesthetic comes
  from the design brief, not from this generic kit.
- **Design brief** (provided in-thread): the authoritative POV — warm paper,
  monochrome + one blue, the time-pill urgency ramp, the strict Parent → Section →
  Task hierarchy. This README encodes that brief.

No live URLs were provided; the Figma is the mounted virtual filesystem only.

---

## Hard rules (carry these into every screen)

1. **Hierarchy is strict:** Parent → Section → Task. **Daily** is pinned to the
   top, always expanded, never deletable.
2. **Blue is accent-only.** Today's date, a checked box, the primary action — and
   nothing else. Never a surface, never body text, never decoration.
3. **Red / amber are urgency-only.** They appear exclusively in time pills.
4. **Newest items on top.** Completion **never** reorders anything — a finished
   task greys out *in place*.
5. **Hairlines, not boxes.** Sections sit on a subtle surface; tasks are separated
   by 0.5px dividers, never their own cards. No cards-within-cards.
6. **Design for truncation.** Titles truncate at 1 line, descriptions at 2.
7. **Two weights only (400 / 500). Sentence case everywhere.** No all-caps.
8. **No gradients, no drop-shadow soup, no decorative icons.** Every visual
   element must mean something.

---

## Content fundamentals

**Voice — quiet, plain, lowercase-friendly.** Copy is terse and human, never
peppy. The header is a single lowercase word: `today`. Labels are short and
literal: `search`, `edit`, `add`, `import`, `daily`. No marketing tone, no
exclamation points, no gamified praise ("Great job!" is forbidden — completion is
just a greyed row).

- **Casing:** sentence case for everything user-facing; the `today` title and mode
  labels are intentionally lowercase. Never all-caps.
- **Person:** address the user implicitly. Prefer imperatives and nouns
  (`viewing — read only`, `planning — hidden until then`, `3 imported`) over
  "you". Avoid "I" entirely — the app doesn't have a personality, it has a desk.
- **Numbers as facts:** progress reads `3 / 8`; time reads `2h 14m`, `45m`,
  `−2h 14m` (overdue counts up with a leading minus). No fuzzy "in a bit".
- **Emoji:** never. Unicode glyphs are not used as icons.
- **Feedback copy:** toast = past-tense fact (`saved`, `task deleted`); snackbar
  pairs the fact with a single verb action (`undo`); banners state the mode
  (`viewing — read only`).

Examples that are *on-voice*: `today` · `daily` · `viewing — read only` ·
`planning — hidden until then` · `3 imported · 2 sections` · `−2h 14m` · `undo`.
Off-voice (avoid): "You crushed it! 🎉", "Oops, something went wrong", "TODAY'S TASKS".

---

## Visual foundations

**Palette.** Warm-paper canvas (`--canvas #F1EFE8`), near-white warm surfaces
(`--surface #FBFAF6`), near-black warm ink (`--ink-900 #2C2C2A`). Greys carry ~90%
of the UI via a warm-tinted ink ramp (`--ink-700 → --ink-200`). **Blue**
(`--blue #378ADD`) is the single confident accent. **Amber** (`#B26C12`) and
**red** (`#CF3030`) live only in time pills; the **overdue capsule** (bg `#FCEBEB`,
border `#E24B4A`, text `#791F1F`) is the loudest moment in the whole product.

**Type.** General Sans, weights 400/500 only. Scale: display 40 (lowercase
`today`) · title 20 (section) · body 16 (task) · sub 14 (description) · meta 13
(progress, time, dates) · caption 12 (weekday, banners). Negative tracking on
display/title; sentence case throughout.

**Backgrounds.** Flat warm paper. **No** images, no full-bleed photography, no
hand-drawn illustration, no repeating texture, **no gradients** anywhere. Depth
comes from the canvas → surface → raised value steps, not from light effects.

**Structure & borders.** Hairlines over boxes. Section surfaces have a 16px radius
and either no border or a single `--hairline-strong` edge; tasks inside are
separated by clear spacing under a **single** divider that sits beneath the
section's title + progress bar (no rule between individual tasks). A
**parent section** is a light warm-tinted **container** (`--parent-fill`) that
visibly holds its SectionCards, with the parent title on a header bar at the top.
Never nest a card inside a card.

**Cards / surfaces.** A "card" here is just a tinted surface (`--surface`) with a
generous radius and roomy vertical padding — no shadow. The **only** shadowed
elements are things that genuinely float: the bottom-bar pill (`--shadow-pill`)
and overlays/menus (`--shadow-overlay`, `--shadow-menu`). Shadows are soft, warm,
and low-contrast.

**Menus vs. sheets.** A **ContextMenu** (small origin-aware popup, like a
desktop right-click) is the right pattern whenever the action list is short and
focused: the screen's three-dot → _Settings / Trash / About / Rate_, and every
long-press on a section / parent / task → _Add… / Rename / Delete_. It opens at
the touch point and flips quadrant so it never gets clipped. A **bottom Sheet**
is reserved for full surfaces with form fields or scrollable content (Add
chooser, New task, Import preview, Search, Calendar, Settings page, Trash page,
About page, Rate this app). Never use a sheet where a context menu will do.

**Corner radii.** checkbox 6 · inputs & overdue capsule 8 · menus/toasts 12 ·
section surface 16 · sheets 22 · bottom bar / date chips fully round (pill).

**Spacing.** Strict 8pt grid (`--space-1 … --space-16`). Take the breathing room
of a premium delivery/receipt screen: roomy vertical padding, clear grouping,
nothing cramped. Screen gutter is 16px; the app column caps at 440px.

**Motion.** Quiet. Fades and short height/transform eases (`--ease-standard`,
120–320ms) — **no bounces, no springs, no infinite loops.** Sections expand/collapse
with a height+opacity ease. Overdue pills may *count up* but never flash or pulse.
Respect `prefers-reduced-motion`.

**Hover / press.** Touch-first, so press matters more than hover. Press = a brief
drop to `--surface-sunken` or ~8% ink wash and a subtle 0.98 scale on pills/buttons;
no colour-shift drama. Hover (pointer devices) = a faint sunken wash only. The blue
primary darkens to `--blue-press` on press.

**Transparency & blur.** Used sparingly: the scrim behind overlays
(`--scrim`, ~32% warm-black). No frosted-glass everywhere — this is paper, not glass.

---

## Iconography

The source Figma's "Sigma Icons" are **Feather/Lucide-family line icons**
(literally named `chevron-right`, `check-circle`, `search`, `plus`, `minus`,
`settings`, `info-circle`, `calendar`, `eye`). TaskCluster therefore standardises
on **[Lucide](https://lucide.dev)** — the same family, reliably available, clean
24px / 1.75px stroke that matches the editorial, low-noise direction. Icons are
loaded from the Lucide CDN (`lucide@latest`) and tinted with `currentColor`.

- **Style:** stroke (line), never filled, never duotone, never coloured. 24px on
  controls, 20px inline, 1.75px stroke. Icons inherit ink colour; an icon is blue
  **only** when it is the active/primary affordance (e.g. the checked box).
- **Set used in product:** `chevron-down` / `chevron-right` (section expand),
  `check` (checkbox), `search`, `pencil` (edit), `plus` (add), `more-vertical`
  (the screen's three-dot → Settings / Trash / About / Rate), `x` (close),
  `calendar` (date input + jump-to-date), `import` (txt import, arrow IN — never
  `upload` for this), `undo`, `info` (banners), `clock` (time-of-day field),
  `settings`, `trash`, `star` (the About → Rate sheet).
- **No decorative icons, no emoji, no unicode glyph-icons.** If an icon doesn't
  carry meaning, it doesn't appear.

A reference sheet of the in-product set lives in `assets/icons.card.html`.

---

## Index / manifest

```
styles.css                 ← global entry (import this). @imports only.
tokens/
  fonts.css                ← General Sans @font-face (Fontshare CDN)
  colors.css               ← canvas/ink/blue/urgency tokens
  typography.css           ← scale, weights, .t-* specimen helpers
  spacing.css              ← 8pt scale, radii, shadows, motion, layout
  base.css                 ← reset, .tc-screen, .tc-hairline, truncation
guidelines/                ← foundation specimen cards (Design System tab)
assets/                    ← logo + icon reference
components/
  forms/                   ← Checkbox, TextField, TimeInput
  core/                    ← Button, IconButton, Badge
  feedback/                ← Banner, Toast, Snackbar
  planner/                 ← TimePill, TaskRow, SectionCard, ParentSection,
                             DateStrip, BottomBar
ui_kits/taskcluster/       ← full single-screen recreation + states
SKILL.md                   ← Agent-Skill entry point
```

See **VISUAL FOUNDATIONS** and **CONTENT FUNDAMENTALS** above before designing
anything new. When in doubt: quieter, warmer, fewer elements.
