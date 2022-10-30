package com.aston.intensive.rickandmortyviki.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aston.intensive.rickandmortyviki.R
import com.aston.intensive.rickandmortyviki.domain.models.CharacterModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CharacterFragmentAdapter(
    private var characterModelsList: List<CharacterModel>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<CharacterFragmentAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private val characterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
        private val characterSpecies: TextView = itemView.findViewById(R.id.textViewSpecies)
        private val characterGender: TextView = itemView.findViewById(R.id.textViewGender)
        private val characterStatus: TextView = itemView.findViewById(R.id.textViewStatus)
        private val characterImage: ImageView = itemView.findViewById(R.id.image_character)

        fun bind(characterModel: CharacterModel) {
            characterName.text = characterModel.name
            characterSpecies.text = characterModel.species
            characterGender.text = characterModel.gender
            characterStatus.text = characterModel.status

            Glide.with(itemView.context).load(characterModel.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(characterImage)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.character_item_list, parent, false)
        return CharacterViewHolder(itemView, onItemClicked)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterModelsList[position])
    }

    override fun getItemCount(): Int {
        return characterModelsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshAdapter(list: List<CharacterModel>) {
        characterModelsList = list
        notifyDataSetChanged()
    }
}