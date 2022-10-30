package com.aston.intensive.rickandmortyviki.data.network.location

import com.aston.intensive.rickandmortyviki.data.dto.location.LocationDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationServiceByIdForCharactersAndEpisodes {
    @GET("location")
    suspend fun getLocationMultiId(@Query("id") id: String): Response<List<LocationDTOById>>


    companion object {
        var retrofitService: LocationServiceByIdForCharactersAndEpisodes? = null

        fun getInstance(): LocationServiceByIdForCharactersAndEpisodes {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(LocationServiceByIdForCharactersAndEpisodes::class.java)
            }
            return retrofitService!!
        }
    }
}