package com.aston.intensive.rickandmortyviki.data.repository.episode

import android.content.Context
import com.aston.intensive.rickandmortyviki.data.dao.EpisodeDao
import com.aston.intensive.rickandmortyviki.data.database.AppDatabase
import com.aston.intensive.rickandmortyviki.data.entities.EpisodeEntity
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class EpisodeDbRepository(context: Context) {
    private var dataBase: EpisodeDao = AppDatabase.getInstance(context)?.episodeDao()!!


    fun getAllEpisodes() = dataBase.getAllEpisodes()


    fun getEpisodeById(id: String): EpisodeModel {
        var episodeModel: EpisodeModel
        try {
            episodeModel = EpisodeModel(
                dataBase.getEpisodeById(id).id,
                dataBase.getEpisodeById(id).name,
                dataBase.getEpisodeById(id).episode,
                dataBase.getEpisodeById(id).airDate,
                dataBase.getEpisodeById(id).characters.split(",").toList()
            )
        } catch (e: Exception) {
            episodeModel = EpisodeModel(
                "not loaded",
                "not loaded",
                "not loaded",
                "not loaded",
                listOf("not loaded", "not loaded")
            )
        }
        return episodeModel
    }


    fun getEpisodeByName(name: String): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        try {
            for (episodeFromDb in dataBase.getEpisodeByName(name)) {
                val episodeModel = EpisodeModel(
                    episodeFromDb.id,
                    episodeFromDb.name,
                    episodeFromDb.airDate,
                    episodeFromDb.episode,
                    episodeFromDb.characters.split(",").toList()
                )
                listOfEpisodeModels.add(episodeModel)
            }
        } catch (e: Exception) {
        }
        return listOfEpisodeModels
    }


    fun getEpisodeByPage(page: Int): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        for (episodeFromDb in dataBase.getEpisodesByPage(
            (page - 1) * 20 + 1,
            (page - 1) * 20 + 20
        )) {
            val episodeModel = EpisodeModel(
                episodeFromDb.id,
                episodeFromDb.name,
                episodeFromDb.airDate,
                episodeFromDb.episode,
                episodeFromDb.characters.split(",").toList()
            )
            listOfEpisodeModels.add(episodeModel)
        }
        return listOfEpisodeModels
    }


    private fun addEpisode(episodeModel: EpisodeModel) {
        dataBase.addEpisode(
            EpisodeEntity(
                episodeModel.id,
                episodeModel.name,
                episodeModel.air_date,
                episodeModel.episode,
                episodeModel.characters.joinToString()
            )
        )
    }


    fun addListOfEpisodes(listOfEpisodeModels: List<EpisodeModel>) {
        for (episode in listOfEpisodeModels) {
            addEpisode(episode)
        }
    }


    fun getEpisodesByIds(ids: List<String>): List<EpisodeModel> {
        val listOfEpisodeModels = mutableListOf<EpisodeModel>()
        for (id in ids) {
            listOfEpisodeModels.add(getEpisodeById(id))
        }
        return listOfEpisodeModels
    }

}