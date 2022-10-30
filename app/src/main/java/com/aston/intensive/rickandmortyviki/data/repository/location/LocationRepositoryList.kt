package com.aston.intensive.rickandmortyviki.data.repository.location

import com.aston.intensive.rickandmortyviki.data.network.location.LocationsServiceList

class LocationRepositoryList (private val retrofitService: LocationsServiceList) {

    suspend fun getAllLocations(page: Int) = retrofitService.getLocationList(page)
}