package com.kamilk2003.rickmortyapp.services.api

import com.kamilk2003.rickmortyapp.application.constants.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: ApiInterface by lazy {
        RetrofitClient.retrofit.create(ApiInterface::class.java)
    }
}