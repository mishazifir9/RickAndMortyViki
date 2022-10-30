package com.aston.intensive.rickandmortyviki.presentation.fragments.locations

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
import com.aston.intensive.rickandmortyviki.MainActivity
import com.aston.intensive.rickandmortyviki.R
import com.aston.intensive.rickandmortyviki.databinding.LocationsFragmentBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.LocationFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.LocationViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.location.factory.LocationVMFactory
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class LocationsFragment : Fragment() {

    private lateinit var viewModel: LocationViewModel
    private val supportFragment = SupportFragment()
    lateinit var binding: LocationsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            LocationVMFactory(requireActivity().application)
        )[LocationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LocationsFragmentBinding.inflate(layoutInflater, container, false)
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.update()
        val locationAdapter = LocationFragmentAdapter(emptyList()) { position ->
            onItemClicked(position)
        }

        binding.locationRecyclerView.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = locationAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.locationProgressBar.visibility = ProgressBar.VISIBLE
            locationAdapter.updateAdapter(it)
            binding.locationProgressBar.visibility = ProgressBar.INVISIBLE
        }

        binding.locationRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && viewModel.liveData.value?.size!! >= 20) {
                    viewModel.addNewPage()
                }
            }
        })

        binding.locationsRefreshLayout.apply {
            setOnRefreshListener {
                onRefresh()
                isRefreshing = false
            }
        }

        binding.locationFiltersButton.apply {
            setOnClickListener {
                val window = PopupWindow(view)
                val v = layoutInflater.inflate(R.layout.location_filters, null)
                window.contentView = v
                v.findViewById<Button>(R.id.confirmFiltersButton).apply {
                    setOnClickListener {
                        val titles = mutableMapOf<String, String>()
                        val g1 = v.findViewById<ChipGroup>(R.id.chipGroup)
                        v.findViewById<SearchView>(R.id.searchView).apply {
                            titles["name"] = this.query.toString()
                        }
                        var ids = g1.checkedChipIds
                        ids.forEach { id ->
                            titles["type"] = g1.findViewById<Chip>(id).text.toString()
                        }
                        viewModel.useFilters(titles)
                        window.dismiss()
                    }
                }
                window.isFocusable = true
                window.width = ViewGroup.LayoutParams.MATCH_PARENT
                window.height = ViewGroup.LayoutParams.MATCH_PARENT
                window.showAtLocation(view, 1, 0, 0)
                window.update()
            }
        }
    }


    private fun onItemClicked(position: Int) {
        val location = viewModel.liveData.value?.get(position)
        val bundle = Bundle().apply {
            putString("id", location?.id)
        }

        val locationItemFragment = LocationItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(locationItemFragment, activity, true)
    }


    private fun onRefresh() {
        viewModel.update()
    }
}