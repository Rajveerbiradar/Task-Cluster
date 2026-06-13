import * as React from "react";
import type { TimeStatus } from "./TimePill";

/**
 * Props for SectionCard.
 * @startingPoint section="TaskCluster" subtitle="A collapsible section of tasks with progress and a time pill" viewport="440x340"
 */
export interface SectionCardProps {
  /** section title — truncates at 1 line */
  title: string;
  /** completed task count */
  done?: number;
  /** total task count (renders "done / total"; hidden when 0) */
  total?: number;
  /** header time-pill urgency */
  status?: TimeStatus;
  /** header time-pill label */
  time?: string;
  /** expanded? */
  expanded?: boolean;
  /** pinned (Daily): always open, no long-press menu, dimmed chevron */
  pinned?: boolean;
  onToggle?: (next: boolean) => void;
  /** opens an origin-aware ContextMenu at the pointer — rename / add task / delete */
  onMenu?: (anchor: { x: number; y: number }) => void;
  /** TaskRow children */
  children?: React.ReactNode;
  style?: React.CSSProperties;
}

/** A section on a paper surface: title with a count + progress bar beneath it,
 *  a time pill, and a right-hand chevron. Press and hold to reveal the menu;
 *  hairlines and whitespace only — never nested cards. */
export function SectionCard(props: SectionCardProps): JSX.Element;
