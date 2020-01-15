package com.benrostudios.studyweather.data.response

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.benrostudios.studyweather.data.WeatherStackAPIService
import com.benrostudios.studyweather.internal.NoConnectivtyException

class WeatherNetworkDataSourceImpl(
    private val weatherStackAPIService: WeatherStackAPIService
) : WeatherNetworkDataSource {

    private val _downloadedCurrenWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrenWeather

    override suspend fun fetchCureentWeather(location: String, languageCode: String) {
       try{
           val fetchedCurrenWeather = weatherStackAPIService.getCurrentWeather("London").await()
           _downloadedCurrenWeather.postValue(fetchedCurrenWeather)
       }
       catch(e: NoConnectivtyException){
           Log.e("Connectivity","No Internet Connection")
       }
    }
}