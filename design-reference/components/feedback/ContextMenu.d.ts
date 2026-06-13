import * as React from "react";

export interface ContextMenuItem {
  /** icon name from the Icon set (optional — items without one get aligned space) */
  icon?: string;
  label: string;
  /** red text + red icon (for destructive items like Delete) */
  danger?: boolean;
  disabled?: boolean;
  /** insert a hairline divider above this item (other fields are ignored when true) */
  divider?: boolean;
  /** right-aligned shortcut hint (e.g. "⌘D") */
  shortcut?: string;
  /** sub-menu items — when present, tapping drills into a secondary panel with a Back row */
  submenu?: ContextMenuItem[];
  onClick?: () => void;
}

/**
 * Props for ContextMenu.
 * @startingPoint section="Feedback" subtitle="Origin-aware popup menu" viewport="700x320"
 */
export interface ContextMenuProps {
  open: boolean;
  /** viewport-relative coords of the touch / right-click point */
  anchor?: { x: number; y: number };
  items: ContextMenuItem[];
  onClose?: () => void;
  /** optional caption rendered above the items (e.g. the section's title) */
  title?: string;
  minWidth?: number;
}

/** A small origin-aware popup menu — opens at the long-press / right-click
 *  point and flips quadrant so it never gets clipped. Use this everywhere a
 *  short, focused action list is needed (not a bottom sheet). */
export function ContextMenu(props: ContextMenuProps): JSX.Element | null;
