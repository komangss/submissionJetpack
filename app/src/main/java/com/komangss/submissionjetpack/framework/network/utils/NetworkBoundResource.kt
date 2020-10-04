package com.komangss.submissionjetpack.framework.network.utils

import androidx.lifecycle.LiveData

import androidx.lifecycle.MediatorLiveData
import com.komangss.submissionjetpack.utils.AppExecutors
import com.komangss.submissionjetpack.vo.*

abstract class NetworkBoundResource<ResultType : Any, RemoteDataType, LocalDataType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.InProgress

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.Success(mapFromLocalTypeToResult(newData))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<LocalDataType>

    protected abstract fun shouldFetch(data: LocalDataType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RemoteDataType>>

    protected abstract fun saveCallResult(data: RemoteDataType)

    protected abstract fun mapFromLocalTypeToResult(data: LocalDataType): ResultType

    private fun fetchFromNetwork(dbSource: LiveData<LocalDataType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) {
            result.value = Resource.InProgress
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                Status.SUCCESS -> {
                    mExecutors.diskIO().execute {
                        response.body?.let { saveCallResult(it) }
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.Success(mapFromLocalTypeToResult(newData))
                            }
                        }
                    }
                }
                Status.ERROR -> mExecutors.mainThread().execute {
                    result.value = response.exception?.let { Resource.Error(it) }
                }

                Status.EMPTY -> {
                    result.value = response.exception?.let { Resource.Error(it) }
                }
            }

        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}