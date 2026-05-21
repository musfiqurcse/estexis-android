package com.estexis.addlisting.ui

sealed class AddListingUiEvent {

    data class ProjectTitleChanged(val value: String) : AddListingUiEvent()
    data class AskingPriceChanged(val value: String) : AddListingUiEvent()
    data class CurrencyChanged(val value: String) : AddListingUiEvent()
    data class LocationChanged(val value: String) : AddListingUiEvent()
    data class LivingAreaChanged(val value: String) : AddListingUiEvent()

    object IncrementBedrooms : AddListingUiEvent()
    object DecrementBedrooms : AddListingUiEvent()
    object IncrementBathrooms : AddListingUiEvent()
    object DecrementBathrooms : AddListingUiEvent()
    object IncrementKitchen : AddListingUiEvent()
    object DecrementKitchen : AddListingUiEvent()

    data class DetailedDescriptionChanged(val value: String) : AddListingUiEvent()
    object GenerateWithAiClicked : AddListingUiEvent()
    object ReGenerateContextClicked : AddListingUiEvent()

    object MediaUploadClicked : AddListingUiEvent()
    data class MediaRemoved(val uri: String) : AddListingUiEvent()
    object InstagramToggled : AddListingUiEvent()
    object FacebookToggled : AddListingUiEvent()
    object TikTokToggled : AddListingUiEvent()

    object BackClicked : AddListingUiEvent()
    object ContinueClicked : AddListingUiEvent()
    object SaveDraftClicked : AddListingUiEvent()
    object PublishClicked : AddListingUiEvent()
}
