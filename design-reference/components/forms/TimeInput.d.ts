import * as React from "react";

export type TimeInputMode = "Date & time" | "Days & hours";

export interface TimeInputProps {
  /** which segment is active */
  mode?: TimeInputMode;
  /** the date-field contents (Date & time mode) */
  value?: string;
  /** the time-of-day field contents (Date & time mode) — default "12:59 PM" */
  time?: string;
  /** the days field contents (Days & hours mode) */
  days?: string;
  /** the hours field contents (Days & hours mode) — default "0" */
  hours?: string;
  onModeChange?: (mode: TimeInputMode) => void;
  onValueChange?: (value: string) => void;
  onTimeChange?: (time: string) => void;
  onDaysChange?: (days: string) => void;
  onHoursChange?: (hours: string) => void;
  /** field label (default "Deadline") */
  label?: string;
  style?: React.CSSProperties;
}

/** Deadline control with two ways to set it: an exact "Date & time" or a
 *  relative "Days & hours" from now. Both modes use equal-width fields so
 *  neither dominates. Drives a task's TimePill. */
export function TimeInput(props: TimeInputProps): JSX.Element;
