package com.amity.todolist.utils

import android.content.Context
import android.net.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkConnectivityObserver(private val context: Context) : ConnectivityObserver {

    override fun observe(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkChangeFilter = NetworkRequest.Builder().build()
                val callback = object : ConnectivityManager.NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        launch {
                            send(ConnectivityObserver.Status.Available("Available"))
                        }
                    }
                }

                connectivityManager.registerNetworkCallback(networkChangeFilter, callback)

                awaitClose {
                    connectivityManager.unregisterNetworkCallback(callback)
                }
        }.distinctUntilChanged()
    }

}