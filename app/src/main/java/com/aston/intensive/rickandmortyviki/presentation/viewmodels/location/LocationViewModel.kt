package com.aston.intensive.rickandmortyviki.presentation.viewmodels.location

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationListUseCase
import com.aston.intensive.rickandmortyviki.CheckNetworkConnection
import kotlinx.coroutines.*

class LocationViewModel(
    application: Application,
    private val getLocationListUseCase: GetLocationListUseCase,
    private val locationDbRepository: LocationDbRepository,
    private val getLocationFilterListUseCase: GetLocationFilterListUseCase
) : AndroidViewModel(application) {

    val liveData = MutableLiveData<List<LocationModel>>()
    private val listOfLocationModels = mutableListOf<LocationModel>()
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
                    listOfLocationModels += getLocationListUseCase.execute(page)
                    liveData.value = listOfLocationModels
                    locationDbRepository.addLocationsList(listOfLocationModels)
                }
                this.cancel()
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    listOfLocationModels += locationDbRepository.getLocationByPage(page)
                    liveData.value = listOfLocationModels
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
        listOfLocationModels.clear()
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listOfLocationModels += getLocationListUseCase.execute(page)
                liveData.value = listOfLocationModels
                locationDbRepository.addLocationsList(listOfLocationModels)
                if (listOfLocationModels.isEmpty()) {
                    Toast.makeText(
                        context,
                        "oy ey, I do not know where all the locations are",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            this.cancel()
        }
    }


    private fun localUpdate() {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listOfLocationModels += locationDbRepository.getLocationByPage(page)
                liveData.value = listOfLocationModels
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
                val list = getLocationFilterListUseCase.execute(filters)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Sorry, bro, i do not where is it", Toast.LENGTH_SHORT)
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
                val list = locationDbRepository.getLocationByName(name)
                if (list.isEmpty()) {
                    Toast.makeText(context, "Sorry, bro, i do not where is it", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    liveData.value = list
                }
            }
            this.cancel()
        }
    }
}