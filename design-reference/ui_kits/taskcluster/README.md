# TaskCluster — UI kit

A full, interactive recreation of TaskCluster's single screen, composed entirely
from the design-system primitives in `_ds_bundle.js`.

## Run
Open `index.html`. It loads the bundle, then two kit-local scripts:
- `tcOverlays.jsx` — the bottom sheets (create/edit form, import preview, search,
  the three-dot action menu). Kit-local screens, not reusable components.
- `tcApp.jsx` — sample data + the screen + the state machine.

## What it demonstrates
- **Today view** — the pinned **daily** parent (always expanded) over newest-first
  sections, some tasks done (greyed in place), the urgency ramp visible across
  Launch prep / Errands (on-track blue, close amber, due red, an overdue capsule).
- **Time-travel** — tap a past date for the **read-only** banner, a future date for
  the **planning — hidden until then** banner + empty state.
- **Three-mode bar** — check (default) · search (overlay with breadcrumbed results) ·
  add → an **Add chooser** (new parent / section / task, plus **import from .txt** →
  import preview with counts + a title-only tree). Editing is inline via press-and-hold.
- **Feedback** — toast (`saved` / `imported`), snackbar with **undo** after delete.
- **Empty section** — "Side project" shows the fresh-section empty state.

## Notes
- The screen is mocked for click-through fidelity, not production logic.
- Everything visual comes from the system tokens + components; nothing is restyled
  here. Blue stays accent-only; red/amber stay inside time pills.
