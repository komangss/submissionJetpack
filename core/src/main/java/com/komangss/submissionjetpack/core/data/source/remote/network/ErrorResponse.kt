package com.komangss.submissionjetpack.core.data.source.remote.network

data class ErrorResponse(
    val errorDescription: String, // this is the translated error shown to the user directly from the API
    val causes: Map<String, String> = emptyMap() //this is for errors on specific field on a form
)