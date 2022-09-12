package com.petp.forecast.data.network

import androidx.lifecycle.LiveData
import com.petp.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        units: String,
    )
}