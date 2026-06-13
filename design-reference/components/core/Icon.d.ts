import * as React from "react";

export type IconName =
  | "chevron-right" | "chevron-down" | "check" | "search" | "plus" | "minus"
  | "x" | "pencil" | "more-vertical" | "calendar" | "upload" | "import" | "undo"
  | "info" | "clock" | "settings" | "trash" | "star"
  | "chevron-left" | "file" | "copy" | "download" | "share";

export interface IconProps extends React.SVGProps<SVGSVGElement> {
  /** Which glyph to render — TaskCluster's full in-product set. */
  name: IconName;
  /** Square px size. 24 on controls, 20 inline. Default 24. */
  size?: number;
  /** Stroke width. Default 1.75 (the house weight). */
  stroke?: number;
  /** Stroke colour. Defaults to currentColor (inherits ink). */
  color?: string;
}

/** Stroke-only line icon (Lucide family). Inherits ink via currentColor;
 *  goes blue only when it is the active/primary affordance. */
export function Icon(props: IconProps): JSX.Element;
