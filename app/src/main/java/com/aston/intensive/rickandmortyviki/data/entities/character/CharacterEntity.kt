package com.aston.intensive.rickandmortyviki.data.entities.character

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity (
    @PrimaryKey
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    @Embedded
    val origin: Origin,
    @Embedded
    val location: Location,
    val image: String,
    val episode: String
)