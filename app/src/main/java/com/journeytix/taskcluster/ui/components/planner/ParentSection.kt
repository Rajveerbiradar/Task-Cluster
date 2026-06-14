package com.journeytix.taskcluster.ui.components.planner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.data.image.ParentImage
import com.journeytix.taskcluster.ui.components.core.TaskIcon
import com.journeytix.taskcluster.ui.components.core.TaskIcons
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink600
import com.journeytix.taskcluster.ui.theme.ParentFill
import com.journeytix.taskcluster.ui.theme.RadiusXl
import com.journeytix.taskcluster.ui.theme.SpringDamping
import com.journeytix.taskcluster.ui.theme.SpringStiffness
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme
import com.journeytix.taskcluster.ui.theme.TrackSnug

/* ParentSection — a light container that visibly holds its SectionCards.
   Tap the header to toggle children; long-press for the context menu. */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParentSection(
    title: String,
    expanded: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    count: String? = null,
    pinned: Boolean = false,
    favourite: Boolean = false,
    emoji: String? = null,
    status: TimePillStatus = TimePillStatus.Calm,
    time: String? = null,
    onMenu: ((IntOffset) -> Unit)? = null,
    onEmojiClick: ((IntOffset) -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
) {
    var headerCenter by remember { mutableStateOf(Offset.Zero) }
    val shape = RoundedCornerShape(RadiusXl)
    Column(
        modifier = modifier
            .clip(shape)
            .background(ParentFill)
            .border(1.5.dp, Hairline, shape)
            .padding(start = 8.dp, end = 8.dp, top = 6.dp, bottom = 10.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 44.dp)
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
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var emojiCenter by remember { mutableStateOf(Offset.Zero) }
            if (emoji != null) {
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coords ->
                            val pos = coords.positionInRoot()
                            emojiCenter = Offset(
                                pos.x + coords.size.width / 2f,
                                pos.y + coords.size.height / 2f,
                            )
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onEmojiClick?.invoke(
                                IntOffset(emojiCenter.x.toInt(), emojiCenter.y.toInt())
                            )
                        },
                ) {
                    if (ParentImage.isImage(emoji)) {
                        val bitmap = remember(emoji) {
                            BitmapFactory.decodeFile(ParentImage.pathOf(emoji))?.asImageBitmap()
                        }
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                            )
                        } else {
                            Text(text = "🏷️", fontSize = 18.sp) // file missing — neutral fallback
                        }
                    } else {
                        Text(text = emoji, fontSize = 18.sp)
                    }
                }
            } else if (pinned) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Ink400),
                )
            } else if (onEmojiClick != null) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Ink400.copy(alpha = 0.2f))
                        .onGloballyPositioned { coords ->
                            val pos = coords.positionInRoot()
                            emojiCenter = Offset(
                                pos.x + coords.size.width / 2f,
                                pos.y + coords.size.height / 2f,
                            )
                        }
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onEmojiClick.invoke(
                                IntOffset(emojiCenter.x.toInt(), emojiCenter.y.toInt())
                            )
                        },
                )
            }
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                    letterSpacing = TrackSnug,
                ),
                color = Ink600,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            if (time != null && status != TimePillStatus.Calm) {
                TimePill(status = status, label = time)
            }
            if (count != null) {
                Text(
                    text = count,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W500,
                        fontSize = 13.sp,
                    ),
                    color = Ink400,
                )
            }
            if (favourite) {
                TaskIcon(icon = TaskIcons.Pin, contentDescription = null, size = 14.dp, tint = Ink400)
            }
            TaskIcon(
                icon = if (expanded) TaskIcons.ChevronDown else TaskIcons.ChevronRight,
                contentDescription = null,
                size = 20.dp,
                tint = Ink400,
            )
        }
        if (content != null) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(spring(stiffness = SpringStiffness)) + expandVertically(
                    spring(
                        dampingRatio = SpringDamping,
                        stiffness = SpringStiffness,
                        visibilityThreshold = IntSize.VisibilityThreshold,
                    )
                ),
                exit = fadeOut(spring(stiffness = SpringStiffness)) + shrinkVertically(
                    spring(
                        dampingRatio = SpringDamping,
                        stiffness = SpringStiffness,
                        visibilityThreshold = IntSize.VisibilityThreshold,
                    )
                ),
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    content()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ParentSectionPreview() {
    TaskClusterTheme {
        ParentSection(
            title = "work",
            expanded = true,
            onToggle = {},
            count = "12",
            favourite = true,
        ) {
            SectionCard(title = "sprint 24", expanded = false, onToggle = {}, done = 2, total = 5)
        }
    }
}
