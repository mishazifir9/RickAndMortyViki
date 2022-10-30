package com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aston.intensive.rickandmortyviki.data.network.character.CharacterServiceFilter
import com.aston.intensive.rickandmortyviki.data.network.character.CharactersListService
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.data.repository.character.CharactersRepositoryFilterList
import com.aston.intensive.rickandmortyviki.data.repository.character.CharactersRepositoryList
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterListUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.CharacterViewModel

class CharacterVMFactory(private val application: Application) : ViewModelProvider.Factory {

    private val charactersListService = CharactersListService.getInstance()
    private val repository = CharactersRepositoryList(charactersListService)
    private val getCharacterListUseCase = GetCharacterListUseCase(repository)

    private val characterServiceFilter = CharacterServiceFilter.getInstance()
    private val filtersRepository = CharactersRepositoryFilterList(characterServiceFilter)
    private val getCharacterFilterListUseCase = GetCharacterFilterListUseCase(filtersRepository)

    private val characterDbRepository = CharacterDbRepository(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CharacterViewModel(
            application, getCharacterListUseCase,
            characterDbRepository, getCharacterFilterListUseCase
        ) as T
    }

}