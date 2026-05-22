package com.estexis.core.ui.gds

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun StepIndicator(
    steps: List<Step>,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {

    if (steps.isEmpty()) return
    val safeStep = currentStep.coerceIn(1, steps.size)
    if (safeStep != currentStep) {
        Log.w("StepIndicator", "currentStep=$currentStep out of range 1..${steps.size}, clamped to $safeStep")
    }

    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        steps.forEachIndexed { index, step ->
            val isActive = step.number <= safeStep
            val circleColor = if (isActive) colors.primary else colors.surfaceDim

            Box(
                modifier = Modifier
                    .height(dimensions.sizes.x18)
                    .weight(1f),
            ) {
                TwoColorLine(
                    isActive = isActive,
                    currentStep = safeStep,
                    index = index,
                    lastIndex = steps.lastIndex,
                    modifier = Modifier
                        .padding(top = dimensions.sizes.x4)
                        .fillMaxWidth(),
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(dimensions.sizes.x8)
                            .clip(CircleShape)
                            .background(circleColor),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = step.number.toString(),
                            style = AppTextStyles.BodyText1SemiBold,
                            color = colors.white,
                        )
                    }

                    Text(
                        text = step.label,
                        style = AppTextStyles.BodyText3SemiBold,
                        color = if (isActive) colors.onBackground else colors.tertiary,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        modifier = Modifier
                            .padding(top = dimensions.spaces.x1)
                            .padding(horizontal = dimensions.spaces.x05)
                            .wrapContentWidth(),
                    )
                }
            }
        }
    }
}

@Composable
fun TwoColorLine(
    isActive: Boolean,
    currentStep: Int,
    index: Int,
    lastIndex: Int,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 2.dp
) {

    val colors = AppTheme.colors
    val lineColor = if (isActive) colors.primary else colors.border

    var firstHalfColor: Color
    var secondHalfColor = colors.border
    if (index == 0) {
        firstHalfColor = colors.onPrimary
        if (currentStep - 1 != 0)
            secondHalfColor = lineColor
    } else if (index == lastIndex) {
        secondHalfColor = colors.onPrimary
        firstHalfColor = if (lastIndex != currentStep - 1)
            colors.border
        else
            lineColor
    } else {
        firstHalfColor = lineColor
        secondHalfColor = lineColor
        if (index == currentStep - 1)
            secondHalfColor = colors.border
    }

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(strokeWidth)
    ) {
        val midX = size.width / 2f
        val y = size.height / 2f
        val stroke = strokeWidth.toPx()

        drawLine(
            color = firstHalfColor,
            start = Offset(0f, y),
            end = Offset(midX, y),
            strokeWidth = stroke
        )

        drawLine(
            color = secondHalfColor,
            start = Offset(midX, y),
            end = Offset(size.width, y),
            strokeWidth = stroke
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StepIndicatorPreview() {
    ExtexisAndroidTheme {
        StepIndicator(
            steps = listOf(
                Step(1, "Data\nCollection"),
                Step(2, "Document\nSubmission"),
                Step(3, "Media"),
            ),
            currentStep = 2,
        )
    }
}

data class Step(
    val number: Int,
    val label: String,
)
