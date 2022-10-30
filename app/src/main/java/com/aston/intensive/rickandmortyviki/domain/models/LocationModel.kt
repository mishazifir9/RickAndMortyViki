package com.aston.intensive.rickandmortyviki.domain.models

data class LocationModel(
    var id: String,
    var name: String,
    var type: String,
    var dimension: String,
    var residents: List<String>
)