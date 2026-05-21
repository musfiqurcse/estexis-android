package com.estexis.core.ui.gds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun CollapsibleSection(
    title: String,
    expanded: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(dimensions.radius.pill))
                .border(
                    width = dimensions.borders.veryLow,
                    color = colors.primary,
                    shape = RoundedCornerShape(dimensions.radius.pill),
                )
                .clickable(onClick = onToggle)
                .padding(
                    horizontal = dimensions.spaces.x4,
                    vertical = dimensions.spaces.x3,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = AppTextStyles.BodyText1Bold,
                color = colors.onBackground,
                modifier = Modifier.weight(1f),
            )

            Box(
                modifier = Modifier
                    .size(dimensions.sizes.x8)
                    .clip(CircleShape)
                    .background(colors.surface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.onBackground,
                    modifier = Modifier.size(dimensions.sizes.x5),
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.padding(top = dimensions.spaces.x2)) {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CollapsibleSectionPreview() {
    ExtexisAndroidTheme {
        var expanded by remember { mutableStateOf(true) }
        Column(modifier = Modifier.padding(16.dp)) {
            CollapsibleSection(
                title = "Identification",
                expanded = expanded,
                onToggle = { expanded = !expanded },
            ) {
                Text("Section content goes here", modifier = Modifier.padding(8.dp))
            }
        }
    }
}
