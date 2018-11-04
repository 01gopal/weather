package au.com.weather.app.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WeatherErrorResponse {
	private String errorCode;
	private String errorMsg;
}
