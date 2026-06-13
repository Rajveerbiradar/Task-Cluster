---
name: taskcluster-design
description: Use this skill to generate well-branded interfaces and assets for TaskCluster, either for production or throwaway prototypes/mocks/etc. Contains essential design guidelines, colors, type, fonts, assets, and UI kit components for prototyping the warm-paper, monochrome-plus-one-blue planner.
user-invocable: true
---

Read the `readme.md` file in this skill first — it is the full design guide
(hard rules, content voice, visual foundations, iconography, manifest). Then
explore the other files as needed.

## What's here
- `styles.css` — the global entry point (import this). `@imports` only.
- `tokens/` — colors, typography, spacing/radius/shadow/motion, fonts, base reset.
- `guidelines/` + `assets/` — foundation specimen cards, wordmark, icon sheet.
- `components/` — React primitives (core, forms, feedback, planner). Each has a
  `.jsx`, a `.d.ts` (props), a `.prompt.md` (what/when + example), and the group
  has one `*.card.html` demo.
- `ui_kits/taskcluster/` — the full interactive single-screen recreation.

## How to work
If creating visual artifacts (slides, mocks, throwaway prototypes), copy the
assets you need and write static HTML files for the user to view — link
`styles.css` for the tokens and fonts, and either reuse the component sources or
the compiled bundle. If working on production code, copy assets and internalise
the rules here to design as an expert in this brand.

If invoked with no other guidance, ask the user what they want to build, ask a
few focused questions, then act as an expert designer who outputs HTML artifacts
or production code as the need dictates.

## Non-negotiables (see readme for the full list)
- Warm paper canvas, ink text. Greys carry ~90% of the UI.
- **Blue is accent-only** (today · checked box · primary action). Never a surface,
  never body text, never decoration.
- **Red/amber are urgency-only** — they live exclusively in time pills.
- Hierarchy is strict: Parent → Section → Task. Daily is pinned, never deletable.
- Hairlines over boxes; no cards-within-cards. Two type weights (400/500),
  sentence case. No gradients, no shadow soup, no decorative icons, no emoji.
