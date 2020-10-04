package com.komangss.submissionjetpack.framework.network.utils

import com.komangss.submissionjetpack.vo.Status
import com.komangss.submissionjetpack.vo.Status.*

class ApiResponse<T>(val status: Status, val body: T?, val message: String?, val exception : Exception?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(SUCCESS, body, null, null)

        fun <T> empty(msg: String, body: T): ApiResponse<T> = ApiResponse(EMPTY, body, msg, null)

        fun <T> error(e: Exception): ApiResponse<T> = ApiResponse(ERROR, null, e.message, e)
    }
}