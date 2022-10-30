package com.aston.intensive.rickandmortyviki.domain.usecases.characters

import com.aston.intensive.rickandmortyviki.data.repository.character.CharactersRepositoryList
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel

class GetCharacterListUseCase (private val repository: CharactersRepositoryList) {

    suspend fun execute(page: Int): List<CharacterModel> {
        val listOfCharacterModels = mutableListOf<CharacterModel>()
        val response = repository.getAllCharacter(page)

        if (response.isSuccessful && response.body() != null) {
            for (obj in response.body()!!.result) {
                val characterModel = CharacterModel(
                    id = obj.id,
                    name = obj.name,
                    image = obj.image,
                    status = obj.status,
                    species = obj.species,
                    gender = obj.gender,
                    origin = obj.origin,
                    location = obj.location,
                    episode = obj.episode
                )
                listOfCharacterModels.add(characterModel)
            }
        }
        return listOfCharacterModels
    }
}