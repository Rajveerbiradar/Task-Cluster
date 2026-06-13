import * as React from "react";

export type BottomBarMode = "check" | "search" | "edit" | "add";

export interface BottomBarProps {
  /** active mode — rendered blue */
  mode?: BottomBarMode;
  onModeChange?: (mode: BottomBarMode) => void;
  style?: React.CSSProperties;
}

/** The floating three-mode pill (check · search · add) — the only
 *  persistently elevated element in the app. Active mode is blue. Editing
 *  happens inline via press-and-hold, so there is no separate edit mode. */
export function BottomBar(props: BottomBarProps): JSX.Element;
