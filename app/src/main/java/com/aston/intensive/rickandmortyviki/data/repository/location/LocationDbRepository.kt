package com.aston.intensive.rickandmortyviki.data.repository.location

import android.content.Context
import com.aston.intensive.rickandmortyviki.data.dao.LocationDao
import com.aston.intensive.rickandmortyviki.data.database.AppDatabase
import com.aston.intensive.rickandmortyviki.data.entities.LocationEntity
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel

class LocationDbRepository(context: Context) {
    private var dataBase: LocationDao = AppDatabase.getInstance(context)?.locationDao()!!


    fun getAllLocations() = dataBase.getAllLocations()


    fun getLocationById(id: String): LocationModel {
        var locationModel: LocationModel
        try{
            locationModel = LocationModel(
                dataBase.getLocationById(id).id,
                dataBase.getLocationById(id).name,
                dataBase.getLocationById(id).type,
                dataBase.getLocationById(id).dimension,
                dataBase.getLocationById(id).residents.split(",").toList()
            )
        }catch (e: Exception){
            locationModel = LocationModel(
                "not loaded",
                "not loaded",
                "not loaded",
                "not loaded",
                listOf("not loaded", "not loaded")
            )
        }
        return locationModel
    }


    fun getLocationByName(name: String): List<LocationModel>{
        val listOfLocationModels = mutableListOf<LocationModel>()
        try {
            for (locationFromDb in dataBase.getLocationByName(name)){
                val locationModel = LocationModel(
                    locationFromDb.id,
                    locationFromDb.name,
                    locationFromDb.type,
                    locationFromDb.dimension,
                    locationFromDb.residents.split(",").toList()
                )
                listOfLocationModels.add(locationModel)
            }
        } catch (e: Exception){}
        return listOfLocationModels
    }


    fun getLocationByPage(page: Int): List<LocationModel>{
        val listOfLocationModels = mutableListOf<LocationModel>()
        for (locationsFromDb in dataBase.getLocationsByPage((page - 1) * 20 + 1, (page - 1) * 20 + 20)){
            val locationModel = LocationModel(
                locationsFromDb.id,
                locationsFromDb.name,
                locationsFromDb.type,
                locationsFromDb.dimension,
                locationsFromDb.residents.split(",").toList()
            )
            listOfLocationModels.add(locationModel)
        }
        return listOfLocationModels
    }


    private fun addLocation(locationModel: LocationModel) {
        dataBase.addLocation(
            LocationEntity(
                locationModel.id,
                locationModel.name,
                locationModel.type,
                locationModel.dimension,
                locationModel.residents.joinToString()
            )
        )
    }


    fun addLocationsList(listOfLocationModels: List<LocationModel>) {
        for (location in listOfLocationModels) {
            addLocation(location)
        }
    }
}