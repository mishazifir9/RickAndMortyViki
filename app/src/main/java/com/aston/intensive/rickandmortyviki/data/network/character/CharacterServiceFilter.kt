package com.aston.intensive.rickandmortyviki.data.network.character

import com.aston.intensive.rickandmortyviki.data.dto.character.CharactersDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface CharacterServiceFilter {
    @GET("character")
    suspend fun getCharacterWithFilters(@QueryMap filters:Map<String, String>): Response<CharactersDTOList>


    companion object {
        var retrofitService: CharacterServiceFilter? = null

        fun getInstance(): CharacterServiceFilter {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CharacterServiceFilter::class.java)
            }
            return retrofitService!!
        }
    }
}