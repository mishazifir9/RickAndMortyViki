package com.aston.intensive.rickandmortyviki.domain.usecases.locations

import com.aston.intensive.rickandmortyviki.data.repository.location.LocationRepositoryById
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel

class GetLocationByIdUseCase (private val repository: LocationRepositoryById) {

    suspend fun execute(id: String): List<LocationModel> {
        val listOfLocationModels = mutableListOf<LocationModel>()
        val response = repository.getLocationById(id)

        if (response.isSuccessful && response.body() != null) {
            val locationModel = LocationModel(
                id = response.body()!!.id,
                name = response.body()!!.name,
                type = response.body()!!.type,
                dimension = response.body()!!.dimension,
                residents = response.body()!!.residents
            )
            listOfLocationModels.add(locationModel)
        }
        return listOfLocationModels
    }
}