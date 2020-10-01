package com.komangss.submissionjetpack.framework.network.utils

import com.komangss.submissionjetpack.vo.Status
import com.komangss.submissionjetpack.vo.Status.*

class ApiResponse<T>(val status: Status, val body: T, val message: String?) {
    companion object {
        fun <T> success(body: T): ApiResponse<T> = ApiResponse(SUCCESS, body, null)

        fun <T> empty(msg: String, body: T): ApiResponse<T> = ApiResponse(EMPTY, body, msg)

        fun <T> error(msg: String, body: T): ApiResponse<T> = ApiResponse(ERROR, body, msg)
    }
}