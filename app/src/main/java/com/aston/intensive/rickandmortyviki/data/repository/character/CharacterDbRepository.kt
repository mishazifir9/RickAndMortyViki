package com.aston.intensive.rickandmortyviki.data.repository.character

import android.content.Context
import com.aston.intensive.rickandmortyviki.data.dao.CharacterDao
import com.aston.intensive.rickandmortyviki.data.database.AppDatabase
import com.aston.intensive.rickandmortyviki.data.entities.character.CharacterEntity
import com.aston.intensive.rickandmortyviki.data.entities.character.Location
import com.aston.intensive.rickandmortyviki.data.entities.character.Origin
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel

class CharacterDbRepository(context: Context) {

    private var dataBase: CharacterDao = AppDatabase.getInstance(context)?.characterDao()!!


    fun getAllCharacters() = dataBase.getAllCharacters()


    fun getCharacterById(id: String): CharacterModel {
        var characterModel: CharacterModel
        try {
            characterModel = CharacterModel(
                dataBase.getCharacterById(id).id,
                dataBase.getCharacterById(id).name,
                dataBase.getCharacterById(id).status,
                dataBase.getCharacterById(id).species,
                dataBase.getCharacterById(id).gender,
                mapOf(
                    dataBase.getCharacterById(id).origin.originName to dataBase.getCharacterById(id).origin.originUrl),
                mapOf(
                    dataBase.getCharacterById(id).location.locationName to dataBase.getCharacterById(id).location.locationUrl),
                dataBase.getCharacterById(id).image,
                dataBase.getCharacterById(id).episode.split(",").toList()
            )
        } catch (e: Exception) {
            characterModel = CharacterModel(
                "not loaded",
                "not loaded",
                "not loaded",
                "not loaded",
                "not loaded",
                mapOf("name" to "not loaded", "url" to "not loaded"),
                mapOf("name" to "not loaded", "url" to "not loaded"),
                "",
                listOf("not loaded", "not loaded")
            )
        }
        return characterModel
    }


    fun getCharacterByName(name: String): List<CharacterModel> {
        val listOfCharacters = mutableListOf<CharacterModel>()
        try {
            for (characterFromDb in dataBase.getCharacterByName(name)) {
                val characterModel = CharacterModel(
                    characterFromDb.id,
                    characterFromDb.name,
                    characterFromDb.status,
                    characterFromDb.species,
                    characterFromDb.gender,
                    mapOf(characterFromDb.origin.originName to characterFromDb.origin.originUrl),
                    mapOf(characterFromDb.location.locationName to characterFromDb.location.locationUrl),
                    characterFromDb.image,
                    characterFromDb.episode.split(",").toList()
                )
                listOfCharacters.add(characterModel)
            }
        } catch (e: Exception) {
        }
        return listOfCharacters
    }


    fun getCharacterByPage(page: Int): List<CharacterModel> {
        val listOfCharacterModels = mutableListOf<CharacterModel>()
        for (characterFromDb in dataBase.getCharactersByPage(
            (page - 1) * 20 + 1,
            (page - 1) * 20 + 20
        )) {
            val characterModel = CharacterModel(
                characterFromDb.id,
                characterFromDb.name,
                characterFromDb.status,
                characterFromDb.species,
                characterFromDb.gender,
                mapOf(characterFromDb.origin.originName to characterFromDb.origin.originUrl),
                mapOf(characterFromDb.location.locationName to characterFromDb.location.locationUrl),
                characterFromDb.image,
                characterFromDb.episode.split(",").toList()
            )
            listOfCharacterModels.add(characterModel)
        }
        return listOfCharacterModels
    }


    private fun addCharacter(characterModel: CharacterModel) {
        dataBase.addCharacter(
            CharacterEntity(
                characterModel.id,
                characterModel.name,
                characterModel.status,
                characterModel.species,
                characterModel.gender,
                Origin(
                    characterModel.origin["name"].toString(),
                    characterModel.origin["url"].toString()
                ),
                Location(
                    characterModel.location["name"].toString(),
                    characterModel.location["url"].toString()
                ),
                characterModel.image,
                characterModel.episode.joinToString()
            )
        )
    }


    fun addListOfCharacters(ListOfCharacterModel: List<CharacterModel>) {
        for (character in ListOfCharacterModel) {
            addCharacter(character)
        }
    }


    fun getCharactersByIds(ids: List<String>): List<CharacterModel> {
        val listOfCharacters = mutableListOf<CharacterModel>()
        for (id in ids) {
            listOfCharacters.add(getCharacterById(id))
        }
        return listOfCharacters
    }
}