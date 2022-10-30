package com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.location.LocationServiceFilter
import com.aston.intensive.rickandmortyviki.data.network.location.LocationsServiceList
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationRepositoryList
import com.aston.intensive.rickandmortyviki.data.repository.location.LocationsRepositoryFilterList
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.locations.GetLocationListUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.LocationViewModel

class LocationVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val locationsServiceList = LocationsServiceList.getInstance()
    private val repository = LocationRepositoryList(locationsServiceList)
    private val getLocationListUseCase = GetLocationListUseCase(repository)

    private val locationServiceFilter = LocationServiceFilter.getInstance()
    private val filtersRepository = LocationsRepositoryFilterList(locationServiceFilter)
    private val getLocationFilterListUseCase = GetLocationFilterListUseCase(filtersRepository)

    private val locationDbRepository = LocationDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LocationViewModel(
            application,
            getLocationListUseCase,
            locationDbRepository,
            getLocationFilterListUseCase
        ) as T
    }
}