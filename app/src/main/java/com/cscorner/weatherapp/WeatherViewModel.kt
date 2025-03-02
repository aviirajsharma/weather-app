package com.cscorner.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel:ViewModel() {

    private var _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData : StateFlow<WeatherResponse?> = _weatherData
    private val weatherApi = WeatherAPI.create()

    fun fetchWeather(city:String, apiKey:String){
        viewModelScope.launch {
            try {
                val response = weatherApi.getWeather(city,apiKey)
                _weatherData.value = response
            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }
}