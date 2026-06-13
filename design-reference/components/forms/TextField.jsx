import React from "react";

/* TextField — paper input. Single line or multiline (description).
   Quiet hairline; focus deepens the border to ink, never a blue glow
   (blue is accent-only). Label sits above in meta type. */
export function TextField({
  label,
  value,
  placeholder,
  multiline = false,
  rows = 3,
  onChange,
  style,
  ...rest
}) {
  const [focused, setFocused] = React.useState(false);
  const shared = {
    width: "100%",
    boxSizing: "border-box",
    padding: multiline ? "12px 14px" : "0 14px",
    height: multiline ? "auto" : 48,
    minHeight: multiline ? rows * 22 + 24 : undefined,
    background: "var(--surface)",
    border: `1px solid ${focused ? "var(--ink-400)" : "var(--hairline-strong)"}`,
    borderRadius: "var(--radius-sm)",
    font: `var(--weight-regular) 16px/1.4 var(--font-sans)`,
    color: "var(--ink-900)",
    outline: "none",
    resize: multiline ? "vertical" : "none",
    transition: "border-color var(--dur-fast) var(--ease-standard)",
  };
  return (
    <label style={{ display: "block", ...style }}>
      {label && (
        <span style={{ display: "block", marginBottom: 8, font: "var(--weight-medium) 13px/1 var(--font-sans)", color: "var(--ink-500)" }}>
          {label}
        </span>
      )}
      {multiline ? (
        <textarea
          rows={rows}
          value={value}
          placeholder={placeholder}
          onFocus={() => setFocused(true)}
          onBlur={() => setFocused(false)}
          onChange={(e) => onChange && onChange(e.target.value)}
          style={shared}
          {...rest}
        />
      ) : (
        <input
          type="text"
          value={value}
          placeholder={placeholder}
          onFocus={() => setFocused(true)}
          onBlur={() => setFocused(false)}
          onChange={(e) => onChange && onChange(e.target.value)}
          style={shared}
          {...rest}
        />
      )}
    </label>
  );
}
