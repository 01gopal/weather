package au.com.weather.windspeed.response;

import au.com.weather.windspeed.constant.WindSpeedUnit;
import lombok.Data;

@Data
public class WindSpeed {
	private double speed;
	private WindSpeedUnit unit;
}
