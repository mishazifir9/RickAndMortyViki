package com.aston.intensive.rickandmortyviki.data.repository.episode

import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceByIdForCharactersAndLocations

class EpisodeRepositoryByIdForCharactersAndLocations (private val retrofitService: EpisodeServiceByIdForCharactersAndLocations) {

    suspend fun getEpisodeByIdForCharactersAndLocations(id: List<String>) = retrofitService.getEpisodeMultiId(id)
}