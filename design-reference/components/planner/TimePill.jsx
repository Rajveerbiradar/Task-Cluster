import React from "react";

/* TimePill — the app's one piece of drama.
   A task's remaining time shifts colour as its deadline nears:
   calm (>50%) → nothing shown
   on-track    → blue text
   close       → amber text
   due         → red text
   overdue     → red CAPSULE (bg + border), counting up */

const STATUS_COLORS = {
  "on-track": "var(--on-track)",
  "close":    "var(--amber)",
  "due":      "var(--red)",
};

export function TimePill({ status = "calm", label, style }) {
  if (status === "calm" || !label) return null;

  if (status === "overdue") {
    return (
      <span
        style={{
          display: "inline-flex",
          alignItems: "center",
          flex: "none",
          whiteSpace: "nowrap",
          padding: "4px 9px",
          borderRadius: "var(--radius-sm)",
          background: "var(--overdue-bg)",
          border: "1px solid var(--overdue-border)",
          color: "var(--overdue-text)",
          font: "var(--weight-medium) 12px/1 var(--font-sans)",
          letterSpacing: "-0.01em",
          ...style,
        }}
      >
        {label}
      </span>
    );
  }

  /* on-track / close / due → coloured text, no background */
  const color = STATUS_COLORS[status] || "var(--ink-500)";
  return (
    <span
      style={{
        display: "inline-flex",
        alignItems: "center",
        flex: "none",
        whiteSpace: "nowrap",
        color,
        font: "var(--weight-medium) 13px/1 var(--font-sans)",
        letterSpacing: "-0.01em",
        ...style,
      }}
    >
      {label}
    </span>
  );
}
