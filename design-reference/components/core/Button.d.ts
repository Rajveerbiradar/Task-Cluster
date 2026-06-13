import * as React from "react";

export interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  /** primary = the single blue action; secondary = paper w/ hairline; ghost = text only */
  variant?: "primary" | "secondary" | "ghost";
  /** lg 48 · md 40 · sm 32 */
  size?: "lg" | "md" | "sm";
  /** stretch to container width */
  full?: boolean;
  disabled?: boolean;
  /** optional leading <Icon/> element */
  iconLeft?: React.ReactNode;
}

/** Text button. Use exactly one `primary` per view — blue is accent-only.
 *  Sentence case labels, no all-caps. */
export function Button(props: ButtonProps): JSX.Element;
