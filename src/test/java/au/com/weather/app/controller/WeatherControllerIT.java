package au.com.weather.app.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import au.com.weather.app.response.Weather;
import au.com.weather.temprature.constant.TempratureUnit;
import au.com.weather.windspeed.constant.WindSpeedUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;
    
    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port);
        
        stubFor(get(urlEqualTo("/temp"))
                .willReturn(aResponse()
                    .withStatus(200)
                    .withHeader("Content-type", "application/json")
                    .withBody("{\n" + 
                    		"		\"value\": 30.0,\n" + 
                    		"		\"unit\": \"DEGREE\"\n" + 
                    		"}")));
        
        stubFor(get(urlEqualTo("/wind"))
        		.willReturn(aResponse()
        				.withStatus(200)
        				.withHeader("Content-type", "application/json")
        				.withBody("{\n" + 
        						"		\"speed\": 25.0,\n" + 
        						"		\"unit\": \"KMPH\"\n" + 
        						"}")));
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8081);
    
    @Test
    public void getWeather() throws Exception {
        ResponseEntity<Weather> response = template.getForEntity(base.toString() + "/weather", Weather.class);
        Assert.assertNotNull(response.getBody());
        
        Weather weather = response.getBody();
        Assert.assertEquals(30.0, weather.getTemprature().getValue(), 0);
        Assert.assertEquals(TempratureUnit.DEGREE, weather.getTemprature().getUnit());
        Assert.assertEquals(25.0, weather.getWindSpeed().getSpeed(), 0);
        Assert.assertEquals(WindSpeedUnit.KMPH, weather.getWindSpeed().getUnit());
    }
}
