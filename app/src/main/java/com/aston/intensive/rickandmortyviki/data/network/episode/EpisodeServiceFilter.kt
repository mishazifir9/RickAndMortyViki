package com.aston.intensive.rickandmortyviki.data.network.episode

import com.aston.intensive.rickandmortyviki.data.dto.episode.EpisodesDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface EpisodeServiceFilter {
    @GET("episode")
    suspend fun getEpisodeNameWithFilters(@QueryMap filters: Map<String, String>): Response<EpisodesDTOList>


    companion object {
        var retrofitService: EpisodeServiceFilter? = null

        fun getInstance(): EpisodeServiceFilter {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EpisodeServiceFilter::class.java)
            }
            return retrofitService!!
        }
    }
}