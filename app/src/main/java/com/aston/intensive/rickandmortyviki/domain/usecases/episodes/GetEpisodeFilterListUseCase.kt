package com.aston.intensive.rickandmortyviki.domain.usecases.episodes

import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodesRepositoryFilterList
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class GetEpisodeFilterListUseCase (private val repository: EpisodesRepositoryFilterList) {
    suspend fun execute(map: Map<String, String>): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        val response = repository.getFilterEpisode(map)

        if (response.isSuccessful && response.body() != null) {
            for (obj in response.body()!!.result) {
                val episodeModel = EpisodeModel(
                    id = obj.id,
                    name = obj.name,
                    episode = obj.episode,
                    air_date = obj.airDate,
                    characters = obj.characters
                )
                listOfEpisodeModels.add(episodeModel)
            }
        }
        return listOfEpisodeModels
    }
}