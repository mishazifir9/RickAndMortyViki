package com.aston.intensive.rickandmortyviki.data.repository.episode

import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceFilter

class EpisodesRepositoryFilterList (private val retrofitService: EpisodeServiceFilter) {

    suspend fun getFilterEpisode(map: Map<String, String>) =
        retrofitService.getEpisodeNameWithFilters(map)
}