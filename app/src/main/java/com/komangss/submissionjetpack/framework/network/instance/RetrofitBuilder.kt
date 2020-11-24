package com.komangss.submissionjetpack.framework.network.instance

import com.komangss.submissionjetpack.framework.network.services.CatalogServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    val catalogServices = retrofit.create(CatalogServices::class.java)
}