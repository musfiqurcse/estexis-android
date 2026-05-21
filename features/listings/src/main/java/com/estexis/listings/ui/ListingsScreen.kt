package com.estexis.listings.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.estexis.core.ui.theme.AppTheme

@Composable
fun ListingsScreen() {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Listings",
            color = colors.onBackground,
        )
    }
}
