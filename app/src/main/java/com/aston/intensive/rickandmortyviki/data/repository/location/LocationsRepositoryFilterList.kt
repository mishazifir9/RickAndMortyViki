package com.aston.intensive.rickandmortyviki.data.repository.location

import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceFilter

class LocationsRepositoryFilterList (private val retrofitService: LocationServiceFilter) {

    suspend fun getFilterLocation(map: Map<String, String>) =
        retrofitService.getLocationsWithFilters(map)
}