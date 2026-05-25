package com.estexis.core.ui.gds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun DocumentCaptureCard(
    label: String,
    captured: Boolean,
    placeholderImage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = AppTextStyles.BodyText2SemiBold,
            color = colors.tertiary,
            modifier = Modifier.padding(bottom = dimensions.spaces.x2),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensions.sizes.x50)
                .clip(RoundedCornerShape(dimensions.radius.large))
                .clickable(onClick = onClick),
        ) {
            if (captured) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.secondaryContainer),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Captured",
                        tint = colors.secondary,
                        modifier = Modifier.size(dimensions.sizes.x12),
                    )
                }
            } else {
                Image(
                    painter = painterResource(placeholderImage),
                    contentDescription = "Document Placeholder Image",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(dimensions.sizes.x8)
                    .clip(CircleShape)
                    .background(colors.onTertiary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = null,
                    tint = colors.white,
                    modifier = Modifier.size(dimensions.sizes.x5),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentCaptureCardEmptyPreview() {
    ExtexisAndroidTheme {
        DocumentCaptureCard(
            label = "Cover Page",
            captured = false,
            placeholderImage = AppIcon.CoverPage.resId,
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DocumentCaptureCardCapturedPreview() {
    ExtexisAndroidTheme {
        DocumentCaptureCard(
            label = "Data Page",
            captured = true,
            placeholderImage = AppIcon.CoverPage.resId,
            onClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}
