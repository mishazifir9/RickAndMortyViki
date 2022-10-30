package com.aston.intensive.rickandmortyviki.data.repository.character

import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceById

class CharacterRepositoryById (private val retrofitService: CharacterServiceById) {

    suspend fun getCharacterById(id: String) = retrofitService.getCharacterById(id)
}