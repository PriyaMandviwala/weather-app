package controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.WeatherService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<String> getWeather(@RequestParam double lat, @RequestParam double lon) {
        return ResponseEntity.ok(weatherService.fetchWeather(lat, lon));
    }
}
