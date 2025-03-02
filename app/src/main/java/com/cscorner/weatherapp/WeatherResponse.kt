package com.cscorner.weatherapp

data class WeatherResponse(
    val name : String,
    val weather : List<Weather>,
    val main : Main

)

data class Main(
    val temp: Float,
    val humadity : Int,
    val pressure : Int,
    val feels_like : Float
)


data class Weather(
    val description : String
)
