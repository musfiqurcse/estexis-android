package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.estexis.core.ui.theme.AppTheme

@Composable
fun AppHorizontalDivider(
    thickNess: Dp = AppTheme.dimensions.sizes.x0,
    background: Color = AppTheme.colors.onSecondary
) {
    Spacer(
        modifier = Modifier
            .height(thickNess)
            .fillMaxWidth()
            .background(background)
    )
}
