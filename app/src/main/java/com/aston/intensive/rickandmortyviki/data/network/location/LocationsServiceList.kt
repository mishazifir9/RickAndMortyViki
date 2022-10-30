package com.aston.intensive.rickandmortyviki.data.network.location

import com.aston.intensive.rickandmortyviki.data.dto.location.LocationsDTOList
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationsServiceList {
    @GET("location")
    suspend fun getLocationList(@Query("page") page: Int): Response<LocationsDTOList>


    companion object {
        var retrofitService: LocationsServiceList? = null

        fun getInstance(): LocationsServiceList {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(LocationsServiceList::class.java)
            }
            return retrofitService!!
        }
    }
}