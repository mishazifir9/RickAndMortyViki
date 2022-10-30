package com.aston.intensive.rickandmortyviki.data.network.character

import com.aston.intensive.rickandmortyviki.data.dto.character.CharactersDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersListService {
    @GET("character")
    suspend fun getCharacterList(@Query("page") page: Int): Response<CharactersDTOList>


    companion object {
        var retrofitService: CharactersListService? = null

        fun getInstance(): CharactersListService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CharactersListService::class.java)
            }
            return retrofitService!!
        }
    }
}