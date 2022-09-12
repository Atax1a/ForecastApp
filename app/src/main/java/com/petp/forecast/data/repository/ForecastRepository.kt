package com.petp.forecast.data.repository

import androidx.lifecycle.LiveData
import com.petp.forecast.data.db.entity.CurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean): LiveData<CurrentWeatherEntry>
}