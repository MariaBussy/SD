package com.sd.laborator.services
import com.sd.laborator.interfaces.WeatherForecastInterface
import com.sd.laborator.pojo.WeatherForecastData
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.net.URL
import kotlin.math.roundToInt
@Service
class WeatherForecastService (private val timeService: TimeService) :
    WeatherForecastInterface {
    override fun getForecastData(location: String): WeatherForecastData {
// ID-ul locaţiei nu trebuie codificat, deoarece este numeric
        val forecastDataURL =
            URL("https://api.weatherapi.com/v1/current.json?key=a71940cdee6041c7add73227240503&q=$location&api=no")
// preluare conţinut răspuns HTTP la o cerere GET către URL-ul de mai sus
        val rawResponse: String = forecastDataURL.readText()
// parsare obiect JSON primit
        val responseRootObject = JSONObject(rawResponse)
        val weatherDataObject = responseRootObject.getJSONObject("current")
        //val weatherState = responseRootObject.getJSONObject("current").getString("condition.text")
        //println(weatherState)
        // Weather state icon URL might require additional logic based on API documentation

        val currentTemp = weatherDataObject.getDouble("temp_c").roundToInt()
        println(currentTemp)
        val humidity = weatherDataObject.getInt("humidity")
        println(humidity)
// construire şi returnare obiect POJO care încapsulează datele meteo
        return WeatherForecastData(
            location = location,
            date = timeService.getCurrentTime(),
            //weatherState =
            //weatherDataObject.getString("weather_state_name"),
            /*weatherStateIconURL =
            "https://www.metaweather.com/static/img/weather/png/${weatherDataObject.getString("weather_state_abbr")}.png",*/
            windDirection =weatherDataObject.getString("wind_dir"),
            windSpeed =weatherDataObject.getDouble("wind_mph").roundToInt(),
            currentTemp =weatherDataObject.getDouble("temp_c").roundToInt(),
            humidity = weatherDataObject.getInt("humidity")
        )
    }
}