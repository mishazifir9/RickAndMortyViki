package com.aston.intensive.rickandmortyviki.data.dao

import androidx.room.*
import com.aston.intensive.rickandmortyviki.data.entities.character.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT*FROM characters_table")
    fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT*FROM characters_table WHERE id=:id")
    fun getCharacterById(id: String): CharacterEntity

    @Query("SELECT*FROM characters_table WHERE name=:name")
    fun getCharacterByName(name: String): List<CharacterEntity>

    @Query("SELECT*FROM characters_table WHERE id >= :start AND id <= :end")
    fun getCharactersByPage(start: Int, end: Int): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacter(characterEntity: CharacterEntity)
}