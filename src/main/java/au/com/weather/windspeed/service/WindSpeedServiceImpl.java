package au.com.weather.windspeed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.weather.rest.RestUtil;
import au.com.weather.windspeed.handler.WindSpeedResponseErrorHandler;
import au.com.weather.windspeed.response.WindSpeed;

@Service
public class WindSpeedServiceImpl implements WindSpeedService {
	private static final String WIND_URL = "http://localhost:8081/wind";
	
	@Autowired
	private WindSpeedResponseErrorHandler errorHandler;
	
	@Override
	public WindSpeed getWindSpeed() {
		return RestUtil.getRequest(WIND_URL, WindSpeed.class, errorHandler);
	}

}
