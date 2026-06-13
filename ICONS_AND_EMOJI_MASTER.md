# TaskCluster — ICONS & EMOJI MASTER ADDENDUM

Apply this ON TOP of MASTER_BUILD_HANDOFF.md. It covers two things:

1. The COMPLETE emoji catalogue for the parent emoji picker (Part 1).
2. Section icons as full-color PNGs — placement, folder-scan, and render (Part 2).

IMPORTANT precedence: For section icons, this file's PNG approach is authoritative.
Do NOT seed or generate vector (.xml) section icons. The user supplies their own
PNG files. Parent emojis are unicode text and unaffected by the icon format.


================================================================================
# PART 1 — COMPLETE EMOJI CATALOGUE
================================================================================
Replace the contents of `ui/screens/home/EmojiData.kt` with the full set below.
Same structure (EmojiCategory list), but every common emoji included across 9
categories. The picker renders category tabs + a scrollable grid per category.

```kotlin
package com.journeytix.taskcluster.ui.screens.home

/* Full emoji catalogue for the parent emoji picker. 9 categories, complete set. */
data class EmojiCategory(val label: String, val tabEmoji: String, val emojis: List<String>)

val EmojiCatalogue = listOf(
    EmojiCategory("Smileys", "😀", listOf(
        "😀","😃","😄","😁","😆","😅","🤣","😂","🙂","🙃","🫠","😉","😊","😇","🥰","😍",
        "🤩","😘","😗","☺️","😚","😙","🥲","😋","😛","😜","🤪","😝","🤑","🤗","🤭","🫢",
        "🫣","🤫","🤔","🫡","🤐","🤨","😐","😑","😶","🫥","😶‍🌫️","😏","😒","🙄","😬","😮‍💨",
        "🤥","🫨","😌","😔","😪","🤤","😴","😷","🤒","🤕","🤢","🤮","🤧","🥵","🥶","🥴",
        "😵","😵‍💫","🤯","🤠","🥳","🥸","😎","🤓","🧐","😕","🫤","😟","🙁","☹️","😮","😯",
        "😲","😳","🥺","🥹","😦","😧","😨","😰","😥","😢","😭","😱","😖","😣","😞","😓",
        "😩","😫","🥱","😤","😡","😠","🤬","😈","👿","💀","☠️","💩","🤡","👹","👺","👻",
        "👽","👾","🤖","😺","😸","😹","😻","😼","😽","🙀","😿","😾"
    )),
    EmojiCategory("Gestures", "👋", listOf(
        "👋","🤚","🖐️","✋","🖖","🫱","🫲","🫳","🫴","🫷","🫸","👌","🤌","🤏","✌️","🤞",
        "🫰","🤟","🤘","🤙","👈","👉","👆","🖕","👇","☝️","🫵","👍","👎","✊","👊","🤛",
        "🤜","👏","🙌","🫶","👐","🤲","🤝","🙏","✍️","💅","🤳","💪","🦾","🦿","🦵","🦶",
        "👂","🦻","👃","🧠","🫀","🫁","🦷","🦴","👀","👁️","👅","👄","🫦","💋","🩸"
    )),
    EmojiCategory("People", "🧑", listOf(
        "👶","🧒","👦","👧","🧑","👨","👩","🧓","👴","👵","👲","👳","🧕","👮","👷","💂",
        "🕵️","👨‍⚕️","👩‍⚕️","👨‍🌾","👩‍🌾","👨‍🍳","👩‍🍳","👨‍🎓","👩‍🎓","👨‍🎤","👩‍🎤","👨‍🏫","👩‍🏫","👨‍💻","👩‍💻","👨‍🔧",
        "👩‍🔧","👨‍🔬","👩‍🔬","👨‍🎨","👩‍🎨","👨‍🚒","👩‍🚒","👨‍✈️","👩‍✈️","👨‍🚀","👩‍🚀","👨‍⚖️","👩‍⚖️","🤵","👰","🤰",
        "🤱","👼","🎅","🤶","🦸","🦹","🧙","🧚","🧛","🧜","🧝","🧞","🧟","💆","💇","🚶",
        "🧍","🧎","🏃","💃","🕺","👯","🧖","🧗","🤺","🏇","⛷️","🏂","🏌️","🏄","🚣","🏊",
        "⛹️","🏋️","🚴","🚵","🤸","🤼","🤽","🤾","🤹","🧘","🛀","🛌"
    )),
    EmojiCategory("Animals", "🐶", listOf(
        "🐶","🐱","🐭","🐹","🐰","🦊","🐻","🐼","🐻‍❄️","🐨","🐯","🦁","🐮","🐷","🐸","🐵",
        "🙈","🙉","🙊","🐒","🐔","🐧","🐦","🐤","🐣","🐥","🦆","🦅","🦉","🦇","🐺","🐗",
        "🐴","🦄","🐝","🪲","🐛","🦋","🐌","🐞","🐜","🪰","🪱","🦗","🕷️","🦂","🐢","🐍",
        "🦎","🦖","🦕","🐙","🦑","🦐","🦞","🦀","🐡","🐠","🐟","🐬","🐳","🐋","🦈","🐊",
        "🐅","🐆","🦓","🦍","🦧","🐘","🦛","🦏","🐪","🐫","🦒","🦘","🦬","🐃","🐂","🐄",
        "🐎","🐖","🐏","🐑","🦙","🐐","🦌","🐕","🐩","🦮","🐈","🐓","🦃","🦤","🦚","🦜",
        "🦢","🦩","🕊️","🐇","🦝","🦨","🦡","🦫","🦦","🦥","🐁","🐀","🐿️","🦔","🐾","🐉","🐲"
    )),
    EmojiCategory("Nature", "🌿", listOf(
        "🌱","🌿","☘️","🍀","🎍","🪴","🎋","🍃","🍂","🍁","🍄","🐚","🪨","🌾","💐","🌷",
        "🌹","🥀","🌺","🌸","🌼","🌻","🌞","🌝","🌛","🌜","🌚","🌕","🌖","🌗","🌘","🌑",
        "🌒","🌓","🌔","🌙","🌎","🌍","🌏","🪐","💫","⭐","🌟","✨","⚡","☄️","💥","🔥",
        "🌪️","🌈","☀️","🌤️","⛅","🌥️","☁️","🌦️","🌧️","⛈️","🌩️","🌨️","❄️","☃️","⛄","🌬️",
        "💨","💧","💦","🫧","☔","🌊","🌫️"
    )),
    EmojiCategory("Food", "🍔", listOf(
        "🍇","🍈","🍉","🍊","🍋","🍌","🍍","🥭","🍎","🍏","🍐","🍑","🍒","🍓","🫐","🥝",
        "🍅","🫒","🥥","🥑","🍆","🥔","🥕","🌽","🌶️","🫑","🥒","🥬","🥦","🧄","🧅","🥜",
        "🫘","🌰","🍞","🥐","🥖","🫓","🥨","🥯","🥞","🧇","🧀","🍖","🍗","🥩","🥓","🍔",
        "🍟","🍕","🌭","🥪","🌮","🌯","🫔","🥙","🧆","🥚","🍳","🥘","🍲","🫕","🥣","🥗",
        "🍿","🧈","🧂","🥫","🍱","🍘","🍙","🍚","🍛","🍜","🍝","🍠","🍢","🍣","🍤","🍥",
        "🥮","🍡","🥟","🥠","🥡","🦪","🍦","🍧","🍨","🍩","🍪","🎂","🍰","🧁","🥧","🍫",
        "🍬","🍭","🍮","🍯","🍼","🥛","☕","🫖","🍵","🍶","🍾","🍷","🍸","🍹","🍺","🍻",
        "🥂","🥃","🫗","🥤","🧋","🧃","🧉","🧊"
    )),
    EmojiCategory("Activity", "⚽", listOf(
        "⚽","🏀","🏈","⚾","🥎","🎾","🏐","🏉","🥏","🎱","🪀","🏓","🏸","🏒","🏑","🥍",
        "🏏","🪃","🥅","⛳","🪁","🏹","🎣","🤿","🥊","🥋","🎽","🛹","🛼","🛷","⛸️","🥌",
        "🎿","⛷️","🏂","🪂","🏋️","🤼","🤸","⛹️","🤺","🤾","🏌️","🏇","🧘","🏄","🏊","🤽",
        "🚣","🧗","🚵","🚴","🏆","🥇","🥈","🥉","🏅","🎖️","🏵️","🎗️","🎫","🎟️","🎪","🤹",
        "🎭","🩰","🎨","🎬","🎤","🎧","🎼","🎹","🥁","🪘","🎷","🎺","🪗","🎸","🪕","🎻",
        "🎲","♟️","🎯","🎳","🎮","🎰","🧩"
    )),
    EmojiCategory("Travel", "✈️", listOf(
        "🚗","🚕","🚙","🚌","🚎","🏎️","🚓","🚑","🚒","🚐","🛻","🚚","🚛","🚜","🦯","🦽",
        "🦼","🛴","🚲","🛵","🏍️","🛺","🚨","🚔","🚍","🚘","🚖","🚡","🚠","🚟","🚃","🚋",
        "🚞","🚝","🚄","🚅","🚈","🚂","🚆","🚇","🚊","🚉","✈️","🛫","🛬","🛩️","💺","🛰️",
        "🚀","🛸","🚁","🛶","⛵","🚤","🛥️","🛳️","⛴️","🚢","⚓","🪝","⛽","🚧","🚦","🚥",
        "🗺️","🗿","🗽","🗼","🏰","🏯","🏟️","🎡","🎢","🎠","⛲","⛱️","🏖️","🏝️","🏜️","🌋",
        "⛰️","🏔️","🗻","🏕️","⛺","🏠","🏡","🏘️","🏚️","🏗️","🏭","🏢","🏬","🏣","🏤","🏥",
        "🏦","🏨","🏪","🏫","🏩","💒","🏛️","⛪","🕌","🕍","🛕","🕋","⛩️","🛤️","🛣️","🗾",
        "🎑","🏞️","🌅","🌄","🌠","🎇","🎆","🌇","🌆","🏙️","🌃","🌌","🌉","🌁"
    )),
    EmojiCategory("Objects", "💡", listOf(
        "⌚","📱","📲","💻","⌨️","🖥️","🖨️","🖱️","🖲️","🕹️","🗜️","💽","💾","💿","📀","📼",
        "📷","📸","📹","🎥","📽️","🎞️","📞","☎️","📟","📠","📺","📻","🎙️","🎚️","🎛️","🧭",
        "⏱️","⏲️","⏰","🕰️","⌛","⏳","📡","🔋","🪫","🔌","💡","🔦","🕯️","🪔","🧯","🛢️",
        "💸","💵","💴","💶","💷","🪙","💰","💳","💎","⚖️","🪜","🧰","🪛","🔧","🔨","⚒️",
        "🛠️","⛏️","🪚","🔩","⚙️","🪤","🧱","⛓️","🧲","🔫","💣","🧨","🪓","🔪","🗡️","⚔️",
        "🛡️","🚬","⚰️","🪦","⚱️","🏺","🔮","📿","🧿","🪬","💈","⚗️","🔭","🔬","🕳️","🩹",
        "🩺","💊","💉","🩸","🧬","🦠","🧫","🧪","🌡️","🧹","🪠","🧺","🧻","🚽","🚰","🚿",
        "🛁","🛀","🧼","🪥","🪒","🧽","🪣","🧴","🛎️","🔑","🗝️","🚪","🪑","🛋️","🛏️","🛌",
        "🧸","🪆","🖼️","🪞","🪟","🛍️","🛒","🎁","🎈","🎏","🎀","🪄","🪅","🎊","🎉","🎎",
        "🏮","🎐","🧧","✉️","📩","📨","📧","💌","📥","📤","📦","🏷️","🪧","📪","📫","📬",
        "📭","📮","📯","📜","📃","📄","📑","🧾","📊","📈","📉","🗒️","🗓️","📆","📅","🗑️",
        "📇","🗃️","🗳️","🗄️","📋","📁","📂","🗂️","🗞️","📰","📓","📔","📒","📕","📗","📘",
        "📙","📚","📖","🔖","🧷","🔗","📎","🖇️","📐","📏","🧮","📌","📍","✂️","🖊️","🖋️",
        "✒️","🖌️","🖍️","📝","✏️","🔍","🔎","🔏","🔐","🔒","🔓"
    )),
    EmojiCategory("Symbols", "❤️", listOf(
        "❤️","🧡","💛","💚","💙","💜","🤎","🖤","🤍","💔","❤️‍🔥","❤️‍🩹","💗","💓","💞","💕",
        "💟","❣️","💌","💋","💯","💢","💥","💫","💦","💨","🕳️","💬","💭","🗯️","♨️","💮",
        "♻️","🔱","📛","🔰","⭕","✅","☑️","✔️","❌","❎","➕","➖","➗","✖️","🟰","♾️",
        "‼️","⁉️","❓","❔","❕","❗","〰️","✳️","✴️","❇️","©️","®️","™️","🔟","🔢","#️⃣",
        "*️⃣","⏏️","▶️","⏸️","⏯️","⏹️","⏺️","⏭️","⏮️","⏩","⏪","🔼","🔽","➡️","⬅️","⬆️",
        "⬇️","↗️","↘️","↙️","↖️","↕️","↔️","↩️","↪️","⤴️","⤵️","🔀","🔁","🔂","🔄","🔃",
        "🎵","🎶","➰","➿","✔️","🔆","🔅","⚜️","🔯","🕎","☯️","☦️","🛐","⛎","♈","♉",
        "♊","♋","♌","♍","♎","♏","♐","♑","♒","♓","🆔","⚛️","🉑","☢️","☣️","📴",
        "📳","🈶","🈚","🈸","🈺","🈷️","✴️","🆚","🉐","㊙️","㊗️","🈴","🈵","🈹","🈲","🅰️",
        "🅱️","🆎","🆑","🅾️","🆘","❗","🔴","🟠","🟡","🟢","🔵","🟣","🟤","⚫","⚪","🟥",
        "🟧","🟨","🟩","🟦","🟪","🟫","⬛","⬜","◼️","◻️","◾","◽","▪️","▫️","🔶","🔷",
        "🔸","🔹","🔺","🔻","💠","🔘","🔳","🔲"
    )),
)
```

The EmojiPicker grid should be 8 columns, scrollable vertically, with the 10
category tabs in a scrollable row at the top. Tapping a tab jumps to that category.


================================================================================
# PART 2 — SECTION ICONS (FULL-COLOR PNG)
================================================================================
This OVERRIDES the icon RENDERING in MASTER_BUILD_HANDOFF.md and the folder-scan's
assumption of vector drawables. The folder, prefix, and dynamic-scan approach stay
identical — only the render path and file format change. Section icons are now
full-color PNG bitmaps, displayed as-is with NO tinting.

================================================================================
## 1 — Icon files
================================================================================

Location (unchanged):  app/src/main/res/drawable/
Format:                 .png  (full-color raster)
Naming:                 ic_section_<name>.png
                        lowercase letters, digits, underscore ONLY.
                        NO capitals, NO spaces, NO hyphens (build fails otherwise).
Examples:               ic_section_thunder.png, ic_section_football.png,
                        ic_section_tree.png, ic_section_magic.png

The user is supplying their own PNGs. Do NOT generate or seed vector icons for
sections anymore — the user's PNGs are the icon set. If any seed vector
ic_section_*.xml files were created in a prior build, leave them; the scan will
include both, but the user's PNGs are primary.

================================================================================
## 2 — SectionIcons folder-scan (UNCHANGED LOGIC)
================================================================================

`ui/components/core/SectionIcons.kt` stays exactly as specified — it scans
R.drawable for fields starting with ic_section_ and maps key -> resId. This works
identically for PNG and XML because R.drawable contains an entry per file
regardless of format. No change needed here.

================================================================================
## 3 — RENDER CHANGE: Image, not Icon; NO tint
================================================================================

PNGs are full-color and must NOT be tinted. Tinting (via Icon(tint=...)) would
flatten the PNG to a single color block. Use Image with painterResource instead.

### In SectionCard.kt — the icon badge render
Replace the Icon(...) call in the icon slot with:

```kotlin
if (iconResId != null) {
    Image(
        painter = painterResource(iconResId),
        contentDescription = null,
        modifier = Modifier.size(20.dp),   // PNGs carry their own padding; 20dp reads better than 17dp
    )
}
```
Imports: androidx.compose.foundation.Image, androidx.compose.ui.res.painterResource
Remove any `tint =` usage for section icons. This is the allowed icon-slot change.

Keep the badge container (the 32dp rounded SurfaceSunken box) as-is — the colored
PNG sits inside it. If colored PNGs look cramped in the tinted box, the box may use
a transparent/very-light background; decide based on the design — a neutral light
box is acceptable. Do NOT remove the box entirely.

### In IconPicker.kt — the picker grid
Render each option with Image, not Icon, and no tint:

```kotlin
Image(
    painter = painterResource(SectionIcons.resId(key)!!),
    contentDescription = key,
    modifier = Modifier.size(28.dp),
)
```
Grid stays 5 columns. "Remove icon" row unchanged.

================================================================================
## 4 — Everything else unchanged
================================================================================

- Section stores iconKey = the <name> after ic_section_ (e.g. "thunder").
- SetSectionIcon / SectionIcons.resId / the picker flow all stay the same.
- Parent EMOJI picker is unaffected — emojis are unicode text, not PNGs.
- Only SECTION icons are PNG. Parent emojis remain emojis.

================================================================================
## 5 — Verify
================================================================================

- Build passes: ./gradlew assembleDebug exits 0.
- A section with an assigned PNG icon shows the full-color image (not a solid
  color block — that would mean tinting wasn't removed).
- IconPicker shows all ic_section_*.png files from the drawable folder.
- Adding a new ic_section_<name>.png + rebuild makes it appear in the picker
  with no code change.

================================================================================
# PART 3 — DEFAULT SECTION ICON
================================================================================

Every section that has NO assigned icon (iconKey == null) must show a default
fallback icon instead of an empty/blank badge.

## The file
The user provides the default icon. It MUST be placed and named:
```
app/src/main/res/drawable/ic_section_default.png
```
(The user's original file is "circle-stroke-rounded.png" — it must be renamed to
ic_section_default.png because hyphens are illegal in Android resource names and
to follow the ic_section_ convention.)

## SectionIcons — resolve with fallback
In `ui/components/core/SectionIcons.kt`, add a resId resolver that falls back to
the default when the key is null OR not found:

```kotlin
fun resIdOrDefault(key: String?): Int {
    val direct = key?.let { all[it] }
    if (direct != null) return direct
    // fall back to the default icon if present in the folder
    return all["default"]
        ?: R.drawable.ic_section_default   // hard reference as last resort
}
```

Keep the existing `resId(key): Int?` as-is for the picker (so "default" can be
hidden from the pickable grid — see below). Add `resIdOrDefault` for rendering.

## SectionCard render — always show an icon
In SectionCard.kt the icon slot should ALWAYS render (never null now). Change the
call site in HomeScreen.kt SectionBlock to:
```kotlin
SectionCard(
    title = section.title,
    iconResId = SectionIcons.resIdOrDefault(section.iconKey),
    onIconClick = { anchor -> iconTarget = section.id to anchor },
    ...
)
```
Because resIdOrDefault always returns a valid resId, the SectionCard icon `Image`
always draws. A section with no chosen icon shows ic_section_default.png; tapping it
still opens the IconPicker so the user can assign a real one.

## IconPicker — exclude "default" from the grid
The default is a fallback, not a user choice. In IconPicker, filter it out:
```kotlin
val pickable = SectionIcons.keys.filter { it != "default" }
```
Render `pickable` in the 5-column grid. Keep the "Remove icon" row — choosing it
sets iconKey = null, which makes the section fall back to the default again.

## Verify
- A brand-new section (no icon chosen) shows ic_section_default.png, not a blank box.
- The default icon does NOT appear as a selectable option in the IconPicker grid.
- Picking "Remove icon" returns a section to the default icon.
