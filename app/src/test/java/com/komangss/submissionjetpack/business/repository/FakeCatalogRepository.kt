//package com.komangss.submissionjetpack.business.repository
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.komangss.submissionjetpack.business.datasource.CatalogDataSource
//import com.komangss.submissionjetpack.business.datasource.cache.CatalogLocalDataSource
//import com.komangss.submissionjetpack.framework.cache.model.TvShowEntity
//import com.komangss.submissionjetpack.business.datasource.network.CatalogRemoteDataSource
//import com.komangss.submissionjetpack.business.domain.model.Movie
//import com.komangss.submissionjetpack.framework.cache.mappers.MovieCacheMapper
//import com.komangss.submissionjetpack.framework.cache.model.MovieEntity
//import com.komangss.submissionjetpack.framework.network.mappers.MovieNetworkMapper
//import com.komangss.submissionjetpack.framework.network.model.MovieResponse
//import com.komangss.submissionjetpack.framework.network.model.TvShowResponse
//import com.komangss.submissionjetpack.framework.network.utils.ApiResponse
//import com.komangss.submissionjetpack.utils.AppExecutors
//import com.komangss.submissionjetpack.vo.Resource
//
//class FakeCatalogRepository(
//    private val catalogRemoteDataSource: CatalogRemoteDataSource,
//    private val catalogLocalDataSource: CatalogLocalDataSource,
//    private val networkMapper: MovieNetworkMapper,
//    private val cacheMapper: MovieCacheMapper,
//    private val appExecutors: AppExecutors
//) : CatalogDataSource {
//
//}