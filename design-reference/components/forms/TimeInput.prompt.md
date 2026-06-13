Deadline control — Date / Days / Hours segmented control over one value field; use in the create/edit form. Drives a task's TimePill.

```jsx
<TimeInput label="Deadline" mode={mode} value={val}
  onModeChange={setMode} onValueChange={setVal} />
```

mode: "Date" | "Days" | "Hours".