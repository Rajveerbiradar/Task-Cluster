import * as React from "react";

export interface DateStripDay {
  /** stable key (e.g. ISO date or day-of-month string) */
  key: string;
  /** short weekday label, e.g. "Mon" */
  weekday?: string;
  /** day-of-month number */
  date?: number;
  /** render an empty spacer cell (date outside the visible month) */
  placeholder?: boolean;
}

export interface DateStripProps {
  /** the week to render; omit for a sample week */
  days?: DateStripDay[];
  /** key of the currently-viewed day */
  selectedKey?: string;
  /** key of the real "today" — rendered blue */
  todayKey?: string;
  onSelect?: (key: string) => void;
  /** opens the month picker (calendar button on the left of the strip) */
  onCalendar?: () => void;
  style?: React.CSSProperties;
}

/** Horizontally-scrollable week strip with a fixed calendar button on the left
 *  (tap to jump to any date). Today is blue; the selected day fills ink.
 *  Days flagged `placeholder` (outside the month) render as empty cells. */
export function DateStrip(props: DateStripProps): JSX.Element;
