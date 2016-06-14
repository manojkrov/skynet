package com.goeuro.controller;

import com.goeuro.client.RestClient;
import com.goeuro.entity.City;
import com.goeuro.util.CSVWriter;

public class Controller {

	/**
	 * Initializing the required objects and rest clients for the task with
	 * values from the property file.
	 * 
	 */
	private RestClient client;

	public Controller() {
		client = new RestClient();
	}

	public void createCityCSV(String[] cities) {

		// Csv writer is automatically closed

		try (CSVWriter csvWriter = new CSVWriter()) {

			for (String city : cities) {

				City[] responseCities = client.get(city);

				if (responseCities.length > 0) {
					
					//We will be writing all the input data in a single file
					csvWriter.writeHeader(city);
					
					for (City cityObject : responseCities) {
						csvWriter.writeCSVLineToFile(cityObject.get_id(), cityObject.getName(), cityObject.getType(),
								cityObject.getGeoPosition().getLatitude(), cityObject.getGeoPosition().getLongitude());
					}
				}

			}
		}

	}
}
