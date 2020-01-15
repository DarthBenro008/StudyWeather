package com.benrostudios.studyweather.data.response

import android.content.Context
import android.net.ConnectivityManager
import com.benrostudios.studyweather.internal.NoConnectivtyException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectityInterceptorImpl(
    context: Context) : ConnectityInterceptor {

    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(isOnline())
            throw NoConnectivtyException()
        return chain.proceed(chain.request())
    }


    private fun isOnline(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}