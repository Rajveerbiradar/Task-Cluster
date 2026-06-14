package com.journeytix.taskcluster.ui.theme

import androidx.compose.ui.graphics.Color

// Paper & surfaces — neutral: white background, grey parents, white sections.
val Canvas = Color(0xFFFFFFFF)        // app background — white
val Surface = Color(0xFFFFFFFF)       // section cards — white
val SurfaceRaised = Color(0xFFFFFFFF) // popups / menus
val SurfaceSunken = Color(0xFFEDEDED) // inset controls, progress tracks, icon chips
val ParentFill = Color(0xFFEDEDED)    // parent containers — grey

// Ink ramp (neutral greys)
val Ink900 = Color(0xFF1E1E1E)
val Ink700 = Color(0xFF444444)
val Ink600 = Color(0xFF6B6B6B)
val Ink500 = Color(0xFF8A8A8A)
val Ink400 = Color(0xFFA6A6A6)
val Ink300 = Color(0xFFC4C4C4)
val Ink200 = Color(0xFFD8D8D8)

// Hairlines (structure without boxes)
val Hairline = Color(0x14000000) // ~8%
val HairlineStrong = Color(0x22000000) // ~13%
val GuideLine = Color(0x29000000) // ~16%

// Primary: near-black — buttons, checked, active tab, today
val Primary = Color(0xFF1E1E1E)
val PrimaryPress = Color(0xFF000000)
val PrimaryTint = Color(0xFFE4E4E4)
val PrimaryOn = Color(0xFFFFFFFF)

// Blue — time pill on-track ONLY
val Blue = Color(0xFF378ADD)
val BluePress = Color(0xFF2C72BC)
val BlueTint = Color(0xFFEAF2FB)

// Urgency — time pills ONLY
val Amber = Color(0xFFB26C12)
val Red = Color(0xFFCF3030)

// Overdue capsule
val OverdueBg = Color(0xFFFCEBEB)
val OverdueBorder = Color(0xFFE24B4A)
val OverdueText = Color(0xFF791F1F)

// Feedback surfaces
val ToastBg = Color(0xFF1E1E1E)
val ToastText = Color(0xFFFAFAFA)
val WarnBg = Color(0xFFF2F2F2)
val WarnBorder = Color(0x1F000000) // ~12%

// Scrim
val Scrim = Color(0x52000000) // ~32%
