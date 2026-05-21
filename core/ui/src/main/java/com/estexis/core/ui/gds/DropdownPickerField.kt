package com.estexis.core.ui.gds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
fun DropdownPickerField(
    value: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        if (label != null) {
            Text(
                text = label,
                style = AppTextStyles.BodyText3Bold,
                color = colors.onBackground,
            )
            androidx.compose.foundation.layout.Spacer(Modifier.height(dimensions.spaces.x1))
        }

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensions.sizes.x13)
                    .clip(RoundedCornerShape(dimensions.radius.pill))
                    .background(colors.background)
                    .clickable { expanded = true }
                    .padding(horizontal = dimensions.spaces.x4),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = value,
                    style = AppTextStyles.BodyText1Bold,
                    color = colors.onBackground,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.tertiary,
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option, style = AppTextStyles.BodyText1Regular) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DropdownPickerFieldPreview() {
    ExtexisAndroidTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            DropdownPickerField(
                value = "USD",
                options = listOf("USD", "EUR", "BDT", "AED"),
                onOptionSelected = {},
                label = "Currency",
            )
        }
    }
}
