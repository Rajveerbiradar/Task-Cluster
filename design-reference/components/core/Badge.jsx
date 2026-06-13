import React from "react";

/* Badge — a quiet count/label pill. Used for import-preview counts and
   section progress where a chip reads better than bare text.
   tone: neutral (default) | accent (blue — use sparingly). */
export function Badge({ children, tone = "neutral", style, ...rest }) {
  const tones = {
    neutral: { background: "var(--surface-sunken)", color: "var(--ink-600)" },
    accent:  { background: "var(--blue-tint)", color: "var(--blue-press)" },
  };
  return (
    <span
      style={{
        display: "inline-flex",
        alignItems: "center",
        gap: 6,
        height: 24,
        padding: "0 10px",
        borderRadius: "var(--radius-pill)",
        font: "var(--weight-medium) 13px/1 var(--font-sans)",
        letterSpacing: "-0.01em",
        ...tones[tone],
        ...style,
      }}
      {...rest}
    >
      {children}
    </span>
  );
}
