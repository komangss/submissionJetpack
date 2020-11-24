package com.komangss.submissionjetpack.framework.network.utils

import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class ApiResponse<out T : Any> {
    data class Success<out T : Any>(val value: T): ApiResponse<T>()
    data class GenericError(
        val code: Int? = null,
        val error: ErrorResponse? = null,
        val exception: Exception? = null
    ): ApiResponse<Nothing>()
    object NetworkError: ApiResponse<Nothing>()
}

suspend fun <T : Any> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ApiResponse<T> {
    return withContext(dispatcher) {
        try {
            ApiResponse.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ApiResponse.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ApiResponse.GenericError(code, errorResponse, throwable)
                }
                else -> {
                    ApiResponse.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}