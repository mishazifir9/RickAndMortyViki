package com.aston.intensive.rickandmortyviki.data.dto.character

import com.google.gson.annotations.SerializedName

data class CharactersDTOList(
    @SerializedName("results")
    val result: List<CharacterDTOById>
)