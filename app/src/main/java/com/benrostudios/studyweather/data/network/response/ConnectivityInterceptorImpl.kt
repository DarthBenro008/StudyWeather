package com.benrostudios.studyweather.data.network.response

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.benrostudios.studyweather.internal.NoConnectivtyException
import okhttp3.Interceptor
import okhttp3.Response


class ConnectivityInterceptorImpl(
    context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(isconnectedToWifi(appContext))
            throw NoConnectivtyException()
        return chain.proceed(chain.request())
    }



    fun isconnectedToWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                ?: return false

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } else {
            val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                ?: return false
            return networkInfo.isConnected
        }
    }
}