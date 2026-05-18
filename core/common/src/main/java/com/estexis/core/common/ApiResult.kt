package com.estexis.core.common

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val statusCode: Int = -1,
        val code: String = "",
        val message: String = "",
    ) : ApiResult<Nothing>()
}
