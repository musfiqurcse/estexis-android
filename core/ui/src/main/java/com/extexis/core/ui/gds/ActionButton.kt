package com.extexis.core.ui.gds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.extexis.core.ui.theme.AppTextStyles
import com.extexis.core.ui.theme.AppTheme
import com.extexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: TextStyle = AppTextStyles.BodyMedium,
) {
    val colors = AppTheme.colors

    Text(
        text = text,
        style = style,
        color = colors.action,
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick,
        ),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ActionButtonPreview() {
    ExtexisAndroidTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ActionButton(text = "Forgot Password?", onClick = {})
            ActionButton(text = "Sign up now", onClick = {})
            ActionButton(text = "Login", onClick = {})
        }
    }
}
