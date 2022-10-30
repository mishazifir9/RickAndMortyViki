package com.aston.intensive.rickandmortyviki.data.dao

import androidx.room.*
import com.aston.intensive.rickandmortyviki.data.entities.LocationEntity

@Dao
interface LocationDao {
    @Query("SELECT*FROM locations_table")
    fun getAllLocations(): List<LocationEntity>

    @Query("SELECT*FROM locations_table WHERE id=:id")
    fun getLocationById(id: String): LocationEntity

    @Query("SELECT*FROM locations_table WHERE name=:name")
    fun getLocationByName(name: String): List<LocationEntity>

    @Query("SELECT*FROM locations_table WHERE id >= :start AND id <= :end")
    fun getLocationsByPage(start: Int, end: Int): List<LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(locationEntity: LocationEntity)
}