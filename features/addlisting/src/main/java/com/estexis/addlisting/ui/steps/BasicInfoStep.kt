package com.estexis.addlisting.ui.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.estexis.addlisting.ui.AddListingFormState
import com.estexis.addlisting.ui.AddListingUiEvent
import com.estexis.core.ui.gds.AppTextField
import com.estexis.core.ui.gds.DropdownPickerField
import com.estexis.core.ui.gds.StepperCounter
import com.estexis.core.ui.gds.VerticalSpacer
import com.estexis.core.ui.theme.AppTextStyles
import com.estexis.core.ui.theme.AppTheme
import com.estexis.core.ui.theme.ExtexisAndroidTheme

@Composable
fun BasicInfoStep(
    formState: AddListingFormState,
    event: (AddListingUiEvent) -> Unit,
) {
    val colors = AppTheme.colors
    val dimensions = AppTheme.dimensions

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Let's start with the basics.",
            style = AppTheme.typography.H1Bold,
            color = colors.onBackground,
        )

        VerticalSpacer(dimensions.spaces.x2)

        Text(
            text = "Tell us about the property's core identity.",
            style = AppTextStyles.BodyText2Regular,
            color = colors.tertiary,
        )

        VerticalSpacer(dimensions.spaces.x6)

        AppTextField(
            value = formState.projectTitle,
            onValueChange = { event(AddListingUiEvent.ProjectTitleChanged(it)) },
            label = "Project Title",
            placeholder = "e.g. Modern Penthouse with Skyline Views",
        )

        VerticalSpacer(dimensions.spaces.x4)

        Row(modifier = Modifier.fillMaxWidth()) {
            AppTextField(
                value = formState.askingPrice,
                onValueChange = { event(AddListingUiEvent.AskingPriceChanged(it)) },
                label = "Asking Price",
                placeholder = "$ 0.00",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f),
            )

            Spacer(Modifier.width(dimensions.spaces.x3))

            DropdownPickerField(
                value = formState.currency,
                options = listOf("USD", "EUR", "BDT", "AED"),
                onOptionSelected = { event(AddListingUiEvent.CurrencyChanged(it)) },
                label = "Currency",
                modifier = Modifier.weight(1f),
            )
        }

        VerticalSpacer(dimensions.spaces.x4)

        AppTextField(
            value = formState.location,
            onValueChange = { event(AddListingUiEvent.LocationChanged(it)) },
            label = "Location",
            placeholder = "Enter property address",
        )

        VerticalSpacer(dimensions.spaces.x4)

        Row(modifier = Modifier.fillMaxWidth()) {
            StepperCounter(
                value = formState.bedrooms,
                onIncrement = { event(AddListingUiEvent.IncrementBedrooms) },
                onDecrement = { event(AddListingUiEvent.DecrementBedrooms) },
                label = "Bedrooms",
                modifier = Modifier.weight(1f),
            )

            Spacer(Modifier.width(dimensions.spaces.x3))

            StepperCounter(
                value = formState.bathrooms,
                onIncrement = { event(AddListingUiEvent.IncrementBathrooms) },
                onDecrement = { event(AddListingUiEvent.DecrementBathrooms) },
                label = "Bathrooms",
                modifier = Modifier.weight(1f),
            )
        }

        VerticalSpacer(dimensions.spaces.x4)

        Row(modifier = Modifier.fillMaxWidth()) {
            StepperCounter(
                value = formState.kitchen,
                onIncrement = { event(AddListingUiEvent.IncrementKitchen) },
                onDecrement = { event(AddListingUiEvent.DecrementKitchen) },
                label = "Kitchen",
                modifier = Modifier.weight(1f),
            )

            Spacer(Modifier.width(dimensions.spaces.x3))

            AppTextField(
                value = formState.livingArea,
                onValueChange = { event(AddListingUiEvent.LivingAreaChanged(it)) },
                label = "Living Area (SQFT)",
                placeholder = "2,500",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBasicInfoStep() {
    ExtexisAndroidTheme {
        BasicInfoStep(
            formState = AddListingFormState(),
            event = {}
        )
    }
}
