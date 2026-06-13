import React from "react";
import { Icon } from "../core/Icon.jsx";

/* Toast — a dark ink confirmation. Past-tense fact only ("saved").
   Small, centred, transient. No colour, no celebration. */
export function Toast({ children = "saved", icon = true, style }) {
  return (
    <div
      role="status"
      style={{
        display: "inline-flex",
        alignItems: "center",
        gap: 8,
        padding: "11px 16px",
        background: "var(--toast-bg)",
        color: "var(--toast-text)",
        borderRadius: "var(--radius-md)",
        font: "var(--weight-medium) 14px/1 var(--font-sans)",
        letterSpacing: "-0.01em",
        boxShadow: "var(--shadow-overlay)",
        ...style,
      }}
    >
      {icon && <Icon name="check" size={18} stroke={2.25} color="var(--toast-text)" />}
      {children}
    </div>
  );
}
