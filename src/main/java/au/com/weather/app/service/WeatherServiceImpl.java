package au.com.weather.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.weather.app.response.Weather;
import au.com.weather.temprature.response.Temprature;
import au.com.weather.temprature.service.TempratureService;
import au.com.weather.windspeed.response.WindSpeed;
import au.com.weather.windspeed.service.WindSpeedService;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private TempratureService tempratureService;
	
	@Autowired
	private WindSpeedService windSpeedService;
	
	@Override
	public Weather weatherInformation() {
		Temprature temprature = tempratureService.currentTemprature();
		WindSpeed windSpeed = windSpeedService.getWindSpeed();
		
		Weather weather = new Weather();
		weather.setTemprature(temprature);
		weather.setWindSpeed(windSpeed);
		
		return weather;
	}

}
