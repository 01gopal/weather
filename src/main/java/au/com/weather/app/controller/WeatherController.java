package au.com.weather.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.weather.app.response.Weather;
import au.com.weather.app.service.WeatherService;

@RestController
public class WeatherController {

	@Autowired
	private WeatherService weatherService;

	@RequestMapping("/weather")
	public ResponseEntity<Weather> currentWeather() {
		Weather weather = weatherService.weatherInformation();
		return new ResponseEntity<>(weather, HttpStatus.OK);
	}

}
