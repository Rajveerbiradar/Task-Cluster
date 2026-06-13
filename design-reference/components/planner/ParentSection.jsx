import React from "react";
import { Icon } from "../core/Icon.jsx";

function useLongPress(onLongPress, ms = 450) {
  const timer = React.useRef(null);
  const start = (e) => {
    const pt = { x: e.clientX, y: e.clientY };
    timer.current = setTimeout(() => onLongPress && onLongPress(pt), ms);
  };
  const cancel = () => { if (timer.current) { clearTimeout(timer.current); timer.current = null; } };
  return {
    onPointerDown: start, onPointerUp: cancel, onPointerLeave: cancel,
    onPointerCancel: cancel, onPointerMove: cancel,
    onContextMenu: (e) => { e.preventDefault(); onLongPress && onLongPress({ x: e.clientX, y: e.clientY }); },
  };
}

/* ParentSection — collapsible container.
   Chevron in the header toggles children. Long-press for context menu. */
export function ParentSection({
  title, count, onMenu, pinned = false,
  expanded = true, onToggle,
  favourite = false, emoji, onEmojiClick,
  children, style,
}) {
  const press = useLongPress((pt) => onMenu && onMenu(pt));

  const handleClick = () => onToggle && onToggle(!expanded);

  return (
    <section style={{
      background: "var(--parent-fill)",
      border: "1.5px solid var(--hairline)",
      borderRadius: "var(--radius-xl)",
      padding: "6px 8px 10px",
      ...style,
    }}>
      <header
        {...press}
        onClick={handleClick}
        className="tc-pressable"
        style={{
          display: "flex", alignItems: "center", gap: 10, minHeight: 44,
          padding: "6px 12px", borderRadius: 12, cursor: "pointer",
          userSelect: "none", touchAction: "pan-y",
        }}>
        {/* emoji — no background, clickable to open picker */}
        {emoji && (
          <span
            onClick={(e) => { e.stopPropagation(); onEmojiClick && onEmojiClick({ x: e.clientX, y: e.clientY }); }}
            style={{
              display: "inline-flex", alignItems: "center", justifyContent: "center",
              flex: "none", fontSize: 18, lineHeight: 1, cursor: "pointer",
            }}>{emoji}</span>
        )}
        {pinned && !emoji && (
          <span aria-hidden="true" style={{ width: 6, height: 6, borderRadius: 999, background: "var(--ink-400)", flex: "none" }} />
        )}
        <span className="tc-truncate-1" style={{
          flex: 1, minWidth: 0,
          font: "var(--weight-medium) 18px/1 var(--font-sans)",
          letterSpacing: "-0.01em", color: "var(--ink-600)",
        }}>{title}</span>
        {count != null && (
          <span style={{ flex: "none", font: "var(--weight-medium) 13px/1 var(--font-sans)", color: "var(--ink-400)" }}>
            {count}
          </span>
        )}
        {favourite && (
          <Icon name="pin" size={14} color="var(--ink-400)" style={{ flex: "none" }} />
        )}
        <Icon
          name={expanded ? "chevron-down" : "chevron-right"}
          size={20}
          color="var(--ink-400)"
          style={{ flex: "none" }}
        />
      </header>

      {expanded && React.Children.count(children) > 0 && (
        <div style={{ display: "flex", flexDirection: "column", gap: 8, marginTop: 8 }}>
          {children}
        </div>
      )}
    </section>
  );
}
