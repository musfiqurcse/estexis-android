package com.extexis.welcome.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.extexis.core.ui.gds.AppIcon
import com.extexis.core.ui.gds.VerticalSpacer
import com.extexis.core.ui.theme.AppRadius
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.welcome.R

@Composable
fun WelcomeScreen(
    event: (WelcomeScreenUiEvent) -> Unit,
) {
    val colors = AppTheme.colors

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary),
        contentAlignment = Alignment.BottomCenter
    ) {

        Image(
            painter = painterResource(AppIcon.SplashBG.resId),
            contentDescription = null,
            modifier = Modifier.wrapContentHeight().fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = AppTheme.dimensions.sizes.x6),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.welcome_to),
                style = AppTheme.typography.H2SemiBold,
                color = AppTheme.colors.primary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            VerticalSpacer(AppTheme.dimensions.sizes.x2)

            Image(
                painter = painterResource(AppIcon.LogoGreen.resId),
                contentDescription = "Grihoo Logo",
                modifier = Modifier.height(AppTheme.dimensions.sizes.x10),
            )

            VerticalSpacer(AppTheme.dimensions.sizes.x4)

            Text(
                text = stringResource(R.string.smart_way_to_find_your_dream_property),
                style = AppTextStyles.BodyText1SemiBold,
                color = AppTheme.colors.tertiary,
            )

            VerticalSpacer(AppTheme.dimensions.sizes.x8)

            GetStartedButton(
                onClick = { event(WelcomeScreenUiEvent.GetStarted) }
            )
            VerticalSpacer(AppTheme.dimensions.sizes.x41)
        }
    }
}

@Composable
private fun GetStartedButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(AppTheme.dimensions.sizes.x15)
            .clip(RoundedCornerShape(AppRadius.pill))
            .background(AppTheme.colors.secondary)
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimensions.spaces.x5,
                vertical = AppTheme.dimensions.spaces.x3
            )
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.get_started),
                style = AppTextStyles.BodyText1Bold,
                color = AppTheme.colors.white,
            )
        }

        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .size(AppTheme.dimensions.sizes.x9)
                    .clip(CircleShape)
                    .background(AppTheme.colors.white),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(AppIcon.IcArrowForward.resId),
                    contentDescription = null,
                    tint = AppTheme.colors.primary,
                    modifier = Modifier.size(AppTheme.dimensions.sizes.x5),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen(
        event = {}
    )
}
