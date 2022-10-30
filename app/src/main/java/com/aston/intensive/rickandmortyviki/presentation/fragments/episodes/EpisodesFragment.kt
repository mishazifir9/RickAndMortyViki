package com.aston.intensive.rickandmortyviki.presentation.fragments.episodes

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
import com.aston.intensive.rickandmortyviki.databinding.EpisodesFragmentBinding
import com.aston.intensive.rickandmortyviki.presentation.adapters.EpisodeFragmentAdapter
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.EpisodeViewModel
import com.aston.intensive.rickandmortyviki.presentation.viewmodels.episode.factory.EpisodeVMFactory

class EpisodesFragment : Fragment() {

    private lateinit var viewModel: EpisodeViewModel
    private val supportFragment = SupportFragment()
    lateinit var binding: EpisodesFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            EpisodeVMFactory(requireActivity().application)
        )[EpisodeViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EpisodesFragmentBinding.inflate(layoutInflater, container, false)
        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setHomeButtonEnabled(false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.update()

        val episodeAdapter = EpisodeFragmentAdapter(emptyList()) { position ->
            onItemClicked(position)
        }

        binding.episodeRecyclerView.apply {
            layoutManager = GridLayoutManager(view.context, 2)
            adapter = episodeAdapter
        }

        viewModel.liveData.observe(viewLifecycleOwner) {
            binding.episodeProgressBar.visibility = ProgressBar.VISIBLE
            episodeAdapter.updateAdapter(it)
            binding.episodeProgressBar.visibility = ProgressBar.INVISIBLE
        }

        binding.episodeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && viewModel.liveData.value?.size!! >= 10) {
                    viewModel.addNewPage()
                }
            }
        })

        binding.episodesRefreshLayout.apply {
            setOnRefreshListener {
                onRefresh()
                isRefreshing = false
            }
        }

        binding.episodeFiltersButton.apply {
            setOnClickListener {
                val popupWindow = PopupWindow(view)
                val filterInflater = layoutInflater.inflate(R.layout.episode_filters, null)
                popupWindow.contentView = filterInflater
                filterInflater.findViewById<Button>(R.id.confirmFiltersButton).apply {
                    setOnClickListener {
                        val titles = mutableMapOf<String, String>()
                        filterInflater.findViewById<SearchView>(R.id.searchView).apply {
                            titles["name"] = this.query.toString()
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


    private fun onItemClicked(position: Int) {
        val episode = viewModel.liveData.value?.get(position)
        val bundle = Bundle().apply {
            putString("id", episode?.id)
        }
        val episodeItemFragment = EpisodeItemFragment().apply {
            arguments = bundle
        }
        supportFragment.startFragment(episodeItemFragment, activity, true)
    }


    private fun onRefresh() {
        viewModel.update()
    }
}