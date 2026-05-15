package com.extexis.core.network

import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

suspend fun <T> executeSafeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        return if (response.isSuccessful) {
            response.body()?.let { body ->
                ApiResult.Success(body)
            } ?: ApiResult.Error(
                statusCode = response.code(),
                code = "EMPTY_BODY",
                message = "Response body is empty."
            )
        } else {
            parseErrorBody(
                statusCode = response.code(),
                errorBody = response.errorBody()?.string()
            )
        }
    } catch (_: IOException) {
        ApiResult.Error(
            code = "NO_INTERNET",
            message = "No internet connection."
        )
    }
//    catch (e: Exception) {
//        ApiResult.Error(
//            code = "UNKNOWN",
//            message = e.message ?: ""
//        )
//    }
}

private fun parseErrorBody(statusCode: Int, errorBody: String?): ApiResult.Error {
    if (errorBody.isNullOrEmpty()) {
        return ApiResult.Error(
            statusCode = statusCode,
            code = "ERROR_BODY_IS_EMPTY",
            message = "Unknown error"
        )
    }
    return try {
        val json = JSONObject(errorBody)
        val errorObject = json.optJSONObject("error")
        val code = errorObject?.optString("code") ?: ""
        val message = errorObject?.optString("message") ?: ""
        val details = errorObject?.optJSONObject("details")?.toMap()
        ApiResult.Error(statusCode = statusCode, code = code, message = message, details = details)
    } catch (_: Exception) {
        ApiResult.Error(
            statusCode = statusCode,
            code = "JSON_PARSING_ERROR",
            message = "Invalid error format"
        )
    }
}

fun JSONObject.toMap(): Map<String, Any> =
    keys().asSequence().associateWith { key ->
        when (val value = this.get(key)) {
            is JSONObject -> value.toMap()
            else -> value
        }
    }
