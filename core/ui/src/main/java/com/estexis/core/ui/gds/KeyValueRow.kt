package com.estexis.core.ui.gds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun KeyValueRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensions.spaces.x2),
    ) {
        Text(
            text = label,
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = value,
            style = AppTextStyles.BodyText2Bold,
            color = colors.onBackground,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun KeyValueRowPreview() {
    ExtexisAndroidTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            KeyValueRow("Asset Class", "Luxury Residential")
            KeyValueRow("Key Feature", "Floor-to-ceiling Glass")
            KeyValueRow("Tone", "Architectural Digest")
        }
    }
}
