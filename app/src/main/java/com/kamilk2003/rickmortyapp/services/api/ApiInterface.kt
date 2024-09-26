package com.kamilk2003.rickmortyapp.services.api

import com.kamilk2003.rickmortyapp.objects.models.CharactersInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("character")
    fun getCharacters(): Call<CharactersInfo>
}