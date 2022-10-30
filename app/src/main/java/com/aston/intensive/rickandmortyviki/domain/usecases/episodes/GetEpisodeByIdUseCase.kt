package com.aston.intensive.rickandmortyviki.domain.usecases.episodes

import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryById
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class GetEpisodeByIdUseCase (private val repository: EpisodeRepositoryById) {

    suspend fun execute(id: String): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        val response = repository.getEpisodeById(id)

        if (response.isSuccessful && response.body() != null) {
            val episodeModel = EpisodeModel(
                id = response.body()!!.id,
                name = response.body()!!.name,
                episode = response.body()!!.episode,
                air_date = response.body()!!.airDate,
                characters = response.body()!!.characters
            )
            listOfEpisodeModels.add(episodeModel)
        }
        return listOfEpisodeModels
    }
}