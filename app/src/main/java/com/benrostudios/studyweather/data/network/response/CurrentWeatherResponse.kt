package com.benrostudios.studyweather.data.network.response

import com.benrostudios.studyweather.data.db.entity.CurrentWeatherEntry
import com.benrostudios.studyweather.data.db.entity.Location
import com.benrostudios.studyweather.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)