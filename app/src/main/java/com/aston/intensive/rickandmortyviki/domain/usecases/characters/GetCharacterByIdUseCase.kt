package com.aston.intensive.rickandmortyviki.domain.usecases.characters

import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterRepositoryById
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel

class GetCharacterByIdUseCase (private val repository: CharacterRepositoryById) {

    suspend fun execute(id: String): List<CharacterModel> {
        val listOfCharacterModels = mutableListOf<CharacterModel>()
        val response = repository.getCharacterById(id)

        if (response.isSuccessful && response.body() != null) {
            val characterModel = CharacterModel(
                id = response.body()!!.id,
                name = response.body()!!.name,
                image = response.body()!!.image,
                status = response.body()!!.status,
                species = response.body()!!.species,
                gender = response.body()!!.gender,
                origin = response.body()!!.origin,
                location = response.body()!!.location,
                episode = response.body()!!.episode
            )
            listOfCharacterModels.add(characterModel)
        }
        return listOfCharacterModels
    }
}