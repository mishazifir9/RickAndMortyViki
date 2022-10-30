package com.aston.intensive.rickandmortyviki.presentation.viewmodels.location

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharactersByIdForUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationByIdUseCase
import com.aston.intensive.rickandmortyviki.CheckNetworkConnection
import kotlinx.coroutines.*

class LocationDetailViewModel(
    application: Application,
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getCharactersByIdForUseCase: GetCharactersByIdForUseCase,
    private val locationDbRepository: LocationDbRepository,
    private val characterDbRepository: CharacterDbRepository
) : ViewModel() {
    val liveData = MutableLiveData<List<LocationModel>>()
    val characterModelLiveData = MutableLiveData<List<CharacterModel>>()
    private var job: Job? = null
    private val context = application

    private fun checkConnection(): Boolean {
        return CheckNetworkConnection.isNetworkConnected(context)
    }


    fun update(id: String) {
        if (checkConnection()) {
            networkUpdate(id)
        } else {
            localUpdate(id)
        }
    }


    fun updateCharacter(charactersList: List<String>) {
        var ids = ""
        for (obj in charactersList) {
            ids += obj.substring(obj.lastIndexOf("/") + 1, obj.length)
            ids += ","
        }
        if (ids.isNotEmpty())
            ids = ids.removeRange(ids.length - 1, ids.length)
        if (checkConnection()) {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    characterModelLiveData.value = getCharactersByIdForUseCase.execute(listOf(ids))
                }
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    characterModelLiveData.value =
                        characterDbRepository.getCharactersByIds(listOf(ids))
                }
            }
        }
    }


    private fun networkUpdate(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                liveData.value = getLocationByIdUseCase.execute(id)
            }
        }
    }


    private fun localUpdate(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                liveData.value = listOf(locationDbRepository.getLocationById(id))
            }
        }
    }
}