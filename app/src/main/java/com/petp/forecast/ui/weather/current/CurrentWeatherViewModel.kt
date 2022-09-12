package com.petp.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.petp.forecast.data.repository.ForecastRepository
import com.petp.forecast.internal.UnitSystem
import com.petp.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }

}