import React from "react";

/* Snackbar — a fact paired with a single undo verb. Dark ink surface.
   Used after destructive actions ("task deleted · undo"). */
export function Snackbar({ children = "task deleted", action = "undo", onAction, style }) {
  return (
    <div
      role="status"
      style={{
        display: "flex",
        alignItems: "center",
        justifyContent: "space-between",
        gap: 16,
        padding: "12px 12px 12px 16px",
        background: "var(--toast-bg)",
        color: "var(--toast-text)",
        borderRadius: "var(--radius-md)",
        boxShadow: "var(--shadow-overlay)",
        ...style,
      }}
    >
      <span style={{ font: "var(--weight-regular) 14px/1 var(--font-sans)" }}>{children}</span>
      <button
        type="button"
        onClick={onAction}
        className="tc-pressable"
        style={{
          flex: "none",
          border: "none",
          background: "transparent",
          padding: "6px 10px",
          borderRadius: 8,
          color: "var(--blue)",
          font: "var(--weight-medium) 14px/1 var(--font-sans)",
          cursor: "pointer",
        }}
      >
        {action}
      </button>
    </div>
  );
}
