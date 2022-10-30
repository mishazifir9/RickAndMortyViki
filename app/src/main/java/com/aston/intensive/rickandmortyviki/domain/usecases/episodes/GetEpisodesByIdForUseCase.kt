package com.aston.intensive.rickandmortyviki.domain.usecases.episodes

import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryByIdForCharactersAndLocations
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class GetEpisodesByIdForUseCase (private val repository: EpisodeRepositoryByIdForCharactersAndLocations) {
    suspend fun execute(id: List<String>): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        val response = repository.getEpisodeByIdForCharactersAndLocations(id)

        if (response.isSuccessful && response.body() != null) {
            for (obj in response.body()!!) {
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