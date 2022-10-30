package com.aston.intensive.rickandmortyviki.data.dto.episode

import com.google.gson.annotations.SerializedName

data class EpisodeDTOById(
    var id: String,
    var name: String,
    @SerializedName("air_date")
    var airDate: String,
    var episode: String,
    var characters: List<String>
)
