package com.benrostudios.studyweather.data.network.response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.studyweather.data.network.WeatherStackAPIService
import java.io.IOException

class WeatherNetworkDataSourceImpl(
    private val weatherStackAPIService: WeatherStackAPIService
) : WeatherNetworkDataSource {

    private val _downloadedCurrenWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrenWeather

    override suspend fun fetchCureentWeather(location: String, languageCode: String) {
       try{
            val fetchedCurrenWeather = weatherStackAPIService.getCurrentWeather(location).await()
            _downloadedCurrenWeather.postValue(fetchedCurrenWeather)

        }
        catch(e: IOException){
            Log.e("Connectivity","No Internet Connection  " + e.toString())
        }
    }
}