package com.estexis.core.ui.gds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme

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
                style = AppTextStyles.BodyText1Bold,
                color = colors.onBackground,
                modifier = Modifier.padding(
                    horizontal = dimensions.spaces.x4,
                    vertical = dimensions.spaces.x2,
                ),
            )

            Spacer(Modifier.height(dimensions.spaces.x2))

            LazyColumn {
                items(countries) { country ->
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
                        Text(text = country.flag)

                        Spacer(Modifier.width(dimensions.spaces.x3))

                        Text(
                            text = country.name,
                            style = AppTextStyles.BodyText2Regular,
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
