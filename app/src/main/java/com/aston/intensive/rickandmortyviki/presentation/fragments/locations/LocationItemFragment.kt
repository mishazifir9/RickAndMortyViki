package com.aston.intensive.rickandmortyviki.presentation.fragments.locations


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aston.intensive.rickandmortyviki.MainActivity
import com.aston.intensive.rickandmortyviki.databinding.LocationFragmentItemBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.CharacterFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.characters.CharacterItemFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.LocationDetailViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.factory.LocationDetailVMFactory

private const val BUNDLE_PARAMETER = "id"

class LocationItemFragment : Fragment() {
    private lateinit var viewModel: LocationDetailViewModel
    private var paramFromBundle: String? = null
    private val supportFragment = SupportFragment()
    lateinit var binding: LocationFragmentItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            paramFromBundle = it.getString(BUNDLE_PARAMETER)
        }

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeButtonEnabled(true)

        viewModel = ViewModelProvider(
            this, LocationDetailVMFactory(
                requireActivity().application
            )
        )[LocationDetailViewModel::class.java]
        paramFromBundle?.let { viewModel.update(it) }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationFragmentItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterAdapter = CharacterFragmentAdapter(emptyList()) { position ->
            onCharacterClicked(position)
        }

        binding.recyclerViewForLocationFragment.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = characterAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.textViewLocationItemName.text = it[0].name
            binding.textViewLocationItemType.text = it[0].type
            binding.textViewLocationItemDimension.text = it[0].dimension
            viewModel.updateCharacter(it[0].residents)
        }

        viewModel.characterModelLiveData.observe(viewLifecycleOwner) {
            binding.progressBarForLocationFragment.visibility = ProgressBar.VISIBLE
            characterAdapter.refreshAdapter(it)
            binding.progressBarForLocationFragment.visibility = ProgressBar.INVISIBLE
        }
    }


    private fun onCharacterClicked(position: Int) {
        viewModel.characterModelLiveData.value.apply {
            if (this != null) {
                val id = this[position].id
                openCharacterFragment(id)
            }
        }
    }


    private fun openCharacterFragment(id: String) {
        val bundle = Bundle().apply {
            putString("id", id)
        }
        val characterItemFragment = CharacterItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(characterItemFragment, activity, true)
    }
}