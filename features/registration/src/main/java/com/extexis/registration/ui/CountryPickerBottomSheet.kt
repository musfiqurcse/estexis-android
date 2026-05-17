package com.extexis.registration.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.extexis.core.ui.theme.AppTheme
import com.extexis.registration.domain.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerBottomSheet(
    countries: List<Country>,
    selectedCountry: Country?,
    sheetState: SheetState,
    onCountrySelected: (Country) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = colors.onPrimary,
    ) {
        Column(modifier = Modifier.padding(bottom = dimensions.spaces.x6)) {
            Text(
                text = "Select Country",
                // style = AppTheme.typography.BodyMedium,
                color = colors.onBackground,
                modifier = Modifier.padding(
                    horizontal = dimensions.spaces.x4,
                    vertical = dimensions.spaces.x2,
                ),
            )

            Spacer(Modifier.height(dimensions.spaces.x2))

            countries.forEachIndexed { index, country ->
                val isSelected = country.code == selectedCountry?.code

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCountrySelected(country) }
                        .padding(
                            horizontal = dimensions.spaces.x4,
                            vertical = dimensions.spaces.x4,
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = country.flag,
                        // style = AppTextStyles.Body
                    )

                    Spacer(Modifier.width(dimensions.spaces.x3))

                    Text(
                        text = country.name,
                        // style = AppTextStyles.Body,
                        color = if (isSelected) colors.primary else colors.onBackground,
                        modifier = Modifier.weight(1f),
                    )

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = colors.primary,
                            modifier = Modifier.size(dimensions.sizes.x5),
                        )
                    }
                }

                if (index < countries.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = dimensions.spaces.x4),
                        color = colors.surface,
                        thickness = 0.5.dp,
                    )
                }
            }
        }
    }
}
