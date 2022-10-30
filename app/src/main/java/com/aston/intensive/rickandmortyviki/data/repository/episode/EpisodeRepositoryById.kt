package com.aston.intensive.rickandmortyviki.data.repository.episode

import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceById

class EpisodeRepositoryById (private val retrofitService: EpisodeServiceById) {

    suspend fun getEpisodeById(id: String) = retrofitService.getEpisodeById(id)
}