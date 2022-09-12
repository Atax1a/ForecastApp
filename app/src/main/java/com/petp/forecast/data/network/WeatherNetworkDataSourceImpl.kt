package com.petp.forecast.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.petp.forecast.data.network.response.CurrentWeatherResponse
import com.petp.forecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val apixuWeatherApiService: ApixuWeatherApiService) :
    WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, units: String) {
        try {
            val fetcherCurrentWeather = apixuWeatherApiService.getCurrentWeather(location, units)
            _downloadedCurrentWeather.postValue(fetcherCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection",e)
        }
    }
}