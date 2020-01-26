package com.benrostudios.studyweather

import android.app.Application
import com.benrostudios.studyweather.data.db.ForecastDatabase
import com.benrostudios.studyweather.data.network.WeatherStackAPIService
import com.benrostudios.studyweather.data.network.response.ConnectivityInterceptor
import com.benrostudios.studyweather.data.network.response.ConnectivityInterceptorImpl
import com.benrostudios.studyweather.data.network.response.WeatherNetworkDataSource
import com.benrostudios.studyweather.data.network.response.WeatherNetworkDataSourceImpl
import com.benrostudios.studyweather.data.repo.ForecastRepo
import com.benrostudios.studyweather.data.repo.ForecastRepoImpl
import com.benrostudios.studyweather.ui.weather.current.CurrentWeatherViewModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeaherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherStackAPIService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepo>() with singleton { ForecastRepoImpl(instance(),instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }

    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}