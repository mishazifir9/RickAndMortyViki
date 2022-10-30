package com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeListUseCase
import com.aston.intensive.rickandmortyviki.CheckNetworkConnection
import kotlinx.coroutines.*

class EpisodeViewModel(
    application: Application,
    private val getEpisodeListUseCase: GetEpisodeListUseCase,
    private val episodeDbRepository: EpisodeDbRepository,
    private val getEpisodeFilterListUseCase: GetEpisodeFilterListUseCase
) : AndroidViewModel(application) {

    val liveData = MutableLiveData<List<EpisodeModel>>()
    private val listOfEpisodeModels = mutableListOf<EpisodeModel>()
    private var job: Job? = null
    private var page = 1
    private val context = application

    private fun checkConnection(): Boolean {
        return CheckNetworkConnection.isNetworkConnected(context)
    }


    fun addNewPage() {
        page++
        if (checkConnection()) {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    listOfEpisodeModels += getEpisodeListUseCase.execute(page)
                    liveData.value = listOfEpisodeModels
                    episodeDbRepository.addListOfEpisodes(listOfEpisodeModels)
                }
                this.cancel()
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    listOfEpisodeModels += episodeDbRepository.getEpisodeByPage(page)
                    liveData.value = listOfEpisodeModels
                }
                this.cancel()
            }
        }
    }


    fun update() {
        if (checkConnection()) {
            networkUpdate()
        } else {
            localUpdate()
        }
    }


    private fun networkUpdate() {
        page = 1
        listOfEpisodeModels.clear()
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listOfEpisodeModels += getEpisodeListUseCase.execute(page)
                liveData.value = listOfEpisodeModels
                episodeDbRepository.addListOfEpisodes(listOfEpisodeModels)
                if (listOfEpisodeModels.isEmpty()) {
                    Toast.makeText(
                        context,
                        "oy ey, I do not know where all the episodes are",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            this.cancel()
        }
    }


    private fun localUpdate() {
        page = 1
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listOfEpisodeModels += episodeDbRepository.getEpisodeByPage(page)
                liveData.value = listOfEpisodeModels
            }
            this.cancel()
        }
    }


    fun useFilters(filters: Map<String, String>) {
        if (checkConnection()) {
            networkFilters(filters)
        } else {
            localFilters(filters["name"].toString())
        }
    }


    private fun networkFilters(filters: Map<String, String>) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val list = getEpisodeFilterListUseCase.execute(filters)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Sorry, bro, i did not watch it", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    liveData.value = list
                }
            }
            this.cancel()
        }
    }


    private fun localFilters(name: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val list = episodeDbRepository.getEpisodeByName(name)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Sorry, bro, i did not watch it", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    liveData.value = list
                }
            }
            this.cancel()
        }
    }
}