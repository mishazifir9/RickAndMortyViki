package com.aston.intensive.rickandmortyviki.data.network.episode

import com.aston.intensive.rickandmortyviki.data.dto.episode.EpisodeDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeServiceByIdForCharactersAndLocations {
    @GET("episode/{id}")
    suspend fun getEpisodeMultiId(@Path("id", encoded = true) id: List<String>): Response<List<EpisodeDTOById>>


    companion object {
        var retrofitService: EpisodeServiceByIdForCharactersAndLocations? = null

        fun getInstance(): EpisodeServiceByIdForCharactersAndLocations {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EpisodeServiceByIdForCharactersAndLocations::class.java)
            }
            return retrofitService!!
        }
    }
}