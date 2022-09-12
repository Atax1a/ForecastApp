package com.petp.forecast.data.network

import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.petp.forecast.data.network.ConnectivityInterceptor
import com.petp.forecast.data.network.ConnectivityInterceptorImpl
import com.petp.forecast.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "a860bd780f21576e213e3c2f80894458"

//http://api.weatherstack.com/current?access_key=a860bd780f21576e213e3c2f80894458&query=NewYork&Lang=en

interface ApixuWeatherApiService {

    @GET("current")
    suspend fun getCurrentWeather(
        @Query("query") location: String,
        @Query("units") units: String,

    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): ApixuWeatherApiService {
            val interceptorChain = Interceptor {

                // Buffer for first query to save it
                // Because I cant find any better solution to put ACCESS_KEY before that query
                // Interceptor just adds the ACCESS_KEY to the end of url but not appending it
                val queryValueBuffer = it.request().url().queryParameterValue(0)
                val queryKeyBuffer = it.request().url().queryParameterName(0)
                val unitsKeyBuffer = it.request().url().queryParameterName(1)
                val unitsValueBuffer = it.request().url().queryParameterValue(1)


                val url = it
                    .request()
                    .url()
                    .newBuilder()
                    .removeAllQueryParameters(queryKeyBuffer)
                    .addQueryParameter("access_key", API_KEY)
                    .addQueryParameter(queryKeyBuffer, queryValueBuffer)
                    .addQueryParameter(unitsKeyBuffer, unitsValueBuffer)
                    .build()
                val request = it.request().newBuilder().url(url).build()

                Log.v("ApixuApiServiceLog", "------: Requested URL: $url")

                return@Interceptor it.proceed(request)
            }
            val okHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(connectivityInterceptor)
                .addInterceptor(interceptorChain)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApixuWeatherApiService::class.java)
        }
    }
}