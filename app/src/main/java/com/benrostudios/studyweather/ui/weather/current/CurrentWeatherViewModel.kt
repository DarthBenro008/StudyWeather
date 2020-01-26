package com.benrostudios.studyweather.ui.weather.current

import androidx.lifecycle.ViewModel
import com.benrostudios.studyweather.data.repo.ForecastRepo
import com.benrostudios.studyweather.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepo: ForecastRepo
): ViewModel() {
    val weather by lazyDeferred() {
        forecastRepo.getCurrentWeather()
    }
}
