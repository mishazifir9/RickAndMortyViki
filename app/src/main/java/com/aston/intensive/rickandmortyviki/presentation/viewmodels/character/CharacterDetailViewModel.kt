package com.aston.intensive.rickandmortyviki.presentation.viewmodels.character

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterByIdUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodesByIdForUseCase
import com.aston.intensive.rickandmortyviki.CheckNetworkConnection
import kotlinx.coroutines.*

class CharacterDetailViewModel(
    application: Application,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getEpisodesByIdForUseCase: GetEpisodesByIdForUseCase,
    private val characterDbRepository: CharacterDbRepository,
    private val episodeDbRepository: EpisodeDbRepository
) : AndroidViewModel(application) {

    val liveData = MutableLiveData<List<CharacterModel>>()
    val episodeModelLiveData = MutableLiveData<List<EpisodeModel>>()
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


    fun updateEpisodes(episodesList: List<String>) {
        var ids = ""
        for (obj in episodesList) {
            ids += obj.substring(obj.lastIndexOf("/") + 1, obj.length)
            ids += ","
        }
        ids = ids.removeRange(ids.length - 1, ids.length)
        if (checkConnection()) {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    episodeModelLiveData.value = getEpisodesByIdForUseCase.execute(listOf(ids))
                }
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    episodeModelLiveData.value = episodeDbRepository.getEpisodesByIds(listOf(ids))
                }
            }
        }
    }


    private fun networkUpdate(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val charUpdate = getCharacterByIdUseCase.execute(id)
                liveData.value = charUpdate
                characterDbRepository.addListOfCharacters(charUpdate)
            }
        }
    }


    private fun localUpdate(id: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                liveData.value = listOf(characterDbRepository.getCharacterById(id))
            }
        }
    }
}