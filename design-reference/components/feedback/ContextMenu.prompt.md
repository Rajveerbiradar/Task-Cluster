A small origin-aware popup menu — opens at the long-press / right-click point and flips quadrant so it never gets clipped.

```jsx
const [menu, setMenu] = useState({ open: false, anchor: null, items: [] });

<div onContextMenu={(e) => { e.preventDefault(); setMenu({ open: true, anchor: { x: e.clientX, y: e.clientY }, items: ITEMS }); }}>
  …
</div>

<ContextMenu
  open={menu.open}
  anchor={menu.anchor}
  items={menu.items}
  onClose={() => setMenu({ ...menu, open: false })}
  title="Launch prep"
/>
```

Use for: long-press on a section/parent/task → Rename / Delete / Add task. The 3-dot menu at the top of the screen → Settings / Trash / About / Rate. Items: `{ icon?, label, danger?, disabled?, divider?, shortcut?, onClick }`. NOT a bottom sheet — for confirmation prompts and full forms reach for the sheet primitives instead.
