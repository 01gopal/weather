package au.com.weather.temprature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.weather.rest.RestUtil;
import au.com.weather.temprature.handler.TempratureResponseErrorHandler;
import au.com.weather.temprature.response.Temprature;

@Service
public class TempratureServiceImpl implements TempratureService {
	private static final String TEMP_URL = "http://localhost:8081/temp";
	
	@Autowired
	private TempratureResponseErrorHandler errorHandler;
	
	@Override
	public Temprature currentTemprature() {
		Temprature temprature = RestUtil.getRequest(TEMP_URL, Temprature.class, errorHandler);
		return temprature;
	}
}
