package com.sd.laborator.interfaces
import com.sd.laborator.pojo.WeatherForecastData
interface WeatherForecastInterface {
    fun getForecastData(location: String): WeatherForecastData
}