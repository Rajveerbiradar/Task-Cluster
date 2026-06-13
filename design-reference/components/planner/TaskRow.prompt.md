A single task line — use inside a SectionCard; checkbox · title(1 line) + description(2 lines) · time pill.

```jsx
<TaskRow title="Email the beta list" description="…" status="overdue"
  time="−2h 14m" checked={done} onToggle={setDone} divider />
```

Set `divider` on every row but the first (0.5px rule). Completion greys it in place and drops the pill; rows never reorder.