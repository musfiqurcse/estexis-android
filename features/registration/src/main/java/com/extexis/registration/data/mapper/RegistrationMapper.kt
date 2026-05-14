package com.extexis.registration.data.mapper

import com.extexis.registration.data.request.RegistrationRequest
import com.extexis.registration.domain.RegistrationParams

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
