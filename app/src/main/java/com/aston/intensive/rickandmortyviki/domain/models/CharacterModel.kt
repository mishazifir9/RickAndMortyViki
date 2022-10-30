package com.aston.intensive.rickandmortyviki.domain.models

data class CharacterModel(
    var id: String,
    var name: String,
    var status: String,
    var species: String,
    var gender: String,
    val origin: Map<String, String>,
    val location: Map<String, String>,
    val image: String,
    val episode: List<String>
)