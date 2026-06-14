package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.annotation.DrawableRes
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.DurBase
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Primary
import com.journeytix.taskcluster.ui.theme.RadiusLg
import com.journeytix.taskcluster.ui.theme.RadiusPill
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Space3
import com.journeytix.taskcluster.ui.theme.SpringDamping
import com.journeytix.taskcluster.ui.theme.SpringStiffness
import com.journeytix.taskcluster.ui.theme.Surface
import com.journeytix.taskcluster.ui.theme.SurfaceSunken
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.TrackSnug

private const val PINNED_CHEVRON_ALPHA = 0.35f

/* SectionCard — a section sits on a subtle surface. Tap the header to
   expand/collapse; long-press for the context menu (pinned sections have
   no menu and a dimmed chevron — Daily can never be deleted). */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SectionCard(
    title: String,
    expanded: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int? = null,
    done: Int = 0,
    total: Int = 0,
    status: TimePillStatus = TimePillStatus.Calm,
    time: String? = null,
    pinned: Boolean = false,
    onMenu: ((IntOffset) -> Unit)? = null,
    onIconClick: ((IntOffset) -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    val complete = total > 0 && done == total
    val targetFraction = if (total > 0) done.toFloat() / total else 0f
    val progress by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(DurBase),
        label = "progress",
    )

    var headerCenter by remember { mutableStateOf(Offset.Zero) }
    val shape = RoundedCornerShape(RadiusLg)
    Column(
        modifier = modifier
            .clip(shape)
            .background(Surface)
            .border(1.dp, Hairline, shape)
            .padding(horizontal = 16.dp, vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 56.dp)
                .onGloballyPositioned { coords ->
                    val pos = coords.positionInRoot()
                    headerCenter = Offset(
                        pos.x + coords.size.width / 2f,
                        pos.y + coords.size.height / 2f,
                    )
                }
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onToggle(!expanded) },
                    onLongClick = {
                        onMenu?.invoke(
                            IntOffset(headerCenter.x.toInt(), headerCenter.y.toInt())
                        )
                    },
                )
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(Space3),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var iconCenter by remember { mutableStateOf(Offset.Zero) }
            if (iconResId != null) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(RadiusSm))
                        .background(SurfaceSunken)
                        .onGloballyPositioned { coords ->
                            val pos = coords.positionInRoot()
                            iconCenter = Offset(
                                pos.x + coords.size.width / 2f,
                                pos.y + coords.size.height / 2f,
                            )
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onIconClick?.invoke(
                                IntOffset(iconCenter.x.toInt(), iconCenter.y.toInt())
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W500,
                        fontSize = 18.sp,
                        lineHeight = 22.sp,
                        letterSpacing = TrackSnug,
                    ),
                    color = Ink900,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (total > 0) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .widthIn(max = 132.dp)
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(RadiusPill))
                                .background(SurfaceSunken),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(RadiusPill))
                                    .background(if (complete) Primary else Ink400),
                            )
                        }
                        Text(
                            text = "$done / $total",
                            style = TextStyle(
                                fontFamily = GeneralSans,
                                fontWeight = FontWeight.W500,
                                fontSize = 13.sp,
                            ),
                            color = if (complete) Primary else Ink500,
                            maxLines = 1,
                        )
                    }
                }
            }
            if (time != null && status != TimePillStatus.Calm) {
                TimePill(status = status, label = time)
            }
            TaskIcon(
                icon = if (expanded) TaskIcons.ChevronDown else TaskIcons.ChevronRight,
                contentDescription = null,
                size = 20.dp,
                tint = Ink500,
                modifier = Modifier.alpha(if (pinned) PINNED_CHEVRON_ALPHA else 1f),
            )
        }
        if (content != null) {
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    spring(
                        dampingRatio = SpringDamping,
                        stiffness = SpringStiffness,
                        visibilityThreshold = IntSize.VisibilityThreshold,
                    )
                ),
                exit = shrinkVertically(
                    spring(
                        dampingRatio = SpringDamping,
                        stiffness = SpringStiffness,
                        visibilityThreshold = IntSize.VisibilityThreshold,
                    )
                ),
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Hairline),
                    )
                    Column(modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)) {
                        content()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionCardPreview() {
    TaskClusterTheme {
        SectionCard(
            title = "daily",
            expanded = true,
            onToggle = {},
            done = 3,
            total = 8,
            pinned = true,
        ) {
            TaskRow(title = "morning stretch", checked = true, onToggle = {})
            TaskRow(title = "review inbox", checked = false, onToggle = {}, divider = true)
        }
    }
}
