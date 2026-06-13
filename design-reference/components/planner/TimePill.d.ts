import * as React from "react";

export type TimeStatus = "calm" | "on-track" | "close" | "due" | "overdue";

export interface TimePillProps {
  /** urgency step. calm renders nothing. overdue renders the red capsule. */
  status?: TimeStatus;
  /** the remaining-time text, e.g. "2h 40m", "9m", "−2h 14m" */
  label?: string;
  style?: React.CSSProperties;
}

/** The signature time pill. Grey/absent when calm; blue → amber → red text
 *  as a deadline nears; a red capsule (counting up) once overdue. */
export function TimePill(props: TimePillProps): JSX.Element | null;
