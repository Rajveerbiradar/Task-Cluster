import React from "react";
import { Icon } from "../core/Icon.jsx";

/* TimeInput — the deadline control from the create/edit form.
   Three states:
     • "No time limit" checkbox checked — no deadline fields shown
     • "Date & time" — an exact calendar date with a time-of-day
     • "Days & hours" — a relative offset from now */
const MODES = ["Date & time", "Days & hours"];

export function TimeInput({
  mode = "Date & time",
  noLimit = false,
  value = "",
  time = "12:59 PM",
  days = "",
  hours = "0",
  onModeChange,
  onNoLimitChange,
  onValueChange,
  onTimeChange,
  onDaysChange,
  onHoursChange,
  label = "Deadline",
  style,
}) {
  const isDate = mode === "Date & time";

  const fieldShell = {
    display: "flex",
    alignItems: "center",
    gap: 10,
    height: 48,
    padding: "0 14px",
    background: "var(--surface)",
    border: "1px solid var(--hairline-strong)",
    borderRadius: "var(--radius-sm)",
    minWidth: 0,
  };
  const inputStyle = {
    flex: 1,
    minWidth: 0,
    width: "100%",
    border: "none",
    outline: "none",
    background: "transparent",
    font: "var(--weight-regular) 16px/1 var(--font-sans)",
    color: "var(--ink-900)",
  };
  const unitStyle = { flex: "none", font: "var(--weight-regular) 14px/1 var(--font-sans)", color: "var(--ink-400)" };

  return (
    <div style={{ ...style }}>
      {label && (
        <span style={{ display: "block", marginBottom: 8, font: "var(--weight-medium) 13px/1 var(--font-sans)", color: "var(--ink-500)" }}>
          {label}
        </span>
      )}

      {/* No time limit checkbox */}
      <label
        style={{
          display: "flex", alignItems: "center", gap: 10,
          padding: "8px 4px", marginBottom: noLimit ? 0 : 12,
          cursor: "pointer", userSelect: "none",
        }}
      >
        <span
          onClick={() => onNoLimitChange && onNoLimitChange(!noLimit)}
          style={{
            display: "grid", placeItems: "center",
            width: 20, height: 20, flex: "none",
            borderRadius: 5,
            border: noLimit ? "1px solid var(--primary)" : "1.5px solid var(--ink-300)",
            background: noLimit ? "var(--primary)" : "transparent",
            cursor: "pointer",
            transition: "background 120ms, border 120ms",
          }}
        >
          {noLimit && (
            <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="var(--primary-on)" strokeWidth="3" strokeLinecap="round" strokeLinejoin="round">
              <path d="M20 6 9 17l-5-5" />
            </svg>
          )}
        </span>
        <span style={{ font: "var(--weight-regular) 15px/1 var(--font-sans)", color: "var(--ink-700)" }}>
          No time limit
        </span>
      </label>

      {/* Only show mode selector + fields when NOT "no limit" */}
      {!noLimit && (
        <>
          {/* segmented control — two options, equal widths */}
          <div
            role="tablist"
            style={{
              display: "grid",
              gridTemplateColumns: "1fr 1fr",
              gap: 3,
              padding: 3,
              background: "var(--surface-sunken)",
              borderRadius: "var(--radius-sm)",
              marginBottom: 12,
            }}
          >
            {MODES.map((m) => {
              const on = m === mode;
              return (
                <button
                  key={m}
                  type="button"
                  role="tab"
                  aria-selected={on}
                  onClick={() => onModeChange && onModeChange(m)}
                  className="tc-pressable"
                  style={{
                    height: 34,
                    border: "none",
                    borderRadius: 6,
                    background: on ? "var(--surface-raised)" : "transparent",
                    boxShadow: on ? "0 1px 2px rgba(44,44,42,0.10)" : "none",
                    color: on ? "var(--ink-900)" : "var(--ink-500)",
                    font: `var(--weight-medium) 14px/1 var(--font-sans)`,
                    cursor: "pointer",
                  }}
                >
                  {m}
                </button>
              );
            })}
          </div>

          {isDate ? (
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 8 }}>
              <div style={fieldShell}>
                <Icon name="calendar" size={20} color="var(--ink-500)" />
                <input
                  value={value}
                  onChange={(e) => onValueChange && onValueChange(e.target.value)}
                  placeholder="Jun 12, 2026"
                  style={inputStyle}
                />
              </div>
              <div style={fieldShell}>
                <Icon name="clock" size={20} color="var(--ink-500)" />
                <input
                  value={time}
                  onChange={(e) => onTimeChange && onTimeChange(e.target.value)}
                  placeholder="12:59 PM"
                  style={inputStyle}
                />
              </div>
            </div>
          ) : (
            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 8 }}>
              <div style={fieldShell}>
                <input
                  value={days}
                  onChange={(e) => onDaysChange && onDaysChange(e.target.value)}
                  placeholder="2"
                  inputMode="numeric"
                  style={{ ...inputStyle, textAlign: "right" }}
                />
                <span style={unitStyle}>days</span>
              </div>
              <div style={fieldShell}>
                <input
                  value={hours}
                  onChange={(e) => onHoursChange && onHoursChange(e.target.value)}
                  placeholder="0"
                  inputMode="numeric"
                  style={{ ...inputStyle, textAlign: "right" }}
                />
                <span style={unitStyle}>hours</span>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}
