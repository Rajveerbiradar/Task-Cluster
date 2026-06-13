package com.journeytix.taskcluster.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.theme.Canvas
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.Hairline
import com.journeytix.taskcluster.ui.theme.Ink300
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink700
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.Red

/* Page — full-screen page shell: back chevron + title header over a
   hairline, scrollable content below. From tcApp.jsx's Page wrapper. */
@Composable
fun Page(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Canvas)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 14.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TaskIconButton(
                icon = TaskIcons.ChevronLeft,
                label = "Back",
                size = 36.dp,
                onClick = onBack,
            )
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp,
                ),
                color = Ink900,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Hairline),
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(if (scrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier)
                .padding(start = 18.dp, end = 18.dp, top = 16.dp, bottom = 40.dp),
        ) {
            content()
        }
    }
}

/* PageGroup — a labelled settings group. */
@Composable
fun PageGroup(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.padding(bottom = 18.dp)) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = GeneralSans,
                fontWeight = FontWeight.W500,
                fontSize = 12.sp,
                letterSpacing = 0.04.em,
            ),
            color = Ink500,
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, bottom = 6.dp),
        )
        content()
    }
}

/* PageRow — title · optional value · chevron, divider-separated. */
@Composable
fun PageRow(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    value: String? = null,
    danger: Boolean = false,
    last: Boolean = false,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                )
                .padding(horizontal = 4.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = GeneralSans,
                    fontWeight = FontWeight.W400,
                    fontSize = 15.sp,
                    lineHeight = 20.sp,
                ),
                color = if (danger) Red else Ink900,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            if (value != null) {
                Text(
                    text = value,
                    style = TextStyle(
                        fontFamily = GeneralSans,
                        fontWeight = FontWeight.W400,
                        fontSize = 14.sp,
                    ),
                    color = Ink500,
                )
            }
            TaskIcon(
                icon = TaskIcons.ChevronRight,
                contentDescription = null,
                size = 16.dp,
                tint = Ink300,
            )
        }
        if (!last) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Hairline),
            )
        }
    }
}
