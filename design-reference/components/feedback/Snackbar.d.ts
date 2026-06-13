import * as React from "react";

export interface SnackbarProps {
  /** the fact, e.g. "task deleted" */
  children?: React.ReactNode;
  /** single action verb (default "undo") */
  action?: string;
  onAction?: () => void;
  style?: React.CSSProperties;
}

/** Dark ink snackbar pairing a fact with one undo action — the blue is the
 *  action affordance. Used after destructive actions. */
export function Snackbar(props: SnackbarProps): JSX.Element;
