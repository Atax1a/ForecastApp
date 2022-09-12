package com.petp.forecast.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.petp.forecast.databinding.FragmentCurrentWeatherBinding
import com.petp.forecast.ui.Base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class CurrentWeatherFragment : ScopedFragment(), DIAware {

    private lateinit var binding: FragmentCurrentWeatherBinding

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrentWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory: CurrentWeatherViewModelFactory by di.instance()
        viewModel = ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()


//        val apiService = ApixuWeatherApiService(ConnectivityInterceptorImpl(this.requireContext()))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadedCurrentWeather.observe(viewLifecycleOwner, Observer {
//            binding.currentasd.text = it.toString()
//        })
//
//        GlobalScope.launch {
//            weatherNetworkDataSource.fetchCurrentWeather("Ufa", "f")
//        }
    }
    private fun bindUI()= launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer{
            if(it == null) return@Observer
            binding.currentasd.text = it.toString()
        })
    }

    override val di: DI by closestDI()

}