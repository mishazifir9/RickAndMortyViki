package com.aston.intensive.rickandmortyviki.data.repository.episode

import android.app.Application
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeFilterListUseCase
import com.aston.intensive.rickandmortyviki.domain.usecases.episodes.GetEpisodeListUseCase
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.EpisodeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

class EpisodeDbRepositoryTest {


    // given
    private val episodeDbRepository = mock<EpisodeDbRepository>()
    private val getEpisodeListUseCase = mock<GetEpisodeListUseCase>()
    private val getEpisodeFilterListUseCase = mock<GetEpisodeFilterListUseCase>()
    private val application = mock<Application>()
    private val episodeModel = EpisodeModel(
        "id",
        "name",
        "episode",
        "air_date",
        listOf<String>("characters", "characters")
    )
    private val episodeViewModel = EpisodeViewModel(
        application,
        getEpisodeListUseCase,
        episodeDbRepository,
        getEpisodeFilterListUseCase
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
    fun `dbRepositoryTestGetEpisodeById()`() = runBlocking {

        Mockito.`when`(episodeDbRepository.getEpisodeById("id"))
            .thenReturn(episodeModel)
        episodeViewModel.liveData.value = listOf(episodeDbRepository.getEpisodeById("id"))

        // then
        val expected = listOf(episodeModel)
        val actual = episodeViewModel.liveData.value
        Assertions.assertEquals(expected, actual)
    }

}