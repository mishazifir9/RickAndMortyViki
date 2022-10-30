package com.aston.intensive.rickandmortyviki

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object CheckNetworkConnection {

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            network ?: return false
            val actNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    true
                }
                actNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }
}