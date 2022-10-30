package com.aston.intensive.rickandmortyviki.presentation.viewmodels.character

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.characters.GetCharacterListUseCase
import com.aston.intensive.rickandmortyviki.CheckNetworkConnection
import kotlinx.coroutines.*

class CharacterViewModel(
    application: Application,
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val characterDbRepository: CharacterDbRepository,
    private val getCharacterFilterListUseCase: GetCharacterFilterListUseCase
) : AndroidViewModel(application) {

    val liveData = MutableLiveData<List<CharacterModel>>()
    private val listOfCharacterModels = mutableListOf<CharacterModel>()
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
                    listOfCharacterModels += getCharacterListUseCase.execute(page)
                    liveData.value = listOfCharacterModels
                    characterDbRepository.addListOfCharacters(listOfCharacterModels)
                }
                this.cancel()
            }
        } else {
            job = CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    listOfCharacterModels += characterDbRepository.getCharacterByPage(page)
                    liveData.value = listOfCharacterModels
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
        listOfCharacterModels.clear()
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                listOfCharacterModels += getCharacterListUseCase.execute(page)
                liveData.value = listOfCharacterModels
                characterDbRepository.addListOfCharacters(listOfCharacterModels)
                if (listOfCharacterModels.isEmpty()) {
                    Toast.makeText(
                        context,
                        "oy ey, I do not know where all the characters are",
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
                listOfCharacterModels += characterDbRepository.getCharacterByPage(page)
                liveData.value = listOfCharacterModels
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
                val charactersFilterList = getCharacterFilterListUseCase.execute(filters)
                if (charactersFilterList.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Impossible!!!! There is no such being in all the universes!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    liveData.value = charactersFilterList
                }
            }
            this.cancel()
        }
    }


    private fun localFilters(name: String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val charactersFilterList = characterDbRepository.getCharacterByName(name)
                if (charactersFilterList.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Impossible!!!! There is no such being in all the universes!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    liveData.value = charactersFilterList
                }
            }
            this.cancel()
        }
    }
}