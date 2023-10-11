import com.weather.forecast.entity.WeatherForecast;
import com.weather.forecast.models.WeatherForecastData;
import com.weather.forecast.repository.WeatherForecastRepository;
import com.weather.forecast.service.WeatherForecastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherForecastServiceTest {

    @Mock
    private WeatherForecastRepository weatherForecastRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherForecastService weatherForecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeatherForecast() {
        Double latitude = 52.52;
        Double longitude = 13.419;

        WeatherForecastData mockWeatherForecastData = new WeatherForecastData();
        // Set properties for mockWeatherForecastData

        when(restTemplate.getForObject(anyString(), eq(WeatherForecastData.class))).thenReturn(mockWeatherForecastData);

        WeatherForecast weatherForecast = weatherForecastService.getWeatherForecast(latitude, longitude);

        assertNotNull(weatherForecast);
        assertEquals(latitude, weatherForecast.getLatitude());
        assertEquals(longitude, weatherForecast.getLongitude());
        // Add more assertions based on your implementation
    }

    @Test
    void testGetAllCoordinates() {
        List<WeatherForecast> mockWeatherForecasts = new ArrayList<>();
        // Add mock weather forecasts to the list

        when(weatherForecastRepository.findAll()).thenReturn(mockWeatherForecasts);

        List<WeatherForecast> weatherForecasts = weatherForecastService.getAllCoordinates();

        assertNotNull(weatherForecasts);
        assertEquals(mockWeatherForecasts.size(), weatherForecasts.size());
        // Add more assertions based on your implementation
    }
}