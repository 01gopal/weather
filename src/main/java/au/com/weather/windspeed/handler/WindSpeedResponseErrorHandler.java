package au.com.weather.windspeed.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;

import au.com.weather.app.exception.WeatherException;

@Service
public class WindSpeedResponseErrorHandler extends DefaultResponseErrorHandler {
	
	@Override
	public void handleError(ClientHttpResponse response, HttpStatus statusCode) {
		throw new WeatherException("10002", "Wind speed service is unreachable");
	}
}
