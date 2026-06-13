import React from "react";

/* IconButton — a 44px tap target wrapping a single line icon.
   active = the current bottom-bar mode (blue). Otherwise quiet ink. */
export function IconButton({
  icon,
  label,
  active = false,
  size = 44,
  disabled = false,
  onClick,
  style,
  ...rest
}) {
  return (
    <button
      type="button"
      aria-label={label}
      disabled={disabled}
      onClick={onClick}
      className="tc-pressable"
      style={{
        display: "inline-flex",
        alignItems: "center",
        justifyContent: "center",
        width: size,
        height: size,
        padding: 0,
        border: "none",
        borderRadius: "var(--radius-pill)",
        background: "transparent",
        color: active ? "var(--blue)" : "var(--ink-700)",
        cursor: disabled ? "not-allowed" : "pointer",
        opacity: disabled ? 0.4 : 1,
        ...style,
      }}
      {...rest}
    >
      {icon}
    </button>
  );
}
