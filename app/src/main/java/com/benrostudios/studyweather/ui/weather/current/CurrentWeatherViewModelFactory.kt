package com.benrostudios.studyweather.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.benrostudios.studyweather.data.repo.ForecastRepo

class CurrentWeatherViewModelFactory (
    private val forecastRepo: ForecastRepo
): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepo) as T
    }
}