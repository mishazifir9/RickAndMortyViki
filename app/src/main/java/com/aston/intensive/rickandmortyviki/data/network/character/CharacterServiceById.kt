package com.aston.intensive.rickandmortyviki.data.network.character

import com.aston.intensive.rickandmortyviki.data.dto.character.CharacterDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterServiceById {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<CharacterDTOById>


    companion object {
        var retrofitService: CharacterServiceById? = null

        fun getInstance(): CharacterServiceById {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CharacterServiceById::class.java)
            }
            return retrofitService!!
        }
    }
}