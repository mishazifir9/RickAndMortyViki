package com.aston.intensive.rickandmortyviki.data.repository.location

import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceByIdForCharactersAndEpisodes

class LocationRepositoryByIdForCharactersAndEpisodes (private val retrofitService: LocationServiceByIdForCharactersAndEpisodes) {

    suspend fun getLocationByIdForCharactersAndEpisodes(id: String) =
        retrofitService.getLocationMultiId(id)
}