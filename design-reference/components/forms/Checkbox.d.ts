import * as React from "react";

export interface CheckboxProps {
  /** checked state */
  checked?: boolean;
  disabled?: boolean;
  /** box size in px (default 22) */
  size?: number;
  /** called with the next boolean */
  onChange?: (next: boolean) => void;
  style?: React.CSSProperties;
}

/** The signature checkbox: hairline square → blue fill + white check.
 *  Checking a task greys the row in place; it never reorders. */
export function Checkbox(props: CheckboxProps): JSX.Element;
