A section on a paper surface — use to group tasks; chevron · title · progress · time pill · menu.

```jsx
<SectionCard title="Launch prep" done={1} total={3} status="due" time="9m"
  expanded={open} onToggle={setOpen} onMenu={openMenu}>
  <TaskRow … /><TaskRow divider … />
</SectionCard>
```

`pinned` (Daily) keeps it open with no menu. Hairlines + whitespace only — never nest cards.