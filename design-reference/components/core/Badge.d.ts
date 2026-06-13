import * as React from "react";

export interface BadgeProps extends React.HTMLAttributes<HTMLSpanElement> {
  /** neutral = sunken paper; accent = blue tint (use sparingly) */
  tone?: "neutral" | "accent";
}

/** Small count/label pill for import-preview counts and the like. */
export function Badge(props: BadgeProps): JSX.Element;
