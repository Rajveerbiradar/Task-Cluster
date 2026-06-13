import React from "react";
import { Icon } from "../core/Icon.jsx";

/* ContextMenu — a small origin-aware popup.
   Opens at the user's touch point and flips quadrant so it never clips.
   Items can carry a `submenu: []` array — tapping drills into a sub-panel
   with a back row (mobile-friendly). */
const PAD = 8;
const NUDGE = 4;

export function ContextMenu({ open, anchor, items = [], onClose, minWidth = 220, title }) {
  const ref = React.useRef(null);
  const [pos, setPos] = React.useState({ left: -9999, top: -9999, originX: "left", originY: "top" });
  const [sub, setSub] = React.useState(null); // { title, items }

  React.useEffect(() => { if (!open) setSub(null); }, [open]);

  React.useLayoutEffect(() => {
    if (!open || !anchor || !ref.current) return;
    const el = ref.current;
    const rect = el.getBoundingClientRect();
    const w = rect.width || minWidth;
    const h = rect.height || 1;
    const vw = window.innerWidth;
    const vh = window.innerHeight;
    const fromRight = anchor.x > vw / 2;
    let left = fromRight ? anchor.x - w - NUDGE : anchor.x + NUDGE;
    if (left < PAD) left = PAD;
    if (left + w > vw - PAD) left = vw - PAD - w;
    let top = anchor.y + NUDGE;
    let originY = "top";
    if (top + h > vh - PAD) { top = anchor.y - h - NUDGE; originY = "bottom"; }
    if (top < PAD) top = PAD;
    setPos({ left, top, originX: fromRight ? "right" : "left", originY });
  }, [open, anchor, items, sub, minWidth]);

  React.useEffect(() => {
    if (!open) return;
    const onKey = (e) => { if (e.key === "Escape") { if (sub) setSub(null); else onClose && onClose(); } };
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [open, onClose, sub]);

  if (!open) return null;

  const displayItems = sub ? sub.items : items;
  const displayTitle = sub ? sub.title : title;

  return (
    <div
      onClick={(e) => { if (e.target === e.currentTarget) { onClose && onClose(); } }}
      onContextMenu={(e) => { e.preventDefault(); onClose && onClose(); }}
      style={{ position: "absolute", inset: 0, zIndex: 60, background: "transparent", animation: "tcFade var(--dur-fast) var(--ease-standard)" }}
    >
      <div ref={ref} role="menu" style={{
        position: "absolute", left: pos.left, top: pos.top,
        minWidth, maxWidth: 280, padding: "6px",
        background: "var(--surface-raised)", border: "1px solid var(--hairline-strong)",
        borderRadius: "var(--radius-md)", boxShadow: "var(--shadow-menu)",
        transformOrigin: `${pos.originX} ${pos.originY}`,
        animation: "tcMenuIn var(--dur-fast) var(--ease-standard)",
      }}>
        {/* sub-menu back row */}
        {sub && (
          <button type="button" className="tc-pressable" onClick={() => setSub(null)}
            style={{ display: "flex", alignItems: "center", gap: 8, width: "100%",
              height: 32, padding: "0 8px", border: "none", background: "transparent",
              borderRadius: 8, cursor: "pointer", color: "var(--blue)",
              font: "var(--weight-medium) 13px/1 var(--font-sans)",
              borderBottom: "1px solid var(--hairline)", marginBottom: 4, paddingBottom: 8 }}>
            <Icon name="chevron-left" size={15} color="var(--blue)" />
            Back
          </button>
        )}
        {displayTitle && !sub && (
          <div style={{ padding: "6px 10px 8px", font: "var(--weight-medium) 12px/1 var(--font-sans)",
            color: "var(--ink-500)", letterSpacing: "0.04em", textTransform: "uppercase",
            borderBottom: "1px solid var(--hairline)", marginBottom: 4 }}>
            {displayTitle}
          </div>
        )}
        {displayItems.map((it, i) => {
          if (it.divider) return <div key={`d${i}`} aria-hidden="true" style={{ height: 1, background: "var(--hairline)", margin: "4px 4px" }} />;
          const danger = it.danger;
          const hasSub = it.submenu && it.submenu.length > 0;
          return (
            <button key={it.label + i} type="button" role="menuitem" disabled={it.disabled}
              onClick={() => {
                if (hasSub) { setSub({ title: it.label, items: it.submenu }); return; }
                onClose && onClose(); it.onClick && it.onClick();
              }}
              className="tc-pressable"
              style={{
                display: "flex", alignItems: "center", gap: 12, width: "100%",
                height: 36, padding: "0 10px", border: "none", background: "transparent",
                borderRadius: 8, textAlign: "left", cursor: it.disabled ? "default" : "pointer",
                color: it.disabled ? "var(--ink-400)" : danger ? "var(--red)" : "var(--ink-900)",
                opacity: it.disabled ? 0.6 : 1,
              }}>
              {it.icon ? (
                <Icon name={it.icon} size={17} color={danger ? "var(--red)" : "var(--ink-500)"} style={{ flex: "none" }} />
              ) : <span style={{ width: 17, flex: "none" }} />}
              <span style={{ flex: 1, font: "var(--weight-regular) 14px/1 var(--font-sans)" }}>{it.label}</span>
              {hasSub && <Icon name="chevron-right" size={14} color="var(--ink-400)" />}
            </button>
          );
        })}
      </div>
    </div>
  );
}
