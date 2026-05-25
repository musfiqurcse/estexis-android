package com.estexis.kyc.documentupload.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.core.navigation.DocumentUploadType
import com.estexis.core.ui.gds.AppButton
import com.estexis.core.ui.gds.AppTitleBar
import com.estexis.core.ui.gds.BrowseFilesCard
import com.estexis.core.ui.gds.UploadedFileRow
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme
import com.estexis.core.ui.util.addBackground
import com.estexis.kyc.R

@Composable
fun DocumentUploadScreen(
    state: DocumentUploadState,
    event: (DocumentUploadUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .addBackground()
            .navigationBarsPadding()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {

            AppTitleBar(
                onBackClick = { event(DocumentUploadUiEvent.BackClicked) },
                title = state.title,
            )

            VerticalSpacer(dimensions.spaces.x6)

            Text(
                text = stringResource(R.string.document_upload_screen_upload_document),
                style = AppTheme.typography.BodyText1SemiBold,
                color = colors.tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            VerticalSpacer(dimensions.spaces.x2)

            Text(
                text = stringResource(R.string.document_upload_screen_drop_your_file_here_to_get_stared),
                style = AppTextStyles.BodyText2Regular,
                color = colors.tertiary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            VerticalSpacer(dimensions.spaces.x4)

            BrowseFilesCard(
                onBrowseClick = { event(DocumentUploadUiEvent.BrowseFilesClicked) },
            )

            VerticalSpacer(dimensions.spaces.x4)

            if (state.uploadedFileName != null) {
                UploadedFileRow(
                    fileName = state.uploadedFileName,
                    onDelete = { event(DocumentUploadUiEvent.DeleteFileClicked) },
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.onPrimary)
                .padding(horizontal = dimensions.spaces.x4, vertical = dimensions.spaces.x4),
        ) {
            AppButton(
                text = stringResource(R.string.document_upload_screen_cta_submit),
                onClick = { event(DocumentUploadUiEvent.SubmitClicked) },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.uploadedFileName != null,
                isLoading = state.isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BankStatementEmptyPreview() {
    ExtexisAndroidTheme {
        DocumentUploadScreen(
            state = DocumentUploadState(type = DocumentUploadType.BANK_STATEMENT),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BankStatementFilledPreview() {
    ExtexisAndroidTheme {
        DocumentUploadScreen(
            state = DocumentUploadState(
                type = DocumentUploadType.BANK_STATEMENT,
                uploadedFileName = "bank_statement",
            ),
            event = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UtilityBillEmptyPreview() {
    ExtexisAndroidTheme {
        DocumentUploadScreen(
            state = DocumentUploadState(type = DocumentUploadType.UTILITY_BILL),
            event = {},
        )
    }
}
