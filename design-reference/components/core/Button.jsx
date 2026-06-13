import React from "react";

/* Button — text actions. Primary is the ONE blue moment per view.
   variants: primary | secondary | ghost   sizes: lg (44) | md (40) | sm (32) */
export function Button({
  children,
  variant = "secondary",
  size = "lg",
  full = false,
  disabled = false,
  iconLeft = null,
  onClick,
  style,
  ...rest
}) {
  const heights = { lg: 48, md: 40, sm: 32 };
  const pads = { lg: "0 20px", md: "0 16px", sm: "0 12px" };
  const fonts = { lg: 16, md: 15, sm: 14 };

  const base = {
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    gap: 8,
    height: heights[size],
    padding: pads[size],
    width: full ? "100%" : "auto",
    border: "1px solid transparent",
    borderRadius: "var(--radius-pill)",
    font: `var(--weight-medium) ${fonts[size]}px/1 var(--font-sans)`,
    letterSpacing: "-0.01em",
    cursor: disabled ? "not-allowed" : "pointer",
    opacity: disabled ? 0.45 : 1,
    transition: "background var(--dur-fast) var(--ease-standard), transform var(--dur-fast) var(--ease-standard)",
    WebkitTapHighlightColor: "transparent",
    userSelect: "none",
  };

  const variants = {
    primary:   { background: "var(--primary)", color: "var(--primary-on)" },
    secondary: { background: "var(--surface)", color: "var(--ink-900)", borderColor: "var(--hairline-strong)" },
    ghost:     { background: "transparent", color: "var(--ink-700)" },
  };

  return (
    <button
      type="button"
      disabled={disabled}
      onClick={onClick}
      data-variant={variant}
      className="tc-btn"
      style={{ ...base, ...variants[variant], ...style }}
      {...rest}
    >
      {iconLeft}
      <span>{children}</span>
    </button>
  );
}
