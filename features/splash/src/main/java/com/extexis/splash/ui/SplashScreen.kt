package com.extexis.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.AppIcon
import com.extexis.core.ui.theme.AppRadius
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.splash.R

@Composable
fun SplashScreen(
    event: (SplashScreenUiEvent) -> Unit,
) {

    val colors = AppTheme.colors

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(AppIcon.SplashBG.resId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Transparent,
                        0.45f to Color.Black.copy(alpha = 0.15f),
                        1.0f to Color.Black.copy(alpha = 0.88f),
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = AppTheme.dimensions.sizes.x6),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.welcome_to),
                style = AppTheme.typography.Title,
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(AppTheme.dimensions.sizes.x2))

            Image(
                painter = painterResource(AppIcon.LogoGreen.resId),
                contentDescription = "Grihoo",
                modifier = Modifier.height(AppTheme.dimensions.sizes.x10),
            )

            Spacer(Modifier.height(AppTheme.dimensions.sizes.x4))

            Text(
                text = stringResource(R.string.smart_way_to_find_your_dream_property),
                style = AppTextStyles.BodyLarge,
                color = Color.White.copy(alpha = 0.85f),
            )

            Spacer(Modifier.height(AppTheme.dimensions.sizes.x8))

            GetStartedButton(
                onClick = { event(SplashScreenUiEvent.GetStarted) },
                primaryColor = colors.primary,
            )

            Spacer(Modifier.height(AppTheme.dimensions.sizes.x4))
        }
    }
}

@Composable
private fun GetStartedButton(
    onClick: () -> Unit,
    primaryColor: Color,
) {
    Box(
        modifier = Modifier
            .height(AppTheme.dimensions.sizes.x15)
            .clip(RoundedCornerShape(AppRadius.pill))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimensions.spaces.x5,
                vertical = AppTheme.dimensions.spaces.x3
            )
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = stringResource(R.string.get_started),
                style = AppTextStyles.ButtonLabel,
                color = Color.Black,
            )
        }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ){
            Box(
                modifier = Modifier
                    .size(AppTheme.dimensions.sizes.x9)
                    .clip(CircleShape)
                    .background(primaryColor),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(AppTheme.dimensions.sizes.x5),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreen(
        event = {}
    )
}
