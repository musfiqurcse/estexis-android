package com.estexis.kyc.document.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

@Composable
fun DocumentUploadScreen(
    state: DocumentUploadState,
    event: (DocumentUploadUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.onPrimary)
            .statusBarsPadding()
            .imePadding(),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimensions.spaces.x4),
        ) {
            VerticalSpacer(dimensions.spaces.x2)

            AppTitleBar(
                onBackClick = { event(DocumentUploadUiEvent.BackClicked) },
                title = state.title,
            )

            VerticalSpacer(dimensions.spaces.x6)

            Text(
                text = "Upload Document",
                style = AppTheme.typography.H3Bold,
                color = colors.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            VerticalSpacer(dimensions.spaces.x1)

            Text(
                text = "Drop your file here to get stared",
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
                text = "Submit",
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
