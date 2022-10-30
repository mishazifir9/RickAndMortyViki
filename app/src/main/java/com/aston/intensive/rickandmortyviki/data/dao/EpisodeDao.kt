package com.aston.intensive.rickandmortyviki.data.dao

import androidx.room.*
import com.aston.intensive.rickandmortyviki.data.entities.EpisodeEntity


@Dao
interface EpisodeDao {
    @Query("SELECT*FROM episodes_table")
    fun getAllEpisodes(): List<EpisodeEntity>

    @Query("SELECT*FROM episodes_table WHERE id=:id")
    fun getEpisodeById(id: String): EpisodeEntity

    @Query("SELECT*FROM episodes_table WHERE name=:name")
    fun getEpisodeByName(name: String): List<EpisodeEntity>

    @Query("SELECT*FROM episodes_table WHERE id >= :start AND id <= :end")
    fun getEpisodesByPage(start: Int, end: Int): List<EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addEpisode(episodeEntity: EpisodeEntity)
}