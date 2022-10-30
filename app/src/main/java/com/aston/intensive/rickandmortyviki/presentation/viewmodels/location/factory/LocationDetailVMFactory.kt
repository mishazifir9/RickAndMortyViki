package com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceByIdForEpisodesAndLocations
import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceById
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterRepositoryByIdForEpisodesAndLocations
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationRepositoryById
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationDbRepository
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharactersByIdForUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationByIdUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.LocationDetailViewModel

class LocationDetailVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val locationServiceById = LocationServiceById.getInstance()
    private val repository = LocationRepositoryById(locationServiceById)
    private val getLocationByIdUseCase = GetLocationByIdUseCase(repository)

    private val characterServiceByIdForEpisodesAndLocations = CharacterServiceByIdForEpisodesAndLocations.getInstance()
    private val characterRepository = CharacterRepositoryByIdForEpisodesAndLocations(characterServiceByIdForEpisodesAndLocations)
    private val getCharactersByIdForUseCase = GetCharactersByIdForUseCase(characterRepository)

    private val locationDbRepository = LocationDbRepository(application)
    private val characterDbRepository = CharacterDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationDetailViewModel(
            application,
            getLocationByIdUseCase,
            getCharactersByIdForUseCase,
            locationDbRepository,
            characterDbRepository
        ) as T
    }
}