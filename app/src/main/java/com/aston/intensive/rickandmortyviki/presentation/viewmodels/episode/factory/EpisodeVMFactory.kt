package com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceFilter
import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodesServiceList
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryList
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodesRepositoryFilterList
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeListUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.EpisodeViewModel

class EpisodeVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val episodesServiceList = EpisodesServiceList.getInstance()
    private val repository = EpisodeRepositoryList(episodesServiceList)
    private val getEpisodeListUseCase = GetEpisodeListUseCase(repository)

    private val episodesFilterService = EpisodeServiceFilter.getInstance()
    private val filterRepository = EpisodesRepositoryFilterList(episodesFilterService)
    private val getEpisodeFilterListUseCase = GetEpisodeFilterListUseCase(filterRepository)

    private val episodeDbRepository = EpisodeDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(
            application,
            getEpisodeListUseCase,
            episodeDbRepository,
            getEpisodeFilterListUseCase
        ) as T
    }
}