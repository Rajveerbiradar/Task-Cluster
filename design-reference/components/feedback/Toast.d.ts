import * as React from "react";

export interface ToastProps {
  /** past-tense fact, e.g. "saved" */
  children?: React.ReactNode;
  /** show the leading check (default true) */
  icon?: boolean;
  style?: React.CSSProperties;
}

/** Dark ink confirmation toast. States a fact in past tense; no celebration. */
export function Toast(props: ToastProps): JSX.Element;
