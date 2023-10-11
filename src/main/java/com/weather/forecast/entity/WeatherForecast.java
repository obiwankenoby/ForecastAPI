package com.weather.forecast.entity;

import jakarta.persistence.*;

@Entity
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double latitude;

    private Double longitude;
    private String forecastData; // JSON representation of weather forecast data

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getForecastData() {
        return forecastData;
    }

    public void setForecastData(String forecastData) {
        this.forecastData = forecastData;
    }
}

