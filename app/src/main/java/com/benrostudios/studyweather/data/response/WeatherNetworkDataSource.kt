package com.benrostudios.studyweather.data.response

import androidx.lifecycle.LiveData

interface WeatherNetworkDataSource {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCureentWeather(
        location: String,
        languageCode: String
    )
}