package com.aston.intensive.rickandmortyviki.data.repository.character

import com.aston.intensive.rickandmortyviki.data.network.character.CharactersListService

class CharactersRepositoryList (private val retrofitService: CharactersListService) {

    suspend fun getAllCharacter(page: Int) = retrofitService.getCharacterList(page)
}