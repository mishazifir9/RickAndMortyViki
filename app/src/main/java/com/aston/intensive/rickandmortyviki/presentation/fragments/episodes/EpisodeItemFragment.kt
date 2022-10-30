package com.aston.intensive.rickandmortyviki.presentation.fragments.episodes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.intensive.rickandmortyviki.MainActivity
import com.aston.intensive.rickandmortyviki.databinding.EpisodeFragmentItemBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.CharacterFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.characters.CharacterItemFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.EpisodeDetailViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.factory.EpisodeDetailVMFactory

private const val BUNDLE_PARAMETER = "id"

class EpisodeItemFragment : Fragment() {
    private lateinit var viewModel: EpisodeDetailViewModel
    private var paramFromBundle: String? = null
    private val supportFragment = SupportFragment()
    lateinit var binding: EpisodeFragmentItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramFromBundle = it.getString(BUNDLE_PARAMETER)
        }

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        viewModel = ViewModelProvider(this, EpisodeDetailVMFactory(requireActivity().
        application))[EpisodeDetailViewModel::class.java]
        paramFromBundle?.let { viewModel.update(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EpisodeFragmentItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterAdapter = CharacterFragmentAdapter(emptyList()){ position ->
            onCharacterClicked(position)
        }

        binding.recyclerViewCharacterItem.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = characterAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.textViewEpisodeItemName.text = it[0].name
            binding.textViewEpisodeItemAirDate.text = it[0].air_date
            binding.textViewEpisodeItemEpisode.text = it[0].episode
            viewModel.updateCharacter(it[0].characters)
        }

        viewModel.characterModelLiveData.observe(viewLifecycleOwner) {
            binding.progressBarCharacterItem.visibility = ProgressBar.VISIBLE
            characterAdapter.refreshAdapter(it)
            binding.progressBarCharacterItem.visibility = ProgressBar.INVISIBLE
        }
    }

    private fun onCharacterClicked(position: Int){
        viewModel.characterModelLiveData.value.apply {
            if (this != null){
                val id = this[position].id
                openCharacterFragment(id)
            }
        }
    }


    private fun openCharacterFragment(id: String){
        val bundle = Bundle().apply {
            putString("id", id)
        }
        val characterItemFragment = CharacterItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(characterItemFragment, activity, true)
    }
}