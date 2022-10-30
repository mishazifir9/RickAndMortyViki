package com.aston.intensive.rickandmortyviki.domain.usecases.episodes

import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryList
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class GetEpisodeListUseCase (private val repository: EpisodeRepositoryList) {

    suspend fun execute(page: Int): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        val response = repository.getAllEpisode(page)

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