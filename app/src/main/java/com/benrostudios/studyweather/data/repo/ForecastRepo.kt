package com.benrostudios.studyweather.data.repo

import androidx.lifecycle.LiveData
import com.benrostudios.studyweather.data.db.entity.CurrentWeatherEntry

interface ForecastRepo {

    suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry>
}