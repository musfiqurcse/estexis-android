package com.estexis.addlisting.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddListingViewModel @Inject constructor() : ViewModel() {

    var formState by mutableStateOf(AddListingFormState())
        private set

    private val _uiState = MutableStateFlow(AddListingUiState())
    val uiState: StateFlow<AddListingUiState> = _uiState

    fun onEvent(event: AddListingUiEvent) {
        when (event) {
            is AddListingUiEvent.ProjectTitleChanged -> formState = formState.copy(projectTitle = event.value)
            is AddListingUiEvent.AskingPriceChanged -> formState = formState.copy(askingPrice = event.value)
            is AddListingUiEvent.CurrencyChanged -> formState = formState.copy(currency = event.value)
            is AddListingUiEvent.LocationChanged -> formState = formState.copy(location = event.value)
            is AddListingUiEvent.LivingAreaChanged -> formState = formState.copy(livingArea = event.value)

            AddListingUiEvent.IncrementBedrooms -> formState = formState.copy(bedrooms = formState.bedrooms + 1)
            AddListingUiEvent.DecrementBedrooms ->
                formState = formState.copy(bedrooms = (formState.bedrooms - 1).coerceAtLeast(0))
            AddListingUiEvent.IncrementBathrooms -> formState = formState.copy(bathrooms = formState.bathrooms + 1)
            AddListingUiEvent.DecrementBathrooms ->
                formState = formState.copy(bathrooms = (formState.bathrooms - 1).coerceAtLeast(0))
            AddListingUiEvent.IncrementKitchen -> formState = formState.copy(kitchen = formState.kitchen + 1)
            AddListingUiEvent.DecrementKitchen ->
                formState = formState.copy(kitchen = (formState.kitchen - 1).coerceAtLeast(0))

            is AddListingUiEvent.DetailedDescriptionChanged ->
                formState = formState.copy(detailedDescription = event.value)
            AddListingUiEvent.GenerateWithAiClicked -> {
                formState = formState.copy(
                    detailedDescription = "This property project offers a modern and comfortable living " +
                        "experience, designed with high-quality construction and thoughtful planning. " +
                        "Located in a prime area, it provides easy access to essential facilities such as " +
                        "schools, hospitals, and shopping centers. The project includes well-designed spaces, " +
                        "essential amenities, and a secure environment, making it an ideal choice for both " +
                        "families and investors."
                )
            }
            AddListingUiEvent.ReGenerateContextClicked -> Unit

            AddListingUiEvent.MediaUploadClicked -> Unit
            is AddListingUiEvent.MediaRemoved ->
                formState = formState.copy(mediaUris = formState.mediaUris - event.uri)
            AddListingUiEvent.InstagramToggled ->
                formState = formState.copy(instagramEnabled = !formState.instagramEnabled)
            AddListingUiEvent.FacebookToggled ->
                formState = formState.copy(facebookEnabled = !formState.facebookEnabled)
            AddListingUiEvent.TikTokToggled ->
                formState = formState.copy(tiktokEnabled = !formState.tiktokEnabled)

            AddListingUiEvent.BackClicked -> goBack()
            AddListingUiEvent.ContinueClicked -> goForward()
            AddListingUiEvent.SaveDraftClicked -> Unit
            AddListingUiEvent.PublishClicked -> Unit
        }
    }

    private fun goForward() {
        _uiState.update { it.copy(currentStep = (it.currentStep + 1).coerceAtMost(TOTAL_STEPS)) }
    }

    private fun goBack() {
        _uiState.update { it.copy(currentStep = (it.currentStep - 1).coerceAtLeast(1)) }
    }

    companion object {
        const val TOTAL_STEPS = 3
    }
}
