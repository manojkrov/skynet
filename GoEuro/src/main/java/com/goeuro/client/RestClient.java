package com.goeuro.client;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.entity.City;
import com.goeuro.util.Constants;
import com.goeuro.util.PropertyLoader;

public class RestClient {

	private String url;
	private String charSet;
	private HttpClient client;
	private String responseString;
	private HttpResponse response = null;
	private ObjectMapper mapper;
	private HttpGet request;
	City[] responseCities = null;
	
	public RestClient() {

		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		url = PropertyLoader.INSTANCE.getProperty(Constants.URL);
		charSet = PropertyLoader.INSTANCE.getProperty(Constants.CHARSET);
		client = HttpClientBuilder.create().build();

	}

	public City[] get(String city) {
		
		try {

			// Encoding the input city string
			
			request = new HttpGet(url + URLEncoder.encode(city.trim(), charSet));
			response = client.execute(request);

			responseString = EntityUtils.toString(response.getEntity());

			responseCities = mapper.readValue(responseString, City[].class);

		} catch (IOException e) {
			throw new RuntimeException(" Error while using the REST API. " + e);
		}

		return responseCities;
	}
}
