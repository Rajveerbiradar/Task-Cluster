Stroke-only line icon (Lucide family) — use anywhere the UI needs a glyph; inherits ink via currentColor, goes blue only as an active affordance.

```jsx
<Icon name="search" size={24} />
<Icon name="check" color="var(--blue-on)" stroke={2.5} />
```

Names: chevron-right/down, check, search, plus, minus, x, pencil, more-vertical, calendar, upload, import, undo, info, clock. The `import` glyph (arrow pointing INTO a tray) is the .txt import action — never use `upload` (arrow out) for it. Default size 24 (20 inline), stroke 1.75. Never filled, never coloured decoratively.