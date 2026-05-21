package com.estexis.addlisting.ui.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.estexis.addlisting.ui.AddListingFormState
import com.estexis.addlisting.ui.AddListingUiEvent
import com.estexis.core.ui.gds.MediaThumbnail
import com.estexis.core.ui.gds.ToggleRow
import com.estexis.core.ui.gds.ToggleRowLeadingCircle
import com.estexis.core.ui.gds.UploadCard
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme

@Composable
fun MediaStep(
    formState: AddListingFormState,
    event: (AddListingUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Property Media",
            style = AppTheme.typography.H1Bold,
            color = colors.onBackground,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = "High-quality photos increase engagement by 40%.",
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x4)

        UploadCard(onClick = { event(AddListingUiEvent.MediaUploadClicked) })

        if (formState.mediaUris.isNotEmpty()) {
            VerticalSpacer(dimensions.spaces.x4)

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(dimensions.spaces.x2),
                verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x2),
                modifier = Modifier.fillMaxWidth().height(220.dp),
            ) {
                items(formState.mediaUris) { uri ->
                    MediaThumbnail(
                        onDelete = { event(AddListingUiEvent.MediaRemoved(uri)) },
                        modifier = Modifier.height(100.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(colors.surfaceDim),
                        )
                    }
                }
            }
        }

        VerticalSpacer(dimensions.spaces.x6)

        Text(
            text = "Social Media Channels",
            style = AppTextStyles.BodyText3Bold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x3)

        ToggleRow(
            title = "Instagram",
            subtitle = "Feed & Stories",
            checked = formState.instagramEnabled,
            onCheckedChange = { event(AddListingUiEvent.InstagramToggled) },
            leading = { ToggleRowLeadingCircle(color = Color(0xFFE1306C), letter = "I") },
        )

        VerticalSpacer(dimensions.spaces.x3)

        ToggleRow(
            title = "Facebook",
            subtitle = "Marketplace",
            checked = formState.facebookEnabled,
            onCheckedChange = { event(AddListingUiEvent.FacebookToggled) },
            leading = { ToggleRowLeadingCircle(color = Color(0xFF1877F2), letter = "F") },
        )

        VerticalSpacer(dimensions.spaces.x3)

        ToggleRow(
            title = "TikTok",
            subtitle = "Cinematic Tours",
            checked = formState.tiktokEnabled,
            onCheckedChange = { event(AddListingUiEvent.TikTokToggled) },
            leading = { ToggleRowLeadingCircle(color = Color(0xFF010101), letter = "T") },
        )
    }
}
