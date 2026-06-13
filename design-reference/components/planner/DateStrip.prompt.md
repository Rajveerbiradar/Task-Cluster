Horizontally-scrollable week strip — use as the time-travel control; today is blue, the selected day fills ink.

```jsx
<DateStrip days={days} selectedKey={day} todayKey="11" onSelect={setDay} />
```

days: [{key, weekday, date}]. Omit `days` for a sample week.