package com.estexis.registration.data.mapper

import com.estexis.registration.data.request.RegistrationRequest
import com.estexis.registration.domain.RegistrationParams

fun RegistrationParams.toRequest() = RegistrationRequest(
    firstName = firstName,
    lastName = lastName,
    email = email,
    phoneNumber = phoneNumber,
    password = password,
    confirmPassword = confirmPassword,
    role = role,
    accountType = accountType,
    countryCode = countryCode
)
