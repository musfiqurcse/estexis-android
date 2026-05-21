package com.estexis.addlisting.ui

data class AddListingFormState(
    val projectTitle: String = "",
    val askingPrice: String = "",
    val currency: String = "USD",
    val location: String = "",
    val bedrooms: Int = 3,
    val bathrooms: Int = 2,
    val kitchen: Int = 3,
    val livingArea: String = "2500",

    val detailedDescription: String = "",
    val assetClass: String = "Luxury Residential",
    val keyFeature: String = "Floor-to-ceiling Glass",
    val tone: String = "Architectural Digest",

    val mediaUris: List<String> = emptyList(),
    val instagramEnabled: Boolean = true,
    val facebookEnabled: Boolean = false,
    val tiktokEnabled: Boolean = true,
)

data class AddListingUiState(
    val currentStep: Int = 1,
    val isGeneratingAi: Boolean = false,
    val isLoading: Boolean = false,
)
