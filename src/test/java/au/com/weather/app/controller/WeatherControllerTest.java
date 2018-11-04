package au.com.weather.app.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import au.com.weather.temprature.constant.TempratureUnit;
import au.com.weather.temprature.response.Temprature;
import au.com.weather.temprature.service.TempratureService;
import au.com.weather.windspeed.constant.WindSpeedUnit;
import au.com.weather.windspeed.response.WindSpeed;
import au.com.weather.windspeed.service.WindSpeedService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private TempratureService tempService;

	@MockBean
	private WindSpeedService speedService;

	@Test
	public void getWeather() throws Exception {
		Mockito.when(tempService.currentTemprature()).thenReturn(buildTemprature());
		Mockito.when(speedService.getWindSpeed()).thenReturn(buildWindSpeed());
		mvc.perform(MockMvcRequestBuilders.get("/weather").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("temprature.value", is(30.0)))
				.andExpect(jsonPath("temprature.unit", is("DEGREE")))
				.andExpect(jsonPath("windSpeed.speed", is(25.0)))
				.andExpect(jsonPath("windSpeed.unit", is("KMPH")));
	}

	private WindSpeed buildWindSpeed() {
		WindSpeed windSpeed = new WindSpeed();
		windSpeed.setSpeed(25);
		windSpeed.setUnit(WindSpeedUnit.KMPH);
		return windSpeed;
	}

	private Temprature buildTemprature() {
		Temprature temprature = new Temprature();
		temprature.setValue(30);
		temprature.setUnit(TempratureUnit.DEGREE);
		return temprature;
	}
}
