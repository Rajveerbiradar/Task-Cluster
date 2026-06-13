import React from "react";
import { Checkbox } from "../forms/Checkbox.jsx";
import { TimePill } from "./TimePill.jsx";

/* Press-and-hold detection — long-press fires onLongPress with the pointer's
   {x, y} so an origin-aware ContextMenu can open right at the touch. Movement
   or scroll cancels, so a held read never opens it. */
function useLongPress(onLongPress, ms = 450) {
  const timer = React.useRef(null);
  const start = (e) => {
    const pt = { x: e.clientX, y: e.clientY };
    timer.current = setTimeout(() => onLongPress && onLongPress(pt), ms);
  };
  const cancel = () => { if (timer.current) { clearTimeout(timer.current); timer.current = null; } };
  return {
    onPointerDown: start,
    onPointerUp: cancel,
    onPointerLeave: cancel,
    onPointerCancel: cancel,
    onPointerMove: cancel,
  };
}

/* TaskRow — no surface of its own, divider-separated inside a SectionCard.
   Layout: checkbox · (title 1 line + description 2 lines) · time pill.
   Completed task greys out IN PLACE — never moves, never restyled to a
   strikethrough circus. A done task carries no urgency, so its pill drops.
   No visible menu — press and hold (or right-click) reveals it. */
export function TaskRow({
  title,
  description,
  checked = false,
  status = "calm",
  time,
  divider = false,
  onToggle,
  onMenu,
  style,
}) {
  const titleColor = checked ? "var(--ink-300)" : "var(--ink-900)";
  const descColor  = checked ? "var(--ink-300)" : "var(--ink-600)";
  const press = useLongPress((pt) => onMenu && onMenu(pt));

  return (
    <div
      {...press}
      onContextMenu={(e) => { e.preventDefault(); onMenu && onMenu({ x: e.clientX, y: e.clientY }); }}
      style={{
        display: "flex",
        alignItems: "flex-start",
        gap: 12,
        padding: "14px 0",
        borderTop: divider ? "1px solid var(--hairline)" : "none",
        touchAction: "pan-y",
        ...style,
      }}
    >
      <div style={{ paddingTop: 1 }}>
        <Checkbox checked={checked} onChange={onToggle} />
      </div>

      <div style={{ flex: 1, minWidth: 0 }}>
        <div
          className="tc-truncate-1"
          style={{ font: "var(--weight-regular) 16px/1.35 var(--font-sans)", color: titleColor }}
        >
          {title}
        </div>
        {description && (
          <div
            className="tc-truncate-2"
            style={{ marginTop: 3, font: "var(--weight-regular) 14px/1.4 var(--font-sans)", color: descColor }}
          >
            {description}
          </div>
        )}
      </div>

      {!checked && <TimePill status={status} label={time} style={{ marginTop: 2 }} />}
    </div>
  );
}
