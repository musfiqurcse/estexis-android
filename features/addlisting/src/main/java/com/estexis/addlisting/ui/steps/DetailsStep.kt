package com.estexis.addlisting.ui.steps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.estexis.addlisting.ui.AddListingFormState
import com.estexis.addlisting.ui.AddListingUiEvent
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppButtonVariant
import com.estexis.core.ui.gds.KeyValueRow
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme

@Composable
fun DetailsStep(
    formState: AddListingFormState,
    event: (AddListingUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Tell us more about your project",
            style = AppTheme.typography.H1Bold,
            color = colors.onBackground,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = "Add detailed information or use AI generate.",
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x6)

        AIAssistantCard(onGenerate = { event(AddListingUiEvent.GenerateWithAiClicked) })

        VerticalSpacer(dimensions.spaces.x6)

        Text(
            text = "Detailed Description",
            style = AppTextStyles.BodyText3Bold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensions.radius.large))
                .background(colors.background)
                .padding(dimensions.spaces.x4),
        ) {
            Column {
                Text(
                    text = formState.detailedDescription.ifBlank {
                        "Add a detailed description or generate one with AI."
                    },
                    style = AppTextStyles.BodyText2Regular,
                    color = if (formState.detailedDescription.isBlank()) colors.surfaceDim
                    else colors.onBackground,
                )

                VerticalSpacer(dimensions.spaces.x3)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    ReGenerateButton(onClick = { event(AddListingUiEvent.GenerateWithAiClicked) })
                }
            }
        }

        VerticalSpacer(dimensions.spaces.x6)

        Text(
            text = "Property Context",
            style = AppTextStyles.BodyText3Bold,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensions.radius.large))
                .background(colors.background)
                .padding(dimensions.spaces.x4),
        ) {
            KeyValueRow("Asset Class", formState.assetClass)
            KeyValueRow("Key Feature", formState.keyFeature)
            KeyValueRow("Tone", formState.tone)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                ReGenerateButton(onClick = { event(AddListingUiEvent.ReGenerateContextClicked) })
            }
        }
    }
}

@Composable
private fun AIAssistantCard(onGenerate: () -> Unit) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensions.radius.large))
            .background(colors.background)
            .padding(dimensions.spaces.x4),
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null,
                    tint = colors.action,
                    modifier = Modifier.size(dimensions.sizes.x6),
                )
                Spacer(Modifier.width(dimensions.spaces.x2))
                Text(
                    text = "AI Assistant",
                    style = AppTextStyles.BodyText1Bold,
                    color = colors.onBackground,
                )
            }

            VerticalSpacer(dimensions.spaces.x2)

            Text(
                text = "Our AI will generate compelling description.",
                style = AppTextStyles.BodyText3Regular,
                color = colors.tertiary,
            )

            VerticalSpacer(dimensions.spaces.x3)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                AppButton(
                    text = "Generate with AI",
                    onClick = onGenerate,
                )
            }
        }
    }
}

@Composable
private fun ReGenerateButton(onClick: () -> Unit) {
    AppButton(
        text = "Re-Generate",
        onClick = onClick,
        variant = AppButtonVariant.OUTLINED,
    )
}
