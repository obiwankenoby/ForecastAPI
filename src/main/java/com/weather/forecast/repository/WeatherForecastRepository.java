package com.weather.forecast.repository;

import com.weather.forecast.entity.WeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherForecastRepository extends JpaRepository<WeatherForecast, Long> {
    List<WeatherForecast> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
