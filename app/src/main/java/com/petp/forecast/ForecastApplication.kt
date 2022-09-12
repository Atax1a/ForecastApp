package com.petp.forecast

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.petp.forecast.data.db.CurrentWeatherDao
import com.petp.forecast.data.db.ForecastDatabase
import com.petp.forecast.data.network.ApixuWeatherApiService
import com.petp.forecast.data.network.ConnectivityInterceptor
import com.petp.forecast.data.network.ConnectivityInterceptorImpl
import com.petp.forecast.data.network.WeatherNetworkDataSourceImpl
import com.petp.forecast.data.repository.ForecastRepository
import com.petp.forecast.data.repository.ForecastRepositoryImpl
import com.petp.forecast.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule


class ForecastApplication : Application(), DIAware {

    override val di: DI = DI.lazy{
        import(androidXModule(this@ForecastApplication))
        bind<ForecastDatabase>() with singleton { ForecastDatabase.invoke(instance()) }
        bind<CurrentWeatherDao>() with singleton { ForecastDatabase(instance()).currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<ApixuWeatherApiService>() with singleton { ApixuWeatherApiService(instance())}
        bind<WeatherNetworkDataSourceImpl>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }
        bind<CurrentWeatherViewModelFactory>() with provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }


}
