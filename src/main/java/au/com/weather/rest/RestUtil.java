package au.com.weather.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class RestUtil {

	public static <T> T getRequest(String url, Class<T> responseType, ResponseErrorHandler errorHandler) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType).getBody();
	}

	public <T, V> T postRequest(String url, V body, Class<T> responseType, ResponseErrorHandler errorHandler) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<V> requestEntity = new HttpEntity<>(body, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(errorHandler);
		return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType).getBody();
	}
}
