package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.Icon
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
fun UploadCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Click here to upload",
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.sizes.x32)
            .clip(RoundedCornerShape(dimensions.radius.xlarge))
            .background(colors.background)
            .clickable(onClick = onClick)
            .padding(dimensions.spaces.x4),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.CloudUpload,
                contentDescription = null,
                tint = colors.tertiary,
                modifier = Modifier.size(dimensions.sizes.x10),
            )
            Spacer(Modifier.height(dimensions.spaces.x2))
            Text(
                text = label,
                style = AppTextStyles.BodyText2Regular,
                color = colors.tertiary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadCardPreview() {
    ExtexisAndroidTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            UploadCard(onClick = {})
        }
    }
}
