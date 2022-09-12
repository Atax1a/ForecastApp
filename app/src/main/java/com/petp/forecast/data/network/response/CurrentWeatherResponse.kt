package com.petp.forecast.data.network.response

import com.google.gson.annotations.SerializedName
import com.petp.forecast.data.db.entity.CurrentWeatherEntry
import com.petp.forecast.data.db.entity.Location
import com.petp.forecast.data.db.entity.Request


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)