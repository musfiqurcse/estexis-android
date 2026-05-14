package com.extexis.core.android.util

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

// This solution for navigation didn't work.
// Try to solve this.

//https://medium.com/mercadona-tech/type-safety-in-navigation-compose-23c03e3d74a5

// somewhere globally
inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json { ignoreUnknownKeys = true }
): NavType<T> = object : NavType<T>(isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? {
        return bundle.getString(key)?.let { json.decodeFromString<T>(it) }
    }
    override fun parseValue(value: String): T {
        return json.decodeFromString(value)
    }
    override fun serializeAsValue(value: T): String =
        Uri.encode(json.encodeToString(value))
    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

