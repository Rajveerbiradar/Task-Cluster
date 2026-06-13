import React from "react";
import { Icon } from "../core/Icon.jsx";
import { TimePill } from "./TimePill.jsx";

/* Press-and-hold detection. */
function useLongPress(onLongPress, onTap, ms = 450) {
  const timer = React.useRef(null);
  const held = React.useRef(false);
  const start = (e) => {
    held.current = false;
    const pt = { x: e.clientX, y: e.clientY };
    timer.current = setTimeout(() => { held.current = true; onLongPress && onLongPress(pt); }, ms);
  };
  const cancel = () => { if (timer.current) { clearTimeout(timer.current); timer.current = null; } };
  return {
    onPointerDown: start,
    onPointerUp: () => { cancel(); if (!held.current && onTap) onTap(); },
    onPointerLeave: cancel,
    onPointerCancel: cancel,
    onPointerMove: cancel,
    onContextMenu: (e) => { e.preventDefault(); onLongPress && onLongPress({ x: e.clientX, y: e.clientY }); },
  };
}

/* SectionCard — a section sits on a subtle surface.
   Now supports an `icon` prop — a Lucide icon name displayed
   in a squircle badge before the title. */
export function SectionCard({
  title,
  icon,
  done = 0,
  total = 0,
  status = "calm",
  time,
  expanded = true,
  pinned = false,
  onToggle,
  onMenu,
  onIconClick,
  children,
  style,
}) {
  const press = useLongPress(
    (pt) => !pinned && onMenu && onMenu(pt),
    () => !pinned && onToggle && onToggle(!expanded),
  );
  const pct = total > 0 ? Math.round((done / total) * 100) : 0;
  const complete = total > 0 && done === total;

  return (
    <section
      style={{
        background: "var(--tint-surface, var(--surface))",
        border: "1px solid var(--tint-surface-border, var(--hairline))",
        borderRadius: "var(--radius-lg)",
        padding: "4px 16px",
        ...style,
      }}
    >
      <header
        {...(pinned ? { onClick: () => onToggle && onToggle(!expanded) } : press)}
        className={pinned ? "" : "tc-pressable"}
        style={{
          display: "flex",
          alignItems: "center",
          gap: 12,
          minHeight: 56,
          margin: "0 -8px",
          padding: "8px",
          borderRadius: 10,
          cursor: "pointer",
          touchAction: "pan-y",
          userSelect: "none",
        }}
      >
        {/* icon badge — squircle, clickable to open icon picker */}
        {icon && (
          <span
            onClick={(e) => { e.stopPropagation(); onIconClick && onIconClick({ x: e.clientX, y: e.clientY }); }}
            style={{
              display: "inline-flex", alignItems: "center", justifyContent: "center",
              width: 32, height: 32, flex: "none",
              borderRadius: 8,
              background: "var(--tint-surface-border, var(--surface-sunken))",
              cursor: "pointer",
            }}>
            <Icon name={icon} size={17} color="var(--tint-ink, var(--ink-600))" />
          </span>
        )}
        <div style={{ flex: 1, minWidth: 0 }}>
          <span
            className="tc-truncate-1"
            style={{ display: "block", font: "var(--weight-medium) 18px/1.2 var(--font-sans)", letterSpacing: "-0.01em", color: "var(--tint-ink, var(--ink-900))" }}
          >
            {title}
          </span>
          {total > 0 && (
            <div style={{ display: "flex", alignItems: "center", gap: 10, marginTop: 8 }}>
              <span
                aria-hidden="true"
                style={{ position: "relative", flex: 1, maxWidth: 132, height: 4, borderRadius: 999, background: "var(--surface-sunken)", overflow: "hidden" }}
              >
                <span
                  style={{
                    position: "absolute", inset: 0, right: "auto",
                    width: `${pct}%`,
                    borderRadius: 999,
                    background: complete ? "var(--primary)" : "var(--tint-progress, var(--ink-400))",
                    transition: "width var(--dur-base) var(--ease-standard)",
                  }}
                />
              </span>
              <span style={{ flex: "none", whiteSpace: "nowrap", font: "var(--weight-medium) 13px/1 var(--font-sans)", color: complete ? "var(--primary)" : "var(--tint-meta, var(--ink-500))" }}>
                {done} / {total}
              </span>
            </div>
          )}
        </div>

        <TimePill status={status} label={time} />

        <Icon
          name={expanded ? "chevron-down" : "chevron-right"}
          size={20}
          color="var(--ink-500)"
          style={{ flex: "none", opacity: pinned ? 0.35 : 1 }}
        />
      </header>

      {expanded && React.Children.count(children) > 0 && (
        <div
          style={{
            borderTop: "1px solid var(--tint-hairline, var(--hairline))",
            margin: "0 -8px",
            padding: "4px 8px 8px",
          }}
        >
          {children}
        </div>
      )}
    </section>
  );
}
