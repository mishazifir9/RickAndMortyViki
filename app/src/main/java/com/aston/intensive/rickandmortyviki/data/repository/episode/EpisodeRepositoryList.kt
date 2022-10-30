package com.aston.intensive.rickandmortyviki.data.repository.episode

import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodesServiceList

class EpisodeRepositoryList (private val retrofitService: EpisodesServiceList) {

    suspend fun getAllEpisode(page: Int) = retrofitService.getEpisodeList(page)
}