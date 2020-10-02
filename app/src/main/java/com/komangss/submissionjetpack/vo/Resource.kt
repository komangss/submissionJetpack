package com.komangss.submissionjetpack.vo

data class Resource<T>(val status: Status, val data: T?, val message: String?) {
//  TODO : Fix result status
    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error(msg: String?, data: T?): Resource<T> = Resource(Status.ERROR, data, msg)

        fun <T> empty(data: T?): Resource<T> = Resource(Status.EMPTY, data, null)
    }
}