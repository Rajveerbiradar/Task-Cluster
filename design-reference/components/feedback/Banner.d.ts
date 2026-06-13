import * as React from "react";

export interface BannerProps {
  /** read-only = past day · planning = future day · info = generic */
  variant?: "read-only" | "planning" | "info";
  /** override copy; defaults to the on-voice text for the variant */
  children?: React.ReactNode;
  style?: React.CSSProperties;
}

/** Quiet, paper-toned notice for date modes. States the mode, never alarms. */
export function Banner(props: BannerProps): JSX.Element;
