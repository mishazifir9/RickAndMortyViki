package com.aston.intensive.rickandmortyviki.data.dto.location

import com.google.gson.annotations.SerializedName

data class LocationsDTOList(
    @SerializedName("results")
    val result: List<LocationDTOById>
)