/* @ds-bundle: {"format":3,"namespace":"TaskClusterDesignSystem_1763fe","components":[{"name":"Badge","sourcePath":"components/core/Badge.jsx"},{"name":"Button","sourcePath":"components/core/Button.jsx"},{"name":"Icon","sourcePath":"components/core/Icon.jsx"},{"name":"IconButton","sourcePath":"components/core/IconButton.jsx"},{"name":"Banner","sourcePath":"components/feedback/Banner.jsx"},{"name":"ContextMenu","sourcePath":"components/feedback/ContextMenu.jsx"},{"name":"Snackbar","sourcePath":"components/feedback/Snackbar.jsx"},{"name":"Toast","sourcePath":"components/feedback/Toast.jsx"},{"name":"Checkbox","sourcePath":"components/forms/Checkbox.jsx"},{"name":"TextField","sourcePath":"components/forms/TextField.jsx"},{"name":"TimeInput","sourcePath":"components/forms/TimeInput.jsx"},{"name":"BottomBar","sourcePath":"components/planner/BottomBar.jsx"},{"name":"DateStrip","sourcePath":"components/planner/DateStrip.jsx"},{"name":"ParentSection","sourcePath":"components/planner/ParentSection.jsx"},{"name":"SectionCard","sourcePath":"components/planner/SectionCard.jsx"},{"name":"TaskRow","sourcePath":"components/planner/TaskRow.jsx"},{"name":"TimePill","sourcePath":"components/planner/TimePill.jsx"}],"sourceHashes":{"components/core/Badge.jsx":"9bb367a8b15d","components/core/Button.jsx":"9565344b0f7b","components/core/Icon.jsx":"070689287d8e","components/core/IconButton.jsx":"2bc4acb0c775","components/feedback/Banner.jsx":"b45f5281f496","components/feedback/ContextMenu.jsx":"fef332722a17","components/feedback/Snackbar.jsx":"c478860d6b16","components/feedback/Toast.jsx":"48e10d8a6b39","components/forms/Checkbox.jsx":"47b58a0fea97","components/forms/TextField.jsx":"5630e5dc8799","components/forms/TimeInput.jsx":"c05b06c1158f","components/planner/BottomBar.jsx":"8a86df43a3ff","components/planner/DateStrip.jsx":"27f2e3eca05e","components/planner/ParentSection.jsx":"ed8a969d305a","components/planner/SectionCard.jsx":"161750fd607c","components/planner/TaskRow.jsx":"d5d22d1d42d3","components/planner/TimePill.jsx":"31e7e6280389","ui_kits/taskcluster/tcApp.jsx":"573a3e4c33d3"},"inlinedExternals":[],"unexposedExports":[]} */

(() => {

const __ds_ns = (window.TaskClusterDesignSystem_1763fe = window.TaskClusterDesignSystem_1763fe || {});

const __ds_scope = {};

(__ds_ns.__errors = __ds_ns.__errors || []);

// components/core/Badge.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* Badge — a quiet count/label pill. Used for import-preview counts and
   section progress where a chip reads better than bare text.
   tone: neutral (default) | accent (blue — use sparingly). */
function Badge({
  children,
  tone = "neutral",
  style,
  ...rest
}) {
  const tones = {
    neutral: {
      background: "var(--surface-sunken)",
      color: "var(--ink-600)"
    },
    accent: {
      background: "var(--blue-tint)",
      color: "var(--blue-press)"
    }
  };
  return /*#__PURE__*/React.createElement("span", _extends({
    style: {
      display: "inline-flex",
      alignItems: "center",
      gap: 6,
      height: 24,
      padding: "0 10px",
      borderRadius: "var(--radius-pill)",
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      letterSpacing: "-0.01em",
      ...tones[tone],
      ...style
    }
  }, rest), children);
}
Object.assign(__ds_scope, { Badge });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Badge.jsx", error: String((e && e.message) || e) }); }

// components/core/Button.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* Button — text actions. Primary is the ONE blue moment per view.
   variants: primary | secondary | ghost   sizes: lg (44) | md (40) | sm (32) */
function Button({
  children,
  variant = "secondary",
  size = "lg",
  full = false,
  disabled = false,
  iconLeft = null,
  onClick,
  style,
  ...rest
}) {
  const heights = {
    lg: 48,
    md: 40,
    sm: 32
  };
  const pads = {
    lg: "0 20px",
    md: "0 16px",
    sm: "0 12px"
  };
  const fonts = {
    lg: 16,
    md: 15,
    sm: 14
  };
  const base = {
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    gap: 8,
    height: heights[size],
    padding: pads[size],
    width: full ? "100%" : "auto",
    border: "1px solid transparent",
    borderRadius: "var(--radius-pill)",
    font: `var(--weight-medium) ${fonts[size]}px/1 var(--font-sans)`,
    letterSpacing: "-0.01em",
    cursor: disabled ? "not-allowed" : "pointer",
    opacity: disabled ? 0.45 : 1,
    transition: "background var(--dur-fast) var(--ease-standard), transform var(--dur-fast) var(--ease-standard)",
    WebkitTapHighlightColor: "transparent",
    userSelect: "none"
  };
  const variants = {
    primary: {
      background: "var(--primary)",
      color: "var(--primary-on)"
    },
    secondary: {
      background: "var(--surface)",
      color: "var(--ink-900)",
      borderColor: "var(--hairline-strong)"
    },
    ghost: {
      background: "transparent",
      color: "var(--ink-700)"
    }
  };
  return /*#__PURE__*/React.createElement("button", _extends({
    type: "button",
    disabled: disabled,
    onClick: onClick,
    "data-variant": variant,
    className: "tc-btn",
    style: {
      ...base,
      ...variants[variant],
      ...style
    }
  }, rest), iconLeft, /*#__PURE__*/React.createElement("span", null, children));
}
Object.assign(__ds_scope, { Button });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Button.jsx", error: String((e && e.message) || e) }); }

// components/core/Icon.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* TaskCluster icon set — Lucide paths (ISC), the same family as the
   source Figma's "Sigma Icons". Stroke-only, currentColor, 1.75px default.
   Only the glyphs the product actually uses are included — no clutter. */

const PATHS = {
  "chevron-right": /*#__PURE__*/React.createElement("path", {
    d: "m9 18 6-6-6-6"
  }),
  "chevron-down": /*#__PURE__*/React.createElement("path", {
    d: "m6 9 6 6 6-6"
  }),
  check: /*#__PURE__*/React.createElement("path", {
    d: "M20 6 9 17l-5-5"
  }),
  search: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "11",
    cy: "11",
    r: "8"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m21 21-4.3-4.3"
  })),
  plus: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M5 12h14"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 5v14"
  })),
  minus: /*#__PURE__*/React.createElement("path", {
    d: "M5 12h14"
  }),
  x: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M18 6 6 18"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m6 6 12 12"
  })),
  pencil: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M12 20h9"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4Z"
  })),
  "more-vertical": /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "5",
    r: "1"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "1"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "19",
    r: "1"
  })),
  calendar: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M8 2v4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M16 2v4"
  }), /*#__PURE__*/React.createElement("rect", {
    width: "18",
    height: "18",
    x: "3",
    y: "4",
    rx: "2"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M3 10h18"
  })),
  upload: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m17 8-5-5-5 5"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 3v12"
  })),
  "import": /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m7 10 5 5 5-5"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 15V3"
  })),
  undo: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M3 7v6h6"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M21 17a9 9 0 0 0-9-9 9 9 0 0 0-6 2.3L3 13"
  })),
  info: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "10"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 16v-4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 8h.01"
  })),
  clock: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "10"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 6v6l4 2"
  })),
  settings: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "3"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 1 1-4 0v-.09a1.65 1.65 0 0 0-1-1.51 1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 1 1 0-4h.09a1.65 1.65 0 0 0 1.51-1 1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33h0a1.65 1.65 0 0 0 1-1.51V3a2 2 0 1 1 4 0v.09a1.65 1.65 0 0 0 1 1.51h0a1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82v0a1.65 1.65 0 0 0 1.51 1H21a2 2 0 1 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"
  })),
  trash: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M3 6h18"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"
  })),
  star: /*#__PURE__*/React.createElement("path", {
    d: "M12 2.6l2.92 5.92 6.53.95-4.72 4.6 1.12 6.5L12 17.5l-5.85 3.07 1.12-6.5L2.55 9.47l6.53-.95L12 2.6z"
  }),
  "chevron-left": /*#__PURE__*/React.createElement("path", {
    d: "m15 18-6-6 6-6"
  }),
  file: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M15 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V7Z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M14 2v4a1 1 0 0 0 1 1h3"
  })),
  copy: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("rect", {
    width: "14",
    height: "14",
    x: "8",
    y: "8",
    rx: "2"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M4 16c-1.1 0-2-.9-2-2V4c0-1.1.9-2 2-2h10c1.1 0 2 .9 2 2"
  })),
  download: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m7 10 5 5 5-5"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 15V3"
  })),
  share: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "18",
    cy: "5",
    r: "3"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "6",
    cy: "12",
    r: "3"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "18",
    cy: "19",
    r: "3"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m8.59 13.51 6.83 3.98"
  }), /*#__PURE__*/React.createElement("path", {
    d: "m15.41 6.51-6.82 3.98"
  })),
  home: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M9 22V12h6v10"
  })),
  list: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M8 6h13"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 12h13"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M8 18h13"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M3 6h.01"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M3 12h.01"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M3 18h.01"
  })),
  heart: /*#__PURE__*/React.createElement("path", {
    d: "M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3.33.88-4.5 2.17A6.4 6.4 0 0 0 7.5 3 5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"
  }),
  pin: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M12 17v5"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M9 10.76a2 2 0 0 1-1.11 1.79l-1.78.9A2 2 0 0 0 5 15.24V16h14v-.76a2 2 0 0 0-1.11-1.79l-1.78-.9A2 2 0 0 1 15 10.76V7a1 1 0 0 1 1-1 2 2 0 0 0 2-2H6a2 2 0 0 0 2 2 1 1 0 0 1 1 1z"
  })),
  bookmark: /*#__PURE__*/React.createElement("path", {
    d: "m19 21-7-4-7 4V5a2 2 0 0 1 2-2h10a2 2 0 0 1 2 2v16z"
  }),
  flag: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M4 15s1-1 4-1 5 2 8 2 4-1 4-1V3s-1 1-4 1-5-2-8-2-4 1-4 1z"
  }), /*#__PURE__*/React.createElement("line", {
    x1: "4",
    x2: "4",
    y1: "22",
    y2: "15"
  })),
  zap: /*#__PURE__*/React.createElement("path", {
    d: "M13 2 3 14h9l-1 8 10-12h-9l1-8z"
  }),
  target: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "10"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "6"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "12",
    r: "2"
  })),
  briefcase: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M16 20V4a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"
  }), /*#__PURE__*/React.createElement("rect", {
    width: "20",
    height: "14",
    x: "2",
    y: "6",
    rx: "2"
  })),
  coffee: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M10 2v2"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M14 2v2"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M16 8a1 1 0 0 1 1 1v3a4 4 0 0 1-4 4H7a4 4 0 0 1-4-4V9a1 1 0 0 1 1-1h12a4 4 0 0 0 4 4"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M6 19h8"
  })),
  "map-pin": /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("path", {
    d: "M20 10c0 6-8 12-8 12s-8-6-8-12a8 8 0 0 1 16 0Z"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "12",
    cy: "10",
    r: "3"
  })),
  palette: /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("circle", {
    cx: "13.5",
    cy: "6.5",
    r: ".5",
    fill: "currentColor"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "17.5",
    cy: "10.5",
    r: ".5",
    fill: "currentColor"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "8.5",
    cy: "7.5",
    r: ".5",
    fill: "currentColor"
  }), /*#__PURE__*/React.createElement("circle", {
    cx: "6.5",
    cy: "12",
    r: ".5",
    fill: "currentColor"
  }), /*#__PURE__*/React.createElement("path", {
    d: "M12 2C6.5 2 2 6.5 2 12s4.5 10 10 10c.93 0 1.5-.75 1.5-1.5 0-.39-.15-.74-.39-1.02a1.5 1.5 0 0 1 1.12-2.48H16a6 6 0 0 0 6-6c0-5.52-4.48-10-10-10Z"
  }))
};
function Icon({
  name,
  size = 24,
  stroke = 1.75,
  color = "currentColor",
  style,
  ...rest
}) {
  const glyph = PATHS[name];
  return /*#__PURE__*/React.createElement("svg", _extends({
    width: size,
    height: size,
    viewBox: "0 0 24 24",
    fill: "none",
    stroke: color,
    strokeWidth: stroke,
    strokeLinecap: "round",
    strokeLinejoin: "round",
    "aria-hidden": "true",
    style: {
      display: "block",
      flex: "none",
      ...style
    }
  }, rest), glyph || null);
}
Object.assign(__ds_scope, { Icon });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/Icon.jsx", error: String((e && e.message) || e) }); }

// components/core/IconButton.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* IconButton — a 44px tap target wrapping a single line icon.
   active = the current bottom-bar mode (blue). Otherwise quiet ink. */
function IconButton({
  icon,
  label,
  active = false,
  size = 44,
  disabled = false,
  onClick,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("button", _extends({
    type: "button",
    "aria-label": label,
    disabled: disabled,
    onClick: onClick,
    className: "tc-pressable",
    style: {
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      width: size,
      height: size,
      padding: 0,
      border: "none",
      borderRadius: "var(--radius-pill)",
      background: "transparent",
      color: active ? "var(--blue)" : "var(--ink-700)",
      cursor: disabled ? "not-allowed" : "pointer",
      opacity: disabled ? 0.4 : 1,
      ...style
    }
  }, rest), icon);
}
Object.assign(__ds_scope, { IconButton });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/core/IconButton.jsx", error: String((e && e.message) || e) }); }

// components/feedback/Banner.jsx
try { (() => {
/* Banner — quiet date-mode notices and info. Never alarming.
   variant: read-only (past day) | planning (future day) | info. */
const COPY = {
  "read-only": "viewing — read only",
  planning: "planning — hidden until then",
  info: ""
};
function Banner({
  variant = "info",
  children,
  style
}) {
  return /*#__PURE__*/React.createElement("div", {
    role: "status",
    style: {
      display: "flex",
      alignItems: "center",
      gap: 10,
      padding: "10px 14px",
      background: "var(--warn-bg)",
      border: "1px solid var(--warn-border)",
      borderRadius: "var(--radius-md)",
      ...style
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "info",
    size: 18,
    color: "var(--ink-500)"
  }), /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-regular) 13px/1.3 var(--font-sans)",
      color: "var(--ink-600)"
    }
  }, children || COPY[variant]));
}
Object.assign(__ds_scope, { Banner });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/Banner.jsx", error: String((e && e.message) || e) }); }

// components/feedback/ContextMenu.jsx
try { (() => {
/* ContextMenu — a small origin-aware popup.
   Opens at the user's touch point and flips quadrant so it never clips.
   Items can carry a `submenu: []` array — tapping drills into a sub-panel
   with a back row (mobile-friendly). */
const PAD = 8;
const NUDGE = 4;
function ContextMenu({
  open,
  anchor,
  items = [],
  onClose,
  minWidth = 220,
  title
}) {
  const ref = React.useRef(null);
  const [pos, setPos] = React.useState({
    left: -9999,
    top: -9999,
    originX: "left",
    originY: "top"
  });
  const [sub, setSub] = React.useState(null); // { title, items }

  React.useEffect(() => {
    if (!open) setSub(null);
  }, [open]);
  React.useLayoutEffect(() => {
    if (!open || !anchor || !ref.current) return;
    const el = ref.current;
    const rect = el.getBoundingClientRect();
    const w = rect.width || minWidth;
    const h = rect.height || 1;
    const vw = window.innerWidth;
    const vh = window.innerHeight;
    const fromRight = anchor.x > vw / 2;
    let left = fromRight ? anchor.x - w - NUDGE : anchor.x + NUDGE;
    if (left < PAD) left = PAD;
    if (left + w > vw - PAD) left = vw - PAD - w;
    let top = anchor.y + NUDGE;
    let originY = "top";
    if (top + h > vh - PAD) {
      top = anchor.y - h - NUDGE;
      originY = "bottom";
    }
    if (top < PAD) top = PAD;
    setPos({
      left,
      top,
      originX: fromRight ? "right" : "left",
      originY
    });
  }, [open, anchor, items, sub, minWidth]);
  React.useEffect(() => {
    if (!open) return;
    const onKey = e => {
      if (e.key === "Escape") {
        if (sub) setSub(null);else onClose && onClose();
      }
    };
    window.addEventListener("keydown", onKey);
    return () => window.removeEventListener("keydown", onKey);
  }, [open, onClose, sub]);
  if (!open) return null;
  const displayItems = sub ? sub.items : items;
  const displayTitle = sub ? sub.title : title;
  return /*#__PURE__*/React.createElement("div", {
    onClick: e => {
      if (e.target === e.currentTarget) {
        onClose && onClose();
      }
    },
    onContextMenu: e => {
      e.preventDefault();
      onClose && onClose();
    },
    style: {
      position: "absolute",
      inset: 0,
      zIndex: 60,
      background: "transparent",
      animation: "tcFade var(--dur-fast) var(--ease-standard)"
    }
  }, /*#__PURE__*/React.createElement("div", {
    ref: ref,
    role: "menu",
    style: {
      position: "absolute",
      left: pos.left,
      top: pos.top,
      minWidth,
      maxWidth: 280,
      padding: "6px",
      background: "var(--surface-raised)",
      border: "1px solid var(--hairline-strong)",
      borderRadius: "var(--radius-md)",
      boxShadow: "var(--shadow-menu)",
      transformOrigin: `${pos.originX} ${pos.originY}`,
      animation: "tcMenuIn var(--dur-fast) var(--ease-standard)"
    }
  }, sub && /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => setSub(null),
    style: {
      display: "flex",
      alignItems: "center",
      gap: 8,
      width: "100%",
      height: 32,
      padding: "0 8px",
      border: "none",
      background: "transparent",
      borderRadius: 8,
      cursor: "pointer",
      color: "var(--blue)",
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      borderBottom: "1px solid var(--hairline)",
      marginBottom: 4,
      paddingBottom: 8
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "chevron-left",
    size: 15,
    color: "var(--blue)"
  }), "Back"), displayTitle && !sub && /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "6px 10px 8px",
      font: "var(--weight-medium) 12px/1 var(--font-sans)",
      color: "var(--ink-500)",
      letterSpacing: "0.04em",
      textTransform: "uppercase",
      borderBottom: "1px solid var(--hairline)",
      marginBottom: 4
    }
  }, displayTitle), displayItems.map((it, i) => {
    if (it.divider) return /*#__PURE__*/React.createElement("div", {
      key: `d${i}`,
      "aria-hidden": "true",
      style: {
        height: 1,
        background: "var(--hairline)",
        margin: "4px 4px"
      }
    });
    const danger = it.danger;
    const hasSub = it.submenu && it.submenu.length > 0;
    return /*#__PURE__*/React.createElement("button", {
      key: it.label + i,
      type: "button",
      role: "menuitem",
      disabled: it.disabled,
      onClick: () => {
        if (hasSub) {
          setSub({
            title: it.label,
            items: it.submenu
          });
          return;
        }
        onClose && onClose();
        it.onClick && it.onClick();
      },
      className: "tc-pressable",
      style: {
        display: "flex",
        alignItems: "center",
        gap: 12,
        width: "100%",
        height: 36,
        padding: "0 10px",
        border: "none",
        background: "transparent",
        borderRadius: 8,
        textAlign: "left",
        cursor: it.disabled ? "default" : "pointer",
        color: it.disabled ? "var(--ink-400)" : danger ? "var(--red)" : "var(--ink-900)",
        opacity: it.disabled ? 0.6 : 1
      }
    }, it.icon ? /*#__PURE__*/React.createElement(__ds_scope.Icon, {
      name: it.icon,
      size: 17,
      color: danger ? "var(--red)" : "var(--ink-500)",
      style: {
        flex: "none"
      }
    }) : /*#__PURE__*/React.createElement("span", {
      style: {
        width: 17,
        flex: "none"
      }
    }), /*#__PURE__*/React.createElement("span", {
      style: {
        flex: 1,
        font: "var(--weight-regular) 14px/1 var(--font-sans)"
      }
    }, it.label), hasSub && /*#__PURE__*/React.createElement(__ds_scope.Icon, {
      name: "chevron-right",
      size: 14,
      color: "var(--ink-400)"
    }));
  })));
}
Object.assign(__ds_scope, { ContextMenu });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/ContextMenu.jsx", error: String((e && e.message) || e) }); }

// components/feedback/Snackbar.jsx
try { (() => {
/* Snackbar — a fact paired with a single undo verb. Dark ink surface.
   Used after destructive actions ("task deleted · undo"). */
function Snackbar({
  children = "task deleted",
  action = "undo",
  onAction,
  style
}) {
  return /*#__PURE__*/React.createElement("div", {
    role: "status",
    style: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      gap: 16,
      padding: "12px 12px 12px 16px",
      background: "var(--toast-bg)",
      color: "var(--toast-text)",
      borderRadius: "var(--radius-md)",
      boxShadow: "var(--shadow-overlay)",
      ...style
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-regular) 14px/1 var(--font-sans)"
    }
  }, children), /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: onAction,
    className: "tc-pressable",
    style: {
      flex: "none",
      border: "none",
      background: "transparent",
      padding: "6px 10px",
      borderRadius: 8,
      color: "var(--blue)",
      font: "var(--weight-medium) 14px/1 var(--font-sans)",
      cursor: "pointer"
    }
  }, action));
}
Object.assign(__ds_scope, { Snackbar });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/Snackbar.jsx", error: String((e && e.message) || e) }); }

// components/feedback/Toast.jsx
try { (() => {
/* Toast — a dark ink confirmation. Past-tense fact only ("saved").
   Small, centred, transient. No colour, no celebration. */
function Toast({
  children = "saved",
  icon = true,
  style
}) {
  return /*#__PURE__*/React.createElement("div", {
    role: "status",
    style: {
      display: "inline-flex",
      alignItems: "center",
      gap: 8,
      padding: "11px 16px",
      background: "var(--toast-bg)",
      color: "var(--toast-text)",
      borderRadius: "var(--radius-md)",
      font: "var(--weight-medium) 14px/1 var(--font-sans)",
      letterSpacing: "-0.01em",
      boxShadow: "var(--shadow-overlay)",
      ...style
    }
  }, icon && /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "check",
    size: 18,
    stroke: 2.25,
    color: "var(--toast-text)"
  }), children);
}
Object.assign(__ds_scope, { Toast });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/feedback/Toast.jsx", error: String((e && e.message) || e) }); }

// components/forms/Checkbox.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* Checkbox — the signature "checked = blue" moment.
   Unchecked: hairline square on paper. Checked: blue fill + white check.
   Completing never moves the row; it just greys in place (see TaskRow). */
function Checkbox({
  checked = false,
  disabled = false,
  size = 22,
  onChange,
  style,
  ...rest
}) {
  return /*#__PURE__*/React.createElement("button", _extends({
    type: "button",
    role: "checkbox",
    "aria-checked": checked,
    disabled: disabled,
    onClick: () => !disabled && onChange && onChange(!checked),
    className: "tc-pressable",
    style: {
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      width: size,
      height: size,
      flex: "none",
      padding: 0,
      borderRadius: "var(--radius-xs)",
      border: checked ? "1px solid var(--primary)" : "1.5px solid var(--ink-300)",
      background: checked ? "var(--primary)" : "transparent",
      color: "var(--primary-on)",
      cursor: disabled ? "not-allowed" : "pointer",
      opacity: disabled ? 0.5 : 1,
      transition: "background var(--dur-fast) var(--ease-standard), border-color var(--dur-fast) var(--ease-standard)",
      ...style
    }
  }, rest), checked && /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "check",
    size: size - 7,
    stroke: 2.5,
    color: "var(--primary-on)"
  }));
}
Object.assign(__ds_scope, { Checkbox });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/Checkbox.jsx", error: String((e && e.message) || e) }); }

// components/forms/TextField.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* TextField — paper input. Single line or multiline (description).
   Quiet hairline; focus deepens the border to ink, never a blue glow
   (blue is accent-only). Label sits above in meta type. */
function TextField({
  label,
  value,
  placeholder,
  multiline = false,
  rows = 3,
  onChange,
  style,
  ...rest
}) {
  const [focused, setFocused] = React.useState(false);
  const shared = {
    width: "100%",
    boxSizing: "border-box",
    padding: multiline ? "12px 14px" : "0 14px",
    height: multiline ? "auto" : 48,
    minHeight: multiline ? rows * 22 + 24 : undefined,
    background: "var(--surface)",
    border: `1px solid ${focused ? "var(--ink-400)" : "var(--hairline-strong)"}`,
    borderRadius: "var(--radius-sm)",
    font: `var(--weight-regular) 16px/1.4 var(--font-sans)`,
    color: "var(--ink-900)",
    outline: "none",
    resize: multiline ? "vertical" : "none",
    transition: "border-color var(--dur-fast) var(--ease-standard)"
  };
  return /*#__PURE__*/React.createElement("label", {
    style: {
      display: "block",
      ...style
    }
  }, label && /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      marginBottom: 8,
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, label), multiline ? /*#__PURE__*/React.createElement("textarea", _extends({
    rows: rows,
    value: value,
    placeholder: placeholder,
    onFocus: () => setFocused(true),
    onBlur: () => setFocused(false),
    onChange: e => onChange && onChange(e.target.value),
    style: shared
  }, rest)) : /*#__PURE__*/React.createElement("input", _extends({
    type: "text",
    value: value,
    placeholder: placeholder,
    onFocus: () => setFocused(true),
    onBlur: () => setFocused(false),
    onChange: e => onChange && onChange(e.target.value),
    style: shared
  }, rest)));
}
Object.assign(__ds_scope, { TextField });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/TextField.jsx", error: String((e && e.message) || e) }); }

// components/forms/TimeInput.jsx
try { (() => {
/* TimeInput — the deadline control from the create/edit form.
   Three states:
     • "No time limit" checkbox checked — no deadline fields shown
     • "Date & time" — an exact calendar date with a time-of-day
     • "Days & hours" — a relative offset from now */
const MODES = ["Date & time", "Days & hours"];
function TimeInput({
  mode = "Date & time",
  noLimit = false,
  value = "",
  time = "12:59 PM",
  days = "",
  hours = "0",
  onModeChange,
  onNoLimitChange,
  onValueChange,
  onTimeChange,
  onDaysChange,
  onHoursChange,
  label = "Deadline",
  style
}) {
  const isDate = mode === "Date & time";
  const fieldShell = {
    display: "flex",
    alignItems: "center",
    gap: 10,
    height: 48,
    padding: "0 14px",
    background: "var(--surface)",
    border: "1px solid var(--hairline-strong)",
    borderRadius: "var(--radius-sm)",
    minWidth: 0
  };
  const inputStyle = {
    flex: 1,
    minWidth: 0,
    width: "100%",
    border: "none",
    outline: "none",
    background: "transparent",
    font: "var(--weight-regular) 16px/1 var(--font-sans)",
    color: "var(--ink-900)"
  };
  const unitStyle = {
    flex: "none",
    font: "var(--weight-regular) 14px/1 var(--font-sans)",
    color: "var(--ink-400)"
  };
  return /*#__PURE__*/React.createElement("div", {
    style: {
      ...style
    }
  }, label && /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      marginBottom: 8,
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, label), /*#__PURE__*/React.createElement("label", {
    style: {
      display: "flex",
      alignItems: "center",
      gap: 10,
      padding: "8px 4px",
      marginBottom: noLimit ? 0 : 12,
      cursor: "pointer",
      userSelect: "none"
    }
  }, /*#__PURE__*/React.createElement("span", {
    onClick: () => onNoLimitChange && onNoLimitChange(!noLimit),
    style: {
      display: "grid",
      placeItems: "center",
      width: 20,
      height: 20,
      flex: "none",
      borderRadius: 5,
      border: noLimit ? "1px solid var(--primary)" : "1.5px solid var(--ink-300)",
      background: noLimit ? "var(--primary)" : "transparent",
      cursor: "pointer",
      transition: "background 120ms, border 120ms"
    }
  }, noLimit && /*#__PURE__*/React.createElement("svg", {
    width: "13",
    height: "13",
    viewBox: "0 0 24 24",
    fill: "none",
    stroke: "var(--primary-on)",
    strokeWidth: "3",
    strokeLinecap: "round",
    strokeLinejoin: "round"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M20 6 9 17l-5-5"
  }))), /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-regular) 15px/1 var(--font-sans)",
      color: "var(--ink-700)"
    }
  }, "No time limit")), !noLimit && /*#__PURE__*/React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    role: "tablist",
    style: {
      display: "grid",
      gridTemplateColumns: "1fr 1fr",
      gap: 3,
      padding: 3,
      background: "var(--surface-sunken)",
      borderRadius: "var(--radius-sm)",
      marginBottom: 12
    }
  }, MODES.map(m => {
    const on = m === mode;
    return /*#__PURE__*/React.createElement("button", {
      key: m,
      type: "button",
      role: "tab",
      "aria-selected": on,
      onClick: () => onModeChange && onModeChange(m),
      className: "tc-pressable",
      style: {
        height: 34,
        border: "none",
        borderRadius: 6,
        background: on ? "var(--surface-raised)" : "transparent",
        boxShadow: on ? "0 1px 2px rgba(44,44,42,0.10)" : "none",
        color: on ? "var(--ink-900)" : "var(--ink-500)",
        font: `var(--weight-medium) 14px/1 var(--font-sans)`,
        cursor: "pointer"
      }
    }, m);
  })), isDate ? /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "1fr 1fr",
      gap: 8
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: fieldShell
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "calendar",
    size: 20,
    color: "var(--ink-500)"
  }), /*#__PURE__*/React.createElement("input", {
    value: value,
    onChange: e => onValueChange && onValueChange(e.target.value),
    placeholder: "Jun 12, 2026",
    style: inputStyle
  })), /*#__PURE__*/React.createElement("div", {
    style: fieldShell
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "clock",
    size: 20,
    color: "var(--ink-500)"
  }), /*#__PURE__*/React.createElement("input", {
    value: time,
    onChange: e => onTimeChange && onTimeChange(e.target.value),
    placeholder: "12:59 PM",
    style: inputStyle
  }))) : /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "1fr 1fr",
      gap: 8
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: fieldShell
  }, /*#__PURE__*/React.createElement("input", {
    value: days,
    onChange: e => onDaysChange && onDaysChange(e.target.value),
    placeholder: "2",
    inputMode: "numeric",
    style: {
      ...inputStyle,
      textAlign: "right"
    }
  }), /*#__PURE__*/React.createElement("span", {
    style: unitStyle
  }, "days")), /*#__PURE__*/React.createElement("div", {
    style: fieldShell
  }, /*#__PURE__*/React.createElement("input", {
    value: hours,
    onChange: e => onHoursChange && onHoursChange(e.target.value),
    placeholder: "0",
    inputMode: "numeric",
    style: {
      ...inputStyle,
      textAlign: "right"
    }
  }), /*#__PURE__*/React.createElement("span", {
    style: unitStyle
  }, "hours")))));
}
Object.assign(__ds_scope, { TimeInput });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/forms/TimeInput.jsx", error: String((e && e.message) || e) }); }

// components/planner/BottomBar.jsx
try { (() => {
/* BottomBar — a floating pill with four modes:
   home (house) · tasks (list) · search · add.
   Active mode is blue. The first two are navigation tabs (Home vs Tasks page);
   search and add open overlays from either page. */
const MODES = [{
  key: "home",
  icon: "home",
  label: "Home"
}, {
  key: "tasks",
  icon: "list",
  label: "Tasks"
}, {
  key: "search",
  icon: "search",
  label: "Search"
}, {
  key: "add",
  icon: "plus",
  label: "Add"
}];
function BottomBar({
  mode = "home",
  onModeChange,
  style
}) {
  return /*#__PURE__*/React.createElement("nav", {
    role: "tablist",
    style: {
      display: "inline-flex",
      gap: 4,
      padding: 6,
      background: "var(--surface-raised)",
      borderRadius: "var(--radius-pill)",
      boxShadow: "var(--shadow-pill)",
      border: "1px solid var(--hairline)",
      ...style
    }
  }, MODES.map(m => {
    const active = m.key === mode;
    return /*#__PURE__*/React.createElement("button", {
      key: m.key,
      role: "tab",
      type: "button",
      "aria-selected": active,
      "aria-label": m.label,
      onClick: () => onModeChange && onModeChange(m.key),
      className: "tc-pressable",
      style: {
        display: "grid",
        placeItems: "center",
        width: 48,
        height: 48,
        border: "none",
        borderRadius: "var(--radius-pill)",
        background: active ? "var(--primary)" : "transparent",
        cursor: "pointer",
        transition: "background var(--dur-fast) var(--ease-standard)"
      }
    }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
      name: m.icon,
      size: 22,
      color: active ? "var(--primary-on)" : "var(--ink-500)"
    }));
  }));
}
Object.assign(__ds_scope, { BottomBar });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/BottomBar.jsx", error: String((e && e.message) || e) }); }

// components/planner/DateStrip.jsx
try { (() => {
/* DateStrip — a horizontally-scrollable week. Mon–Sun labels over numbers.
   Today is blue (the accent). The selected day (if not today) fills ink.
   A fixed calendar button sits on the LEFT: tap it to open a month picker
   and jump to any date, past or future. Placeholder days (outside the month)
   render as empty cells so the week stays aligned. */
const WD = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
function DateStrip({
  days,
  selectedKey,
  todayKey,
  onSelect,
  onCalendar,
  style
}) {
  // default: a sample week
  const list = days || WD.map((w, i) => ({
    key: String(9 + i),
    weekday: w,
    date: 9 + i
  }));
  return /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "stretch",
      gap: 4,
      ...style
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": "Open calendar",
    onClick: onCalendar,
    className: "tc-pressable",
    style: {
      display: "flex",
      flexDirection: "column",
      alignItems: "center",
      justifyContent: "center",
      gap: 6,
      flex: "none",
      width: 44,
      padding: "4px 0",
      border: "none",
      background: "transparent",
      borderRadius: 14,
      cursor: "pointer",
      color: "var(--ink-600)"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-regular) 12px/1 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, "All"), /*#__PURE__*/React.createElement("span", {
    style: {
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      width: 36,
      height: 36,
      borderRadius: "var(--radius-pill)",
      background: "var(--surface)",
      border: "1px solid var(--hairline-strong)"
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "calendar",
    size: 19,
    color: "var(--ink-600)"
  }))), /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      flex: "none",
      width: 1,
      alignSelf: "center",
      height: 40,
      background: "var(--hairline-strong)",
      margin: "0 4px 0 2px"
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      gap: 6,
      overflowX: "auto",
      padding: "4px 2px 8px",
      scrollbarWidth: "none",
      WebkitOverflowScrolling: "touch"
    }
  }, list.map(d => {
    if (d.placeholder) {
      return /*#__PURE__*/React.createElement("span", {
        key: d.key,
        "aria-hidden": "true",
        style: {
          flex: "none",
          width: 44
        }
      });
    }
    const isToday = d.key === todayKey;
    const isSel = d.key === selectedKey;
    let circleBg = "transparent";
    let numColor = "var(--ink-900)";
    if (isToday) {
      circleBg = "var(--primary)";
      numColor = "var(--primary-on)";
    } else if (isSel) {
      circleBg = "var(--ink-200)";
      numColor = "var(--ink-900)";
    }
    return /*#__PURE__*/React.createElement("button", {
      key: d.key,
      type: "button",
      onClick: () => onSelect && onSelect(d.key),
      className: "tc-pressable",
      style: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: 6,
        flex: "none",
        width: 44,
        padding: "4px 0",
        border: "none",
        background: "transparent",
        borderRadius: 14,
        cursor: "pointer"
      }
    }, /*#__PURE__*/React.createElement("span", {
      style: {
        font: "var(--weight-regular) 12px/1 var(--font-sans)",
        color: isToday ? "var(--primary)" : "var(--ink-500)"
      }
    }, d.weekday), /*#__PURE__*/React.createElement("span", {
      style: {
        display: "inline-flex",
        alignItems: "center",
        justifyContent: "center",
        width: 36,
        height: 36,
        borderRadius: 8,
        background: circleBg,
        color: numColor,
        font: `var(--weight-medium) 16px/1 var(--font-sans)`
      }
    }, d.date));
  })));
}
Object.assign(__ds_scope, { DateStrip });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/DateStrip.jsx", error: String((e && e.message) || e) }); }

// components/planner/ParentSection.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
function useLongPress(onLongPress, ms = 450) {
  const timer = React.useRef(null);
  const start = e => {
    const pt = {
      x: e.clientX,
      y: e.clientY
    };
    timer.current = setTimeout(() => onLongPress && onLongPress(pt), ms);
  };
  const cancel = () => {
    if (timer.current) {
      clearTimeout(timer.current);
      timer.current = null;
    }
  };
  return {
    onPointerDown: start,
    onPointerUp: cancel,
    onPointerLeave: cancel,
    onPointerCancel: cancel,
    onPointerMove: cancel,
    onContextMenu: e => {
      e.preventDefault();
      onLongPress && onLongPress({
        x: e.clientX,
        y: e.clientY
      });
    }
  };
}

/* ParentSection — collapsible container.
   Chevron in the header toggles children. Long-press for context menu. */
function ParentSection({
  title,
  count,
  onMenu,
  pinned = false,
  expanded = true,
  onToggle,
  favourite = false,
  emoji,
  onEmojiClick,
  children,
  style
}) {
  const press = useLongPress(pt => onMenu && onMenu(pt));
  const handleClick = () => onToggle && onToggle(!expanded);
  return /*#__PURE__*/React.createElement("section", {
    style: {
      background: "var(--parent-fill)",
      border: "1.5px solid var(--hairline)",
      borderRadius: "var(--radius-xl)",
      padding: "6px 8px 10px",
      ...style
    }
  }, /*#__PURE__*/React.createElement("header", _extends({}, press, {
    onClick: handleClick,
    className: "tc-pressable",
    style: {
      display: "flex",
      alignItems: "center",
      gap: 10,
      minHeight: 44,
      padding: "6px 12px",
      borderRadius: 12,
      cursor: "pointer",
      userSelect: "none",
      touchAction: "pan-y"
    }
  }), emoji && /*#__PURE__*/React.createElement("span", {
    onClick: e => {
      e.stopPropagation();
      onEmojiClick && onEmojiClick({
        x: e.clientX,
        y: e.clientY
      });
    },
    style: {
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      flex: "none",
      fontSize: 18,
      lineHeight: 1,
      cursor: "pointer"
    }
  }, emoji), pinned && !emoji && /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      width: 6,
      height: 6,
      borderRadius: 999,
      background: "var(--ink-400)",
      flex: "none"
    }
  }), /*#__PURE__*/React.createElement("span", {
    className: "tc-truncate-1",
    style: {
      flex: 1,
      minWidth: 0,
      font: "var(--weight-medium) 18px/1 var(--font-sans)",
      letterSpacing: "-0.01em",
      color: "var(--ink-600)"
    }
  }, title), count != null && /*#__PURE__*/React.createElement("span", {
    style: {
      flex: "none",
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, count), favourite && /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: "pin",
    size: 14,
    color: "var(--ink-400)",
    style: {
      flex: "none"
    }
  }), /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: expanded ? "chevron-down" : "chevron-right",
    size: 20,
    color: "var(--ink-400)",
    style: {
      flex: "none"
    }
  })), expanded && React.Children.count(children) > 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      flexDirection: "column",
      gap: 8,
      marginTop: 8
    }
  }, children));
}
Object.assign(__ds_scope, { ParentSection });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/ParentSection.jsx", error: String((e && e.message) || e) }); }

// components/planner/TimePill.jsx
try { (() => {
/* TimePill — the app's one piece of drama.
   A task's remaining time shifts colour as its deadline nears:
   calm (>50%) → nothing shown
   on-track    → blue text
   close       → amber text
   due         → red text
   overdue     → red CAPSULE (bg + border), counting up */

const STATUS_COLORS = {
  "on-track": "var(--on-track)",
  "close": "var(--amber)",
  "due": "var(--red)"
};
function TimePill({
  status = "calm",
  label,
  style
}) {
  if (status === "calm" || !label) return null;
  if (status === "overdue") {
    return /*#__PURE__*/React.createElement("span", {
      style: {
        display: "inline-flex",
        alignItems: "center",
        flex: "none",
        whiteSpace: "nowrap",
        padding: "4px 9px",
        borderRadius: "var(--radius-sm)",
        background: "var(--overdue-bg)",
        border: "1px solid var(--overdue-border)",
        color: "var(--overdue-text)",
        font: "var(--weight-medium) 12px/1 var(--font-sans)",
        letterSpacing: "-0.01em",
        ...style
      }
    }, label);
  }

  /* on-track / close / due → coloured text, no background */
  const color = STATUS_COLORS[status] || "var(--ink-500)";
  return /*#__PURE__*/React.createElement("span", {
    style: {
      display: "inline-flex",
      alignItems: "center",
      flex: "none",
      whiteSpace: "nowrap",
      color,
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      letterSpacing: "-0.01em",
      ...style
    }
  }, label);
}
Object.assign(__ds_scope, { TimePill });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/TimePill.jsx", error: String((e && e.message) || e) }); }

// components/planner/SectionCard.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* Press-and-hold detection. */
function useLongPress(onLongPress, onTap, ms = 450) {
  const timer = React.useRef(null);
  const held = React.useRef(false);
  const start = e => {
    held.current = false;
    const pt = {
      x: e.clientX,
      y: e.clientY
    };
    timer.current = setTimeout(() => {
      held.current = true;
      onLongPress && onLongPress(pt);
    }, ms);
  };
  const cancel = () => {
    if (timer.current) {
      clearTimeout(timer.current);
      timer.current = null;
    }
  };
  return {
    onPointerDown: start,
    onPointerUp: () => {
      cancel();
      if (!held.current && onTap) onTap();
    },
    onPointerLeave: cancel,
    onPointerCancel: cancel,
    onPointerMove: cancel,
    onContextMenu: e => {
      e.preventDefault();
      onLongPress && onLongPress({
        x: e.clientX,
        y: e.clientY
      });
    }
  };
}

/* SectionCard — a section sits on a subtle surface.
   Now supports an `icon` prop — a Lucide icon name displayed
   in a squircle badge before the title. */
function SectionCard({
  title,
  icon,
  done = 0,
  total = 0,
  status = "calm",
  time,
  expanded = true,
  pinned = false,
  onToggle,
  onMenu,
  onIconClick,
  children,
  style
}) {
  const press = useLongPress(pt => !pinned && onMenu && onMenu(pt), () => !pinned && onToggle && onToggle(!expanded));
  const pct = total > 0 ? Math.round(done / total * 100) : 0;
  const complete = total > 0 && done === total;
  return /*#__PURE__*/React.createElement("section", {
    style: {
      background: "var(--tint-surface, var(--surface))",
      border: "1px solid var(--tint-surface-border, var(--hairline))",
      borderRadius: "var(--radius-lg)",
      padding: "4px 16px",
      ...style
    }
  }, /*#__PURE__*/React.createElement("header", _extends({}, pinned ? {
    onClick: () => onToggle && onToggle(!expanded)
  } : press, {
    className: pinned ? "" : "tc-pressable",
    style: {
      display: "flex",
      alignItems: "center",
      gap: 12,
      minHeight: 56,
      margin: "0 -8px",
      padding: "8px",
      borderRadius: 10,
      cursor: "pointer",
      touchAction: "pan-y",
      userSelect: "none"
    }
  }), icon && /*#__PURE__*/React.createElement("span", {
    onClick: e => {
      e.stopPropagation();
      onIconClick && onIconClick({
        x: e.clientX,
        y: e.clientY
      });
    },
    style: {
      display: "inline-flex",
      alignItems: "center",
      justifyContent: "center",
      width: 32,
      height: 32,
      flex: "none",
      borderRadius: 8,
      background: "var(--tint-surface-border, var(--surface-sunken))",
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: icon,
    size: 17,
    color: "var(--tint-ink, var(--ink-600))"
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("span", {
    className: "tc-truncate-1",
    style: {
      display: "block",
      font: "var(--weight-medium) 18px/1.2 var(--font-sans)",
      letterSpacing: "-0.01em",
      color: "var(--tint-ink, var(--ink-900))"
    }
  }, title), total > 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      gap: 10,
      marginTop: 8
    }
  }, /*#__PURE__*/React.createElement("span", {
    "aria-hidden": "true",
    style: {
      position: "relative",
      flex: 1,
      maxWidth: 132,
      height: 4,
      borderRadius: 999,
      background: "var(--surface-sunken)",
      overflow: "hidden"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      position: "absolute",
      inset: 0,
      right: "auto",
      width: `${pct}%`,
      borderRadius: 999,
      background: complete ? "var(--primary)" : "var(--tint-progress, var(--ink-400))",
      transition: "width var(--dur-base) var(--ease-standard)"
    }
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: "none",
      whiteSpace: "nowrap",
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      color: complete ? "var(--primary)" : "var(--tint-meta, var(--ink-500))"
    }
  }, done, " / ", total))), /*#__PURE__*/React.createElement(__ds_scope.TimePill, {
    status: status,
    label: time
  }), /*#__PURE__*/React.createElement(__ds_scope.Icon, {
    name: expanded ? "chevron-down" : "chevron-right",
    size: 20,
    color: "var(--ink-500)",
    style: {
      flex: "none",
      opacity: pinned ? 0.35 : 1
    }
  })), expanded && React.Children.count(children) > 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      borderTop: "1px solid var(--tint-hairline, var(--hairline))",
      margin: "0 -8px",
      padding: "4px 8px 8px"
    }
  }, children));
}
Object.assign(__ds_scope, { SectionCard });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/SectionCard.jsx", error: String((e && e.message) || e) }); }

// components/planner/TaskRow.jsx
try { (() => {
function _extends() { return _extends = Object.assign ? Object.assign.bind() : function (n) { for (var e = 1; e < arguments.length; e++) { var t = arguments[e]; for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]); } return n; }, _extends.apply(null, arguments); }
/* Press-and-hold detection — long-press fires onLongPress with the pointer's
   {x, y} so an origin-aware ContextMenu can open right at the touch. Movement
   or scroll cancels, so a held read never opens it. */
function useLongPress(onLongPress, ms = 450) {
  const timer = React.useRef(null);
  const start = e => {
    const pt = {
      x: e.clientX,
      y: e.clientY
    };
    timer.current = setTimeout(() => onLongPress && onLongPress(pt), ms);
  };
  const cancel = () => {
    if (timer.current) {
      clearTimeout(timer.current);
      timer.current = null;
    }
  };
  return {
    onPointerDown: start,
    onPointerUp: cancel,
    onPointerLeave: cancel,
    onPointerCancel: cancel,
    onPointerMove: cancel
  };
}

/* TaskRow — no surface of its own, divider-separated inside a SectionCard.
   Layout: checkbox · (title 1 line + description 2 lines) · time pill.
   Completed task greys out IN PLACE — never moves, never restyled to a
   strikethrough circus. A done task carries no urgency, so its pill drops.
   No visible menu — press and hold (or right-click) reveals it. */
function TaskRow({
  title,
  description,
  checked = false,
  status = "calm",
  time,
  divider = false,
  onToggle,
  onMenu,
  style
}) {
  const titleColor = checked ? "var(--ink-300)" : "var(--ink-900)";
  const descColor = checked ? "var(--ink-300)" : "var(--ink-600)";
  const press = useLongPress(pt => onMenu && onMenu(pt));
  return /*#__PURE__*/React.createElement("div", _extends({}, press, {
    onContextMenu: e => {
      e.preventDefault();
      onMenu && onMenu({
        x: e.clientX,
        y: e.clientY
      });
    },
    style: {
      display: "flex",
      alignItems: "flex-start",
      gap: 12,
      padding: "14px 0",
      borderTop: divider ? "1px solid var(--hairline)" : "none",
      touchAction: "pan-y",
      ...style
    }
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      paddingTop: 1
    }
  }, /*#__PURE__*/React.createElement(__ds_scope.Checkbox, {
    checked: checked,
    onChange: onToggle
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("div", {
    className: "tc-truncate-1",
    style: {
      font: "var(--weight-regular) 16px/1.35 var(--font-sans)",
      color: titleColor
    }
  }, title), description && /*#__PURE__*/React.createElement("div", {
    className: "tc-truncate-2",
    style: {
      marginTop: 3,
      font: "var(--weight-regular) 14px/1.4 var(--font-sans)",
      color: descColor
    }
  }, description)), !checked && /*#__PURE__*/React.createElement(__ds_scope.TimePill, {
    status: status,
    label: time,
    style: {
      marginTop: 2
    }
  }));
}
Object.assign(__ds_scope, { TaskRow });
})(); } catch (e) { __ds_ns.__errors.push({ path: "components/planner/TaskRow.jsx", error: String((e && e.message) || e) }); }

// ui_kits/taskcluster/tcApp.jsx
try { (() => {
/* TaskCluster UI kit — the single screen + state machine.
   Self-contained: pulls primitives from the design-system bundle.

   ═══════════════════════════════════════════════════════════
   DEVELOPMENT NOTES (for implementation, not for the prototype)
   ═══════════════════════════════════════════════════════════

   1. COMPLETED SECTIONS SINK ON REOPEN
      When a section's last task is checked, it stays in place for the
      current session (the user may undo). On the NEXT app launch, all
      fully-completed sections move to the bottom of their parent's stack.
      Partially-completed sections (even 99%) keep their original position.

   2. COMPLETED PARENTS HIDE NEXT DAY
      A parent whose every section is fully completed should NOT appear
      the following day. When the user opens the app on a new calendar day,
      completed parents are hidden from both Home and Tasks. They remain
      accessible in Trash / history but are invisible on the main screens.

   3. FAVOURITES ARE SHORTCUTS, NOT COPIES
      Pinning a parent to Home creates a live reference. Checking a task on
      Home also marks it complete on the Tasks page (and vice-versa) — there
      is only one underlying data model. The favourite badge (heart icon) is
      purely a display flag.

   4. STACK ORDER
      Within a parent, sections are ordered newest-first. Completed sections
      sink to the bottom only on reopen (see note 1). Everything else
      maintains its creation order regardless of completion percentage.

   5. SPRING ANIMATIONS (DEVELOPER NOTE)
      True spring physics (damping ratio, stiffness, natural frequency)
      cannot be expressed in CSS alone. The prototype uses cubic-bezier
      approximations with slight overshoot. For production Android, use
      SpringForce + SpringAnimation from AndroidX DynamicAnimation.
      Recommended defaults: damping ratio 0.7, stiffness 300. Apply to:
      • Section expand / collapse height transitions
      • Popup & context-menu entry scale
      • Bottom bar active-tab indicator slide
      • Checkbox scale bounce on toggle
      • Parent "View all" expand
      For iOS, use UISpringTimingParameters with equivalent values.
   ═══════════════════════════════════════════════════════════ */
const {
  Button,
  TextField,
  TimeInput,
  Badge,
  Icon,
  IconButton,
  TimePill,
  DateStrip,
  SectionCard,
  TaskRow,
  BottomBar,
  ParentSection,
  Banner,
  Toast,
  Snackbar,
  ContextMenu
} = window.TaskClusterDesignSystem_1763fe;

/* ===================================================================
   Wrapper primitives: Page (full-screen pages), Popup (centered dialogs)
   =================================================================== */

function Page({
  open,
  title,
  onBack,
  children
}) {
  if (!open) return null;
  return /*#__PURE__*/React.createElement("div", {
    style: {
      position: "absolute",
      inset: 0,
      zIndex: 50,
      background: "var(--canvas)",
      display: "flex",
      flexDirection: "column",
      animation: "tcSlideIn var(--dur-base) var(--ease-standard)"
    }
  }, /*#__PURE__*/React.createElement("header", {
    style: {
      display: "flex",
      alignItems: "center",
      gap: 6,
      padding: "14px 10px 12px",
      borderBottom: "1px solid var(--hairline)",
      flex: "none"
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    onClick: onBack,
    className: "tc-pressable",
    style: {
      display: "grid",
      placeItems: "center",
      width: 36,
      height: 36,
      border: "none",
      background: "transparent",
      borderRadius: 999,
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "chevron-left",
    size: 22,
    color: "var(--ink-700)"
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1,
      font: "var(--weight-medium) 18px/1 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, title)), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      overflowY: "auto",
      padding: "16px 18px 40px"
    }
  }, children));
}
function Popup({
  open,
  onClose,
  title,
  children,
  maxWidth = 380
}) {
  if (!open) return null;
  return /*#__PURE__*/React.createElement("div", {
    onClick: onClose,
    style: {
      position: "absolute",
      inset: 0,
      zIndex: 50,
      background: "var(--scrim)",
      display: "flex",
      alignItems: "center",
      justifyContent: "center",
      padding: 20,
      animation: "tcFade var(--dur-base) var(--ease-standard)"
    }
  }, /*#__PURE__*/React.createElement("div", {
    onClick: e => e.stopPropagation(),
    style: {
      width: "100%",
      maxWidth,
      background: "var(--surface-raised)",
      borderRadius: 20,
      padding: "20px 22px 24px",
      boxShadow: "var(--shadow-overlay)",
      border: "1.5px solid var(--hairline-strong)",
      animation: "tcSpringIn var(--dur-base) cubic-bezier(0.175, 0.885, 0.32, 1.1)"
    }
  }, title && /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      marginBottom: 16
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-medium) 18px/1.2 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, title), /*#__PURE__*/React.createElement(IconButton, {
    label: "Close",
    icon: /*#__PURE__*/React.createElement(Icon, {
      name: "x",
      size: 20
    }),
    size: 32,
    onClick: onClose
  })), children));
}

/* ===================================================================
   Calendar popup — full month/year navigation, any date
   =================================================================== */

const MONTH_NAMES = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
const WD_LABELS = ["M", "T", "W", "T", "F", "S", "S"];
function CalendarPopup({
  open,
  onClose,
  todayYear,
  todayMonth,
  todayDate,
  selectedYear,
  selectedMonth,
  selectedDate,
  onPick
}) {
  const [vY, setVY] = React.useState(selectedYear);
  const [vM, setVM] = React.useState(selectedMonth);
  const [yearPicker, setYearPicker] = React.useState(false);
  React.useEffect(() => {
    if (open) {
      setVY(selectedYear);
      setVM(selectedMonth);
      setYearPicker(false);
    }
  }, [open]);
  const dim = new Date(vY, vM + 1, 0).getDate();
  const firstWd = (new Date(vY, vM, 1).getDay() + 6) % 7; // 0=Mon

  const prev = () => {
    let m = vM - 1,
      y = vY;
    if (m < 0) {
      m = 11;
      y--;
    }
    setVM(m);
    setVY(y);
  };
  const next = () => {
    let m = vM + 1,
      y = vY;
    if (m > 11) {
      m = 0;
      y++;
    }
    setVM(m);
    setVY(y);
  };
  const cells = [];
  for (let i = 0; i < firstWd; i++) cells.push(null);
  for (let d = 1; d <= dim; d++) cells.push(d);

  // year picker: grid of years
  const baseYear = Math.floor(vY / 12) * 12;
  const years = [];
  for (let y = baseYear - 4; y <= baseYear + 7; y++) years.push(y);
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    maxWidth: 340
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      marginBottom: 14
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: yearPicker ? () => setVY(vY - 12) : prev,
    style: {
      display: "grid",
      placeItems: "center",
      width: 32,
      height: 32,
      border: "none",
      background: "transparent",
      borderRadius: 999,
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "chevron-left",
    size: 20,
    color: "var(--ink-600)"
  })), /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => setYearPicker(!yearPicker),
    style: {
      border: "none",
      background: "transparent",
      cursor: "pointer",
      padding: "4px 12px",
      borderRadius: 8,
      font: "var(--weight-medium) 16px/1 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, yearPicker ? `${years[0]} – ${years[years.length - 1]}` : `${MONTH_NAMES[vM]} ${vY}`), /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: yearPicker ? () => setVY(vY + 12) : next,
    style: {
      display: "grid",
      placeItems: "center",
      width: 32,
      height: 32,
      border: "none",
      background: "transparent",
      borderRadius: 999,
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "chevron-right",
    size: 20,
    color: "var(--ink-600)"
  }))), yearPicker ?
  /*#__PURE__*/
  /* year grid */
  React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(4, 1fr)",
      gap: 6
    }
  }, years.map(y => {
    const isCur = y === todayYear;
    const isSel = y === vY;
    return /*#__PURE__*/React.createElement("button", {
      key: y,
      type: "button",
      className: "tc-pressable",
      onClick: () => {
        setVY(y);
        setYearPicker(false);
      },
      style: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        height: 40,
        border: "1px solid transparent",
        borderRadius: "var(--radius-sm)",
        background: isSel ? "var(--ink-900)" : "transparent",
        color: isSel ? "var(--surface)" : isCur ? "var(--blue)" : "var(--ink-900)",
        font: "var(--weight-medium) 14px/1 var(--font-sans)",
        cursor: "pointer"
      }
    }, y);
  })) :
  /*#__PURE__*/
  /* day grid */
  React.createElement(React.Fragment, null, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(7, 1fr)",
      gap: 2,
      marginBottom: 4
    }
  }, WD_LABELS.map((w, i) => /*#__PURE__*/React.createElement("span", {
    key: i,
    style: {
      textAlign: "center",
      font: "var(--weight-regular) 12px/1 var(--font-sans)",
      color: "var(--ink-400)",
      padding: "2px 0 6px"
    }
  }, w))), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(7, 1fr)",
      gap: 2
    }
  }, cells.map((d, i) => {
    if (d == null) return /*#__PURE__*/React.createElement("span", {
      key: `b${i}`
    });
    const isToday = d === todayDate && vM === todayMonth && vY === todayYear;
    const isSel = d === selectedDate && vM === selectedMonth && vY === selectedYear;
    let bg = "transparent",
      color = "var(--ink-900)";
    if (isToday) {
      bg = "var(--blue)";
      color = "var(--blue-on)";
    } else if (isSel) {
      bg = "var(--ink-900)";
      color = "var(--surface)";
    }
    return /*#__PURE__*/React.createElement("button", {
      key: d,
      type: "button",
      className: "tc-pressable",
      onClick: () => onPick({
        year: vY,
        month: vM,
        date: d
      }),
      style: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        aspectRatio: "1 / 1",
        border: "1px solid transparent",
        borderRadius: "var(--radius-pill)",
        background: bg,
        color,
        font: "var(--weight-medium) 14px/1 var(--font-sans)",
        cursor: "pointer"
      }
    }, d);
  }))));
}

/* ===================================================================
   Parent color palettes — each generates 3 hues from a base color.
   paper = parent fill, border = parent border, surface = section bg,
   surfaceBorder = section border, ink = text, meta = secondary text.
   =================================================================== */

const PALETTES = {
  default: {
    paper: "#E8E5DB",
    border: "#C8C4B8",
    surface: "#F5F3ED",
    surfaceBorder: "#DDD9CE",
    ink: "#2C2C2A",
    meta: "#6E6B63",
    label: "Default",
    swatch: "#C8C4B8"
  },
  lemon: {
    paper: "#E4D24C",
    border: "#C0AE2C",
    surface: "#FAF6D0",
    surfaceBorder: "#DCD480",
    ink: "#3A3210",
    meta: "#6A5A18",
    label: "Lemon",
    swatch: "#E4D24C"
  },
  orange: {
    paper: "#E09848",
    border: "#C07828",
    surface: "#FAECD0",
    surfaceBorder: "#DCC480",
    ink: "#3A2410",
    meta: "#6A4418",
    label: "Orange",
    swatch: "#E09848"
  },
  coral: {
    paper: "#D87058",
    border: "#B85038",
    surface: "#F8E4DC",
    surfaceBorder: "#D8AC9C",
    ink: "#381410",
    meta: "#682C20",
    label: "Coral",
    swatch: "#D87058"
  },
  red: {
    paper: "#CC4848",
    border: "#A82828",
    surface: "#F8DCDC",
    surfaceBorder: "#D89898",
    ink: "#380C0C",
    meta: "#681C1C",
    label: "Red",
    swatch: "#CC4848"
  },
  rose: {
    paper: "#CC5880",
    border: "#A83860",
    surface: "#F8DCE8",
    surfaceBorder: "#D898B4",
    ink: "#380C24",
    meta: "#681C3C",
    label: "Rose",
    swatch: "#CC5880"
  },
  pink: {
    paper: "#C060B0",
    border: "#A04090",
    surface: "#F4DCF0",
    surfaceBorder: "#D098C4",
    ink: "#340C38",
    meta: "#5C1858",
    label: "Pink",
    swatch: "#C060B0"
  },
  purple: {
    paper: "#9060C0",
    border: "#7040A0",
    surface: "#ECDCF4",
    surfaceBorder: "#C098D4",
    ink: "#280C38",
    meta: "#481858",
    label: "Purple",
    swatch: "#9060C0"
  },
  indigo: {
    paper: "#6060CC",
    border: "#4040AC",
    surface: "#DCDCF8",
    surfaceBorder: "#9898D8",
    ink: "#0C0C38",
    meta: "#1C1C68",
    label: "Indigo",
    swatch: "#6060CC"
  },
  blue: {
    paper: "#4888C8",
    border: "#2868A8",
    surface: "#DCE8F4",
    surfaceBorder: "#98BCD4",
    ink: "#0C2438",
    meta: "#1C4058",
    label: "Blue",
    swatch: "#4888C8"
  },
  teal: {
    paper: "#40A8A8",
    border: "#208888",
    surface: "#DCF0F0",
    surfaceBorder: "#98CCD0",
    ink: "#0C3434",
    meta: "#1C5050",
    label: "Teal",
    swatch: "#40A8A8"
  },
  green: {
    paper: "#48A848",
    border: "#288828",
    surface: "#DCF0DC",
    surfaceBorder: "#98CC98",
    ink: "#0C240C",
    meta: "#1C401C",
    label: "Green",
    swatch: "#48A848"
  },
  olive: {
    paper: "#80A040",
    border: "#608020",
    surface: "#E8F0DC",
    surfaceBorder: "#B8CC98",
    ink: "#243410",
    meta: "#3C501C",
    label: "Olive",
    swatch: "#80A040"
  },
  inverse: {
    paper: "#2C2C2A",
    border: "#444440",
    surface: "#3A3A38",
    surfaceBorder: "#505050",
    ink: "#F1EFE8",
    meta: "#A8A498",
    label: "Inverse",
    swatch: "#2C2C2A"
  }
};
const PALETTE_KEYS = ["default", "lemon", "orange", "coral", "red", "rose", "pink", "purple", "indigo", "blue", "teal", "green", "olive", "inverse"];

/* Color picker popup — shown from parent long-press menu */
function ColorPicker({
  open,
  onClose,
  current,
  onPick
}) {
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Parent color",
    maxWidth: 380
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(7, 1fr)",
      gap: 6,
      padding: "4px 0 8px"
    }
  }, PALETTE_KEYS.map(k => {
    const p = PALETTES[k];
    const sel = k === current;
    return /*#__PURE__*/React.createElement("button", {
      key: k,
      type: "button",
      className: "tc-pressable",
      onClick: () => {
        onPick(k);
        onClose();
      },
      style: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: 5,
        border: "none",
        background: "transparent",
        cursor: "pointer",
        padding: "4px 0"
      }
    }, /*#__PURE__*/React.createElement("span", {
      style: {
        width: 32,
        height: 32,
        borderRadius: 8,
        background: p.swatch,
        border: sel ? "2.5px solid var(--primary)" : k === "inverse" ? "1.5px solid var(--ink-300)" : "1.5px solid var(--hairline-strong)",
        boxShadow: sel ? "0 0 0 2px var(--primary-tint)" : "none"
      }
    }), /*#__PURE__*/React.createElement("span", {
      style: {
        font: "var(--weight-regular) 10px/1 var(--font-sans)",
        color: sel ? "var(--primary)" : "var(--ink-500)"
      }
    }, p.label));
  })));
}

/* ===================================================================
   Emoji picker — origin-aware grid (Notion-style), for parents only.
   Icon picker — origin-aware grid of Lucide icons, for sections only.
   =================================================================== */

const EMOJI_CATS = [{
  label: "😊",
  emoji: ["😀", "😃", "😄", "😁", "😆", "😅", "🤣", "😂", "🙂", "🙃", "😉", "😊", "😇", "🥰", "😍", "🤩", "😘", "😗", "😚", "😙", "🥲", "😋", "😛", "😜", "🤪", "😝", "🤑", "🤗", "🤭", "🫢", "🫣", "🤫", "🤔", "🫡", "🤐", "🤨", "😐", "😑", "😶", "🫥", "😏", "😒", "🙄", "😬", "🤥", "😌", "😔", "😪", "🤤", "😴", "😷", "🤒", "🤕", "🤢", "🤮", "🥵", "🥶", "🥴", "😵", "🤯", "🤠", "🥳", "🥸", "😎", "🤓", "🧐", "😕", "🫤", "😟", "🙁", "😮", "😯", "😲", "😳", "🥺", "🥹", "😦", "😧", "😨", "😰", "😥", "😢", "😭", "😱", "😖", "😣", "😞", "😓", "😩", "😫", "🥱", "😤", "😡", "😠", "🤬", "😈", "👿", "💀", "☠️", "💩", "🤡", "👹", "👺", "👻", "👽", "👾", "🤖", "🎃", "😺", "😸", "😹", "😻", "😼", "😽", "🙀", "😿", "😾"]
}, {
  label: "👋",
  emoji: ["👋", "🤚", "🖐️", "✋", "🖖", "🫱", "🫲", "🫳", "🫴", "👌", "🤌", "🤏", "✌️", "🤞", "🫰", "🤟", "🤘", "🤙", "👈", "👉", "👆", "🖕", "👇", "☝️", "🫵", "👍", "👎", "✊", "👊", "🤛", "🤜", "👏", "🙌", "🫶", "👐", "🤲", "🤝", "🙏", "✍️", "💅", "🤳", "💪", "🦾", "🦿", "🦵", "🦶", "👂", "🦻", "👃", "🧠", "🫀", "🫁", "🦷", "🦴", "👀", "👁️", "👅", "👄", "🫦", "💋", "👶", "🧒", "👦", "👧", "🧑", "👱", "👨", "🧔", "👩", "🧓", "👴", "👵", "🙍", "🙎", "🙅", "🙆", "💁", "🙋", "🧏", "🙇", "🤦", "🤷", "👮", "🕵️", "💂", "🥷", "👷", "🫅", "🤴", "👸", "👳", "👲", "🧕", "🤵", "👰", "🤰", "🫄", "🫃", "🤱", "👼", "🎅", "🤶", "🦸", "🦹", "🧙", "🧚", "🧛", "🧜", "🧝", "🧞", "🧟", "🧌", "💆", "💇", "🚶", "🧍", "🧎", "🏃", "💃", "🕺", "👯", "🧖", "🧗", "🤸", "⛹️", "🏋️", "🚴", "🚵", "🤼", "🤽", "🤾", "🤺", "⛷️", "🏂", "🏄", "🏊", "🤿", "🏇", "🧘", "👨‍💻"]
}, {
  label: "📦",
  emoji: ["📋", "📌", "📎", "✏️", "📝", "📁", "📂", "📊", "📈", "🗂️", "🗄️", "💼", "🎒", "🔑", "🔒", "🛠️", "⚙️", "🧰", "📦", "💡", "🔔", "📱", "💻", "🖥️", "🖨️", "⌨️", "🖱️", "🖲️", "💾", "💿", "📀", "🎥", "📷", "📸", "📹", "📼", "🔍", "🔎", "🕯️", "💰", "💳", "💎", "⚖️", "🧲", "🔧", "🔨", "⛏️", "🪛", "🔩", "🪜", "🧱", "⛓️", "🧳", "🛡️", "🪃", "🏹", "🔮", "🪄", "🧿", "🪬", "📿", "💈", "⚗️", "🔭", "🔬", "🕳️", "🩹", "🩺", "🩻", "💊", "💉", "🩸", "🧬", "🦠", "🧫", "🧪", "🌡️", "🧹", "🪣", "🧺", "🧻", "🚰", "🚿", "🛁", "🛀", "🧼", "🪥", "🪒", "🧽", "🪢", "🧶", "🧵", "🪡", "🧲", "🪙", "💵", "💴", "💶", "💷", "🪪"]
}, {
  label: "🌿",
  emoji: ["🌱", "🌿", "🍃", "🌸", "🌻", "🌙", "⭐", "🌈", "☀️", "🔥", "💧", "🌊", "❄️", "🍂", "🌵", "🌴", "🦋", "🐝", "🐙", "🦊", "🐸", "🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼", "🐨", "🐯", "🦁", "🐮", "🐷", "🐽", "🐵", "🙈", "🙉", "🙊", "🐒", "🐔", "🐧", "🐦", "🐤", "🐣", "🦆", "🦅", "🦉", "🦇", "🐺", "🐗", "🐴", "🦄", "🐝", "🪱", "🐛", "🦋", "🐌", "🐞", "🐜", "🪲", "🪳", "🦟", "🦗", "🕷️", "🕸️", "🦂", "🐢", "🐍", "🦎", "🦖", "🦕", "🐙", "🦑", "🦐", "🦞", "🦀", "🐡", "🐠", "🐟", "🐬", "🐳", "🐋", "🦈", "🪸", "🐊", "🐅", "🐆", "🦓", "🦍", "🦧", "🐘", "🦛", "🦏", "🐪", "🐫", "🦒", "🦘", "🦬", "🐃", "🌲", "🌳", "🌴", "🌵", "🌾", "🌿", "☘️", "🍀", "🍁", "🍂", "🍃", "🪹", "🪺", "🍄", "🌰", "🦞", "🌺", "🌼", "🌷", "🪻"]
}, {
  label: "☕",
  emoji: ["☕", "🍵", "🧃", "🍎", "🍕", "🍔", "🥗", "🍣", "🧁", "🍩", "🎂", "🍫", "🥐", "🍳", "🥑", "🍉", "🫐", "🍋", "🥝", "🧇", "🍿", "🥤", "🍺", "🍷", "🍸", "🍹", "🧊", "🫖", "🥛", "🍼", "🥂", "🍾", "🧉", "🫗", "🥃", "🍶", "🍭", "🍬", "🍮", "🍰", "🥧", "🍦", "🍨", "🍧", "🎂", "🍡", "🍘", "🍙", "🍚", "🍛", "🍜", "🍝", "🍞", "🥖", "🥨", "🧀", "🥚", "🍗", "🍖", "🦴", "🌭", "🍟", "🥙", "🌮", "🌯", "🫔", "🥘", "🫕", "🥫", "🧆", "🥜", "🌰", "🍠", "🥔", "🧅", "🥕", "🌽", "🫑", "🥒", "🥬"]
}, {
  label: "✈️",
  emoji: ["🏠", "🏢", "🏗️", "🏖️", "🗺️", "✈️", "🚀", "🚗", "🚲", "🛤️", "⛺", "🎯", "🏆", "🎮", "🎵", "🎬", "📸", "🎨", "🧩", "♟️", "🎲", "🎰", "🎳", "🎪", "🎭", "🎷", "🎸", "🎹", "🥁", "🎺", "🎻", "🪕", "🎤", "🎧", "📻", "🎼", "🎶", "🎙️", "📺", "📽️", "🚂", "🚃", "🚄", "🚅", "🚆", "🚇", "🚈", "🚉", "🚊", "🚝", "🚞", "🚋", "🚌", "🚍", "🚎", "🚐", "🚑", "🚒", "🚓", "🚔", "🚕", "🚖", "🚗", "🚘", "🚙", "🛻", "🚚", "🚛", "🚜", "🏎️", "🏍️", "🛵", "🛺", "🚏", "🛣️", "🛤️", "🛞", "⛽", "🛞", "🚨", "🚥", "🚦", "🛑", "🚧", "⚓", "🛟", "⛵", "🛶", "🚤", "🛳️", "⛴️", "🛥️", "🚢", "🛩️", "🛫", "🛬", "🪂", "💺", "🚁", "🚠"]
}, {
  label: "❤️",
  emoji: ["✅", "❌", "⚡", "💫", "🔴", "🟠", "🟡", "🟢", "🔵", "🟣", "⚪", "⚫", "❤️", "🧡", "💛", "💚", "💙", "💜", "🖤", "🤍", "💯", "💢", "💥", "💦", "💨", "🕳️", "💣", "💬", "🗨️", "🗯️", "💭", "💤", "🔶", "🔷", "🔸", "🔹", "▪️", "▫️", "◾", "◽", "◼️", "◻️", "🟥", "🟧", "🟨", "🟩", "🟦", "🟪", "⬛", "⬜", "🔺", "🔻", "🔲", "🔳", "🏁", "🚩", "🎌", "🏴", "🏳️", "🏳️‍🌈", "♈", "♉", "♊", "♋", "♌", "♍", "♎", "♏", "♐", "♑", "♒", "♓", "⛎", "🔀", "🔁", "🔂", "▶️", "⏩", "⏭️", "⏯️", "◀️", "⏪", "⏮️", "🔼", "⏫", "🔽", "⏬", "⏸️", "⏹️", "⏺️", "⏏️", "🔃", "🔄", "🔅", "🔆", "📶", "📳", "📴", "♀️", "♂️"]
}];
function EmojiPicker({
  open,
  anchor,
  onClose,
  onPick
}) {
  const [cat, setCat] = React.useState(0);
  if (!open || !anchor) return null;
  const W = 300,
    H = 380;
  const fx = anchor.x,
    fy = anchor.y;
  let left = fx + 8,
    top = fy + 8;
  if (left + W > 430) left = fx - W - 8;
  if (top + H > 900) top = fy - H - 8;
  if (left < 4) left = 4;
  if (top < 4) top = 4;
  return /*#__PURE__*/React.createElement("div", {
    onClick: onClose,
    style: {
      position: "absolute",
      inset: 0,
      zIndex: 52
    }
  }, /*#__PURE__*/React.createElement("div", {
    onClick: e => e.stopPropagation(),
    style: {
      position: "absolute",
      left,
      top,
      width: W,
      background: "var(--surface-raised)",
      border: "1.5px solid var(--hairline-strong)",
      borderRadius: 16,
      boxShadow: "var(--shadow-overlay)",
      animation: "tcSpringIn 180ms cubic-bezier(0.175, 0.885, 0.32, 1.1)",
      overflow: "hidden"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      gap: 0,
      borderBottom: "1px solid var(--hairline)",
      padding: "0 4px"
    }
  }, EMOJI_CATS.map((c, i) => /*#__PURE__*/React.createElement("button", {
    key: i,
    type: "button",
    onClick: () => setCat(i),
    className: "tc-pressable",
    style: {
      flex: 1,
      padding: "10px 0",
      border: "none",
      background: "transparent",
      cursor: "pointer",
      fontSize: 16,
      lineHeight: 1,
      borderBottom: i === cat ? "2px solid var(--primary)" : "2px solid transparent",
      opacity: i === cat ? 1 : 0.5
    }
  }, c.label))), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(7, 1fr)",
      gap: 2,
      padding: "8px 6px",
      maxHeight: 290,
      overflowY: "auto"
    }
  }, EMOJI_CATS[cat].emoji.map((e, i) => /*#__PURE__*/React.createElement("button", {
    key: i,
    type: "button",
    className: "tc-pressable",
    onClick: () => {
      onPick(e);
      onClose();
    },
    style: {
      display: "grid",
      placeItems: "center",
      width: 38,
      height: 38,
      border: "none",
      background: "transparent",
      borderRadius: 8,
      cursor: "pointer",
      fontSize: 22,
      lineHeight: 1
    }
  }, e))), /*#__PURE__*/React.createElement("div", {
    style: {
      borderTop: "1px solid var(--hairline)",
      padding: "6px 10px"
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => {
      onPick(null);
      onClose();
    },
    style: {
      display: "flex",
      alignItems: "center",
      gap: 8,
      width: "100%",
      padding: "8px 6px",
      border: "none",
      background: "transparent",
      borderRadius: 8,
      cursor: "pointer",
      font: "var(--weight-regular) 13px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "x",
    size: 14,
    color: "var(--ink-400)"
  }), " Remove emoji"))));
}
const SECTION_ICONS = ["star", "flag", "bookmark", "zap", "target", "briefcase", "coffee", "map-pin", "palette", "clock", "calendar", "search", "settings", "heart", "home", "file", "list", "info", "check", "plus", "download", "share", "trash", "pencil", "copy"];
function IconPicker({
  open,
  anchor,
  onClose,
  onPick
}) {
  if (!open || !anchor) return null;
  const W = 240,
    H = 240;
  let left = anchor.x + 8,
    top = anchor.y + 8;
  if (left + W > 430) left = anchor.x - W - 8;
  if (top + H > 900) top = anchor.y - H - 8;
  if (left < 4) left = 4;
  if (top < 4) top = 4;
  return /*#__PURE__*/React.createElement("div", {
    onClick: onClose,
    style: {
      position: "absolute",
      inset: 0,
      zIndex: 52
    }
  }, /*#__PURE__*/React.createElement("div", {
    onClick: e => e.stopPropagation(),
    style: {
      position: "absolute",
      left,
      top,
      width: W,
      background: "var(--surface-raised)",
      border: "1.5px solid var(--hairline-strong)",
      borderRadius: 16,
      boxShadow: "var(--shadow-overlay)",
      padding: "12px 8px",
      animation: "tcSpringIn 180ms cubic-bezier(0.175, 0.885, 0.32, 1.1)"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      font: "var(--weight-medium) 12px/1 var(--font-sans)",
      color: "var(--ink-500)",
      padding: "0 6px 8px"
    }
  }, "Section icon"), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gridTemplateColumns: "repeat(5, 1fr)",
      gap: 4
    }
  }, SECTION_ICONS.map(ic => /*#__PURE__*/React.createElement("button", {
    key: ic,
    type: "button",
    className: "tc-pressable",
    onClick: () => {
      onPick(ic);
      onClose();
    },
    style: {
      display: "grid",
      placeItems: "center",
      width: 40,
      height: 40,
      border: "none",
      background: "transparent",
      borderRadius: 8,
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: ic,
    size: 18,
    color: "var(--ink-600)"
  })))), /*#__PURE__*/React.createElement("div", {
    style: {
      borderTop: "1px solid var(--hairline)",
      marginTop: 8,
      paddingTop: 6
    }
  }, /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => {
      onPick(null);
      onClose();
    },
    style: {
      display: "flex",
      alignItems: "center",
      gap: 8,
      width: "100%",
      padding: "8px 6px",
      border: "none",
      background: "transparent",
      borderRadius: 8,
      cursor: "pointer",
      font: "var(--weight-regular) 13px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "x",
    size: 14,
    color: "var(--ink-400)"
  }), " Remove icon"))));
}

/* ===================================================================
   Popup overlays: AddChooser, NameForm
   =================================================================== */

function AddChooser({
  open,
  onClose,
  onPick,
  onImport
}) {
  const opts = [{
    key: "parent",
    icon: "chevron-down",
    title: "New parent",
    sub: "A container that holds sections"
  }, {
    key: "section",
    icon: "plus",
    title: "New section",
    sub: "A group of tasks"
  }, {
    key: "task",
    icon: "check",
    title: "New task",
    sub: "A single thing to do"
  }];
  const row = {
    display: "flex",
    alignItems: "center",
    gap: 12,
    padding: "10px 8px",
    border: "none",
    background: "transparent",
    borderRadius: 10,
    cursor: "pointer",
    textAlign: "left"
  };
  const chip = {
    display: "grid",
    placeItems: "center",
    width: 36,
    height: 36,
    flex: "none",
    borderRadius: 8,
    background: "var(--surface-sunken)"
  };
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Add",
    maxWidth: 360
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gap: 2
    }
  }, opts.map(o => /*#__PURE__*/React.createElement("button", {
    key: o.key,
    type: "button",
    className: "tc-pressable",
    onClick: () => onPick(o.key),
    style: {
      ...row,
      width: "100%"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: chip
  }, /*#__PURE__*/React.createElement(Icon, {
    name: o.icon,
    size: 18,
    color: "var(--ink-600)"
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      font: "var(--weight-medium) 15px/1.2 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, o.title), /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      marginTop: 2,
      font: "var(--weight-regular) 13px/1.3 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, o.sub))))), /*#__PURE__*/React.createElement("div", {
    style: {
      height: 1,
      background: "var(--hairline)",
      margin: "8px 4px"
    }
  }), /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: onImport,
    style: {
      ...row,
      width: "100%"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: chip
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "import",
    size: 18,
    color: "var(--ink-600)"
  })), /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      font: "var(--weight-medium) 15px/1.2 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, "Import from .txt"), /*#__PURE__*/React.createElement("span", {
    style: {
      display: "block",
      marginTop: 2,
      font: "var(--weight-regular) 13px/1.3 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, "Build from a plain-text plan"))));
}
function NameForm({
  open,
  kind,
  onClose,
  onSave
}) {
  const [name, setName] = React.useState("");
  React.useEffect(() => {
    if (open) setName("");
  }, [open]);
  const label = kind === "parent" ? "parent" : "section";
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: `New ${label}`,
    maxWidth: 360
  }, /*#__PURE__*/React.createElement(TextField, {
    label: "Title",
    value: name,
    onChange: setName,
    placeholder: kind === "parent" ? "e.g. Work" : "e.g. Launch prep"
  }), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 18
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    full: true,
    onClick: () => onSave(name || `Untitled ${label}`)
  }, "Add ", label)));
}

/* ===================================================================
   Popup overlays: Form, Import, Search, Download, Rate
   =================================================================== */

function FormPopup({
  open,
  mode = "create",
  draft,
  onClose,
  onSave
}) {
  const [title, setTitle] = React.useState(draft?.title || "");
  const [desc, setDesc] = React.useState(draft?.desc || "");
  const [noLimit, setNoLimit] = React.useState(false);
  const [tmode, setTmode] = React.useState("Date & time");
  const [tval, setTval] = React.useState("Jun 12, 2026");
  const [ttime, setTtime] = React.useState("12:59 PM");
  const [tdays, setTdays] = React.useState("2");
  const [thours, setThours] = React.useState("0");
  React.useEffect(() => {
    if (open) {
      setTitle(draft?.title || "");
      setDesc(draft?.desc || "");
      setNoLimit(false);
    }
  }, [open, draft]);
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: mode === "edit" ? "Edit task" : "New task",
    maxWidth: 400
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gap: 16
    }
  }, /*#__PURE__*/React.createElement(TextField, {
    label: "Title",
    value: title,
    onChange: setTitle,
    placeholder: "What needs doing?"
  }), /*#__PURE__*/React.createElement(TextField, {
    label: "Description",
    value: desc,
    onChange: setDesc,
    multiline: true,
    rows: 2,
    placeholder: "Optional \u2014 truncates at two lines"
  }), /*#__PURE__*/React.createElement(TimeInput, {
    label: "Deadline",
    mode: tmode,
    noLimit: noLimit,
    onNoLimitChange: setNoLimit,
    value: tval,
    time: ttime,
    days: tdays,
    hours: thours,
    onModeChange: setTmode,
    onValueChange: setTval,
    onTimeChange: setTtime,
    onDaysChange: setTdays,
    onHoursChange: setThours
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 20
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    full: true,
    onClick: () => onSave({
      title: title || "Untitled task",
      desc
    })
  }, mode === "edit" ? "Save changes" : "Add task")));
}
function ImportPopup({
  open,
  onClose,
  onConfirm
}) {
  const tree = [{
    t: "Q3 planning",
    kids: ["Define OKRs", "Draft roadmap", "Budget review"]
  }, {
    t: "Reading list",
    kids: ["Design systems handbook", "The Timeless Way of Building"]
  }, {
    t: "Home",
    kids: ["Renew lease", "Fix the shelf"]
  }];
  const tasks = tree.reduce((n, s) => n + s.kids.length, 0);
  /* miniature section card */
  const mini = s => /*#__PURE__*/React.createElement("div", {
    key: s.t,
    style: {
      background: "var(--surface)",
      border: "1px solid var(--hairline)",
      borderRadius: 10,
      padding: "8px 12px"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      marginBottom: 4
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-medium) 13px/1.2 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, s.t), /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-medium) 11px/1 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, s.kids.length)), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      flexDirection: "column",
      gap: 2
    }
  }, s.kids.map((k, j) => /*#__PURE__*/React.createElement("div", {
    key: j,
    style: {
      display: "flex",
      alignItems: "center",
      gap: 6
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      width: 12,
      height: 12,
      borderRadius: 3,
      border: "1.5px solid var(--ink-300)",
      flex: "none"
    }
  }), /*#__PURE__*/React.createElement("span", {
    className: "tc-truncate-1",
    style: {
      font: "var(--weight-regular) 12px/1.3 var(--font-sans)",
      color: "var(--ink-600)"
    }
  }, k)))));
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Import preview",
    maxWidth: 400
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      background: "var(--parent-fill)",
      border: "1.5px solid var(--hairline-strong)",
      borderRadius: 14,
      padding: "8px 8px 10px",
      display: "flex",
      flexDirection: "column",
      gap: 6,
      maxHeight: 280,
      overflowY: "auto"
    }
  }, tree.map(mini)), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      gap: 8,
      marginTop: 12,
      flexWrap: "wrap"
    }
  }, /*#__PURE__*/React.createElement(Badge, null, tree.length, " sections"), /*#__PURE__*/React.createElement(Badge, null, tasks, " tasks"), /*#__PURE__*/React.createElement(Badge, null, "from plan.txt")), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      gap: 10,
      marginTop: 16
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "secondary",
    full: true,
    onClick: onClose
  }, "Cancel"), /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    full: true,
    onClick: onConfirm
  }, "Import ", tasks)));
}
function SearchPopup({
  open,
  onClose
}) {
  const [q, setQ] = React.useState("re");
  const all = [{
    crumb: "Daily · Morning",
    title: "Read for 20 minutes",
    status: "on-track",
    time: "3h"
  }, {
    crumb: "Work · Launch prep",
    title: "Write release notes",
    status: "calm"
  }, {
    crumb: "Work · Launch prep",
    title: "Review the import flow",
    status: "due",
    time: "9m"
  }, {
    crumb: "Work · Errands",
    title: "Renew the lease",
    status: "overdue",
    time: "−1d 2h"
  }];
  const results = all.filter(r => (r.title + r.crumb).toLowerCase().includes(q.toLowerCase()));
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Search",
    maxWidth: 400
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      gap: 10,
      height: 44,
      padding: "0 12px",
      background: "var(--surface)",
      border: "1px solid var(--hairline-strong)",
      borderRadius: "var(--radius-sm)",
      marginBottom: 12
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "search",
    size: 18,
    color: "var(--ink-500)"
  }), /*#__PURE__*/React.createElement("input", {
    value: q,
    onChange: e => setQ(e.target.value),
    placeholder: "Search every task",
    autoFocus: true,
    style: {
      flex: 1,
      border: "none",
      outline: "none",
      background: "transparent",
      font: "var(--weight-regular) 15px/1 var(--font-sans)",
      color: "var(--ink-900)"
    }
  })), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gap: 2,
      maxHeight: 300,
      overflowY: "auto"
    }
  }, results.length === 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "24px 0",
      textAlign: "center",
      font: "var(--weight-regular) 14px/1.4 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, "Nothing matches \"", q, "\""), results.map((r, i) => /*#__PURE__*/React.createElement("div", {
    key: i,
    className: "tc-pressable",
    style: {
      display: "flex",
      alignItems: "center",
      gap: 12,
      padding: "10px 6px",
      borderRadius: 8,
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      font: "var(--weight-regular) 11px/1.2 var(--font-sans)",
      color: "var(--ink-400)",
      marginBottom: 2
    }
  }, r.crumb), /*#__PURE__*/React.createElement("div", {
    className: "tc-truncate-1",
    style: {
      font: "var(--weight-regular) 15px/1.3 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, r.title)), /*#__PURE__*/React.createElement(TimePill, {
    status: r.status,
    label: r.time
  })))));
}

/* --- Reusable page rows --- */
function PageRow({
  title,
  value,
  danger,
  last,
  onClick
}) {
  return /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: onClick,
    style: {
      display: "flex",
      alignItems: "center",
      gap: 12,
      width: "100%",
      padding: "14px 4px",
      border: "none",
      background: "transparent",
      borderBottom: last ? "none" : "1px solid var(--hairline)",
      cursor: "pointer",
      textAlign: "left"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      flex: 1,
      minWidth: 0,
      font: "var(--weight-regular) 15px/1.3 var(--font-sans)",
      color: danger ? "var(--red)" : "var(--ink-900)"
    }
  }, title), value && /*#__PURE__*/React.createElement("span", {
    style: {
      flex: "none",
      font: "var(--weight-regular) 14px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, value), /*#__PURE__*/React.createElement(Icon, {
    name: "chevron-right",
    size: 16,
    color: "var(--ink-300)"
  }));
}
function PageGroup({
  title,
  children
}) {
  return /*#__PURE__*/React.createElement("div", {
    style: {
      marginBottom: 18
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "0 4px 6px",
      font: "var(--weight-medium) 12px/1 var(--font-sans)",
      color: "var(--ink-500)",
      letterSpacing: "0.04em",
      textTransform: "uppercase"
    }
  }, title), /*#__PURE__*/React.createElement("div", null, children));
}
function SettingsPage({
  open,
  onBack,
  onAction
}) {
  return /*#__PURE__*/React.createElement(Page, {
    open: open,
    title: "Settings",
    onBack: onBack
  }, /*#__PURE__*/React.createElement(PageGroup, {
    title: "Appearance"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Theme",
    value: "System",
    onClick: () => onAction("Theme"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Tasks"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Default priority",
    value: "None",
    onClick: () => onAction("Default priority")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Default sort order",
    value: "Date added",
    onClick: () => onAction("Sort")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Auto-hide completed",
    value: "Off",
    onClick: () => onAction("Auto-hide")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Clear all completed",
    danger: true,
    onClick: () => onAction("Clear completed"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Notifications"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Due-date reminders",
    value: "On",
    onClick: () => onAction("Reminders")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Default reminder time",
    value: "30 min before",
    onClick: () => onAction("Default reminder"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Trash"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Auto-delete trashed items",
    value: "30 days",
    onClick: () => onAction("Auto-delete"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Data"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Export tasks",
    value: ".csv \xB7 .json",
    onClick: () => onAction("Export")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Import tasks",
    value: ".csv \xB7 .json",
    onClick: () => onAction("Import"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Legal"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Privacy Policy",
    onClick: () => onAction("Privacy")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Terms of Service",
    onClick: () => onAction("Terms")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "End User License Agreement",
    onClick: () => onAction("EULA")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Data Deletion Policy",
    onClick: () => onAction("Data deletion"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Danger zone"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Reset to default settings",
    danger: true,
    onClick: () => onAction("Reset"),
    last: true
  })));
}
function TrashPage({
  open,
  onBack,
  onAction
}) {
  const items = [{
    id: "t1",
    title: "Old grocery list",
    due: "—",
    deleted: "Jun 4",
    days: 27
  }, {
    id: "t2",
    title: "Email Sarah back",
    due: "Jun 2",
    deleted: "Jun 5",
    days: 28
  }, {
    id: "t3",
    title: "Renew gym pass",
    due: "May 30",
    deleted: "May 31",
    days: 23
  }];
  return /*#__PURE__*/React.createElement(Page, {
    open: open,
    title: "Trash",
    onBack: onBack
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      marginBottom: 14
    }
  }, /*#__PURE__*/React.createElement(Banner, {
    variant: "read-only"
  }, "Items are permanently deleted after 30 days.")), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "grid",
      gap: 1
    }
  }, items.map((it, i) => /*#__PURE__*/React.createElement("div", {
    key: it.id,
    style: {
      display: "flex",
      alignItems: "flex-start",
      gap: 12,
      padding: "14px 4px",
      borderBottom: i === items.length - 1 ? "none" : "1px solid var(--hairline)"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("div", {
    className: "tc-truncate-1",
    style: {
      font: "var(--weight-regular) 16px/1.3 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, it.title), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 4,
      font: "var(--weight-regular) 12px/1.4 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, "Due ", it.due, " \xB7 Deleted ", it.deleted, " \xB7 ", it.days, "d left")), /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => onAction(`Restored ${it.title}`),
    style: {
      flex: "none",
      height: 30,
      padding: "0 12px",
      border: "1px solid var(--hairline-strong)",
      background: "var(--surface)",
      borderRadius: 999,
      cursor: "pointer",
      font: "var(--weight-medium) 13px/1 var(--font-sans)",
      color: "var(--ink-700)"
    }
  }, "Restore")))), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      gap: 10,
      marginTop: 18
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "secondary",
    full: true,
    onClick: () => onAction("All restored")
  }, "Restore all"), /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    full: true,
    onClick: () => onAction("Trash emptied")
  }, "Empty trash")));
}
function AboutPage({
  open,
  onBack,
  onAction,
  onRate
}) {
  return /*#__PURE__*/React.createElement(Page, {
    open: open,
    title: "About",
    onBack: onBack
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      gap: 14,
      padding: "4px 4px 18px"
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      display: "grid",
      placeItems: "center",
      width: 56,
      height: 56,
      borderRadius: 16,
      background: "var(--ink-900)",
      color: "var(--surface)",
      font: "var(--weight-medium) 26px/1 var(--font-sans)",
      letterSpacing: "-0.02em"
    }
  }, "T"), /*#__PURE__*/React.createElement("div", {
    style: {
      flex: 1,
      minWidth: 0
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      font: "var(--weight-medium) 18px/1.2 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, "Task Cluster"), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 2,
      font: "var(--weight-regular) 13px/1.4 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, "v2.2 \xB7 Build 22 \xB7 A calm, sectioned planner."))), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Made by"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Sumit Bhide",
    value: "@sumitbhide",
    onClick: () => onAction("Profile"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Help us"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Rate this app",
    onClick: onRate
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Share this app",
    onClick: () => onAction("Share sheet opened")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Send feedback",
    onClick: () => onAction("Email composer opened"),
    last: true
  })), /*#__PURE__*/React.createElement(PageGroup, {
    title: "Legal"
  }, /*#__PURE__*/React.createElement(PageRow, {
    title: "Privacy Policy",
    onClick: () => onAction("Privacy")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Terms of Service",
    onClick: () => onAction("Terms")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "End User License Agreement",
    onClick: () => onAction("EULA")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Data Deletion Policy",
    onClick: () => onAction("Data deletion")
  }), /*#__PURE__*/React.createElement(PageRow, {
    title: "Open-source licenses",
    onClick: () => onAction("Licenses"),
    last: true
  })));
}
function DownloadPopup({
  open,
  onClose,
  onAction
}) {
  const row = (icon, label) => /*#__PURE__*/React.createElement("button", {
    type: "button",
    className: "tc-pressable",
    onClick: () => {
      onClose();
      onAction(label);
    },
    style: {
      display: "flex",
      alignItems: "center",
      gap: 12,
      width: "100%",
      padding: "12px 8px",
      border: "none",
      background: "transparent",
      borderRadius: 10,
      cursor: "pointer",
      textAlign: "left"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: icon,
    size: 18,
    color: "var(--ink-500)"
  }), /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-regular) 15px/1 var(--font-sans)",
      color: "var(--ink-900)"
    }
  }, label));
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Download",
    maxWidth: 320
  }, row("file", "Download SKILL.md"), row("copy", "Copy as prompt"));
}
function RatePopup({
  open,
  onClose,
  onSubmit
}) {
  const [stars, setStars] = React.useState(0);
  React.useEffect(() => {
    if (open) setStars(0);
  }, [open]);
  const star = n => /*#__PURE__*/React.createElement("button", {
    key: n,
    type: "button",
    onClick: () => setStars(n),
    className: "tc-pressable",
    style: {
      display: "grid",
      placeItems: "center",
      width: 44,
      height: 44,
      border: "none",
      background: "transparent",
      cursor: "pointer"
    }
  }, /*#__PURE__*/React.createElement("svg", {
    width: "30",
    height: "30",
    viewBox: "0 0 24 24"
  }, /*#__PURE__*/React.createElement("path", {
    d: "M12 2.6l2.92 5.92 6.53.95-4.72 4.6 1.12 6.5L12 17.5l-5.85 3.07 1.12-6.5L2.55 9.47l6.53-.95L12 2.6z",
    fill: n <= stars ? "var(--blue)" : "transparent",
    stroke: "var(--ink-400)",
    strokeWidth: "1.4",
    strokeLinejoin: "round"
  })));
  return /*#__PURE__*/React.createElement(Popup, {
    open: open,
    onClose: onClose,
    title: "Rate this app",
    maxWidth: 360
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "0 4px 8px",
      font: "var(--weight-regular) 15px/1.5 var(--font-sans)",
      color: "var(--ink-600)"
    }
  }, "If Task Cluster has earned its place on your home screen, a quick rating helps other focused people find it."), /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      justifyContent: "center",
      gap: 4,
      padding: "10px 0 4px"
    }
  }, [1, 2, 3, 4, 5].map(star)), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 14
    }
  }, /*#__PURE__*/React.createElement(Button, {
    variant: "primary",
    full: true,
    disabled: stars === 0,
    onClick: () => onSubmit(stars)
  }, stars >= 4 ? "Rate on Play Store" : stars === 0 ? "Tap a star" : "Send feedback")));
}

/* ===================================================================
   Page overlays: Settings, Trash, About
   =================================================================== */

/* ===================================================================
   The single screen + state machine
   =================================================================== */

const TODAY_Y = 2026;
const TODAY_M = 5; /* June = 5 (0-indexed) */
const TODAY_D = 11;
const WEEKDAYS = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
const MONTH_SHORT = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
function weekdayOf(y, m, d) {
  return (new Date(y, m, d).getDay() + 6) % 7;
} // 0=Mon
function labelOf(y, m, d) {
  return WEEKDAYS[weekdayOf(y, m, d)];
}
function weekOf(y, m, d) {
  const wd = weekdayOf(y, m, d);
  const dim = new Date(y, m + 1, 0).getDate();
  const monday = d - wd;
  const out = [];
  for (let i = 0; i < 7; i++) {
    const dd = monday + i;
    if (dd < 1 || dd > dim) out.push({
      key: `p${i}`,
      placeholder: true
    });else out.push({
      key: `${y}-${m}-${dd}`,
      weekday: WEEKDAYS[i],
      date: dd
    });
  }
  return out;
}
function sameDay(a, b) {
  return a.y === b.y && a.m === b.m && a.d === b.d;
}
function dayCompare(a, b) {
  return a.y !== b.y ? a.y - b.y : a.m !== b.m ? a.m - b.m : a.d - b.d;
}
const DATA = {
  daily: [{
    id: "morning",
    title: "Morning",
    icon: "coffee",
    tasks: [{
      id: "m1",
      title: "Inbox to zero",
      done: true
    }, {
      id: "m2",
      title: "Plan the day",
      desc: "Pick the three things that actually matter.",
      status: "on-track",
      time: "45m"
    }, {
      id: "m3",
      title: "Read for 20 minutes",
      status: "calm"
    }]
  }, {
    id: "wind",
    title: "Wind down",
    icon: "clock",
    tasks: [{
      id: "w1",
      title: "Tidy the desk",
      status: "calm"
    }, {
      id: "w2",
      title: "Set tomorrow's top task",
      status: "on-track",
      time: "8h"
    }]
  }],
  tree: [{
    type: "parent",
    id: "work",
    title: "Work",
    emoji: "💼",
    sections: [{
      id: "launch",
      title: "Launch prep",
      icon: "zap",
      status: "on-track",
      time: "2h 40m",
      tasks: [{
        id: "l1",
        title: "Write release notes",
        done: true,
        desc: "Summarise the 2.2 changes for the changelog."
      }, {
        id: "l2",
        title: "Final QA pass on the import flow",
        status: "on-track",
        time: "2h 40m"
      }, {
        id: "l3",
        title: "Email the beta list",
        status: "overdue",
        time: "−2h 14m",
        desc: "Include the opt-out link and the new screenshots."
      }]
    }, {
      id: "errands",
      title: "Errands",
      icon: "map-pin",
      status: "due",
      time: "9m",
      tasks: [{
        id: "e1",
        title: "Renew the lease",
        status: "due",
        time: "9m"
      }, {
        id: "e2",
        title: "Pick up dry cleaning",
        status: "close",
        time: "48m"
      }, {
        id: "e3",
        title: "Buy a birthday card",
        done: true
      }]
    }, {
      id: "design",
      title: "Design review",
      icon: "palette",
      status: "on-track",
      time: "4h",
      tasks: [{
        id: "d1",
        title: "Prepare mockups for v2.3"
      }, {
        id: "d2",
        title: "Colour palette audit",
        done: true
      }]
    }, {
      id: "hiring",
      title: "Hiring",
      icon: "briefcase",
      tasks: [{
        id: "h1",
        title: "Review portfolio submissions"
      }, {
        id: "h2",
        title: "Schedule interviews"
      }]
    }, {
      id: "infra",
      title: "Infrastructure",
      icon: "settings",
      status: "calm",
      tasks: [{
        id: "i1",
        title: "Upgrade CI pipeline"
      }, {
        id: "i2",
        title: "Rotate API keys"
      }]
    }]
  }, {
    type: "section",
    id: "fresh",
    title: "Side project",
    tasks: []
  }]
};
function allSections(data) {
  const out = [...data.daily];
  data.tree.forEach(n => n.type === "parent" ? out.push(...n.sections) : out.push(n));
  return out;
}
function TaskClusterScreen() {
  const today = {
    y: TODAY_Y,
    m: TODAY_M,
    d: TODAY_D
  };
  const [sel, setSel] = React.useState({
    ...today
  });
  const [page, setPage] = React.useState("home"); // home | tasks
  const [mode, setMode] = React.useState("home");
  const [overlay, setOverlay] = React.useState(null);
  const [addKind, setAddKind] = React.useState("section");
  const [parentEmoji, setParentEmoji] = React.useState({
    daily: "☀️",
    work: "💼"
  });
  const [sectionIcons, setSectionIcons] = React.useState({
    morning: "coffee",
    wind: "clock",
    launch: "zap",
    errands: "map-pin",
    design: "palette",
    hiring: "briefcase",
    infra: "settings"
  });
  const [emojiTarget, setEmojiTarget] = React.useState(null);
  const [iconTarget, setIconTarget] = React.useState(null);
  const [favourites, setFavourites] = React.useState({
    work: true
  });
  const [collapsedParents, setCollapsedParents] = React.useState({
    work: true
  });
  const [ctx, setCtx] = React.useState({
    open: false,
    anchor: null,
    items: [],
    title: null
  });
  const closeCtx = () => setCtx(c => ({
    ...c,
    open: false
  }));
  const [done, setDone] = React.useState(() => {
    const m = {};
    allSections(DATA).forEach(s => s.tasks.forEach(t => {
      m[t.id] = !!t.done;
    }));
    return m;
  });
  /* All sections start collapsed; Daily sections are also collapsed by default */
  const [collapsed, setCollapsed] = React.useState(() => {
    const m = {};
    allSections(DATA).forEach(s => {
      m[s.id] = true;
    });
    return m;
  });
  const [toast, setToast] = React.useState(null);
  const [snack, setSnack] = React.useState(null);
  const isToday = sameDay(sel, today);
  const cmp = dayCompare(sel, today);
  const view = isToday ? "today" : cmp < 0 ? "past" : "future";
  const readOnly = view === "past";
  const planning = view === "future";
  const flash = msg => {
    setToast(msg);
    setTimeout(() => setToast(null), 2000);
  };
  const onBar = m => {
    if (m === "home" || m === "tasks") {
      setPage(m);
      setMode(m);
      setOverlay(null);
    } else if (m === "add") {
      setMode(m);
      setOverlay("addchoose");
    } else if (m === "search") {
      setMode(m);
      setOverlay("search");
    } else setOverlay(null);
  };
  const closeOverlay = () => {
    setOverlay(null);
    setMode(page);
  };
  const pickAdd = kind => {
    if (kind === "task") setOverlay("form");else {
      setAddKind(kind);
      setOverlay("nameform");
    }
  };
  const startDelete = title => {
    setSnack(`${title} deleted`);
    setTimeout(() => setSnack(null), 3200);
  };

  /* three-dot menu — Settings, Trash, About, Download (with submenu) */
  const openTopMenu = anchor => {
    setCtx({
      open: true,
      anchor,
      title: null,
      items: [{
        icon: "settings",
        label: "Settings",
        onClick: () => setOverlay("settings")
      }, {
        icon: "trash",
        label: "Trash",
        onClick: () => setOverlay("trash")
      }, {
        icon: "info",
        label: "About",
        onClick: () => setOverlay("about")
      }, {
        divider: true
      }, {
        icon: "download",
        label: "Download",
        onClick: () => setOverlay("download")
      }]
    });
  };

  /* long-press menus */
  const openSectionMenu = (anchor, section) => {
    setCtx({
      open: true,
      anchor,
      title: section.title,
      items: [{
        icon: "plus",
        label: "Add task",
        onClick: () => setOverlay("form")
      }, {
        icon: "pencil",
        label: "Rename",
        onClick: () => flash("Renamed")
      }, {
        divider: true
      }, {
        icon: "x",
        label: "Delete section",
        danger: true,
        onClick: () => startDelete(section.title)
      }]
    });
  };
  const openParentMenu = (anchor, parent, pinned) => {
    const isFav = !!favourites[parent.id];
    const items = [{
      icon: "plus",
      label: "Add section",
      onClick: () => {
        setAddKind("section");
        setOverlay("nameform");
      }
    }, {
      icon: "pencil",
      label: "Rename",
      onClick: () => flash("Renamed")
    }, {
      divider: true
    }, {
      icon: "pin",
      label: isFav ? "Unpin from Home" : "Pin to Home",
      onClick: () => {
        setFavourites(f => {
          const n = {
            ...f
          };
          if (n[parent.id]) delete n[parent.id];else n[parent.id] = true;
          return n;
        });
        flash(isFav ? "Unpinned" : "Pinned to Home");
      }
    }];
    if (!pinned) {
      items.push({
        divider: true
      });
      items.push({
        icon: "x",
        label: "Delete parent",
        danger: true,
        onClick: () => startDelete(parent.title)
      });
    }
    setCtx({
      open: true,
      anchor,
      title: parent.title,
      items
    });
  };
  const openTaskMenu = (anchor, task) => {
    setCtx({
      open: true,
      anchor,
      title: task.title,
      items: [{
        icon: "pencil",
        label: "Edit",
        onClick: () => setOverlay("form")
      }, {
        icon: "clock",
        label: "Set deadline",
        onClick: () => setOverlay("form")
      }, {
        divider: true
      }, {
        icon: "x",
        label: "Delete task",
        danger: true,
        onClick: () => startDelete(task.title)
      }]
    });
  };
  const renderSection = s => {
    const tasks = s.tasks;
    const d = tasks.filter(t => done[t.id]).length;
    const ic = sectionIcons[s.id] || s.icon;
    return /*#__PURE__*/React.createElement(SectionCard, {
      key: s.id,
      title: s.title,
      icon: ic,
      done: d,
      total: tasks.length,
      status: s.status,
      time: s.time,
      expanded: !collapsed[s.id],
      onToggle: () => setCollapsed(c => ({
        ...c,
        [s.id]: !c[s.id]
      })),
      onMenu: pt => openSectionMenu(pt, s),
      onIconClick: pt => setIconTarget({
        id: s.id,
        anchor: pt
      })
    }, tasks.length === 0 ? /*#__PURE__*/React.createElement("div", {
      style: {
        padding: "10px 0 6px",
        font: "var(--weight-regular) 14px/1.4 var(--font-sans)",
        color: "var(--ink-400)"
      }
    }, "No tasks yet \u2014 press and hold the title to add one.") : tasks.map(t => /*#__PURE__*/React.createElement(TaskRow, {
      key: t.id,
      title: t.title,
      description: t.desc,
      status: t.status,
      time: t.time,
      checked: done[t.id],
      onToggle: next => !readOnly && setDone(m => ({
        ...m,
        [t.id]: next
      })),
      onMenu: pt => openTaskMenu(pt, t)
    })));
  };
  const renderParent = (p, pinned = false) => {
    const isFav = !!favourites[p.id];
    const em = parentEmoji[p.id] || p.emoji;
    return /*#__PURE__*/React.createElement(ParentSection, {
      key: p.id,
      title: p.title,
      count: p.sections.length,
      pinned: pinned,
      favourite: isFav,
      emoji: em,
      expanded: !collapsedParents[p.id],
      onToggle: v => setCollapsedParents(c => ({
        ...c,
        [p.id]: !v
      })),
      onEmojiClick: pt => setEmojiTarget({
        id: p.id,
        anchor: pt
      }),
      onMenu: pt => openParentMenu(pt, p, pinned)
    }, p.sections.map(s => renderSection(s)));
  };
  const selKey = `${sel.y}-${sel.m}-${sel.d}`;
  const todayKey = `${today.y}-${today.m}-${today.d}`;
  const header = isToday ? "Today" : `${labelOf(sel.y, sel.m, sel.d)}, ${MONTH_SHORT[sel.m]} ${sel.d}`;
  const pickDate = ({
    year,
    month,
    date
  }) => {
    setSel({
      y: year,
      m: month,
      d: date
    });
    setOverlay(null);
    setMode("check");
  };
  return /*#__PURE__*/React.createElement("div", {
    className: "tc-frame"
  }, /*#__PURE__*/React.createElement("div", {
    className: "tc-scroll",
    style: {
      pointerEvents: readOnly ? "none" : "auto"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "26px 16px 140px",
      maxWidth: 440,
      margin: "0 auto"
    }
  }, /*#__PURE__*/React.createElement("header", {
    style: {
      margin: "0 2px 18px",
      pointerEvents: "auto"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      display: "flex",
      alignItems: "center",
      justifyContent: "space-between",
      gap: 10
    }
  }, /*#__PURE__*/React.createElement("span", {
    style: {
      font: "var(--weight-medium) 32px/1 var(--font-sans)",
      letterSpacing: "-0.02em",
      color: "var(--ink-900)"
    }
  }, "Task Cluster"), /*#__PURE__*/React.createElement("button", {
    type: "button",
    "aria-label": "Open menu",
    onClick: e => openTopMenu({
      x: e.clientX,
      y: e.clientY
    }),
    onContextMenu: e => {
      e.preventDefault();
      openTopMenu({
        x: e.clientX,
        y: e.clientY
      });
    },
    className: "tc-pressable",
    style: {
      display: "grid",
      placeItems: "center",
      width: 36,
      height: 36,
      border: "none",
      background: "transparent",
      borderRadius: 999,
      cursor: "pointer",
      color: "var(--ink-700)"
    }
  }, /*#__PURE__*/React.createElement(Icon, {
    name: "more-vertical",
    size: 22,
    color: "var(--ink-700)"
  }))), /*#__PURE__*/React.createElement("div", {
    style: {
      marginTop: 4,
      font: "var(--weight-medium) 20px/1 var(--font-sans)",
      color: "var(--ink-500)"
    }
  }, page === "home" ? header : "Tasks")), page === "home" && /*#__PURE__*/React.createElement(DateStrip, {
    days: weekOf(sel.y, sel.m, sel.d),
    selectedKey: selKey,
    todayKey: todayKey,
    onSelect: k => {
      const [y, m, d] = k.split("-").map(Number);
      setSel({
        y,
        m,
        d
      });
    },
    onCalendar: () => setOverlay("calendar")
  }), page === "home" && (readOnly || planning) && /*#__PURE__*/React.createElement("div", {
    style: {
      margin: "8px 0 4px",
      pointerEvents: "auto"
    }
  }, /*#__PURE__*/React.createElement(Banner, {
    variant: readOnly ? "read-only" : "planning"
  })), planning ? /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "40px 8px",
      textAlign: "center"
    }
  }, /*#__PURE__*/React.createElement("div", {
    style: {
      font: "var(--weight-regular) 15px/1.5 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, "Nothing planned for this day yet.")) : page === "home" ?
  /*#__PURE__*/
  /* HOME: Daily + pinned favourites */
  React.createElement("div", {
    style: {
      marginTop: 14,
      display: "flex",
      flexDirection: "column",
      gap: 12
    }
  }, renderParent({
    id: "daily",
    title: "Daily",
    emoji: "☀️",
    sections: DATA.daily
  }, true), DATA.tree.filter(n => n.type === "parent" && favourites[n.id]).map(n => renderParent(n)), Object.keys(favourites).length === 0 && /*#__PURE__*/React.createElement("div", {
    style: {
      padding: "24px 8px",
      textAlign: "center",
      font: "var(--weight-regular) 14px/1.5 var(--font-sans)",
      color: "var(--ink-400)"
    }
  }, "Pin parents from the Tasks tab to see them here.")) :
  /*#__PURE__*/
  /* TASKS: all parents + standalone sections */
  React.createElement("div", {
    style: {
      marginTop: 14,
      display: "flex",
      flexDirection: "column",
      gap: 12
    }
  }, DATA.tree.map(n => n.type === "parent" ? renderParent(n) : renderSection(n))))), /*#__PURE__*/React.createElement("div", {
    className: "tc-bar"
  }, /*#__PURE__*/React.createElement(BottomBar, {
    mode: mode,
    onModeChange: onBar
  })), toast && /*#__PURE__*/React.createElement("div", {
    className: "tc-feedback"
  }, /*#__PURE__*/React.createElement(Toast, null, toast)), snack && /*#__PURE__*/React.createElement("div", {
    className: "tc-feedback"
  }, /*#__PURE__*/React.createElement(Snackbar, {
    onAction: () => {
      setSnack(null);
      flash("Restored");
    }
  }, snack)), /*#__PURE__*/React.createElement(ContextMenu, {
    open: ctx.open,
    anchor: ctx.anchor,
    title: ctx.title,
    items: ctx.items,
    onClose: closeCtx
  }), /*#__PURE__*/React.createElement(AddChooser, {
    open: overlay === "addchoose",
    onClose: closeOverlay,
    onPick: pickAdd,
    onImport: () => setOverlay("import")
  }), /*#__PURE__*/React.createElement(NameForm, {
    open: overlay === "nameform",
    kind: addKind,
    onClose: closeOverlay,
    onSave: name => {
      closeOverlay();
      flash(`${name} added`);
    }
  }), /*#__PURE__*/React.createElement(CalendarPopup, {
    open: overlay === "calendar",
    onClose: closeOverlay,
    todayYear: TODAY_Y,
    todayMonth: TODAY_M,
    todayDate: TODAY_D,
    selectedYear: sel.y,
    selectedMonth: sel.m,
    selectedDate: sel.d,
    onPick: pickDate
  }), /*#__PURE__*/React.createElement(FormPopup, {
    open: overlay === "form",
    onClose: closeOverlay,
    onSave: () => {
      closeOverlay();
      flash("Saved");
    }
  }), /*#__PURE__*/React.createElement(ImportPopup, {
    open: overlay === "import",
    onClose: closeOverlay,
    onConfirm: () => {
      closeOverlay();
      flash("3 sections imported");
    }
  }), /*#__PURE__*/React.createElement(SearchPopup, {
    open: overlay === "search",
    onClose: closeOverlay
  }), /*#__PURE__*/React.createElement(DownloadPopup, {
    open: overlay === "download",
    onClose: closeOverlay,
    onAction: msg => flash(msg)
  }), /*#__PURE__*/React.createElement(SettingsPage, {
    open: overlay === "settings",
    onBack: closeOverlay,
    onAction: msg => {
      closeOverlay();
      flash(msg);
    }
  }), /*#__PURE__*/React.createElement(TrashPage, {
    open: overlay === "trash",
    onBack: closeOverlay,
    onAction: msg => {
      closeOverlay();
      flash(msg);
    }
  }), /*#__PURE__*/React.createElement(AboutPage, {
    open: overlay === "about",
    onBack: closeOverlay,
    onAction: msg => {
      closeOverlay();
      flash(msg);
    },
    onRate: () => setOverlay("rate")
  }), /*#__PURE__*/React.createElement(RatePopup, {
    open: overlay === "rate",
    onClose: closeOverlay,
    onSubmit: n => {
      closeOverlay();
      flash(n >= 4 ? "Thanks!" : "Feedback noted");
    }
  }), /*#__PURE__*/React.createElement(EmojiPicker, {
    open: !!emojiTarget,
    anchor: emojiTarget?.anchor,
    onClose: () => setEmojiTarget(null),
    onPick: e => {
      if (emojiTarget) setParentEmoji(m => ({
        ...m,
        [emojiTarget.id]: e
      }));
    }
  }), /*#__PURE__*/React.createElement(IconPicker, {
    open: !!iconTarget,
    anchor: iconTarget?.anchor,
    onClose: () => setIconTarget(null),
    onPick: ic => {
      if (iconTarget) setSectionIcons(m => ({
        ...m,
        [iconTarget.id]: ic
      }));
    }
  }));
}
(function mountTaskCluster() {
  const node = document.getElementById("tc-appRoot");
  if (!node) return;
  const ns = window.TaskClusterDesignSystem_1763fe || {};
  if (!ns.SectionCard || !ns.DateStrip || !ns.BottomBar || !ns.ContextMenu) return;
  if (!node.__tcRoot) node.__tcRoot = ReactDOM.createRoot(node);
  node.__tcRoot.render(/*#__PURE__*/React.createElement(TaskClusterScreen, null));
})();
})(); } catch (e) { __ds_ns.__errors.push({ path: "ui_kits/taskcluster/tcApp.jsx", error: String((e && e.message) || e) }); }

__ds_ns.Badge = __ds_scope.Badge;

__ds_ns.Button = __ds_scope.Button;

__ds_ns.Icon = __ds_scope.Icon;

__ds_ns.IconButton = __ds_scope.IconButton;

__ds_ns.Banner = __ds_scope.Banner;

__ds_ns.ContextMenu = __ds_scope.ContextMenu;

__ds_ns.Snackbar = __ds_scope.Snackbar;

__ds_ns.Toast = __ds_scope.Toast;

__ds_ns.Checkbox = __ds_scope.Checkbox;

__ds_ns.TextField = __ds_scope.TextField;

__ds_ns.TimeInput = __ds_scope.TimeInput;

__ds_ns.BottomBar = __ds_scope.BottomBar;

__ds_ns.DateStrip = __ds_scope.DateStrip;

__ds_ns.ParentSection = __ds_scope.ParentSection;

__ds_ns.SectionCard = __ds_scope.SectionCard;

__ds_ns.TaskRow = __ds_scope.TaskRow;

__ds_ns.TimePill = __ds_scope.TimePill;

})();
