import * as React from "react";
import type { TimeStatus } from "./TimePill";

export interface TaskRowProps {
  /** task title — truncates at 1 line */
  title: string;
  /** optional description — truncates at 2 lines */
  description?: string;
  /** completed? greys the row in place and drops the pill */
  checked?: boolean;
  /** time-pill urgency status (ignored when checked) */
  status?: TimeStatus;
  /** time-pill label, e.g. "2h 40m" / "−2h 14m" */
  time?: string;
  /** show a 0.5px divider above (set on all rows but the first) */
  divider?: boolean;
  onToggle?: (next: boolean) => void;
  /** opens an origin-aware ContextMenu at the pointer — rename / delete */
  onMenu?: (anchor: { x: number; y: number }) => void;
  style?: React.CSSProperties;
}

/** A single task line. No card of its own — lives inside a SectionCard,
 *  separated by hairlines. Completion greys it in place; rows never reorder. */
export function TaskRow(props: TaskRowProps): JSX.Element;
