package com.journeytix.taskcluster.ui.components.forms

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.journeytix.taskcluster.ui.theme.DurFast
import com.journeytix.taskcluster.ui.theme.GeneralSans
import com.journeytix.taskcluster.ui.theme.HairlineStrong
import com.journeytix.taskcluster.ui.theme.Ink400
import com.journeytix.taskcluster.ui.theme.Ink500
import com.journeytix.taskcluster.ui.theme.Ink900
import com.journeytix.taskcluster.ui.theme.RadiusSm
import com.journeytix.taskcluster.ui.theme.Space2
import com.journeytix.taskcluster.ui.theme.Surface
import com.journeytix.taskcluster.ui.theme.TaskClusterTheme

private val InputTextStyle = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 22.sp, // 1.4
    color = Ink900,
)

internal val FieldLabelStyle = TextStyle(
    fontFamily = GeneralSans,
    fontWeight = FontWeight.W500,
    fontSize = 13.sp,
    color = Ink500,
)

@Composable
fun TaskTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    multiline: Boolean = false,
    rows: Int = 3,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val borderColor by animateColorAsState(
        targetValue = if (isFocused) Ink400 else HairlineStrong,
        animationSpec = tween(DurFast),
        label = "border",
    )
    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = FieldLabelStyle,
                modifier = Modifier.padding(bottom = Space2),
            )
        }
        val shape = RoundedCornerShape(RadiusSm)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = InputTextStyle,
            singleLine = !multiline,
            cursorBrush = SolidColor(Ink900),
            interactionSource = interactionSource,
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Surface, shape)
                        .border(1.dp, borderColor, shape)
                        .then(
                            if (multiline) {
                                Modifier
                                    .defaultMinSize(minHeight = (rows * 22 + 24).dp)
                                    .padding(horizontal = 14.dp, vertical = 12.dp)
                            } else {
                                Modifier
                                    .height(48.dp)
                                    .padding(horizontal = 14.dp)
                            }
                        ),
                    contentAlignment = if (multiline) Alignment.TopStart else Alignment.CenterStart,
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(text = placeholder, style = InputTextStyle.copy(color = Ink400))
                    }
                    innerTextField()
                }
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskTextFieldPreview() {
    TaskClusterTheme {
        TaskTextField(
            value = "",
            onValueChange = {},
            label = "Title",
            placeholder = "task title",
        )
    }
}
