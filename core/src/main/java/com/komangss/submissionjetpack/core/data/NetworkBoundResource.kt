package com.komangss.submissionjetpack.core.data

import com.komangss.submissionjetpack.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

inline fun <DB : Any, REMOTE : Any, DOMAIN : Any> networkBoundResource(
    crossinline fetchFromLocal: () -> Flow<DB>,
    crossinline shouldFetchFromRemote: (DB?) -> Boolean = { true },
    crossinline fetchFromRemote: suspend () -> Flow<ApiResponse<REMOTE>>,
    crossinline processRemoteResponse: (response: ApiResponse.Success<REMOTE>) -> Unit = { },
    crossinline saveRemoteData: suspend (REMOTE) -> Unit = { },
    crossinline mapFromCache: (DB) -> DOMAIN,
    crossinline mapFromRemote: (REMOTE) -> DOMAIN,
    crossinline shouldCache: () -> Boolean = { true }
) = flow {
//    EspressoIdlingResources.increment()
    emit(Resource.InProgress)
    val localData = fetchFromLocal().first()

    if (shouldFetchFromRemote(localData)) {
        fetchFromRemote().collect { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> {
                    processRemoteResponse(apiResponse)
                    if (shouldCache()) {
                        saveRemoteData(apiResponse.value)
                        emitAll(fetchFromLocal().map { dbData ->
                            Resource.Success(mapFromCache(dbData))
                        })
                    } else {
                        emit(Resource.Success(mapFromRemote(apiResponse.value)))
                    }
                }
                is ApiResponse.GenericError -> {
                    emit(Resource.Error(apiResponse.code, apiResponse.error))
                }
                ApiResponse.NetworkError -> emit(Resource.Error())
            }
        }
    } else {
        emit(Resource.Success(mapFromCache(localData)))
    }
//    EspressoIdlingResources.decrement()
}