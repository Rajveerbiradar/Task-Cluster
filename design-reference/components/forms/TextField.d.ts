import * as React from "react";

export interface TextFieldProps {
  /** label shown above in meta type */
  label?: string;
  value?: string;
  placeholder?: string;
  /** render a textarea (task description) */
  multiline?: boolean;
  /** textarea rows when multiline */
  rows?: number;
  onChange?: (value: string) => void;
  style?: React.CSSProperties;
}

/** Paper text input. Focus deepens the hairline to ink — never a blue glow. */
export function TextField(props: TextFieldProps): JSX.Element;
