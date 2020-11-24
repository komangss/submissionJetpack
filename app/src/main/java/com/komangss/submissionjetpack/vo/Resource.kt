package com.komangss.submissionjetpack.vo

import com.komangss.submissionjetpack.business.domain.model.Movie
import com.komangss.submissionjetpack.framework.network.utils.ErrorResponse

// Handling Result, More at : https://medium.com/androiddevelopers/sealed-with-a-class-a906f28ab7b5
sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(
        val code: Int? = null,
        val error: ErrorResponse? = null
    ) : Resource<Nothing>()
    object InProgress : Resource<Nothing>()
}