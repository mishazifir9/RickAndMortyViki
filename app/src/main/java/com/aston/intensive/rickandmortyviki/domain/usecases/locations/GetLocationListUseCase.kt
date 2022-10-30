package com.aston.intensive.rickandmortyviki.domain.usecases.locations

import com.aston.intensive.rickandmortyviki.data.repository.location.LocationRepositoryList
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel

class GetLocationListUseCase (private val repository: LocationRepositoryList) {

    suspend fun execute(page: Int): List<LocationModel> {
        val listOfLocationModels = mutableListOf<LocationModel>()
        val response = repository.getAllLocations(page)

        if (response.isSuccessful && response.body() != null) {
            for (obj in response.body()!!.result) {
                val locationModel = LocationModel(
                    id = obj.id,
                    name = obj.name,
                    type = obj.type,
                    dimension = obj.dimension,
                    residents = obj.residents
                )
                listOfLocationModels.add(locationModel)
            }
        }
        return listOfLocationModels
    }
}