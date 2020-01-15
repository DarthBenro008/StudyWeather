package com.benrostudios.studyweather.data

import com.benrostudios.studyweather.data.response.ConnectityInterceptor
import com.benrostudios.studyweather.data.response.ConnectityInterceptorImpl
import com.benrostudios.studyweather.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "7da43124caf2388ffdaf70989ac1f349"

//http://api.weatherstack.com/current?access_key=7da43124caf2388ffdaf70989ac1f349&query=New%20York
//http://api.weatherstack.com/current?access_key=7da43124caf2388ffdaf70989ac1f349&query=New York

interface WeatherStackAPIService {
    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String
        //@Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>
    
    
    companion object{
        operator fun invoke(
                connectityInterceptor: ConnectityInterceptor
        ): WeatherStackAPIService{
            // Easier to invoke WeatherStackAPIService()
            val requestInterceptor = Interceptor{
                chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherStackAPIService::class.java)
        }
    }
}