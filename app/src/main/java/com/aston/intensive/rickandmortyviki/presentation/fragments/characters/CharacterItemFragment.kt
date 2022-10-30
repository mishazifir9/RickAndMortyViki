package com.aston.intensive.rickandmortyviki.presentation.fragments.characters


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aston.intensive.rickandmortyviki.MainActivity
import com.aston.intensive.rickandmortyviki.databinding.CharacterFragmentItemBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.EpisodeFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.episodes.EpisodeItemFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.locations.LocationItemFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.CharacterDetailViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.factory.CharacterDetailVMFactory
import com.bumptech.glide.Glide

private const val BUNDLE_PARAMETER = "id"

class CharacterItemFragment : Fragment() {
    private lateinit var viewModel: CharacterDetailViewModel
    private var paramFromBundle: String? = null
    private val supportFragment = SupportFragment()
    lateinit var binding: CharacterFragmentItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramFromBundle = it.getString(BUNDLE_PARAMETER)
        }

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        viewModel = ViewModelProvider(this, CharacterDetailVMFactory(requireActivity().
        application))[CharacterDetailViewModel::class.java]
        paramFromBundle?.let { viewModel.update(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterFragmentItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodeAdapter = EpisodeFragmentAdapter(emptyList()){ position ->
            onEpisodeClicked(position)
        }

       binding.recyclerViewEpisodeItem.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = episodeAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.textViewCharacterItemName.text = it[0].name
            binding.textViewCharacterItemStatus.text = it[0].status
            binding.textViewCharacterItemSpecie.text = it[0].species
            binding.textViewCharacterItemGender.text = it[0].gender
            binding.textextViewCharacterItemOrigin.apply {
                setOnClickListener { onOriginClick() }
            }.text = it[0].origin["name"]
            binding.textViewCharacterItemLocation.apply {
                setOnClickListener { locationClick() }
            }.text = it[0].location["name"]
            viewModel.updateEpisodes(it[0].episode)
            Glide.with(view.context).asBitmap().load(it[0].image).into(binding.imageViewCharacterItem)
        }

        viewModel.episodeModelLiveData.observe(viewLifecycleOwner) {
            binding.progressBarEpisodeItem.visibility = ProgressBar.VISIBLE
            episodeAdapter.updateAdapter(it)
            binding.progressBarEpisodeItem.visibility = ProgressBar.INVISIBLE
        }
    }


    private fun onEpisodeClicked(position: Int){
        viewModel.episodeModelLiveData.value.apply {
            if (this != null){
                val id = this[position].id
                openEpisodeFragment(id)
            }
        }
    }


    private fun locationClick(){
        viewModel.liveData.value.apply {
            if (this != null){
                var locationId = this[0].location["url"]
                locationId = locationId?.substring(locationId.lastIndexOf("/") + 1, locationId.length)
                openLocationFragment(locationId)
            }else {
                Toast.makeText(context, "oy ey,I do not know where it is", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }


    private fun onOriginClick(){
        viewModel.liveData.value.apply {
            if (this != null && this[0].origin["name"] != "unknown"){
                var originId = this[0].origin["url"]
                originId = originId?.substring(originId.lastIndexOf("/") + 1, originId.length)
                openLocationFragment(originId)
            }else{
                Toast.makeText(context, "oy ey, I do not know where it is", Toast.LENGTH_SHORT).show()
                return
            }
        }
    }


    private fun openEpisodeFragment(id: String?){
        val bundle = Bundle().apply {
            putString("id", id)
        }
        val episodeItemFragment = EpisodeItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(episodeItemFragment, activity, true)
    }


    private fun openLocationFragment(id: String?){
        val bundle = Bundle().apply {
            putString("id", id)
        }
        val locationItemFragment = LocationItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(locationItemFragment, activity, true)
    }
}