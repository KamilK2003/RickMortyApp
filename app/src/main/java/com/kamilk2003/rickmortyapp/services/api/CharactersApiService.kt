package com.kamilk2003.rickmortyapp.services.api

import android.content.ContentValues
import android.util.Log
import com.kamilk2003.rickmortyapp.objects.models.Character
import com.kamilk2003.rickmortyapp.objects.models.CharactersInfo
import com.kamilk2003.rickmortyapp.service.api.mapper.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface CharactersApiService {
    suspend fun getCharacters(completion: (List<Character>?) -> Unit)
}

class CharactersApiServiceImpl: CharactersApiService {

    // MARK: - Stored Properties

    private val client = ApiClient.apiService

    // MARK: - Methods

    override suspend fun getCharacters(completion: (List<Character>?) -> Unit) {
        withContext(Dispatchers.IO) {
            val call = client.getCharacters()
            call.enqueue(object : Callback<CharactersInfo> {
                override fun onResponse(call: Call<CharactersInfo>, response: Response<CharactersInfo>) {
                    if (response.isSuccessful) {
                        val responseCharactersInfo = response.body()
                        completion(responseCharactersInfo?.results.toDomain())
                    } else {
                        Log.i(ContentValues.TAG, "$response")
                        completion(null)
                    }
                }

                override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                    Log.e(ContentValues.TAG, "${t.message}")
                    completion(null)
                }
            })
        }
    }
}