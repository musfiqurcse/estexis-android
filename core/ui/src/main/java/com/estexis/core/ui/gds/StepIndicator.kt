package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            steps.forEachIndexed { index, step ->
                val isActive = step.number <= currentStep
                val circleColor = if (isActive) colors.primary else colors.border
                val numberColor = if (isActive) colors.white else colors.tertiary

                Box(
                    modifier = Modifier
                        .size(dimensions.sizes.x8)
                        .clip(CircleShape)
                        .background(circleColor),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = step.number.toString(),
                        style = AppTextStyles.BodyText2Bold,
                        color = numberColor,
                    )
                }

                if (index < steps.size - 1) {
                    val lineColor = if (step.number < currentStep) colors.primary else colors.border
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(dimensions.borders.low)
                            .background(lineColor),
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensions.spaces.x2),
        ) {
            steps.forEachIndexed { index, step ->
                val isActive = step.number <= currentStep
                Text(
                    text = step.label,
                    style = AppTextStyles.BodyText3Bold,
                    color = if (isActive) colors.onBackground else colors.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(dimensions.sizes.x18),
                )
                if (index < steps.size - 1) {
                    Box(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StepIndicatorPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            StepIndicator(
                steps = listOf(
                    Step(1, "Basic\ninfo"),
                    Step(2, "Details"),
                    Step(3, "Media"),
                ),
                currentStep = 2,
            )
        }
    }
}

data class Step(
    val number: Int,
    val label: String,
)
