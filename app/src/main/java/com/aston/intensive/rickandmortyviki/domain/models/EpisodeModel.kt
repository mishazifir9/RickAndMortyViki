package com.aston.intensive.rickandmortyviki.domain.models

data class EpisodeModel (
    var id: String,
    var name: String,
    var episode: String,
    var air_date: String,
    var characters: List<String>
)