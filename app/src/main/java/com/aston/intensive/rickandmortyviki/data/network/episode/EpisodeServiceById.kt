package com.aston.intensive.rickandmortyviki.data.network.episode

import com.aston.intensive.rickandmortyviki.data.dto.episode.EpisodeDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeServiceById {
    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: String): Response<EpisodeDTOById>


    companion object {
        var retrofitService: EpisodeServiceById? = null

        fun getInstance(): EpisodeServiceById {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EpisodeServiceById::class.java)
            }
            return retrofitService!!
        }
    }
}
