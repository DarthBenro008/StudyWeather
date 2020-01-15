package com.benrostudios.studyweather.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benrostudios.studyweather.data.db.entity.CURRENT_WEATHER_ID
import com.benrostudios.studyweather.data.db.entity.CurrentWeatherEntry


@Dao
interface CurrentWeatherDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from weather_table where id = $CURRENT_WEATHER_ID")
    fun getWeatherData(): LiveData<CurrentWeatherEntry>


}