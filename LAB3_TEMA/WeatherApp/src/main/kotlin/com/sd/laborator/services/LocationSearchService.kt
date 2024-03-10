package com.sd.laborator.services
import com.sd.laborator.interfaces.LocationSearchInterface
import org.springframework.stereotype.Service
import java.net.URL
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
@Service
class LocationSearchService : LocationSearchInterface {
    override fun getLocationId(locationName: String): String {
// codificare parametru URL (deoarece poate conţine caractere speciale)
        val encodedLocationName = URLEncoder.encode(locationName, StandardCharsets.UTF_8.toString())
// construire obiect de tip URL
        val locationSearchURL = URL("https://api.weatherapi.com/v1/current.json?key=a71940cdee6041c7add73227240503&q=$encodedLocationName&api=no")
// preluare raspuns HTTP (se face cerere GET şi se preia conţinutul răspunsului sub formă de text)
        val rawResponse: String = locationSearchURL.readText()
//// parsare obiect JSON
        val responseRootObject = JSONObject(rawResponse)
        val cityName = responseRootObject.getJSONObject("location").getString("name")
        return  cityName ?: "-1"
    }
}