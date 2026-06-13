import React from "react";
import { Icon } from "../core/Icon.jsx";

/* Banner — quiet date-mode notices and info. Never alarming.
   variant: read-only (past day) | planning (future day) | info. */
const COPY = {
  "read-only": "viewing — read only",
  planning:    "planning — hidden until then",
  info:        "",
};

export function Banner({ variant = "info", children, style }) {
  return (
    <div
      role="status"
      style={{
        display: "flex",
        alignItems: "center",
        gap: 10,
        padding: "10px 14px",
        background: "var(--warn-bg)",
        border: "1px solid var(--warn-border)",
        borderRadius: "var(--radius-md)",
        ...style,
      }}
    >
      <Icon name="info" size={18} color="var(--ink-500)" />
      <span style={{ font: "var(--weight-regular) 13px/1.3 var(--font-sans)", color: "var(--ink-600)" }}>
        {children || COPY[variant]}
      </span>
    </div>
  );
}
