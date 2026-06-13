package com.journeytix.taskcluster.ui.components.core

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val STROKE = 1.75f

internal fun lucide(name: String, vararg paths: String, stroke: Float = STROKE): ImageVector {
    val builder = ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    )
    for (d in paths) {
        builder.addPath(
            pathData = addPathNodes(d),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = stroke,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
        )
    }
    return builder.build()
}

/* The in-product Lucide set — stroke only, 1.75 weight, same paths as the
   design reference's Icon.jsx. Circles/rects converted to path arcs. */
object TaskIcons {
    val ChevronRight by lazy { lucide("chevron-right", "m9 18 6-6-6-6") }
    val ChevronDown by lazy { lucide("chevron-down", "m6 9 6 6 6-6") }
    val ChevronLeft by lazy { lucide("chevron-left", "m15 18-6-6 6-6") }
    val Check by lazy { lucide("check", "M20 6 9 17l-5-5") }
    val Search by lazy {
        lucide(
            "search",
            "M3 11a8 8 0 1 0 16 0 8 8 0 1 0 -16 0",
            "m21 21-4.3-4.3",
        )
    }
    val Plus by lazy { lucide("plus", "M5 12h14", "M12 5v14") }
    val X by lazy { lucide("x", "M18 6 6 18", "m6 6 12 12") }
    val Pencil by lazy {
        lucide(
            "pencil",
            "M12 20h9",
            "M16.5 3.5a2.12 2.12 0 0 1 3 3L7 19l-4 1 1-4Z",
        )
    }
    val MoreVertical by lazy {
        lucide(
            "more-vertical",
            "M11 5a1 1 0 1 0 2 0 1 1 0 1 0 -2 0",
            "M11 12a1 1 0 1 0 2 0 1 1 0 1 0 -2 0",
            "M11 19a1 1 0 1 0 2 0 1 1 0 1 0 -2 0",
        )
    }
    val Calendar by lazy {
        lucide(
            "calendar",
            "M8 2v4",
            "M16 2v4",
            "M5 4h14a2 2 0 0 1 2 2v14a2 2 0 0 1 -2 2H5a2 2 0 0 1 -2 -2V6a2 2 0 0 1 2 -2z",
            "M3 10h18",
        )
    }
    val Import by lazy {
        lucide(
            "import",
            "M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4",
            "m7 10 5 5 5-5",
            "M12 15V3",
        )
    }
    val Undo by lazy {
        lucide(
            "undo",
            "M3 7v6h6",
            "M21 17a9 9 0 0 0-9-9 9 9 0 0 0-6 2.3L3 13",
        )
    }
    val Info by lazy {
        lucide(
            "info",
            "M2 12a10 10 0 1 0 20 0 10 10 0 1 0 -20 0",
            "M12 16v-4",
            "M12 8h.01",
        )
    }
    val Clock by lazy {
        lucide(
            "clock",
            "M2 12a10 10 0 1 0 20 0 10 10 0 1 0 -20 0",
            "M12 6v6l4 2",
        )
    }
    val Settings by lazy {
        lucide(
            "settings",
            "M9 12a3 3 0 1 0 6 0 3 3 0 1 0 -6 0",
            "M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 1 1-2.83 2.83l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 1 1-4 0v-.09a1.65 1.65 0 0 0-1-1.51 1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 1 1-2.83-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 1 1 0-4h.09a1.65 1.65 0 0 0 1.51-1 1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 1 1 2.83-2.83l.06.06a1.65 1.65 0 0 0 1.82.33h0a1.65 1.65 0 0 0 1-1.51V3a2 2 0 1 1 4 0v.09a1.65 1.65 0 0 0 1 1.51h0a1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 1 1 2.83 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82v0a1.65 1.65 0 0 0 1.51 1H21a2 2 0 1 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z",
        )
    }
    val Trash by lazy {
        lucide(
            "trash",
            "M3 6h18",
            "M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6",
            "M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2",
        )
    }
    val Star by lazy {
        lucide(
            "star",
            "M12 2.6l2.92 5.92 6.53.95-4.72 4.6 1.12 6.5L12 17.5l-5.85 3.07 1.12-6.5L2.55 9.47l6.53-.95L12 2.6z",
        )
    }
    val Home by lazy {
        lucide(
            "home",
            "m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z",
            "M9 22V12h6v10",
        )
    }
    val List by lazy {
        lucide(
            "list",
            "M8 6h13", "M8 12h13", "M8 18h13",
            "M3 6h.01", "M3 12h.01", "M3 18h.01",
        )
    }
    val Pin by lazy {
        lucide(
            "pin",
            "M12 17v5",
            "M9 10.76a2 2 0 0 1-1.11 1.79l-1.78.9A2 2 0 0 0 5 15.24V16h14v-.76a2 2 0 0 0-1.11-1.79l-1.78-.9A2 2 0 0 1 15 10.76V7a1 1 0 0 1 1-1 2 2 0 0 0 2-2H6a2 2 0 0 0 2 2 1 1 0 0 1 1 1z",
        )
    }
    val Share by lazy {
        lucide(
            "share",
            "M15 3a3 3 0 1 0 6 0 3 3 0 1 0 -6 0",
            "M15 21a3 3 0 1 0 6 0 3 3 0 1 0 -6 0",
            "M3 12a3 3 0 1 0 6 0 3 3 0 1 0 -6 0",
            "m8.59 13.51 6.83 3.98",
            "m15.41 6.51-6.82 3.98",
        )
    }
    val Mail by lazy {
        lucide(
            "mail",
            "M3 5h18a2 2 0 0 1 2 2v12a2 2 0 0 1 -2 2H3a2 2 0 0 1 -2 -2V7a2 2 0 0 1 2 -2z",
            "m22 6-10 7L2 6",
        )
    }
    val ExternalLink by lazy {
        lucide(
            "external-link",
            "M15 3h6v6",
            "M10 14 21 3",
            "M18 13v6a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h6",
        )
    }
    val Briefcase by lazy {
        lucide(
            "briefcase",
            "M16 20V4a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16",
            "M4 7h16a2 2 0 0 1 2 2v9a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V9a2 2 0 0 1 2-2z",
        )
    }
    val Book by lazy {
        lucide(
            "book",
            "M4 19.5v-15A2.5 2.5 0 0 1 6.5 2H20v20H6.5a2.5 2.5 0 0 1 0-5H20",
        )
    }
    val Dumbbell by lazy {
        lucide(
            "dumbbell",
            "M14.4 14.4 9.6 9.6",
            "M18.657 21.485a2 2 0 1 1-2.829-2.828l-1.767 1.768a2 2 0 1 1-2.829-2.829l6.364-6.364a2 2 0 1 1 2.829 2.829l-1.768 1.767a2 2 0 1 1 2.828 2.829z",
            "m21.5 21.5-1.4-1.4",
            "M3.9 3.9 2.5 2.5",
            "M6.404 12.768a2 2 0 1 1-2.829-2.829l1.768-1.767a2 2 0 1 1-2.828-2.829l2.828-2.828a2 2 0 1 1 2.829 2.828l1.767-1.768a2 2 0 1 1 2.829 2.829z",
        )
    }
    val Heart by lazy {
        lucide(
            "heart",
            "M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z",
        )
    }
    val ShoppingCart by lazy {
        lucide(
            "shopping-cart",
            "M8 21a1 1 0 1 0 2 0 1 1 0 1 0-2 0",
            "M19 21a1 1 0 1 0 2 0 1 1 0 1 0-2 0",
            "M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12",
        )
    }
    val Code by lazy {
        lucide(
            "code",
            "m16 18 6-6-6-6",
            "m8 6-6 6 6 6",
        )
    }
    val Music by lazy {
        lucide(
            "music",
            "M9 18V5l12-2v13",
            "M6 15a3 3 0 1 0 3 3V5",
            "M18 13a3 3 0 1 0 3 3",
        )
    }
    val Coffee by lazy {
        lucide(
            "coffee",
            "M10 2v2",
            "M14 2v2",
            "M16 8a1 1 0 0 1 1 1v8a4 4 0 0 1-4 4H7a4 4 0 0 1-4-4V9a1 1 0 0 1 1-1h14a4 4 0 1 1 0 8h-1",
            "M6 2v2",
        )
    }
    val Smile by lazy {
        lucide(
            "smile",
            "M2 12a10 10 0 1 0 20 0 10 10 0 1 0 -20 0",
            "M8 14s1.5 2 4 2 4-2 4-2",
            "M9 9h.01",
            "M15 9h.01",
        )
    }
    val Palette by lazy {
        lucide(
            "palette",
            "M12 2a10 10 0 0 0-9.95 11.08 1 1 0 0 0 1.45.87l1.73-1A4 4 0 0 1 12 17a4 4 0 0 1 0 8 10 10 0 1 0 0-20z",
            "M6.5 10a.5.5 0 1 0 1 0 .5.5 0 1 0-1 0",
            "M9.5 6a.5.5 0 1 0 1 0 .5.5 0 1 0-1 0",
            "M13.5 6a.5.5 0 1 0 1 0 .5.5 0 1 0-1 0",
            "M16.5 10a.5.5 0 1 0 1 0 .5.5 0 1 0-1 0",
        )
    }
    val Image by lazy {
        lucide(
            "image",
            "M5 3h14a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2z",
            "M8.5 8.5a1.5 1.5 0 1 0 3 0 1.5 1.5 0 1 0-3 0",
            "m21 15-5-5L5 21",
        )
    }
    val FileJson by lazy {
        lucide(
            "file-json",
            "M4 22h14a2 2 0 0 0 2-2V7l-5-5H6a2 2 0 0 0-2 2v4",
            "M14 2v4a2 2 0 0 0 2 2h4",
            "M4 12a1 1 0 0 0-1 1v1a1 1 0 0 1-1 1 1 1 0 0 1 1 1v1a1 1 0 0 0 1 1",
            "M8 18a1 1 0 0 0 1-1v-1a1 1 0 0 1 1-1 1 1 0 0 1-1-1v-1a1 1 0 0 0-1-1",
        )
    }
}

@Composable
fun TaskIcon(
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp, // 24 controls / 20 inline
    tint: Color = LocalContentColor.current,
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        tint = tint,
    )
}
