package com.estexis.core.network

import com.estexis.core.common.ApiResult
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

@Suppress("TooGenericExceptionCaught")
suspend fun <T> executeSafeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
    return try {
        val response = apiCall()
        val edf = response.isSuccessful
        print(edf)
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
    } catch (ex: IOException) {
        val message = ex.localizedMessage
        ApiResult.Error(
            code = "NO_INTERNET",
            message = message ?: ""
        )
    } catch (e: Exception) {
        ApiResult.Error(
            code = "UNKNOWN",
            message = e.message ?: ""
        )
    }
}

@Suppress("TooGenericExceptionCaught")
suspend fun executeNoContentApiCall(apiCall: suspend () -> Response<Unit>): ApiResult<Unit> {
    return try {
        val response = apiCall()
        if (response.isSuccessful) ApiResult.Success(Unit)
        else parseErrorBody(statusCode = response.code(), errorBody = response.errorBody()?.string())
    } catch (_: IOException) {
        ApiResult.Error(code = "NO_INTERNET", message = "No internet connection.")
    } catch (e: Exception) {
        ApiResult.Error(code = "UNKNOWN", message = e.message ?: "")
    }
}

internal fun parseErrorBody(statusCode: Int, errorBody: String?): ApiResult.Error {
    if (errorBody.isNullOrEmpty()) {
        return ApiResult.Error(
            statusCode = statusCode,
            code = "ERROR_BODY_IS_EMPTY",
            message = "Unknown error"
        )
    }

    return try {
        val json = JSONObject(errorBody)
        val code = json.keys().asSequence()
            .firstNotNullOfOrNull { key -> json.optJSONArray(key)?.optString(0) } ?: ""
        ApiResult.Error(statusCode = statusCode, code = code, message = errorBody)
    } catch (_: Exception) {
        ApiResult.Error(
            statusCode = statusCode,
            code = "JSON_PARSING_ERROR",
            message = errorBody,
        )
    }
}
