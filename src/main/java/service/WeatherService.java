package service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Service
public class WeatherService {

    private final String WEATHER_API_KEY = "f6dbd410803a652ca3c254343aa5c4bb";

    public String fetchWeather(double lat, double lon) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon +
                "&appid=" + WEATHER_API_KEY + "&units=metric";

        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(url, String.class);
        JSONObject obj = new JSONObject(jsonResponse);

        String cityName = obj.getString("name");
        String country = obj.getJSONObject("sys").getString("country");
        String description = obj.getJSONArray("weather").getJSONObject(0).getString("description");
        double temp = obj.getJSONObject("main").getDouble("temp");
        int humidity = obj.getJSONObject("main").getInt("humidity");
        double windSpeed = obj.getJSONObject("wind").getDouble("speed");
        int clouds = obj.getJSONObject("clouds").getInt("all");

        long sunrise = obj.getJSONObject("sys").getLong("sunrise");
        long sunset = obj.getJSONObject("sys").getLong("sunset");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a")
                .withZone(ZoneId.of("Asia/Kolkata"));

        return String.format("""
                📍 City: %s (%s)
                🌦 Weather: %s
                🌡 Temperature: %.1f°C
                💧 Humidity: %d%%
                💨 Wind Speed: %.1f m/s
                ☁ Cloud Cover: %d%%
                🌅 Sunrise: %s
                🌇 Sunset: %s
                """,
                cityName, country, capitalize(description), temp, humidity, windSpeed, clouds,
                formatter.format(Instant.ofEpochSecond(sunrise)),
                formatter.format(Instant.ofEpochSecond(sunset))
        );
    }

    private String capitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
}