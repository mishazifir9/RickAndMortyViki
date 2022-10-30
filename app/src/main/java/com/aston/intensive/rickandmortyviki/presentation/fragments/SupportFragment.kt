package com.aston.intensive.rickandmortyviki.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aston.intensive.rickandmortyviki.R

class SupportFragment {
    fun startFragment(fragment: Fragment, activity: FragmentActivity?, saved: Boolean = false) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (saved) {
            transaction?.replace(R.id.fragmentContainerView, fragment)
            transaction?.addToBackStack(null)
        } else {
            transaction?.replace(R.id.fragmentContainerView, fragment)
        }
        transaction?.commit()
    }
}