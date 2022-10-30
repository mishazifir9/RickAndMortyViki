package com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceById
import com.aston.intensive.rickandmortyviki.data.network.episode.EpisodeServiceByIdForCharactersAndLocations
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterRepositoryById
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.episode.EpisodeRepositoryByIdForCharactersAndLocations
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterByIdUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodesByIdForUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.CharacterDetailViewModel

class CharacterDetailVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val characterServiceById = CharacterServiceById.getInstance()
    private val repository = CharacterRepositoryById(characterServiceById)
    private val getCharacterByIdUseCase = GetCharacterByIdUseCase(repository)

    private val episodeServiceByIdForCharactersAndLocations =
        EpisodeServiceByIdForCharactersAndLocations.getInstance()
    private val episodeRepository =
        EpisodeRepositoryByIdForCharactersAndLocations(episodeServiceByIdForCharactersAndLocations)
    private val getEpisodesByIdForUseCase = GetEpisodesByIdForUseCase(episodeRepository)

    private val characterDbRepository = CharacterDbRepository(application)
    private val episodeDbRepository = EpisodeDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterDetailViewModel(
            application,
            getCharacterByIdUseCase,
            getEpisodesByIdForUseCase,
            characterDbRepository,
            episodeDbRepository
        ) as T
    }
}