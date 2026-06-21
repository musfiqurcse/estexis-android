package com.estexis.core.ui.gds

data class Country(val code: String, val name: String, val flag: String)

val availableCountries = listOf(
    Country(code = "BD", name = "Bangladesh", flag = "🇧🇩"),
    Country(code = "DE", name = "Germany", flag = "🇩🇪"),
    Country(code = "AE", name = "UAE", flag = "🇦🇪"),
)
