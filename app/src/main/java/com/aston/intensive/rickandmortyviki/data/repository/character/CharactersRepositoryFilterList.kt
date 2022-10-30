package com.aston.intensive.rickandmortyviki.data.repository.character

import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceFilter

class CharactersRepositoryFilterList (private val retrofitService: CharacterServiceFilter) {

    suspend fun getFilterCharacter(filters: Map<String, String>) =
        retrofitService.getCharacterWithFilters(filters)
}