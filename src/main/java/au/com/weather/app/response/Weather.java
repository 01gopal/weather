package au.com.weather.app.response;

import au.com.weather.temprature.response.Temprature;
import au.com.weather.windspeed.response.WindSpeed;
import lombok.Data;

@Data
public class Weather {
	private Temprature temprature;
	private WindSpeed windSpeed;
}
