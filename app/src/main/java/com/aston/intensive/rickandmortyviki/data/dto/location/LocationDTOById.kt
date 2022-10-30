package com.aston.intensive.rickandmortyviki.data.dto.location

data class LocationDTOById(
    var id: String,
    var name: String,
    var type: String,
    var dimension: String,
    val residents: List<String>
)