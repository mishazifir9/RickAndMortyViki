package com.aston.intensive.rickandmortyviki.data.network.location

import com.aston.intensive.rickandmortyviki.data.dto.location.LocationDTOById
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationServiceById {
    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: String): Response<LocationDTOById>


    companion object {
        var retrofitService: LocationServiceById? = null

        fun getInstance(): LocationServiceById {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(LocationServiceById::class.java)
            }
            return retrofitService!!
        }
    }
}