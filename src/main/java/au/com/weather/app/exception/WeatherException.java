package au.com.weather.app.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WeatherException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final String errorMsg;
}
