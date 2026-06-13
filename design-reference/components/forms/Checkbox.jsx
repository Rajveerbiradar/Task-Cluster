import React from "react";
import { Icon } from "../core/Icon.jsx";

/* Checkbox — the signature "checked = blue" moment.
   Unchecked: hairline square on paper. Checked: blue fill + white check.
   Completing never moves the row; it just greys in place (see TaskRow). */
export function Checkbox({ checked = false, disabled = false, size = 22, onChange, style, ...rest }) {
  return (
    <button
      type="button"
      role="checkbox"
      aria-checked={checked}
      disabled={disabled}
      onClick={() => !disabled && onChange && onChange(!checked)}
      className="tc-pressable"
      style={{
        display: "inline-flex",
        alignItems: "center",
        justifyContent: "center",
        width: size,
        height: size,
        flex: "none",
        padding: 0,
        borderRadius: "var(--radius-xs)",
        border: checked ? "1px solid var(--primary)" : "1.5px solid var(--ink-300)",
        background: checked ? "var(--primary)" : "transparent",
        color: "var(--primary-on)",
        cursor: disabled ? "not-allowed" : "pointer",
        opacity: disabled ? 0.5 : 1,
        transition: "background var(--dur-fast) var(--ease-standard), border-color var(--dur-fast) var(--ease-standard)",
        ...style,
      }}
      {...rest}
    >
      {checked && <Icon name="check" size={size - 7} stroke={2.5} color="var(--primary-on)" />}
    </button>
  );
}
