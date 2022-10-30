package com.aston.intensive.rickandmortyviki.data.repository.character

import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceByIdForEpisodesAndLocations

class CharacterRepositoryByIdForEpisodesAndLocations (private val retrofitService: CharacterServiceByIdForEpisodesAndLocations) {

    suspend fun getCharacterByIdForEpisodesAndLocations(id: List<String>) = retrofitService.getCharacterMultiId(id)
}