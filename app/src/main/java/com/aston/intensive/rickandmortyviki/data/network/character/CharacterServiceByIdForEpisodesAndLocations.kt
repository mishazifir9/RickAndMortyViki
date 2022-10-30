package com.aston.intensive.rickandmortyviki.data.network.character

import com.aston.intensive.rickandmortyviki.data.dto.character.CharacterDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterServiceByIdForEpisodesAndLocations {
    @GET("character/{id}")
    suspend fun getCharacterMultiId(@Path("id", encoded = true) id: List<String>
    ): Response<List<CharacterDTOById>>


    companion object {
        var retrofitService: CharacterServiceByIdForEpisodesAndLocations? = null

        fun getInstance(): CharacterServiceByIdForEpisodesAndLocations {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CharacterServiceByIdForEpisodesAndLocations::class.java)
            }
            return retrofitService!!
        }
    }
}