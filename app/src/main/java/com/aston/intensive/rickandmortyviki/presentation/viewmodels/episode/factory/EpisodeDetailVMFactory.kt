package com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceByIdForEpisodesAndLocations
import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceById
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterRepositoryByIdForEpisodesAndLocations
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryById
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeDbRepository
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharactersByIdForUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeByIdUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.EpisodeDetailViewModel

class EpisodeDetailVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val episodeServiceById = EpisodeServiceById.getInstance()
    private val repository = EpisodeRepositoryById(episodeServiceById)
    private val getEpisodeByIdUseCase = GetEpisodeByIdUseCase(repository)

    private val characterServiceByIdForEpisodesAndLocations = CharacterServiceByIdForEpisodesAndLocations.getInstance()
    private val characterRepository = CharacterRepositoryByIdForEpisodesAndLocations(characterServiceByIdForEpisodesAndLocations)
    private val getCharactersByIdForUseCase = GetCharactersByIdForUseCase(characterRepository)

    private val episodeDbRepository = EpisodeDbRepository(application)
    private val characterDbRepository = CharacterDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeDetailViewModel(
            application,
            getEpisodeByIdUseCase,
            getCharactersByIdForUseCase,
            episodeDbRepository,
            characterDbRepository
        ) as T
    }
}