import * as React from "react";

export interface ParentSectionProps {
  /** parent title (quiet, meta-sized) — e.g. "Daily" */
  title: string;
  /** optional count shown at the right of the header (e.g. number of sections) */
  count?: number;
  /** pinned parents (e.g. Daily) show a pin dot before the title and can't be deleted */
  pinned?: boolean;
  /** opens an origin-aware ContextMenu at the pointer — rename / color / add section / delete */
  onMenu?: (anchor: { x: number; y: number }) => void;
  /** optional color tint palette — when set, cascades CSS custom properties
   *  into child SectionCards so they inherit the parent's hue. */
  tint?: {
    paper: string;
    border: string;
    surface: string;
    surfaceBorder: string;
    ink: string;
    meta: string;
  };
  /** child SectionCards */
  children?: React.ReactNode;
  style?: React.CSSProperties;
}

/** Top of the hierarchy (Parent → Section → Task). A non-collapsible
 *  warm-tinted CONTAINER that visibly holds its SectionCards, with the
 *  parent title on a header bar at the top. Daily is `pinned`. */
export function ParentSection(props: ParentSectionProps): JSX.Element;
