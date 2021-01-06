package com.komangss.submissionjetpack.utils

import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
import com.komangss.submissionjetpack.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
inline fun <DB : Any, REMOTE : Any, DOMAIN : Any> networkBoundResourceNoResultInProgress(
    crossinline fetchFromLocal: () -> Flow<DB>,
    crossinline shouldFetchFromRemote : (DB?) -> Boolean = { true },
    crossinline fetchFromRemote : suspend () -> Flow<ApiResponse<REMOTE>>,
    crossinline processRemoteResponse : (response : ApiResponse.Success<REMOTE>) -> Unit = { },
    crossinline saveRemoteData: suspend (REMOTE) -> Unit = { },
    crossinline mapFromCache : (DB) -> DOMAIN
) = flow {

    val localData = fetchFromLocal().first()

    if (shouldFetchFromRemote(localData)) {
        fetchFromRemote().collect { apiResponse ->
            when(apiResponse) {
                is ApiResponse.Success -> {
                    processRemoteResponse(apiResponse)
                    saveRemoteData(apiResponse.value)
                    emitAll(fetchFromLocal().map { dbData ->
                        Resource.Success(mapFromCache(dbData))
                    })
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
}