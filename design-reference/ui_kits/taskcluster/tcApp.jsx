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
  Button, TextField, TimeInput, Badge, Icon, IconButton, TimePill,
  DateStrip, SectionCard, TaskRow, BottomBar, ParentSection,
  Banner, Toast, Snackbar, ContextMenu,
} = window.TaskClusterDesignSystem_1763fe;

/* ===================================================================
   Wrapper primitives: Page (full-screen pages), Popup (centered dialogs)
   =================================================================== */

function Page({ open, title, onBack, children }) {
  if (!open) return null;
  return (
    <div style={{
      position: "absolute", inset: 0, zIndex: 50,
      background: "var(--canvas)", display: "flex", flexDirection: "column",
      animation: "tcSlideIn var(--dur-base) var(--ease-standard)",
    }}>
      <header style={{
        display: "flex", alignItems: "center", gap: 6, padding: "14px 10px 12px",
        borderBottom: "1px solid var(--hairline)", flex: "none",
      }}>
        <button type="button" onClick={onBack} className="tc-pressable"
          style={{ display: "grid", placeItems: "center", width: 36, height: 36,
            border: "none", background: "transparent", borderRadius: 999, cursor: "pointer" }}>
          <Icon name="chevron-left" size={22} color="var(--ink-700)" />
        </button>
        <span style={{ flex: 1, font: "var(--weight-medium) 18px/1 var(--font-sans)", color: "var(--ink-900)" }}>{title}</span>
      </header>
      <div style={{ flex: 1, overflowY: "auto", padding: "16px 18px 40px" }}>{children}</div>
    </div>
  );
}

function Popup({ open, onClose, title, children, maxWidth = 380 }) {
  if (!open) return null;
  return (
    <div onClick={onClose} style={{
      position: "absolute", inset: 0, zIndex: 50,
      background: "var(--scrim)", display: "flex", alignItems: "center", justifyContent: "center",
      padding: 20, animation: "tcFade var(--dur-base) var(--ease-standard)",
    }}>
      <div onClick={(e) => e.stopPropagation()} style={{
        width: "100%", maxWidth, background: "var(--surface-raised)",
        borderRadius: 20, padding: "20px 22px 24px",
        boxShadow: "var(--shadow-overlay)",
        border: "1.5px solid var(--hairline-strong)",
        animation: "tcSpringIn var(--dur-base) cubic-bezier(0.175, 0.885, 0.32, 1.1)",
      }}>
        {title && (
          <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between", marginBottom: 16 }}>
            <span style={{ font: "var(--weight-medium) 18px/1.2 var(--font-sans)", color: "var(--ink-900)" }}>{title}</span>
            <IconButton label="Close" icon={<Icon name="x" size={20} />} size={32} onClick={onClose} />
          </div>
        )}
        {children}
      </div>
    </div>
  );
}



/* ===================================================================
   Calendar popup — full month/year navigation, any date
   =================================================================== */

const MONTH_NAMES = ["January","February","March","April","May","June","July","August","September","October","November","December"];
const WD_LABELS = ["M","T","W","T","F","S","S"];

function CalendarPopup({ open, onClose, todayYear, todayMonth, todayDate, selectedYear, selectedMonth, selectedDate, onPick }) {
  const [vY, setVY] = React.useState(selectedYear);
  const [vM, setVM] = React.useState(selectedMonth);
  const [yearPicker, setYearPicker] = React.useState(false);

  React.useEffect(() => { if (open) { setVY(selectedYear); setVM(selectedMonth); setYearPicker(false); } }, [open]);

  const dim = new Date(vY, vM + 1, 0).getDate();
  const firstWd = (new Date(vY, vM, 1).getDay() + 6) % 7; // 0=Mon

  const prev = () => { let m = vM - 1, y = vY; if (m < 0) { m = 11; y--; } setVM(m); setVY(y); };
  const next = () => { let m = vM + 1, y = vY; if (m > 11) { m = 0; y++; } setVM(m); setVY(y); };

  const cells = [];
  for (let i = 0; i < firstWd; i++) cells.push(null);
  for (let d = 1; d <= dim; d++) cells.push(d);

  // year picker: grid of years
  const baseYear = Math.floor(vY / 12) * 12;
  const years = [];
  for (let y = baseYear - 4; y <= baseYear + 7; y++) years.push(y);

  return (
    <Popup open={open} onClose={onClose} maxWidth={340}>
      {/* month/year header */}
      <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between", marginBottom: 14 }}>
        <button type="button" className="tc-pressable" onClick={yearPicker ? () => setVY(vY - 12) : prev}
          style={{ display: "grid", placeItems: "center", width: 32, height: 32, border: "none", background: "transparent", borderRadius: 999, cursor: "pointer" }}>
          <Icon name="chevron-left" size={20} color="var(--ink-600)" />
        </button>
        <button type="button" className="tc-pressable" onClick={() => setYearPicker(!yearPicker)}
          style={{ border: "none", background: "transparent", cursor: "pointer", padding: "4px 12px", borderRadius: 8,
            font: "var(--weight-medium) 16px/1 var(--font-sans)", color: "var(--ink-900)" }}>
          {yearPicker ? `${years[0]} – ${years[years.length-1]}` : `${MONTH_NAMES[vM]} ${vY}`}
        </button>
        <button type="button" className="tc-pressable" onClick={yearPicker ? () => setVY(vY + 12) : next}
          style={{ display: "grid", placeItems: "center", width: 32, height: 32, border: "none", background: "transparent", borderRadius: 999, cursor: "pointer" }}>
          <Icon name="chevron-right" size={20} color="var(--ink-600)" />
        </button>
      </div>

      {yearPicker ? (
        /* year grid */
        <div style={{ display: "grid", gridTemplateColumns: "repeat(4, 1fr)", gap: 6 }}>
          {years.map((y) => {
            const isCur = y === todayYear;
            const isSel = y === vY;
            return (
              <button key={y} type="button" className="tc-pressable"
                onClick={() => { setVY(y); setYearPicker(false); }}
                style={{ display: "flex", alignItems: "center", justifyContent: "center",
                  height: 40, border: "1px solid transparent", borderRadius: "var(--radius-sm)",
                  background: isSel ? "var(--ink-900)" : "transparent",
                  color: isSel ? "var(--surface)" : isCur ? "var(--blue)" : "var(--ink-900)",
                  font: "var(--weight-medium) 14px/1 var(--font-sans)", cursor: "pointer" }}>
                {y}
              </button>
            );
          })}
        </div>
      ) : (
        /* day grid */
        <>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(7, 1fr)", gap: 2, marginBottom: 4 }}>
            {WD_LABELS.map((w, i) => (
              <span key={i} style={{ textAlign: "center", font: "var(--weight-regular) 12px/1 var(--font-sans)", color: "var(--ink-400)", padding: "2px 0 6px" }}>{w}</span>
            ))}
          </div>
          <div style={{ display: "grid", gridTemplateColumns: "repeat(7, 1fr)", gap: 2 }}>
            {cells.map((d, i) => {
              if (d == null) return <span key={`b${i}`} />;
              const isToday = d === todayDate && vM === todayMonth && vY === todayYear;
              const isSel = d === selectedDate && vM === selectedMonth && vY === selectedYear;
              let bg = "transparent", color = "var(--ink-900)";
              if (isToday) { bg = "var(--blue)"; color = "var(--blue-on)"; }
              else if (isSel) { bg = "var(--ink-900)"; color = "var(--surface)"; }
              return (
                <button key={d} type="button" className="tc-pressable"
                  onClick={() => onPick({ year: vY, month: vM, date: d })}
                  style={{ display: "flex", alignItems: "center", justifyContent: "center",
                    aspectRatio: "1 / 1", border: "1px solid transparent",
                    borderRadius: "var(--radius-pill)", background: bg, color,
                    font: "var(--weight-medium) 14px/1 var(--font-sans)", cursor: "pointer" }}>
                  {d}
                </button>
              );
            })}
          </div>
        </>
      )}
    </Popup>
  );
}

/* ===================================================================
   Parent color palettes — each generates 3 hues from a base color.
   paper = parent fill, border = parent border, surface = section bg,
   surfaceBorder = section border, ink = text, meta = secondary text.
   =================================================================== */

const PALETTES = {
  default: { paper: "#E8E5DB", border: "#C8C4B8", surface: "#F5F3ED", surfaceBorder: "#DDD9CE", ink: "#2C2C2A", meta: "#6E6B63", label: "Default", swatch: "#C8C4B8" },
  lemon:   { paper: "#E4D24C", border: "#C0AE2C", surface: "#FAF6D0", surfaceBorder: "#DCD480", ink: "#3A3210", meta: "#6A5A18", label: "Lemon",   swatch: "#E4D24C" },
  orange:  { paper: "#E09848", border: "#C07828", surface: "#FAECD0", surfaceBorder: "#DCC480", ink: "#3A2410", meta: "#6A4418", label: "Orange",  swatch: "#E09848" },
  coral:   { paper: "#D87058", border: "#B85038", surface: "#F8E4DC", surfaceBorder: "#D8AC9C", ink: "#381410", meta: "#682C20", label: "Coral",   swatch: "#D87058" },
  red:     { paper: "#CC4848", border: "#A82828", surface: "#F8DCDC", surfaceBorder: "#D89898", ink: "#380C0C", meta: "#681C1C", label: "Red",     swatch: "#CC4848" },
  rose:    { paper: "#CC5880", border: "#A83860", surface: "#F8DCE8", surfaceBorder: "#D898B4", ink: "#380C24", meta: "#681C3C", label: "Rose",    swatch: "#CC5880" },
  pink:    { paper: "#C060B0", border: "#A04090", surface: "#F4DCF0", surfaceBorder: "#D098C4", ink: "#340C38", meta: "#5C1858", label: "Pink",    swatch: "#C060B0" },
  purple:  { paper: "#9060C0", border: "#7040A0", surface: "#ECDCF4", surfaceBorder: "#C098D4", ink: "#280C38", meta: "#481858", label: "Purple",  swatch: "#9060C0" },
  indigo:  { paper: "#6060CC", border: "#4040AC", surface: "#DCDCF8", surfaceBorder: "#9898D8", ink: "#0C0C38", meta: "#1C1C68", label: "Indigo",  swatch: "#6060CC" },
  blue:    { paper: "#4888C8", border: "#2868A8", surface: "#DCE8F4", surfaceBorder: "#98BCD4", ink: "#0C2438", meta: "#1C4058", label: "Blue",    swatch: "#4888C8" },
  teal:    { paper: "#40A8A8", border: "#208888", surface: "#DCF0F0", surfaceBorder: "#98CCD0", ink: "#0C3434", meta: "#1C5050", label: "Teal",    swatch: "#40A8A8" },
  green:   { paper: "#48A848", border: "#288828", surface: "#DCF0DC", surfaceBorder: "#98CC98", ink: "#0C240C", meta: "#1C401C", label: "Green",   swatch: "#48A848" },
  olive:   { paper: "#80A040", border: "#608020", surface: "#E8F0DC", surfaceBorder: "#B8CC98", ink: "#243410", meta: "#3C501C", label: "Olive",   swatch: "#80A040" },
  inverse: { paper: "#2C2C2A", border: "#444440", surface: "#3A3A38", surfaceBorder: "#505050", ink: "#F1EFE8", meta: "#A8A498", label: "Inverse", swatch: "#2C2C2A" },
};
const PALETTE_KEYS = ["default","lemon","orange","coral","red","rose","pink","purple","indigo","blue","teal","green","olive","inverse"];

/* Color picker popup — shown from parent long-press menu */
function ColorPicker({ open, onClose, current, onPick }) {
  return (
    <Popup open={open} onClose={onClose} title="Parent color" maxWidth={380}>
      <div style={{ display: "grid", gridTemplateColumns: "repeat(7, 1fr)", gap: 6, padding: "4px 0 8px" }}>
        {PALETTE_KEYS.map((k) => {
          const p = PALETTES[k];
          const sel = k === current;
          return (
            <button key={k} type="button" className="tc-pressable" onClick={() => { onPick(k); onClose(); }}
              style={{
                display: "flex", flexDirection: "column", alignItems: "center", gap: 5,
                border: "none", background: "transparent", cursor: "pointer", padding: "4px 0",
              }}>
              <span style={{
                width: 32, height: 32, borderRadius: 8,
                background: p.swatch,
                border: sel ? "2.5px solid var(--primary)" : k === "inverse" ? "1.5px solid var(--ink-300)" : "1.5px solid var(--hairline-strong)",
                boxShadow: sel ? "0 0 0 2px var(--primary-tint)" : "none",
              }} />
              <span style={{ font: "var(--weight-regular) 10px/1 var(--font-sans)", color: sel ? "var(--primary)" : "var(--ink-500)" }}>{p.label}</span>
            </button>
          );
        })}
      </div>
    </Popup>
  );
}

/* ===================================================================
   Emoji picker — origin-aware grid (Notion-style), for parents only.
   Icon picker — origin-aware grid of Lucide icons, for sections only.
   =================================================================== */

const EMOJI_CATS = [
  { label: "😊", emoji: [
    "😀","😃","😄","😁","😆","😅","🤣","😂","🙂","🙃","😉","😊","😇","🥰","😍","🤩","😘","😗","😚","😙",
    "🥲","😋","😛","😜","🤪","😝","🤑","🤗","🤭","🫢","🫣","🤫","🤔","🫡","🤐","🤨","😐","😑","😶","🫥",
    "😏","😒","🙄","😬","🤥","😌","😔","😪","🤤","😴","😷","🤒","🤕","🤢","🤮","🥵","🥶","🥴","😵","🤯",
    "🤠","🥳","🥸","😎","🤓","🧐","😕","🫤","😟","🙁","😮","😯","😲","😳","🥺","🥹","😦","😧","😨","😰",
    "😥","😢","😭","😱","😖","😣","😞","😓","😩","😫","🥱","😤","😡","😠","🤬","😈","👿","💀","☠️","💩",
    "🤡","👹","👺","👻","👽","👾","🤖","🎃","😺","😸","😹","😻","😼","😽","🙀","😿","😾",
  ]},
  { label: "👋", emoji: [
    "👋","🤚","🖐️","✋","🖖","🫱","🫲","🫳","🫴","👌","🤌","🤏","✌️","🤞","🫰","🤟","🤘","🤙","👈","👉",
    "👆","🖕","👇","☝️","🫵","👍","👎","✊","👊","🤛","🤜","👏","🙌","🫶","👐","🤲","🤝","🙏","✍️","💅",
    "🤳","💪","🦾","🦿","🦵","🦶","👂","🦻","👃","🧠","🫀","🫁","🦷","🦴","👀","👁️","👅","👄","🫦","💋",
    "👶","🧒","👦","👧","🧑","👱","👨","🧔","👩","🧓","👴","👵","🙍","🙎","🙅","🙆","💁","🙋","🧏","🙇",
    "🤦","🤷","👮","🕵️","💂","🥷","👷","🫅","🤴","👸","👳","👲","🧕","🤵","👰","🤰","🫄","🫃","🤱","👼",
    "🎅","🤶","🦸","🦹","🧙","🧚","🧛","🧜","🧝","🧞","🧟","🧌","💆","💇","🚶","🧍","🧎","🏃","💃","🕺",
    "👯","🧖","🧗","🤸","⛹️","🏋️","🚴","🚵","🤼","🤽","🤾","🤺","⛷️","🏂","🏄","🏊","🤿","🏇","🧘","👨‍💻",
  ]},
  { label: "📦", emoji: [
    "📋","📌","📎","✏️","📝","📁","📂","📊","📈","🗂️","🗄️","💼","🎒","🔑","🔒","🛠️","⚙️","🧰","📦","💡",
    "🔔","📱","💻","🖥️","🖨️","⌨️","🖱️","🖲️","💾","💿","📀","🎥","📷","📸","📹","📼","🔍","🔎","🕯️","💰",
    "💳","💎","⚖️","🧲","🔧","🔨","⛏️","🪛","🔩","🪜","🧱","⛓️","🧳","🛡️","🪃","🏹","🔮","🪄","🧿","🪬",
    "📿","💈","⚗️","🔭","🔬","🕳️","🩹","🩺","🩻","💊","💉","🩸","🧬","🦠","🧫","🧪","🌡️","🧹","🪣","🧺",
    "🧻","🚰","🚿","🛁","🛀","🧼","🪥","🪒","🧽","🪢","🧶","🧵","🪡","🧲","🪙","💵","💴","💶","💷","🪪",
  ]},
  { label: "🌿", emoji: [
    "🌱","🌿","🍃","🌸","🌻","🌙","⭐","🌈","☀️","🔥","💧","🌊","❄️","🍂","🌵","🌴","🦋","🐝","🐙","🦊",
    "🐸","🐶","🐱","🐭","🐹","🐰","🦊","🐻","🐼","🐨","🐯","🦁","🐮","🐷","🐽","🐵","🙈","🙉","🙊","🐒",
    "🐔","🐧","🐦","🐤","🐣","🦆","🦅","🦉","🦇","🐺","🐗","🐴","🦄","🐝","🪱","🐛","🦋","🐌","🐞","🐜",
    "🪲","🪳","🦟","🦗","🕷️","🕸️","🦂","🐢","🐍","🦎","🦖","🦕","🐙","🦑","🦐","🦞","🦀","🐡","🐠","🐟",
    "🐬","🐳","🐋","🦈","🪸","🐊","🐅","🐆","🦓","🦍","🦧","🐘","🦛","🦏","🐪","🐫","🦒","🦘","🦬","🐃",
    "🌲","🌳","🌴","🌵","🌾","🌿","☘️","🍀","🍁","🍂","🍃","🪹","🪺","🍄","🌰","🦞","🌺","🌼","🌷","🪻",
  ]},
  { label: "☕", emoji: [
    "☕","🍵","🧃","🍎","🍕","🍔","🥗","🍣","🧁","🍩","🎂","🍫","🥐","🍳","🥑","🍉","🫐","🍋","🥝","🧇",
    "🍿","🥤","🍺","🍷","🍸","🍹","🧊","🫖","🥛","🍼","🥂","🍾","🧉","🫗","🥃","🍶","🍭","🍬","🍮","🍰",
    "🥧","🍦","🍨","🍧","🎂","🍡","🍘","🍙","🍚","🍛","🍜","🍝","🍞","🥖","🥨","🧀","🥚","🍗","🍖","🦴",
    "🌭","🍟","🥙","🌮","🌯","🫔","🥘","🫕","🥫","🧆","🥜","🌰","🍠","🥔","🧅","🥕","🌽","🫑","🥒","🥬",
  ]},
  { label: "✈️", emoji: [
    "🏠","🏢","🏗️","🏖️","🗺️","✈️","🚀","🚗","🚲","🛤️","⛺","🎯","🏆","🎮","🎵","🎬","📸","🎨","🧩","♟️",
    "🎲","🎰","🎳","🎪","🎭","🎷","🎸","🎹","🥁","🎺","🎻","🪕","🎤","🎧","📻","🎼","🎶","🎙️","📺","📽️",
    "🚂","🚃","🚄","🚅","🚆","🚇","🚈","🚉","🚊","🚝","🚞","🚋","🚌","🚍","🚎","🚐","🚑","🚒","🚓","🚔",
    "🚕","🚖","🚗","🚘","🚙","🛻","🚚","🚛","🚜","🏎️","🏍️","🛵","🛺","🚏","🛣️","🛤️","🛞","⛽","🛞","🚨",
    "🚥","🚦","🛑","🚧","⚓","🛟","⛵","🛶","🚤","🛳️","⛴️","🛥️","🚢","🛩️","🛫","🛬","🪂","💺","🚁","🚠",
  ]},
  { label: "❤️", emoji: [
    "✅","❌","⚡","💫","🔴","🟠","🟡","🟢","🔵","🟣","⚪","⚫","❤️","🧡","💛","💚","💙","💜","🖤","🤍",
    "💯","💢","💥","💦","💨","🕳️","💣","💬","🗨️","🗯️","💭","💤","🔶","🔷","🔸","🔹","▪️","▫️","◾","◽",
    "◼️","◻️","🟥","🟧","🟨","🟩","🟦","🟪","⬛","⬜","🔺","🔻","🔲","🔳","🏁","🚩","🎌","🏴","🏳️","🏳️‍🌈",
    "♈","♉","♊","♋","♌","♍","♎","♏","♐","♑","♒","♓","⛎","🔀","🔁","🔂","▶️","⏩","⏭️","⏯️",
    "◀️","⏪","⏮️","🔼","⏫","🔽","⏬","⏸️","⏹️","⏺️","⏏️","🔃","🔄","🔅","🔆","📶","📳","📴","♀️","♂️",
  ]},
];

function EmojiPicker({ open, anchor, onClose, onPick }) {
  const [cat, setCat] = React.useState(0);
  if (!open || !anchor) return null;

  const W = 300, H = 380;
  const fx = anchor.x, fy = anchor.y;
  let left = fx + 8, top = fy + 8;
  if (left + W > 430) left = fx - W - 8;
  if (top + H > 900) top = fy - H - 8;
  if (left < 4) left = 4;
  if (top < 4) top = 4;

  return (
    <div onClick={onClose} style={{ position: "absolute", inset: 0, zIndex: 52 }}>
      <div onClick={(e) => e.stopPropagation()} style={{
        position: "absolute", left, top, width: W,
        background: "var(--surface-raised)", border: "1.5px solid var(--hairline-strong)",
        borderRadius: 16, boxShadow: "var(--shadow-overlay)",
        animation: "tcSpringIn 180ms cubic-bezier(0.175, 0.885, 0.32, 1.1)",
        overflow: "hidden",
      }}>
        {/* category tabs */}
        <div style={{ display: "flex", gap: 0, borderBottom: "1px solid var(--hairline)", padding: "0 4px" }}>
          {EMOJI_CATS.map((c, i) => (
            <button key={i} type="button" onClick={() => setCat(i)} className="tc-pressable"
              style={{ flex: 1, padding: "10px 0", border: "none", background: "transparent", cursor: "pointer",
                fontSize: 16, lineHeight: 1,
                borderBottom: i === cat ? "2px solid var(--primary)" : "2px solid transparent",
                opacity: i === cat ? 1 : 0.5,
              }}>{c.label}</button>
          ))}
        </div>
        {/* emoji grid — scrollable */}
        <div style={{ display: "grid", gridTemplateColumns: "repeat(7, 1fr)", gap: 2, padding: "8px 6px", maxHeight: 290, overflowY: "auto" }}>
          {EMOJI_CATS[cat].emoji.map((e, i) => (
            <button key={i} type="button" className="tc-pressable" onClick={() => { onPick(e); onClose(); }}
              style={{ display: "grid", placeItems: "center", width: 38, height: 38, border: "none",
                background: "transparent", borderRadius: 8, cursor: "pointer", fontSize: 22, lineHeight: 1 }}>
              {e}
            </button>
          ))}
        </div>
        {/* remove option */}
        <div style={{ borderTop: "1px solid var(--hairline)", padding: "6px 10px" }}>
          <button type="button" className="tc-pressable" onClick={() => { onPick(null); onClose(); }}
            style={{ display: "flex", alignItems: "center", gap: 8, width: "100%", padding: "8px 6px",
              border: "none", background: "transparent", borderRadius: 8, cursor: "pointer",
              font: "var(--weight-regular) 13px/1 var(--font-sans)", color: "var(--ink-500)" }}>
            <Icon name="x" size={14} color="var(--ink-400)" /> Remove emoji
          </button>
        </div>
      </div>
    </div>
  );
}

const SECTION_ICONS = [
  "star","flag","bookmark","zap","target","briefcase","coffee","map-pin","palette",
  "clock","calendar","search","settings","heart","home","file","list","info",
  "check","plus","download","share","trash","pencil","copy",
];

function IconPicker({ open, anchor, onClose, onPick }) {
  if (!open || !anchor) return null;
  const W = 240, H = 240;
  let left = anchor.x + 8, top = anchor.y + 8;
  if (left + W > 430) left = anchor.x - W - 8;
  if (top + H > 900) top = anchor.y - H - 8;
  if (left < 4) left = 4;
  if (top < 4) top = 4;

  return (
    <div onClick={onClose} style={{ position: "absolute", inset: 0, zIndex: 52 }}>
      <div onClick={(e) => e.stopPropagation()} style={{
        position: "absolute", left, top, width: W,
        background: "var(--surface-raised)", border: "1.5px solid var(--hairline-strong)",
        borderRadius: 16, boxShadow: "var(--shadow-overlay)", padding: "12px 8px",
        animation: "tcSpringIn 180ms cubic-bezier(0.175, 0.885, 0.32, 1.1)",
      }}>
        <div style={{ font: "var(--weight-medium) 12px/1 var(--font-sans)", color: "var(--ink-500)", padding: "0 6px 8px" }}>Section icon</div>
        <div style={{ display: "grid", gridTemplateColumns: "repeat(5, 1fr)", gap: 4 }}>
          {SECTION_ICONS.map((ic) => (
            <button key={ic} type="button" className="tc-pressable" onClick={() => { onPick(ic); onClose(); }}
              style={{ display: "grid", placeItems: "center", width: 40, height: 40, border: "none",
                background: "transparent", borderRadius: 8, cursor: "pointer" }}>
              <Icon name={ic} size={18} color="var(--ink-600)" />
            </button>
          ))}
        </div>
        <div style={{ borderTop: "1px solid var(--hairline)", marginTop: 8, paddingTop: 6 }}>
          <button type="button" className="tc-pressable" onClick={() => { onPick(null); onClose(); }}
            style={{ display: "flex", alignItems: "center", gap: 8, width: "100%", padding: "8px 6px",
              border: "none", background: "transparent", borderRadius: 8, cursor: "pointer",
              font: "var(--weight-regular) 13px/1 var(--font-sans)", color: "var(--ink-500)" }}>
            <Icon name="x" size={14} color="var(--ink-400)" /> Remove icon
          </button>
        </div>
      </div>
    </div>
  );
}

/* ===================================================================
   Popup overlays: AddChooser, NameForm
   =================================================================== */

function AddChooser({ open, onClose, onPick, onImport }) {
  const opts = [
    { key: "parent",  icon: "chevron-down", title: "New parent",  sub: "A container that holds sections" },
    { key: "section", icon: "plus",         title: "New section", sub: "A group of tasks" },
    { key: "task",    icon: "check",        title: "New task",    sub: "A single thing to do" },
  ];
  const row = { display: "flex", alignItems: "center", gap: 12, padding: "10px 8px", border: "none", background: "transparent", borderRadius: 10, cursor: "pointer", textAlign: "left" };
  const chip = { display: "grid", placeItems: "center", width: 36, height: 36, flex: "none", borderRadius: 8, background: "var(--surface-sunken)" };
  return (
    <Popup open={open} onClose={onClose} title="Add" maxWidth={360}>
      <div style={{ display: "grid", gap: 2 }}>
        {opts.map((o) => (
          <button key={o.key} type="button" className="tc-pressable" onClick={() => onPick(o.key)} style={{ ...row, width: "100%" }}>
            <span style={chip}><Icon name={o.icon} size={18} color="var(--ink-600)" /></span>
            <span style={{ flex: 1, minWidth: 0 }}>
              <span style={{ display: "block", font: "var(--weight-medium) 15px/1.2 var(--font-sans)", color: "var(--ink-900)" }}>{o.title}</span>
              <span style={{ display: "block", marginTop: 2, font: "var(--weight-regular) 13px/1.3 var(--font-sans)", color: "var(--ink-500)" }}>{o.sub}</span>
            </span>
          </button>
        ))}
      </div>
      <div style={{ height: 1, background: "var(--hairline)", margin: "8px 4px" }} />
      <button type="button" className="tc-pressable" onClick={onImport} style={{ ...row, width: "100%" }}>
        <span style={chip}><Icon name="import" size={18} color="var(--ink-600)" /></span>
        <span style={{ flex: 1, minWidth: 0 }}>
          <span style={{ display: "block", font: "var(--weight-medium) 15px/1.2 var(--font-sans)", color: "var(--ink-900)" }}>Import from .txt</span>
          <span style={{ display: "block", marginTop: 2, font: "var(--weight-regular) 13px/1.3 var(--font-sans)", color: "var(--ink-500)" }}>Build from a plain-text plan</span>
        </span>
      </button>
    </Popup>
  );
}

function NameForm({ open, kind, onClose, onSave }) {
  const [name, setName] = React.useState("");
  React.useEffect(() => { if (open) setName(""); }, [open]);
  const label = kind === "parent" ? "parent" : "section";
  return (
    <Popup open={open} onClose={onClose} title={`New ${label}`} maxWidth={360}>
      <TextField label="Title" value={name} onChange={setName} placeholder={kind === "parent" ? "e.g. Work" : "e.g. Launch prep"} />
      <div style={{ marginTop: 18 }}>
        <Button variant="primary" full onClick={() => onSave(name || `Untitled ${label}`)}>Add {label}</Button>
      </div>
    </Popup>
  );
}

/* ===================================================================
   Popup overlays: Form, Import, Search, Download, Rate
   =================================================================== */

function FormPopup({ open, mode = "create", draft, onClose, onSave }) {
  const [title, setTitle] = React.useState(draft?.title || "");
  const [desc, setDesc] = React.useState(draft?.desc || "");
  const [noLimit, setNoLimit] = React.useState(false);
  const [tmode, setTmode] = React.useState("Date & time");
  const [tval, setTval] = React.useState("Jun 12, 2026");
  const [ttime, setTtime] = React.useState("12:59 PM");
  const [tdays, setTdays] = React.useState("2");
  const [thours, setThours] = React.useState("0");
  React.useEffect(() => { if (open) { setTitle(draft?.title || ""); setDesc(draft?.desc || ""); setNoLimit(false); } }, [open, draft]);
  return (
    <Popup open={open} onClose={onClose} title={mode === "edit" ? "Edit task" : "New task"} maxWidth={400}>
      <div style={{ display: "grid", gap: 16 }}>
        <TextField label="Title" value={title} onChange={setTitle} placeholder="What needs doing?" />
        <TextField label="Description" value={desc} onChange={setDesc} multiline rows={2} placeholder="Optional — truncates at two lines" />
        <TimeInput label="Deadline" mode={tmode}
          noLimit={noLimit} onNoLimitChange={setNoLimit}
          value={tval} time={ttime} days={tdays} hours={thours}
          onModeChange={setTmode} onValueChange={setTval} onTimeChange={setTtime}
          onDaysChange={setTdays} onHoursChange={setThours} />
      </div>
      <div style={{ marginTop: 20 }}>
        <Button variant="primary" full onClick={() => onSave({ title: title || "Untitled task", desc })}>
          {mode === "edit" ? "Save changes" : "Add task"}
        </Button>
      </div>
    </Popup>
  );
}

function ImportPopup({ open, onClose, onConfirm }) {
  const tree = [
    { t: "Q3 planning", kids: ["Define OKRs", "Draft roadmap", "Budget review"] },
    { t: "Reading list", kids: ["Design systems handbook", "The Timeless Way of Building"] },
    { t: "Home", kids: ["Renew lease", "Fix the shelf"] },
  ];
  const tasks = tree.reduce((n, s) => n + s.kids.length, 0);
  /* miniature section card */
  const mini = (s) => (
    <div key={s.t} style={{
      background: "var(--surface)", border: "1px solid var(--hairline)",
      borderRadius: 10, padding: "8px 12px",
    }}>
      <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between", marginBottom: 4 }}>
        <span style={{ font: "var(--weight-medium) 13px/1.2 var(--font-sans)", color: "var(--ink-900)" }}>{s.t}</span>
        <span style={{ font: "var(--weight-medium) 11px/1 var(--font-sans)", color: "var(--ink-400)" }}>{s.kids.length}</span>
      </div>
      <div style={{ display: "flex", flexDirection: "column", gap: 2 }}>
        {s.kids.map((k, j) => (
          <div key={j} style={{ display: "flex", alignItems: "center", gap: 6 }}>
            <span style={{ width: 12, height: 12, borderRadius: 3, border: "1.5px solid var(--ink-300)", flex: "none" }} />
            <span className="tc-truncate-1" style={{ font: "var(--weight-regular) 12px/1.3 var(--font-sans)", color: "var(--ink-600)" }}>{k}</span>
          </div>
        ))}
      </div>
    </div>
  );
  return (
    <Popup open={open} onClose={onClose} title="Import preview" maxWidth={400}>
      {/* miniature preview */}
      <div style={{
        background: "var(--parent-fill)", border: "1.5px solid var(--hairline-strong)",
        borderRadius: 14, padding: "8px 8px 10px",
        display: "flex", flexDirection: "column", gap: 6,
        maxHeight: 280, overflowY: "auto",
      }}>
        {tree.map(mini)}
      </div>
      {/* grey tags below preview */}
      <div style={{ display: "flex", gap: 8, marginTop: 12, flexWrap: "wrap" }}>
        <Badge>{tree.length} sections</Badge>
        <Badge>{tasks} tasks</Badge>
        <Badge>from plan.txt</Badge>
      </div>
      <div style={{ display: "flex", gap: 10, marginTop: 16 }}>
        <Button variant="secondary" full onClick={onClose}>Cancel</Button>
        <Button variant="primary" full onClick={onConfirm}>Import {tasks}</Button>
      </div>
    </Popup>
  );
}

function SearchPopup({ open, onClose }) {
  const [q, setQ] = React.useState("re");
  const all = [
    { crumb: "Daily · Morning", title: "Read for 20 minutes", status: "on-track", time: "3h" },
    { crumb: "Work · Launch prep", title: "Write release notes", status: "calm" },
    { crumb: "Work · Launch prep", title: "Review the import flow", status: "due", time: "9m" },
    { crumb: "Work · Errands", title: "Renew the lease", status: "overdue", time: "−1d 2h" },
  ];
  const results = all.filter((r) => (r.title + r.crumb).toLowerCase().includes(q.toLowerCase()));
  return (
    <Popup open={open} onClose={onClose} title="Search" maxWidth={400}>
      <div style={{ display: "flex", alignItems: "center", gap: 10, height: 44, padding: "0 12px",
        background: "var(--surface)", border: "1px solid var(--hairline-strong)", borderRadius: "var(--radius-sm)", marginBottom: 12 }}>
        <Icon name="search" size={18} color="var(--ink-500)" />
        <input value={q} onChange={(e) => setQ(e.target.value)} placeholder="Search every task" autoFocus
          style={{ flex: 1, border: "none", outline: "none", background: "transparent",
            font: "var(--weight-regular) 15px/1 var(--font-sans)", color: "var(--ink-900)" }} />
      </div>
      <div style={{ display: "grid", gap: 2, maxHeight: 300, overflowY: "auto" }}>
        {results.length === 0 && (
          <div style={{ padding: "24px 0", textAlign: "center", font: "var(--weight-regular) 14px/1.4 var(--font-sans)", color: "var(--ink-400)" }}>
            Nothing matches "{q}"
          </div>
        )}
        {results.map((r, i) => (
          <div key={i} className="tc-pressable" style={{ display: "flex", alignItems: "center", gap: 12, padding: "10px 6px", borderRadius: 8, cursor: "pointer" }}>
            <div style={{ flex: 1, minWidth: 0 }}>
              <div style={{ font: "var(--weight-regular) 11px/1.2 var(--font-sans)", color: "var(--ink-400)", marginBottom: 2 }}>{r.crumb}</div>
              <div className="tc-truncate-1" style={{ font: "var(--weight-regular) 15px/1.3 var(--font-sans)", color: "var(--ink-900)" }}>{r.title}</div>
            </div>
            <TimePill status={r.status} label={r.time} />
          </div>
        ))}
      </div>
    </Popup>
  );
}

/* --- Reusable page rows --- */
function PageRow({ title, value, danger, last, onClick }) {
  return (
    <button type="button" className="tc-pressable" onClick={onClick}
      style={{ display: "flex", alignItems: "center", gap: 12, width: "100%",
        padding: "14px 4px", border: "none", background: "transparent",
        borderBottom: last ? "none" : "1px solid var(--hairline)", cursor: "pointer", textAlign: "left" }}>
      <span style={{ flex: 1, minWidth: 0, font: "var(--weight-regular) 15px/1.3 var(--font-sans)",
        color: danger ? "var(--red)" : "var(--ink-900)" }}>{title}</span>
      {value && <span style={{ flex: "none", font: "var(--weight-regular) 14px/1 var(--font-sans)", color: "var(--ink-500)" }}>{value}</span>}
      <Icon name="chevron-right" size={16} color="var(--ink-300)" />
    </button>
  );
}

function PageGroup({ title, children }) {
  return (
    <div style={{ marginBottom: 18 }}>
      <div style={{ padding: "0 4px 6px", font: "var(--weight-medium) 12px/1 var(--font-sans)",
        color: "var(--ink-500)", letterSpacing: "0.04em", textTransform: "uppercase" }}>{title}</div>
      <div>{children}</div>
    </div>
  );
}

function SettingsPage({ open, onBack, onAction }) {
  return (
    <Page open={open} title="Settings" onBack={onBack}>
      <PageGroup title="Appearance">
        <PageRow title="Theme" value="System" onClick={() => onAction("Theme")} last />
      </PageGroup>
      <PageGroup title="Tasks">
        <PageRow title="Default priority" value="None" onClick={() => onAction("Default priority")} />
        <PageRow title="Default sort order" value="Date added" onClick={() => onAction("Sort")} />
        <PageRow title="Auto-hide completed" value="Off" onClick={() => onAction("Auto-hide")} />
        <PageRow title="Clear all completed" danger onClick={() => onAction("Clear completed")} last />
      </PageGroup>
      <PageGroup title="Notifications">
        <PageRow title="Due-date reminders" value="On" onClick={() => onAction("Reminders")} />
        <PageRow title="Default reminder time" value="30 min before" onClick={() => onAction("Default reminder")} last />
      </PageGroup>
      <PageGroup title="Trash">
        <PageRow title="Auto-delete trashed items" value="30 days" onClick={() => onAction("Auto-delete")} last />
      </PageGroup>
      <PageGroup title="Data">
        <PageRow title="Export tasks" value=".csv · .json" onClick={() => onAction("Export")} />
        <PageRow title="Import tasks" value=".csv · .json" onClick={() => onAction("Import")} last />
      </PageGroup>
      <PageGroup title="Legal">
        <PageRow title="Privacy Policy" onClick={() => onAction("Privacy")} />
        <PageRow title="Terms of Service" onClick={() => onAction("Terms")} />
        <PageRow title="End User License Agreement" onClick={() => onAction("EULA")} />
        <PageRow title="Data Deletion Policy" onClick={() => onAction("Data deletion")} last />
      </PageGroup>
      <PageGroup title="Danger zone">
        <PageRow title="Reset to default settings" danger onClick={() => onAction("Reset")} last />
      </PageGroup>
    </Page>
  );
}

function TrashPage({ open, onBack, onAction }) {
  const items = [
    { id: "t1", title: "Old grocery list", due: "—", deleted: "Jun 4", days: 27 },
    { id: "t2", title: "Email Sarah back", due: "Jun 2", deleted: "Jun 5", days: 28 },
    { id: "t3", title: "Renew gym pass", due: "May 30", deleted: "May 31", days: 23 },
  ];
  return (
    <Page open={open} title="Trash" onBack={onBack}>
      <div style={{ marginBottom: 14 }}>
        <Banner variant="read-only">Items are permanently deleted after 30 days.</Banner>
      </div>
      <div style={{ display: "grid", gap: 1 }}>
        {items.map((it, i) => (
          <div key={it.id} style={{ display: "flex", alignItems: "flex-start", gap: 12, padding: "14px 4px",
            borderBottom: i === items.length - 1 ? "none" : "1px solid var(--hairline)" }}>
            <div style={{ flex: 1, minWidth: 0 }}>
              <div className="tc-truncate-1" style={{ font: "var(--weight-regular) 16px/1.3 var(--font-sans)", color: "var(--ink-900)" }}>{it.title}</div>
              <div style={{ marginTop: 4, font: "var(--weight-regular) 12px/1.4 var(--font-sans)", color: "var(--ink-500)" }}>
                Due {it.due} · Deleted {it.deleted} · {it.days}d left
              </div>
            </div>
            <button type="button" className="tc-pressable" onClick={() => onAction(`Restored ${it.title}`)}
              style={{ flex: "none", height: 30, padding: "0 12px", border: "1px solid var(--hairline-strong)",
                background: "var(--surface)", borderRadius: 999, cursor: "pointer",
                font: "var(--weight-medium) 13px/1 var(--font-sans)", color: "var(--ink-700)" }}>Restore</button>
          </div>
        ))}
      </div>
      <div style={{ display: "flex", gap: 10, marginTop: 18 }}>
        <Button variant="secondary" full onClick={() => onAction("All restored")}>Restore all</Button>
        <Button variant="primary" full onClick={() => onAction("Trash emptied")}>Empty trash</Button>
      </div>
    </Page>
  );
}

function AboutPage({ open, onBack, onAction, onRate }) {
  return (
    <Page open={open} title="About" onBack={onBack}>
      <div style={{ display: "flex", alignItems: "center", gap: 14, padding: "4px 4px 18px" }}>
        <span style={{ display: "grid", placeItems: "center", width: 56, height: 56, borderRadius: 16,
          background: "var(--ink-900)", color: "var(--surface)",
          font: "var(--weight-medium) 26px/1 var(--font-sans)", letterSpacing: "-0.02em" }}>T</span>
        <div style={{ flex: 1, minWidth: 0 }}>
          <div style={{ font: "var(--weight-medium) 18px/1.2 var(--font-sans)", color: "var(--ink-900)" }}>Task Cluster</div>
          <div style={{ marginTop: 2, font: "var(--weight-regular) 13px/1.4 var(--font-sans)", color: "var(--ink-500)" }}>
            v2.2 · Build 22 · A calm, sectioned planner.
          </div>
        </div>
      </div>
      <PageGroup title="Made by">
        <PageRow title="Sumit Bhide" value="@sumitbhide" onClick={() => onAction("Profile")} last />
      </PageGroup>
      <PageGroup title="Help us">
        <PageRow title="Rate this app" onClick={onRate} />
        <PageRow title="Share this app" onClick={() => onAction("Share sheet opened")} />
        <PageRow title="Send feedback" onClick={() => onAction("Email composer opened")} last />
      </PageGroup>
      <PageGroup title="Legal">
        <PageRow title="Privacy Policy" onClick={() => onAction("Privacy")} />
        <PageRow title="Terms of Service" onClick={() => onAction("Terms")} />
        <PageRow title="End User License Agreement" onClick={() => onAction("EULA")} />
        <PageRow title="Data Deletion Policy" onClick={() => onAction("Data deletion")} />
        <PageRow title="Open-source licenses" onClick={() => onAction("Licenses")} last />
      </PageGroup>
    </Page>
  );
}

function DownloadPopup({ open, onClose, onAction }) {
  const row = (icon, label) => (
    <button type="button" className="tc-pressable" onClick={() => { onClose(); onAction(label); }}
      style={{ display: "flex", alignItems: "center", gap: 12, width: "100%",
        padding: "12px 8px", border: "none", background: "transparent",
        borderRadius: 10, cursor: "pointer", textAlign: "left" }}>
      <Icon name={icon} size={18} color="var(--ink-500)" />
      <span style={{ font: "var(--weight-regular) 15px/1 var(--font-sans)", color: "var(--ink-900)" }}>{label}</span>
    </button>
  );
  return (
    <Popup open={open} onClose={onClose} title="Download" maxWidth={320}>
      {row("file", "Download SKILL.md")}
      {row("copy", "Copy as prompt")}
    </Popup>
  );
}

function RatePopup({ open, onClose, onSubmit }) {
  const [stars, setStars] = React.useState(0);
  React.useEffect(() => { if (open) setStars(0); }, [open]);
  const star = (n) => (
    <button key={n} type="button" onClick={() => setStars(n)} className="tc-pressable"
      style={{ display: "grid", placeItems: "center", width: 44, height: 44, border: "none", background: "transparent", cursor: "pointer" }}>
      <svg width="30" height="30" viewBox="0 0 24 24">
        <path d="M12 2.6l2.92 5.92 6.53.95-4.72 4.6 1.12 6.5L12 17.5l-5.85 3.07 1.12-6.5L2.55 9.47l6.53-.95L12 2.6z"
          fill={n <= stars ? "var(--blue)" : "transparent"} stroke="var(--ink-400)" strokeWidth="1.4" strokeLinejoin="round" />
      </svg>
    </button>
  );
  return (
    <Popup open={open} onClose={onClose} title="Rate this app" maxWidth={360}>
      <div style={{ padding: "0 4px 8px", font: "var(--weight-regular) 15px/1.5 var(--font-sans)", color: "var(--ink-600)" }}>
        If Task Cluster has earned its place on your home screen, a quick rating helps other focused people find it.
      </div>
      <div style={{ display: "flex", justifyContent: "center", gap: 4, padding: "10px 0 4px" }}>
        {[1,2,3,4,5].map(star)}
      </div>
      <div style={{ marginTop: 14 }}>
        <Button variant="primary" full disabled={stars === 0} onClick={() => onSubmit(stars)}>
          {stars >= 4 ? "Rate on Play Store" : stars === 0 ? "Tap a star" : "Send feedback"}
        </Button>
      </div>
    </Popup>
  );
}

/* ===================================================================
   Page overlays: Settings, Trash, About
   =================================================================== */

/* ===================================================================
   The single screen + state machine
   =================================================================== */

const TODAY_Y = 2026; const TODAY_M = 5; /* June = 5 (0-indexed) */ const TODAY_D = 11;
const WEEKDAYS = ["Mon","Tue","Wed","Thu","Fri","Sat","Sun"];
const MONTH_SHORT = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];

function weekdayOf(y, m, d) { return (new Date(y, m, d).getDay() + 6) % 7; } // 0=Mon
function labelOf(y, m, d) { return WEEKDAYS[weekdayOf(y, m, d)]; }
function weekOf(y, m, d) {
  const wd = weekdayOf(y, m, d);
  const dim = new Date(y, m + 1, 0).getDate();
  const monday = d - wd;
  const out = [];
  for (let i = 0; i < 7; i++) {
    const dd = monday + i;
    if (dd < 1 || dd > dim) out.push({ key: `p${i}`, placeholder: true });
    else out.push({ key: `${y}-${m}-${dd}`, weekday: WEEKDAYS[i], date: dd });
  }
  return out;
}
function sameDay(a, b) { return a.y === b.y && a.m === b.m && a.d === b.d; }
function dayCompare(a, b) { return a.y !== b.y ? a.y - b.y : a.m !== b.m ? a.m - b.m : a.d - b.d; }

const DATA = {
  daily: [
    { id: "morning", title: "Morning", icon: "coffee", tasks: [
      { id: "m1", title: "Inbox to zero", done: true },
      { id: "m2", title: "Plan the day", desc: "Pick the three things that actually matter.", status: "on-track", time: "45m" },
      { id: "m3", title: "Read for 20 minutes", status: "calm" },
    ]},
    { id: "wind", title: "Wind down", icon: "clock", tasks: [
      { id: "w1", title: "Tidy the desk", status: "calm" },
      { id: "w2", title: "Set tomorrow's top task", status: "on-track", time: "8h" },
    ]},
  ],
  tree: [
    { type: "parent", id: "work", title: "Work", emoji: "💼", sections: [
      { id: "launch", title: "Launch prep", icon: "zap", status: "on-track", time: "2h 40m", tasks: [
        { id: "l1", title: "Write release notes", done: true, desc: "Summarise the 2.2 changes for the changelog." },
        { id: "l2", title: "Final QA pass on the import flow", status: "on-track", time: "2h 40m" },
        { id: "l3", title: "Email the beta list", status: "overdue", time: "−2h 14m", desc: "Include the opt-out link and the new screenshots." },
      ]},
      { id: "errands", title: "Errands", icon: "map-pin", status: "due", time: "9m", tasks: [
        { id: "e1", title: "Renew the lease", status: "due", time: "9m" },
        { id: "e2", title: "Pick up dry cleaning", status: "close", time: "48m" },
        { id: "e3", title: "Buy a birthday card", done: true },
      ]},
      { id: "design", title: "Design review", icon: "palette", status: "on-track", time: "4h", tasks: [
        { id: "d1", title: "Prepare mockups for v2.3" },
        { id: "d2", title: "Colour palette audit", done: true },
      ]},
      { id: "hiring", title: "Hiring", icon: "briefcase", tasks: [
        { id: "h1", title: "Review portfolio submissions" },
        { id: "h2", title: "Schedule interviews" },
      ]},
      { id: "infra", title: "Infrastructure", icon: "settings", status: "calm", tasks: [
        { id: "i1", title: "Upgrade CI pipeline" },
        { id: "i2", title: "Rotate API keys" },
      ]},
    ]},
    { type: "section", id: "fresh", title: "Side project", tasks: [] },
  ],
};

function allSections(data) {
  const out = [...data.daily];
  data.tree.forEach((n) => n.type === "parent" ? out.push(...n.sections) : out.push(n));
  return out;
}

function TaskClusterScreen() {
  const today = { y: TODAY_Y, m: TODAY_M, d: TODAY_D };
  const [sel, setSel] = React.useState({ ...today });
  const [page, setPage] = React.useState("home"); // home | tasks
  const [mode, setMode] = React.useState("home");
  const [overlay, setOverlay] = React.useState(null);
  const [addKind, setAddKind] = React.useState("section");
  const [parentEmoji, setParentEmoji] = React.useState({ daily: "☀️", work: "💼" });
  const [sectionIcons, setSectionIcons] = React.useState({ morning: "coffee", wind: "clock", launch: "zap", errands: "map-pin", design: "palette", hiring: "briefcase", infra: "settings" });
  const [emojiTarget, setEmojiTarget] = React.useState(null);
  const [iconTarget, setIconTarget] = React.useState(null);
  const [favourites, setFavourites] = React.useState({ work: true });
  const [collapsedParents, setCollapsedParents] = React.useState({ work: true });
  const [ctx, setCtx] = React.useState({ open: false, anchor: null, items: [], title: null });
  const closeCtx = () => setCtx((c) => ({ ...c, open: false }));
  const [done, setDone] = React.useState(() => {
    const m = {}; allSections(DATA).forEach((s) => s.tasks.forEach((t) => { m[t.id] = !!t.done; })); return m;
  });
  /* All sections start collapsed; Daily sections are also collapsed by default */
  const [collapsed, setCollapsed] = React.useState(() => {
    const m = {}; allSections(DATA).forEach((s) => { m[s.id] = true; }); return m;
  });
  const [toast, setToast] = React.useState(null);
  const [snack, setSnack] = React.useState(null);

  const isToday = sameDay(sel, today);
  const cmp = dayCompare(sel, today);
  const view = isToday ? "today" : (cmp < 0 ? "past" : "future");
  const readOnly = view === "past";
  const planning = view === "future";

  const flash = (msg) => { setToast(msg); setTimeout(() => setToast(null), 2000); };
  const onBar = (m) => {
    if (m === "home" || m === "tasks") { setPage(m); setMode(m); setOverlay(null); }
    else if (m === "add") { setMode(m); setOverlay("addchoose"); }
    else if (m === "search") { setMode(m); setOverlay("search"); }
    else setOverlay(null);
  };
  const closeOverlay = () => { setOverlay(null); setMode(page); };
  const pickAdd = (kind) => { if (kind === "task") setOverlay("form"); else { setAddKind(kind); setOverlay("nameform"); } };
  const startDelete = (title) => { setSnack(`${title} deleted`); setTimeout(() => setSnack(null), 3200); };

  /* three-dot menu — Settings, Trash, About, Download (with submenu) */
  const openTopMenu = (anchor) => {
    setCtx({
      open: true, anchor, title: null,
      items: [
        { icon: "settings", label: "Settings",  onClick: () => setOverlay("settings") },
        { icon: "trash",    label: "Trash",      onClick: () => setOverlay("trash") },
        { icon: "info",     label: "About",      onClick: () => setOverlay("about") },
        { divider: true },
        { icon: "download", label: "Download", onClick: () => setOverlay("download") },
      ],
    });
  };

  /* long-press menus */
  const openSectionMenu = (anchor, section) => {
    setCtx({ open: true, anchor, title: section.title, items: [
      { icon: "plus",     label: "Add task",        onClick: () => setOverlay("form") },
      { icon: "pencil",   label: "Rename",           onClick: () => flash("Renamed") },
      { divider: true },
      { icon: "x",        label: "Delete section", danger: true, onClick: () => startDelete(section.title) },
    ]});
  };
  const openParentMenu = (anchor, parent, pinned) => {
    const isFav = !!favourites[parent.id];
    const items = [
      { icon: "plus",    label: "Add section", onClick: () => { setAddKind("section"); setOverlay("nameform"); } },
      { icon: "pencil",  label: "Rename",      onClick: () => flash("Renamed") },
      { divider: true },
      { icon: "pin",     label: isFav ? "Unpin from Home" : "Pin to Home", onClick: () => {
        setFavourites((f) => { const n = { ...f }; if (n[parent.id]) delete n[parent.id]; else n[parent.id] = true; return n; });
        flash(isFav ? "Unpinned" : "Pinned to Home");
      }},
    ];
    if (!pinned) { items.push({ divider: true }); items.push({ icon: "x", label: "Delete parent", danger: true, onClick: () => startDelete(parent.title) }); }
    setCtx({ open: true, anchor, title: parent.title, items });
  };
  const openTaskMenu = (anchor, task) => {
    setCtx({ open: true, anchor, title: task.title, items: [
      { icon: "pencil", label: "Edit",         onClick: () => setOverlay("form") },
      { icon: "clock",  label: "Set deadline", onClick: () => setOverlay("form") },
      { divider: true },
      { icon: "x", label: "Delete task", danger: true, onClick: () => startDelete(task.title) },
    ]});
  };

  const renderSection = (s) => {
    const tasks = s.tasks;
    const d = tasks.filter((t) => done[t.id]).length;
    const ic = sectionIcons[s.id] || s.icon;
    return (
      <SectionCard key={s.id} title={s.title} icon={ic} done={d} total={tasks.length}
        status={s.status} time={s.time} expanded={!collapsed[s.id]}
        onToggle={() => setCollapsed((c) => ({ ...c, [s.id]: !c[s.id] }))}
        onMenu={(pt) => openSectionMenu(pt, s)}
        onIconClick={(pt) => setIconTarget({ id: s.id, anchor: pt })}>
        {tasks.length === 0 ? (
          <div style={{ padding: "10px 0 6px", font: "var(--weight-regular) 14px/1.4 var(--font-sans)", color: "var(--ink-400)" }}>
            No tasks yet — press and hold the title to add one.
          </div>
        ) : tasks.map((t) => (
          <TaskRow key={t.id} title={t.title} description={t.desc}
            status={t.status} time={t.time} checked={done[t.id]}
            onToggle={(next) => !readOnly && setDone((m) => ({ ...m, [t.id]: next }))}
            onMenu={(pt) => openTaskMenu(pt, t)} />
        ))}
      </SectionCard>
    );
  };

  const renderParent = (p, pinned = false) => {
    const isFav = !!favourites[p.id];
    const em = parentEmoji[p.id] || p.emoji;
    return (
      <ParentSection key={p.id} title={p.title} count={p.sections.length} pinned={pinned}
        favourite={isFav} emoji={em}
        expanded={!collapsedParents[p.id]}
        onToggle={(v) => setCollapsedParents((c) => ({ ...c, [p.id]: !v }))}
        onEmojiClick={(pt) => setEmojiTarget({ id: p.id, anchor: pt })}
        onMenu={(pt) => openParentMenu(pt, p, pinned)}>
        {p.sections.map((s) => renderSection(s))}
      </ParentSection>
    );
  };

  const selKey = `${sel.y}-${sel.m}-${sel.d}`;
  const todayKey = `${today.y}-${today.m}-${today.d}`;
  const header = isToday ? "Today" : `${labelOf(sel.y, sel.m, sel.d)}, ${MONTH_SHORT[sel.m]} ${sel.d}`;

  const pickDate = ({ year, month, date }) => {
    setSel({ y: year, m: month, d: date });
    setOverlay(null); setMode("check");
  };

  return (
    <div className="tc-frame">
      <div className="tc-scroll" style={{ pointerEvents: readOnly ? "none" : "auto" }}>
        <div style={{ padding: "26px 16px 140px", maxWidth: 440, margin: "0 auto" }}>
          {/* header */}
          <header style={{ margin: "0 2px 18px", pointerEvents: "auto" }}>
            <div style={{ display: "flex", alignItems: "center", justifyContent: "space-between", gap: 10 }}>
              <span style={{ font: "var(--weight-medium) 32px/1 var(--font-sans)", letterSpacing: "-0.02em", color: "var(--ink-900)" }}>
                Task Cluster
              </span>
              <button type="button" aria-label="Open menu"
                onClick={(e) => openTopMenu({ x: e.clientX, y: e.clientY })}
                onContextMenu={(e) => { e.preventDefault(); openTopMenu({ x: e.clientX, y: e.clientY }); }}
                className="tc-pressable"
                style={{ display: "grid", placeItems: "center", width: 36, height: 36, border: "none",
                  background: "transparent", borderRadius: 999, cursor: "pointer", color: "var(--ink-700)" }}>
                <Icon name="more-vertical" size={22} color="var(--ink-700)" />
              </button>
            </div>
            <div style={{ marginTop: 4, font: "var(--weight-medium) 20px/1 var(--font-sans)", color: "var(--ink-500)" }}>
              {page === "home" ? header : "Tasks"}
            </div>
          </header>

          {/* date strip — Home only */}
          {page === "home" && (
            <DateStrip days={weekOf(sel.y, sel.m, sel.d)} selectedKey={selKey} todayKey={todayKey}
              onSelect={(k) => { const [y,m,d] = k.split("-").map(Number); setSel({y,m,d}); }}
              onCalendar={() => setOverlay("calendar")} />
          )}

          {/* date-mode banner — Home only */}
          {page === "home" && (readOnly || planning) && (
            <div style={{ margin: "8px 0 4px", pointerEvents: "auto" }}>
              <Banner variant={readOnly ? "read-only" : "planning"} />
            </div>
          )}

          {/* tree */}
          {planning ? (
            <div style={{ padding: "40px 8px", textAlign: "center" }}>
              <div style={{ font: "var(--weight-regular) 15px/1.5 var(--font-sans)", color: "var(--ink-400)" }}>
                Nothing planned for this day yet.
              </div>
            </div>
          ) : page === "home" ? (
            /* HOME: Daily + pinned favourites */
            <div style={{ marginTop: 14, display: "flex", flexDirection: "column", gap: 12 }}>
              {renderParent({ id: "daily", title: "Daily", emoji: "☀️", sections: DATA.daily }, true)}
              {DATA.tree.filter((n) => n.type === "parent" && favourites[n.id]).map((n) => renderParent(n))}
              {Object.keys(favourites).length === 0 && (
                <div style={{ padding: "24px 8px", textAlign: "center", font: "var(--weight-regular) 14px/1.5 var(--font-sans)", color: "var(--ink-400)" }}>
                  Pin parents from the Tasks tab to see them here.
                </div>
              )}
            </div>
          ) : (
            /* TASKS: all parents + standalone sections */
            <div style={{ marginTop: 14, display: "flex", flexDirection: "column", gap: 12 }}>
              {DATA.tree.map((n) => n.type === "parent" ? renderParent(n) : renderSection(n))}
            </div>
          )}
        </div>
      </div>

      <div className="tc-bar"><BottomBar mode={mode} onModeChange={onBar} /></div>

      {toast && <div className="tc-feedback"><Toast>{toast}</Toast></div>}
      {snack && <div className="tc-feedback"><Snackbar onAction={() => { setSnack(null); flash("Restored"); }}>{snack}</Snackbar></div>}

      {/* origin-aware popup */}
      <ContextMenu open={ctx.open} anchor={ctx.anchor} title={ctx.title} items={ctx.items} onClose={closeCtx} />

      {/* popup overlays */}
      <AddChooser open={overlay === "addchoose"} onClose={closeOverlay} onPick={pickAdd} onImport={() => setOverlay("import")} />
      <NameForm open={overlay === "nameform"} kind={addKind} onClose={closeOverlay} onSave={(name) => { closeOverlay(); flash(`${name} added`); }} />
      <CalendarPopup open={overlay === "calendar"} onClose={closeOverlay}
        todayYear={TODAY_Y} todayMonth={TODAY_M} todayDate={TODAY_D}
        selectedYear={sel.y} selectedMonth={sel.m} selectedDate={sel.d}
        onPick={pickDate} />

      <FormPopup open={overlay === "form"} onClose={closeOverlay} onSave={() => { closeOverlay(); flash("Saved"); }} />
      <ImportPopup open={overlay === "import"} onClose={closeOverlay} onConfirm={() => { closeOverlay(); flash("3 sections imported"); }} />
      <SearchPopup open={overlay === "search"} onClose={closeOverlay} />
      <DownloadPopup open={overlay === "download"} onClose={closeOverlay} onAction={(msg) => flash(msg)} />
      <SettingsPage open={overlay === "settings"} onBack={closeOverlay} onAction={(msg) => { closeOverlay(); flash(msg); }} />
      <TrashPage open={overlay === "trash"} onBack={closeOverlay} onAction={(msg) => { closeOverlay(); flash(msg); }} />
      <AboutPage open={overlay === "about"} onBack={closeOverlay} onAction={(msg) => { closeOverlay(); flash(msg); }} onRate={() => setOverlay("rate")} />
      <RatePopup open={overlay === "rate"} onClose={closeOverlay} onSubmit={(n) => { closeOverlay(); flash(n >= 4 ? "Thanks!" : "Feedback noted"); }} />

      {/* origin-aware pickers */}
      <EmojiPicker open={!!emojiTarget} anchor={emojiTarget?.anchor}
        onClose={() => setEmojiTarget(null)}
        onPick={(e) => { if (emojiTarget) setParentEmoji((m) => ({ ...m, [emojiTarget.id]: e })); }} />
      <IconPicker open={!!iconTarget} anchor={iconTarget?.anchor}
        onClose={() => setIconTarget(null)}
        onPick={(ic) => { if (iconTarget) setSectionIcons((m) => ({ ...m, [iconTarget.id]: ic })); }} />
    </div>
  );
}

(function mountTaskCluster() {
  const node = document.getElementById("tc-appRoot");
  if (!node) return;
  const ns = window.TaskClusterDesignSystem_1763fe || {};
  if (!ns.SectionCard || !ns.DateStrip || !ns.BottomBar || !ns.ContextMenu) return;
  if (!node.__tcRoot) node.__tcRoot = ReactDOM.createRoot(node);
  node.__tcRoot.render(<TaskClusterScreen />);
})();
