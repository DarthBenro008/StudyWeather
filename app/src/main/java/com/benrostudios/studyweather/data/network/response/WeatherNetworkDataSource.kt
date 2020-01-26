package com.benrostudios.studyweather.data.network.response

import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCureentWeather(
        location: String,
        languageCode: String
    )
}