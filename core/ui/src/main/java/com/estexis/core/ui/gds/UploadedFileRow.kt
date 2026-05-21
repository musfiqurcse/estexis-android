package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun UploadedFileRow(
    fileName: String,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(dimensions.radius.pill))
            .background(colors.background)
            .padding(
                horizontal = dimensions.spaces.x4,
                vertical = dimensions.spaces.x3,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.AssignmentTurnedIn,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(dimensions.sizes.x6),
        )

        Spacer(Modifier.width(dimensions.spaces.x3))

        Text(
            text = fileName,
            style = AppTextStyles.BodyText2Regular,
            color = colors.onBackground,
            modifier = Modifier.weight(1f),
        )

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "Remove",
                tint = colors.tertiary,
                modifier = Modifier.size(dimensions.sizes.x5),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF7F3ED)
@Composable
private fun UploadedFileRowPreview() {
    ExtexisAndroidTheme {
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(16.dp)) {
            UploadedFileRow(fileName = "bank_statement", onDelete = {})
        }
    }
}
