package com.aston.intensive.rickandmortyviki.data.network.location

import com.aston.intensive.rickandmortyviki.data.dto.location.LocationsDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface LocationServiceFilter {
    @GET("location")
    suspend fun getLocationsWithFilters(@QueryMap filters: Map<String, String>): Response<LocationsDTOList>


    companion object {
        var retrofitService: LocationServiceFilter? = null

        fun getInstance(): LocationServiceFilter {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(LocationServiceFilter::class.java)
            }
            return retrofitService!!
        }
    }
}