package com.journeytix.taskcluster.ui.components.feedback

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.Blue
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusMd
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Red
import com.journeytix.taskcluster.ui.theme.SurfaceRaised

private const val EDGE_PAD = 8
private const val NUDGE = 4
private const val DISABLED_ALPHA = 0.6f

data class ContextMenuItem(
    val label: String,
    val icon: ImageVector? = null,
    val danger: Boolean = false,
    val disabled: Boolean = false,
    val submenu: List<ContextMenuItem> = emptyList(),
    val onClick: () -> Unit = {},
)

/* Origin-aware placement, same rules as ContextMenu.jsx: open at the touch
   point, flip to the left half when the anchor is right of centre, flip
   above when the menu would clip the bottom, clamp to the edge padding. */
fun contextMenuPosition(
    anchor: IntOffset,
    menuSize: IntSize,
    windowSize: IntSize,
): IntOffset {
    val fromRight = anchor.x > windowSize.width / 2
    var left = if (fromRight) anchor.x - menuSize.width - NUDGE else anchor.x + NUDGE
    if (left < EDGE_PAD) left = EDGE_PAD
    if (left + menuSize.width > windowSize.width - EDGE_PAD) {
        left = windowSize.width - EDGE_PAD - menuSize.width
    }
    var top = anchor.y + NUDGE
    if (top + menuSize.height > windowSize.height - EDGE_PAD) {
        top = anchor.y - menuSize.height - NUDGE
    }
    if (top < EDGE_PAD) top = EDGE_PAD
    return IntOffset(left, top)
}

private class ContextMenuPositionProvider(
    private val anchor: IntOffset,
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
    ): IntOffset = contextMenuPosition(anchor, popupContentSize, windowSize)
}

@Composable
fun TaskContextMenu(
    open: Boolean,
    anchor: IntOffset,
    items: List<ContextMenuItem>,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
) {
    if (!open) return
    var sub by remember(open) { mutableStateOf<ContextMenuItem?>(null) }
    val displayItems = sub?.submenu ?: items

    // Spring entrance: scale + fade in from the anchor point.
    val visible = remember(open) { MutableTransitionState(false) }
    LaunchedEffect(open) { visible.targetState = true }
    val transition = rememberTransition(visible, label = "menu")
    val scale by transition.animateFloat(
        transitionSpec = { spring(dampingRatio = 0.7f, stiffness = 300f) },
        label = "scale",
    ) { if (it) 1f else 0.85f }
    val alpha by transition.animateFloat(
        transitionSpec = { tween(120) },
        label = "alpha",
    ) { if (it) 1f else 0f }

    Popup(
        popupPositionProvider = remember(anchor) { ContextMenuPositionProvider(anchor) },
        onDismissRequest = onClose,
    ) {
        val shape = RoundedCornerShape(RadiusMd)
        Column(
            modifier = modifier
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                    transformOrigin = TransformOrigin(0f, 0f)
                }
                .widthIn(min = 160.dp, max = 200.dp)
                .shadow(8.dp, shape)
                .background(SurfaceRaised, shape)
                .border(1.dp, HairlineStrong, shape)
                .padding(6.dp),
        ) {
            val currentSub = sub
            if (currentSub != null) {
                // Sub-menu back row
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .clip(RoundedCornerShape(RadiusSm))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) { sub = null }
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        TaskIcon(
                            icon = TaskIcons.ChevronLeft,
                            contentDescription = null,
                            size = 15.dp,
                            tint = Blue,
                        )
                        Text(
                            text = "back",
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                            ),
                            color = Blue,
                        )
                    }
                    MenuDivider()
                }
            } else if (title != null) {
                Column {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontFamily = GeneralSans,
                            fontWeight = FontWeight.W500,
                            fontSize = 12.sp,
                            letterSpacing = 0.04.em,
                        ),
                        color = Ink500,
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 8.dp),
                    )
                    MenuDivider()
                }
            }
            for (item in displayItems) {
                MenuRow(
                    item = item,
                    onClick = {
                        if (item.submenu.isNotEmpty()) {
                            sub = item
                        } else {
                            onClose()
                            item.onClick()
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun MenuDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .height(1.dp)
            .background(Hairline),
    )
}

@Composable
private fun MenuRow(item: ContextMenuItem, onClick: () -> Unit) {
    val labelColor = when {
        item.disabled -> Ink400
        item.danger -> Red
        else -> Ink900
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .alpha(if (item.disabled) DISABLED_ALPHA else 1f)
            .clip(RoundedCornerShape(RadiusSm))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = !item.disabled,
                onClick = onClick,
            )
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (item.icon != null) {
            TaskIcon(
                icon = item.icon,
                contentDescription = null,
                size = 17.dp,
                tint = if (item.danger) Red else Ink500,
            )
        } else {
            Spacer(modifier = Modifier.width(17.dp))
        }
        Text(
            text = item.label,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
            ),
            color = labelColor,
            modifier = Modifier.weight(1f),
            maxLines = 1,
        )
        if (item.submenu.isNotEmpty()) {
            TaskIcon(
                icon = TaskIcons.ChevronRight,
                contentDescription = null,
                size = 14.dp,
                tint = Ink400,
            )
        }
    }
}
