package com.aston.intensive.rickandmortyviki.domain.usecases.locations

import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceByIdForCharactersAndEpisodes
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel

class GetLocationByIdForUseCase (private val repository: LocationServiceByIdForCharactersAndEpisodes) {

    suspend fun execute(id: String): List<LocationModel> {
        val listOfLocationModels = mutableListOf<LocationModel>()
        val response = repository.getLocationMultiId(id)

        if (response.isSuccessful && response.body() != null) {
            for (obj in response.body()!!) {
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