Text action button — use for form actions and confirmations; exactly one `primary` (blue) per view.

```jsx
<Button variant="primary" full>Add task</Button>
<Button variant="secondary">Cancel</Button>
<Button variant="ghost" iconLeft={<Icon name="import" size={18} />}>Import</Button>
```

variants: primary | secondary | ghost · sizes: lg(48) | md(40) | sm(32) · full, disabled, iconLeft. Sentence case, never all-caps.