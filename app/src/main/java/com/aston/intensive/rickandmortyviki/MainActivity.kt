package com.aston.intensive.rickandmortyviki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import com.aston.intensive.rickandmortyviki.databinding.ActivityMainBinding
import com.aston.intensive.rickandmortyviki.presentation.fragments.SupportFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.characters.CharactersFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.episodes.EpisodesFragment
import com.aston.intensive.rickandmortyviki.presentation.fragments.locations.LocationsFragment

class MainActivity : AppCompatActivity() {
    private val supportFragment = SupportFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getClickBottomNavigation()
    }


    private fun getClickBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.character_page -> {
                    supportFragment.startFragment(CharactersFragment(), this)
                }
                R.id.location_page -> {
                    supportFragment.startFragment(LocationsFragment(), this)
                }
                R.id.episode_page -> {
                    supportFragment.startFragment(EpisodesFragment(), this)
                }
            }
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            val fragmentManager: FragmentManager = supportFragmentManager
            fragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}