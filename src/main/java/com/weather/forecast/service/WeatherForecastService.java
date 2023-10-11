package com.weather.forecast.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.forecast.entity.WeatherForecast;
import com.weather.forecast.models.WeatherForecastData;
import com.weather.forecast.repository.WeatherForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherForecastService {

    @Autowired
    private WeatherForecastRepository weatherForecastRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String OPEN_METEO_API_URL = "https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}";


    public void addWeatherForecast(WeatherForecast weatherForecast) {
        weatherForecastRepository.save(weatherForecast);
    }

    public void deleteWeatherForecast(Long id) {
        weatherForecastRepository.deleteById(id);
    }

    public WeatherForecast getWeatherForecast(Double latitude, Double longitude) {
        String apiUrl = OPEN_METEO_API_URL.replace("{latitude}", latitude.toString()).replace("{longitude}", longitude.toString());
        WeatherForecastData forecastData = restTemplate.getForObject(apiUrl, WeatherForecastData.class);
        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setLatitude(latitude);
        weatherForecast.setLongitude(longitude);
        assert forecastData != null;
        weatherForecast.setForecastData(convertToJson(forecastData)); // Convert to JSON or any other format as needed
        weatherForecastRepository.save(weatherForecast);
        return weatherForecast;
    }
    private String convertToJson(WeatherForecastData forecastData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(forecastData);
        } catch (JsonProcessingException e) {
            // Handle the exception or log it
            e.printStackTrace();
            return null;
        }
    }

    public List<WeatherForecast> getAllCoordinates() {
        return weatherForecastRepository.findAll();
    }

    public boolean updateWeatherForecast(Double latitude, Double longitude, WeatherForecast updatedForecast) {
        List<WeatherForecast> existingForecasts = weatherForecastRepository.findByLatitudeAndLongitude(latitude, longitude);
        if (!existingForecasts.isEmpty()) {
            WeatherForecast existingForecast = existingForecasts.get(0);
            existingForecast.setForecastData(updatedForecast.getForecastData());
            weatherForecastRepository.save(existingForecast);
            return true;
        } else {
            return false;
        }
    }
}

