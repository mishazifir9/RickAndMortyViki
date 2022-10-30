package com.aston.intensive.rickandmortyviki.data.network.episode

import com.aston.intensive.rickandmortyviki.data.dto.episode.EpisodesDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodesServiceList {
    @GET("episode")
    suspend fun getEpisodeList(@Query("page") page: Int): Response<EpisodesDTOList>


    companion object {
        var retrofitService: EpisodesServiceList? = null

        fun getInstance() : EpisodesServiceList {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(EpisodesServiceList::class.java)
            }
            return retrofitService!!
        }
    }
}