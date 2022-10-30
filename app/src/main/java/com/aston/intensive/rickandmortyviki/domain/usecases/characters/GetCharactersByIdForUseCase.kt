package com.aston.intensive.rickandmortyviki.domain.usecases.characters

import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterRepositoryByIdForEpisodesAndLocations
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel

class GetCharactersByIdForUseCase (private val repository: CharacterRepositoryByIdForEpisodesAndLocations) {

    suspend fun execute(id: List<String>): List<CharacterModel> {
        val listOfECharactersModels = mutableListOf<CharacterModel>()
        val response = repository.getCharacterByIdForEpisodesAndLocations(id)


        if (response.isSuccessful && response.body() != null) {
            for (character in response.body()!!) {
                val characterModel = CharacterModel(
                    id = character.id,
                    name = character.name,
                    image = character.image,
                    status = character.status,
                    species = character.species,
                    gender = character.gender,
                    origin = character.origin,
                    location = character.location,
                    episode = character.episode
                )
                listOfECharactersModels.add(characterModel)
            }
        }
        return listOfECharactersModels
    }
}