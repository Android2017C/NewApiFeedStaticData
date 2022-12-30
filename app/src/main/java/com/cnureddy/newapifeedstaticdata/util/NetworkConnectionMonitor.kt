package com.cnureddy.newapifeedstaticdata.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class NetworkConnectionMonitor(var context: Context) :
    LiveData<Boolean?>() {
    private var callback: ConnectionNetworkCallback? = null
    fun unregisterDefaultNetworkCallback() {
        val connectivityManager =
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        connectivityManager.unregisterNetworkCallback(callback!!)
    }

    @SuppressLint("LongLogTag")
    @RequiresApi(Build.VERSION_CODES.N)
    fun registerDefaultNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            postValue(checkConnection(connectivityManager))
            callback = ConnectionNetworkCallback()
            connectivityManager.registerDefaultNetworkCallback(callback!!)
        } catch (e: Exception) {
            Log.d("Connection: Exception in registerDefaultNetworkCallback", "xD")
            postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkConnection(connectivityManager: ConnectivityManager): Boolean {
        val network = connectivityManager.activeNetwork
        return if (network == null) {
            false
        } else {
            val actNw = connectivityManager.getNetworkCapabilities(network)
            (actNw != null
                    && (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
        }
    }

    inner class ConnectionNetworkCallback : NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
            Log.d("Connection:", "onAvailable")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
            Log.d("Connection:", "onLost")
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
            Log.d("Connection:", "onBlockedStatusChanged")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            Log.d("Connection:", "onCapabilitiesChanged")
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
            Log.d("Connection:", "onLinkPropertiesChanged")
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Log.d("Connection:", "onLosing")
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.d("Connection:", "onUnavailable")
        }
    }
}