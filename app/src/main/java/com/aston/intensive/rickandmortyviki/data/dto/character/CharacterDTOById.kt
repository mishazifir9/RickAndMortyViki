package com.aston.intensive.rickandmortyviki.data.dto.character

data class CharacterDTOById (
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Map<String, String>,
    val location: Map<String, String>,
    val image: String,
    val episode: List<String>
)