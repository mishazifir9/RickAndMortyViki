package com.aston.intensive.rickandmortyviki.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aston.intensive.rickandmortyviki.data.dao.CharacterDao
import com.aston.intensive.rickandmortyviki.data.dao.EpisodeDao
import com.aston.intensive.rickandmortyviki.data.dao.LocationDao
import com.aston.intensive.rickandmortyviki.data.entities.character.CharacterEntity
import com.aston.intensive.rickandmortyviki.data.entities.EpisodeEntity
import com.aston.intensive.rickandmortyviki.data.entities.LocationEntity

@Database(entities = [CharacterEntity::class, LocationEntity::class,  EpisodeEntity::class],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao


    companion object {
        private var INSTANCE_STATE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE_STATE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE_STATE = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "database").allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE_STATE
        }

        fun destroyInstance() {
            INSTANCE_STATE = null
        }
    }
}
