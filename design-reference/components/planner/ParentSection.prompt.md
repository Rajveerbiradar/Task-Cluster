Top of the hierarchy (Parent → Section → Task) — a non-collapsible, warm-tinted CONTAINER that visibly holds its SectionCards, parent title on a header bar at the top.

```jsx
<ParentSection title="Daily" pinned count={2} onMenu={(p) => openMenu(p, "parent")}>
  <SectionCard … /><SectionCard … />
</ParentSection>
```

Parents do NOT collapse (the brief is strict about Daily being always-expanded; we extend that calm rule to every parent). Press-and-hold the title opens a ContextMenu (rename / add section / delete). `pinned` parents (Daily) show a dot before the title and have no delete option.
