package au.com.weather.temprature.response;

import au.com.weather.temprature.constant.TempratureUnit;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Temprature {
	private double value;
	private TempratureUnit unit;
}
