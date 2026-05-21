package com.estexis.kyc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.ui.gds.AppIconButton
import com.estexis.core.ui.gds.CollapsibleSection
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun KycScreen(
    state: KycState,
    event: (KycUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = dimensions.spaces.x4),
    ) {
        VerticalSpacer(dimensions.spaces.x4)

        Row(verticalAlignment = Alignment.CenterVertically) {
            AppIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                onClick = { event(KycUiEvent.BackClicked) },
                contentDescription = "Back",
            )
            Text(
                text = "KYC",
                style = AppTheme.typography.H3Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(start = dimensions.spaces.x2),
            )
        }

        VerticalSpacer(dimensions.spaces.x4)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.ContactPage,
                contentDescription = null,
                tint = colors.secondary,
                modifier = Modifier.size(dimensions.sizes.x10),
            )
            Text(
                text = "Personal Identification",
                style = AppTextStyles.BodyText1Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(start = dimensions.spaces.x3),
            )
        }

        VerticalSpacer(dimensions.spaces.x4)

        CollapsibleSection(
            title = "Identification",
            expanded = state.identificationExpanded,
            onToggle = { event(KycUiEvent.ToggleIdentification) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensions.radius.large))
                    .background(colors.background)
                    .padding(dimensions.spaces.x3),
                verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
            ) {
                state.identificationItems.forEach { item ->
                    KycItemRow(
                        icon = iconFor(item.type),
                        title = item.title,
                        status = item.status,
                        onClick = { event(KycUiEvent.IdentificationClicked(item.type)) },
                    )
                }
            }
        }

        VerticalSpacer(dimensions.spaces.x4)

        CollapsibleSection(
            title = "Upload Verification Document",
            expanded = state.documentsExpanded,
            onToggle = { event(KycUiEvent.ToggleDocuments) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensions.radius.large))
                    .background(colors.background)
                    .padding(dimensions.spaces.x3),
                verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
            ) {
                state.documentItems.forEach { item ->
                    KycItemRow(
                        icon = iconFor(item.type),
                        title = item.title,
                        status = item.status,
                        onClick = { event(KycUiEvent.DocumentClicked(item.type)) },
                    )
                }
            }
        }

        VerticalSpacer(dimensions.spaces.x8)
    }
}

@Composable
private fun KycItemRow(
    icon: ImageVector,
    title: String,
    status: KycStatus,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = dimensions.spaces.x2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.tertiary,
            modifier = Modifier.size(dimensions.sizes.x9),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = dimensions.spaces.x3),
        ) {
            Text(
                text = title,
                style = AppTextStyles.BodyText2Bold,
                color = colors.onBackground,
            )
            Text(
                text = statusLabel(status),
                style = AppTextStyles.BodyText3Bold,
                color = statusColor(status),
            )
        }

        StatusBadge(status)
    }
}

@Composable
private fun StatusBadge(status: KycStatus) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    val (icon, tint) = when (status) {
        KycStatus.VERIFIED -> Icons.Default.CheckCircle to colors.success
        KycStatus.FAILED -> Icons.Default.Cancel to colors.error
        KycStatus.NOT_VERIFIED -> Icons.Default.Warning to colors.primary
        KycStatus.PENDING -> Icons.Default.HourglassEmpty to colors.action
    }

    Box(
        modifier = Modifier
            .size(dimensions.sizes.x7)
            .clip(CircleShape)
            .background(tint),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(dimensions.sizes.x5),
        )
    }
}

@Composable
private fun statusColor(status: KycStatus): Color = when (status) {
    KycStatus.VERIFIED -> AppTheme.colors.success
    KycStatus.FAILED -> AppTheme.colors.error
    KycStatus.NOT_VERIFIED -> AppTheme.colors.primary
    KycStatus.PENDING -> AppTheme.colors.action
}

private fun statusLabel(status: KycStatus): String = when (status) {
    KycStatus.VERIFIED -> "Verified"
    KycStatus.FAILED -> "Verified Failed"
    KycStatus.NOT_VERIFIED -> "Not Verified"
    KycStatus.PENDING -> "Pending"
}

private fun iconFor(type: IdentificationType): ImageVector = when (type) {
    IdentificationType.NID -> Icons.Default.Badge
    IdentificationType.PASSPORT -> Icons.Default.ContactPage
    IdentificationType.DRIVING_LICENSE -> Icons.Default.AssignmentInd
}

private fun iconFor(type: DocumentType): ImageVector = when (type) {
    DocumentType.BANK_STATEMENT -> Icons.Default.AccountBalance
    DocumentType.UTILITY_BILL -> Icons.Default.ReceiptLong
}

@Preview(showBackground = true)
@Composable
private fun KycScreenPreview() {
    ExtexisAndroidTheme {
        KycScreen(state = KycState(), event = {})
    }
}
