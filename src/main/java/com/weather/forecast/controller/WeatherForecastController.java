package com.weather.forecast.controller;

import com.weather.forecast.entity.WeatherForecast;
import com.weather.forecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherForecastController {

    @Autowired
    private WeatherForecastService weatherForecastService;

    // Create a new weather forecast
    @PostMapping("/add")
    public ResponseEntity<String> addWeatherForecast(@RequestBody WeatherForecast weatherForecast) {
        weatherForecastService.addWeatherForecast(weatherForecast);
        return ResponseEntity.status(HttpStatus.CREATED).body("Weather forecast added successfully!");
    }

    // Delete a weather forecast by its ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteWeatherForecast(@PathVariable Long id) {
        weatherForecastService.deleteWeatherForecast(id);
        return ResponseEntity.status(HttpStatus.OK).body("Weather forecast deleted successfully!");
    }

    // Get weather forecast by latitude and longitude
    @GetMapping("/forecast/{latitude}/{longitude}")
    public ResponseEntity<WeatherForecast> getWeatherForecast(@PathVariable Double latitude, @PathVariable Double longitude) {
        WeatherForecast weatherForecast = weatherForecastService.getWeatherForecast(latitude, longitude);
        if (weatherForecast != null) {
            return ResponseEntity.status(HttpStatus.OK).body(weatherForecast);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Get a list of previously used latitude and longitude values
    @GetMapping("/coordinates")
    public ResponseEntity<List<WeatherForecast>> getCoordinates() {
        List<WeatherForecast> coordinatesList = weatherForecastService.getAllCoordinates();
        if (coordinatesList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(coordinatesList);
        }
    }

    // Update weather forecast data for a specific latitude and longitude
    @PutMapping("/update/{latitude}/{longitude}")
    public ResponseEntity<String> updateWeatherForecast(@PathVariable Double latitude, @PathVariable Double longitude,
                                                        @RequestBody WeatherForecast updatedForecast) {
        boolean updated = weatherForecastService.updateWeatherForecast(latitude, longitude, updatedForecast);
        if (updated) {
            return ResponseEntity.status(HttpStatus.OK).body("Weather forecast updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Weather forecast not found for the given coordinates.");
        }
    }
}

