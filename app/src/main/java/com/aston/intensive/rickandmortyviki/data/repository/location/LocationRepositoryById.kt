package com.aston.intensive.rickandmortyviki.data.repository.location

import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceById

class LocationRepositoryById (private val retrofitService: LocationServiceById) {

    suspend fun getLocationById(id: String) = retrofitService.getLocationById(id)
}