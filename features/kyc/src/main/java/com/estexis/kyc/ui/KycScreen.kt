package com.estexis.kyc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.navigation.DocumentUploadType
import com.estexis.core.ui.gds.AppIcon
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.CollapsibleSection
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground
import com.estexis.kyc.R

@Composable
fun KycScreen(
    state: KycState,
    event: (KycUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    when {
        state.isLoading -> KycShimmer()
        state.error != null -> KycRetry(
            message = state.error,
            onRetry = { event(KycUiEvent.Retry) },
        )
        else -> Column(modifier = Modifier.addBackground()) {

        AppTitleBar(
            onBackClick = {
                event(KycUiEvent.BackClicked)
            },
            title = stringResource(R.string.kyc_screen_title_kyc)
        )

        VerticalSpacer(dimensions.spaces.x4)

        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = painterResource(AppIcon.NID.resId),
                contentDescription = null,
                modifier = Modifier.size(dimensions.sizes.x12),
            )

            Text(
                text = stringResource(R.string.kyc_screen_personal_identification),
                style = AppTextStyles.BodyText1Bold,
                color = colors.tertiary,
                modifier = Modifier.padding(start = dimensions.spaces.x3),
            )
        }

        VerticalSpacer(dimensions.spaces.x4)

        CollapsibleSection(
            title = stringResource(R.string.kyc_screen_identification),
            expanded = state.identificationExpanded,
            onToggle = { event(KycUiEvent.ToggleIdentification) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensions.radius.large))
                    .background(colors.white),
                verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
            ) {
                KycItemRow(
                    image = AppIcon.NidVerification.resId,
                    title = stringResource(R.string.kyc_screen_nid_verification),
                    status = KycStatus.FAILED,
                    onClick = { event(KycUiEvent.IdentificationClicked(
                        IdentificationType.NID
                    )) },
                )

                KycItemRow(
                    image = AppIcon.Passport.resId,
                    title = stringResource(R.string.kyc_screen_passport_verification),
                    status = KycStatus.NOT_VERIFIED,
                    onClick = { event(KycUiEvent.IdentificationClicked(
                        IdentificationType.PASSPORT
                    )) },
                )

                KycItemRow(
                    image = AppIcon.License.resId,
                    title = stringResource(R.string.kyc_screen_driving_license_verification),
                    status = KycStatus.PENDING,
                    onClick = { event(KycUiEvent.IdentificationClicked(
                        IdentificationType.DRIVING_LICENSE
                    )) },
                    hasBorder = false
                )
            }
        }

        VerticalSpacer(dimensions.spaces.x4)

        CollapsibleSection(
            title = stringResource(R.string.kyc_screen_upload_verification_document),
            expanded = state.documentsExpanded,
            onToggle = { event(KycUiEvent.ToggleDocuments) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dimensions.radius.large))
                    .background(colors.background),
                verticalArrangement = Arrangement.spacedBy(dimensions.spaces.x3),
            ) {

                KycItemRow(
                    image = AppIcon.BankStatement.resId,
                    title = stringResource(R.string.kyc_screen_bank_account_statement),
                    status = KycStatus.VERIFIED,
                    onClick = { event(KycUiEvent.DocumentClicked(
                        DocumentUploadType.BANK_STATEMENT
                    )) },
                )

                KycItemRow(
                    image = AppIcon.Invoice.resId,
                    title = stringResource(R.string.kyc_screen_utility_bill),
                    status = KycStatus.NOT_VERIFIED,
                    onClick = { event(KycUiEvent.DocumentClicked(
                        DocumentUploadType.UTILITY_BILL
                    )) },
                    hasBorder = false
                )
            }
        }

        VerticalSpacer(dimensions.spaces.x8)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun KycScreenPreview() {
    ExtexisAndroidTheme {
        KycScreen(state = KycState(), event = {})
    }
}
