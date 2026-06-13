package com.journeytix.taskcluster.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.R

// General Sans — the only typeface. TWO weights only: 400 / 500.
val GeneralSans = FontFamily(
    Font(R.font.general_sans_regular, FontWeight.W400),
    Font(R.font.general_sans_medium, FontWeight.W500),
)

val TrackTight = (-0.02).em // display, title
val TrackSnug = (-0.01).em // body

// Type scale
val Display = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W500,
    fontSize = 40.sp, // day header
    lineHeight = 43.sp, // 1.08
    letterSpacing = TrackTight,
)
val Title = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W500,
    fontSize = 20.sp, // section title
    lineHeight = 24.sp, // 1.2
    letterSpacing = TrackSnug,
)
val Body = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp, // task title
    lineHeight = 21.sp, // 1.35
)
val Sub = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 14.sp, // description
    lineHeight = 19.sp, // 1.35
)
val Meta = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W500,
    fontSize = 13.sp, // progress, time, date
    lineHeight = 16.sp, // 1.2
)
val Caption = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 12.sp, // weekday, banner
    lineHeight = 14.sp, // 1.2
)

val Typography = Typography(
    displayMedium = Display,
    titleMedium = Title,
    bodyLarge = Body,
    bodyMedium = Sub,
    labelMedium = Meta,
    labelSmall = Caption,
)
