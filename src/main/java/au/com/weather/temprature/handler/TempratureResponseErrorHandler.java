package au.com.weather.temprature.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;

import au.com.weather.app.exception.WeatherException;

@Service
public class TempratureResponseErrorHandler extends DefaultResponseErrorHandler {
	
	@Override
	public void handleError(ClientHttpResponse response, HttpStatus statusCode) {
		throw new WeatherException("10001", "Temprature service is unreachable");
	}
}
