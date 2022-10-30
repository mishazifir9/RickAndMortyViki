package com.aston.intensive.rickandmortyviki.data.dto.episode

import com.google.gson.annotations.SerializedName

data class EpisodesDTOList(
    @SerializedName("results")
    val result: List<EpisodeDTOById>
)