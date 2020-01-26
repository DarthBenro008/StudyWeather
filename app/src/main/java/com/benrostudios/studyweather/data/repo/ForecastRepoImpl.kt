package com.benrostudios.studyweather.data.repo

import androidx.lifecycle.LiveData
import com.benrostudios.studyweather.data.db.CurrentWeatherDAO
import com.benrostudios.studyweather.data.db.entity.CurrentWeatherEntry
import com.benrostudios.studyweather.data.network.response.CurrentWeatherResponse
import com.benrostudios.studyweather.data.network.response.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepoImpl(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepo {
    override suspend fun getCurrentWeather(): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext currentWeatherDAO.getWeatherData()
        }
    }

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever { newCurrentWeather ->
            //persist
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    private fun persistFetchedCurrentWeather(fethcedWeather: CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
                currentWeatherDAO.upsert(fethcedWeather.current)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }
    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCureentWeather("New York","en")
    }
    private fun isFetchNeeded(lastFetched: ZonedDateTime): Boolean{
        val thirtyMinsAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetched.isBefore(thirtyMinsAgo)

    }
}