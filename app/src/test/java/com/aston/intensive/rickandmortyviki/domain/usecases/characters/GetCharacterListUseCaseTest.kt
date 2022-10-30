package com.aston.intensive.rickandmortyviki.domain.usecases.characters

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.aston.intensive.rickandmortyviki.data.repository.character.CharacterDbRepository
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.CharacterViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCharacterListUseCaseTest {
    // given
    private val characterDbRepository = mock<CharacterDbRepository>()
    private val getCharacterListUseCase = mock<GetCharacterListUseCase>()
    private val getCharacterFilterListUseCase = mock<GetCharacterFilterListUseCase>()
    private val application = mock<Application>()
    private val characterModel = CharacterModel(
        "id",
        "name",
        "status",
        "species",
        "gender",
        mapOf("name" to "name", "url" to "url"),
        mapOf("name" to "name", "url" to "url"),
        "url",
        listOf("str", "str")
    )
    private val characterViewModel = CharacterViewModel(
        application,
        getCharacterListUseCase,
        characterDbRepository,
        getCharacterFilterListUseCase
    )

    @BeforeEach()
    fun beforeEach() {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    // when
    @org.junit.jupiter.api.Test
    fun getCharacterListUseCaseTest() = runBlocking {

        Mockito.`when`(getCharacterListUseCase.execute(6)).thenReturn(listOf(characterModel))
        characterViewModel.liveData.value = getCharacterListUseCase.execute(6)

        // then
        val expected = listOf(characterModel)
        val actual = characterViewModel.liveData.value
        Assertions.assertEquals(expected, actual)
    }

}