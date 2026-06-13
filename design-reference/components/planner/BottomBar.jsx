import React from "react";
import { Icon } from "../core/Icon.jsx";

/* BottomBar — a floating pill with four modes:
   home (house) · tasks (list) · search · add.
   Active mode is blue. The first two are navigation tabs (Home vs Tasks page);
   search and add open overlays from either page. */
const MODES = [
  { key: "home",   icon: "home",   label: "Home" },
  { key: "tasks",  icon: "list",   label: "Tasks" },
  { key: "search", icon: "search", label: "Search" },
  { key: "add",    icon: "plus",   label: "Add" },
];

export function BottomBar({ mode = "home", onModeChange, style }) {
  return (
    <nav
      role="tablist"
      style={{
        display: "inline-flex",
        gap: 4,
        padding: 6,
        background: "var(--surface-raised)",
        borderRadius: "var(--radius-pill)",
        boxShadow: "var(--shadow-pill)",
        border: "1px solid var(--hairline)",
        ...style,
      }}
    >
      {MODES.map((m) => {
        const active = m.key === mode;
        return (
          <button
            key={m.key}
            role="tab"
            type="button"
            aria-selected={active}
            aria-label={m.label}
            onClick={() => onModeChange && onModeChange(m.key)}
            className="tc-pressable"
            style={{
              display: "grid",
              placeItems: "center",
              width: 48,
              height: 48,
              border: "none",
              borderRadius: "var(--radius-pill)",
              background: active ? "var(--primary)" : "transparent",
              cursor: "pointer",
              transition: "background var(--dur-fast) var(--ease-standard)",
            }}
          >
            <Icon
              name={m.icon}
              size={22}
              color={active ? "var(--primary-on)" : "var(--ink-500)"}
            />
          </button>
        );
      })}
    </nav>
  );
}
