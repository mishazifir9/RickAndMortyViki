package com.aston.intensive.rickandmortyviki.presentation.fragments.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aston.intensive.rickandmortyviki.R
import com.aston.intensive.rickandmortyviki.MainActivity
import com.aston.intensive.rickandmortyviki.databinding.CharactersFragmentBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.CharacterFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.CharacterViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.character.factory.CharacterVMFactory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CharactersFragment : Fragment() {

    private lateinit var viewModel: CharacterViewModel
    private val supportFragment = SupportFragment()
    lateinit var binding: CharactersFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            CharacterVMFactory(requireActivity().application)
        )[CharacterViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(layoutInflater, container, false)
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.update()

        val characterAdapter = CharacterFragmentAdapter(emptyList()) { position ->
            onCharacterClicked(position)
        }

        binding.characterRecyclerView.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = characterAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.characterListProgressBar.visibility = ProgressBar.VISIBLE
            characterAdapter.refreshAdapter(it)
            binding.characterListProgressBar.visibility = ProgressBar.INVISIBLE
        }

        binding.characterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && viewModel.liveData.value?.size!! >= 20) {
                    viewModel.addNewPage()
                }
            }
        })

        binding.characterRefreshLayout.apply {
            setOnRefreshListener {
                onRefresh()
                isRefreshing = false
            }
        }

        binding.filtersButton.apply {
            setOnClickListener {
                val popupWindow = PopupWindow(view)
                val filterInflater = layoutInflater.inflate(R.layout.character_filters, null)
                popupWindow.contentView = filterInflater
                filterInflater.findViewById<Button>(R.id.confirmFiltersButton).apply {
                    setOnClickListener {
                        val titles = mutableMapOf<String, String>()
                        filterInflater.findViewById<SearchView>(R.id.searchView).apply {
                            titles["name"] = this.query.toString()
                        }
                        val g1 = filterInflater.findViewById<ChipGroup>(R.id.chipGroup)
                        val g2 = filterInflater.findViewById<ChipGroup>(R.id.chipGroup2)
                        val g3 = filterInflater.findViewById<ChipGroup>(R.id.chipGroup3)
                        var ids = g1.checkedChipIds
                        ids.forEach { id ->
                            titles["status"] = g1.findViewById<Chip>(id).text.toString()
                        }
                        ids = g2.checkedChipIds
                        ids.forEach { id ->
                            titles["species"] = g2.findViewById<Chip>(id).text.toString()
                        }
                        ids = g3.checkedChipIds
                        ids.forEach { id ->
                            titles["gender"] = g3.findViewById<Chip>(id).text.toString()
                        }
                        viewModel.useFilters(titles)
                        popupWindow.dismiss()
                    }
                }
                popupWindow.isFocusable = true
                popupWindow.width = ViewGroup.LayoutParams.MATCH_PARENT
                popupWindow.height = ViewGroup.LayoutParams.MATCH_PARENT
                popupWindow.showAtLocation(view, 1, 0, 0)
                popupWindow.update()
            }
        }
    }


    private fun onCharacterClicked(position: Int) {
        val character = viewModel.liveData.value?.get(position)
        val bundle = Bundle().apply {
            putString("id", character?.id)
        }
        val characterItemFragment = CharacterItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(characterItemFragment, activity, true)
    }


    private fun onRefresh() {
        viewModel.update()
    }
}