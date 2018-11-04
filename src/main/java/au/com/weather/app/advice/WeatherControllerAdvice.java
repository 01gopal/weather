package au.com.weather.app.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import au.com.weather.app.exception.WeatherException;
import au.com.weather.app.response.WeatherErrorResponse;

@ControllerAdvice
public class WeatherControllerAdvice {
	@ExceptionHandler(value = WeatherException.class)
	public ResponseEntity<WeatherErrorResponse> handleWeatherExceptionn(WeatherException e) {
		WeatherErrorResponse errorResponse = WeatherErrorResponse.builder()
				.errorCode(e.getErrorCode())
				.errorMsg(e.getErrorMsg())
				.build();
		return new ResponseEntity<WeatherErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
