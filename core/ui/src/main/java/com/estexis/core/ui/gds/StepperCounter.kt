package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun StepperCounter(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = AppTextStyles.BodyText3Bold,
                color = colors.onBackground,
            )
            Spacer(Modifier.height(dimensions.spaces.x1))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.sizes.x13)
                .clip(RoundedCornerShape(dimensions.radius.pill))
                .background(colors.background)
                .padding(horizontal = dimensions.spaces.x4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            StepperButton(
                symbol = "–",
                enabled = value > minValue,
                onClick = onDecrement,
            )

            Text(
                text = value.toString(),
                style = AppTextStyles.BodyText1Bold,
                color = colors.onBackground,
            )

            StepperButton(
                symbol = "+",
                enabled = value < maxValue,
                onClick = onIncrement,
            )
        }
    }
}

@Composable
private fun StepperButton(
    symbol: String,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions
    Box(
        modifier = Modifier
            .size(dimensions.sizes.x8)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = symbol,
            style = AppTextStyles.H3Bold,
            color = if (enabled) colors.onBackground else colors.surfaceDim,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun StepperCounterPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp).width(180.dp)) {
            StepperCounter(value = 3, onIncrement = {}, onDecrement = {}, label = "Bedrooms")
        }
    }
}
