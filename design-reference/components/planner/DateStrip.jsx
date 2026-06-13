import React from "react";
import { Icon } from "../core/Icon.jsx";

/* DateStrip — a horizontally-scrollable week. Mon–Sun labels over numbers.
   Today is blue (the accent). The selected day (if not today) fills ink.
   A fixed calendar button sits on the LEFT: tap it to open a month picker
   and jump to any date, past or future. Placeholder days (outside the month)
   render as empty cells so the week stays aligned. */
const WD = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];

export function DateStrip({ days, selectedKey, todayKey, onSelect, onCalendar, style }) {
  // default: a sample week
  const list = days || WD.map((w, i) => ({ key: String(9 + i), weekday: w, date: 9 + i }));

  return (
    <div style={{ display: "flex", alignItems: "stretch", gap: 4, ...style }}>
      {/* calendar / jump-to-date button */}
      <button
        type="button"
        aria-label="Open calendar"
        onClick={onCalendar}
        className="tc-pressable"
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          justifyContent: "center",
          gap: 6,
          flex: "none",
          width: 44,
          padding: "4px 0",
          border: "none",
          background: "transparent",
          borderRadius: 14,
          cursor: "pointer",
          color: "var(--ink-600)",
        }}
      >
        <span style={{ font: "var(--weight-regular) 12px/1 var(--font-sans)", color: "var(--ink-400)" }}>All</span>
        <span
          style={{
            display: "inline-flex", alignItems: "center", justifyContent: "center",
            width: 36, height: 36, borderRadius: "var(--radius-pill)",
            background: "var(--surface)", border: "1px solid var(--hairline-strong)",
          }}
        >
          <Icon name="calendar" size={19} color="var(--ink-600)" />
        </span>
      </button>

      {/* divider between the button and the week */}
      <span aria-hidden="true" style={{ flex: "none", width: 1, alignSelf: "center", height: 40, background: "var(--hairline-strong)", margin: "0 4px 0 2px" }} />

      {/* the scrollable week */}
      <div
        style={{
          display: "flex",
          gap: 6,
          overflowX: "auto",
          padding: "4px 2px 8px",
          scrollbarWidth: "none",
          WebkitOverflowScrolling: "touch",
        }}
      >
        {list.map((d) => {
          if (d.placeholder) {
            return <span key={d.key} aria-hidden="true" style={{ flex: "none", width: 44 }} />;
          }
          const isToday = d.key === todayKey;
          const isSel = d.key === selectedKey;
          let circleBg = "transparent";
          let numColor = "var(--ink-900)";
          if (isToday) { circleBg = "var(--primary)"; numColor = "var(--primary-on)"; }
          else if (isSel) { circleBg = "var(--ink-200)"; numColor = "var(--ink-900)"; }
          return (
            <button
              key={d.key}
              type="button"
              onClick={() => onSelect && onSelect(d.key)}
              className="tc-pressable"
              style={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                gap: 6,
                flex: "none",
                width: 44,
                padding: "4px 0",
                border: "none",
                background: "transparent",
                borderRadius: 14,
                cursor: "pointer",
              }}
            >
              <span style={{ font: "var(--weight-regular) 12px/1 var(--font-sans)", color: isToday ? "var(--primary)" : "var(--ink-500)" }}>
                {d.weekday}
              </span>
              <span
                style={{
                  display: "inline-flex",
                  alignItems: "center",
                  justifyContent: "center",
                  width: 36,
                  height: 36,
                  borderRadius: 8,
                  background: circleBg,
                  color: numColor,
                  font: `var(--weight-medium) 16px/1 var(--font-sans)`,
                }}
              >
                {d.date}
              </span>
            </button>
          );
        })}
      </div>
    </div>
  );
}
