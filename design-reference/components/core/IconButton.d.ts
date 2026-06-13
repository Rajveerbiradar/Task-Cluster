import * as React from "react";

export interface IconButtonProps extends Omit<React.ButtonHTMLAttributes<HTMLButtonElement>, "aria-label"> {
  /** an <Icon/> element */
  icon: React.ReactNode;
  /** accessible label (required — icon-only control) */
  label: string;
  /** true when this is the active mode/selection — renders blue */
  active?: boolean;
  /** square tap-target px (default 44 — never go below 44) */
  size?: number;
  disabled?: boolean;
}

/** Icon-only button with a ≥44px hit target. Blue only when `active`. */
export function IconButton(props: IconButtonProps): JSX.Element;
