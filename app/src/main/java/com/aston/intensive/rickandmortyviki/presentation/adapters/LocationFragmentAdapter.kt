package com.aston.intensive.rickandmortyviki.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aston.intensive.rickandmortyviki.R
import com.aston.intensive.rickandmortyviki.domain.models.LocationModel

class LocationFragmentAdapter(
    private var locationModelsList: List<LocationModel>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<LocationFragmentAdapter.LocationViewHolder>() {

    class LocationViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private val locationName: TextView = itemView.findViewById(R.id.textViewLocationName)
        private val locationType: TextView = itemView.findViewById(R.id.textViewLocationType)
        private val locationDimension: TextView = itemView.findViewById(R.id.textViewDimension)

        fun bind(locationModel: LocationModel) {
            locationName.text = locationModel.name
            locationType.text = locationModel.type
            locationDimension.text = locationModel.dimension
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.location_item_list, parent, false)
        return LocationViewHolder(itemView, onItemClicked)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(locationModelsList[position])
    }

    override fun getItemCount(): Int {
        return locationModelsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAdapter(list: List<LocationModel>) {
        locationModelsList = list
        notifyDataSetChanged()
    }
}