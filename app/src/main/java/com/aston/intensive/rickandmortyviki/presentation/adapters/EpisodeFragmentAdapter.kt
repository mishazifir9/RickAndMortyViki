package com.aston.intensive.rickandmortyviki.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aston.intensive.rickandmortyviki.R
import com.aston.intensive.rickandmortyviki.domain.models.EpisodeModel

class EpisodeFragmentAdapter(
    private var episodeModelsList: List<EpisodeModel>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<EpisodeFragmentAdapter.EpisodeViewHolder>() {

    class EpisodeViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private val episodeName: TextView = itemView.findViewById(R.id.textViewEpisodeName)
        private val episodeNumber: TextView = itemView.findViewById(R.id.textViewEpisodeNumber)
        private val airDate: TextView = itemView.findViewById(R.id.textViewEpisodeAirDate)

        fun bind(episodeModel: EpisodeModel) {
            episodeName.text = episodeModel.name
            episodeNumber.text = episodeModel.episode
            airDate.text = episodeModel.air_date
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.episode_item_list, parent, false)
        return EpisodeViewHolder(itemView, onItemClicked)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodeModelsList[position])
    }

    override fun getItemCount(): Int {
        return episodeModelsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<EpisodeModel>) {
        episodeModelsList = list
        notifyDataSetChanged()
    }
}