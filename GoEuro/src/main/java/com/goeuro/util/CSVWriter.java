package com.goeuro.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;

public class CSVWriter implements AutoCloseable {

	private String fileName = "";
	private String fileHeader;
	private String fileOption;

	private Path path;
	private BufferedWriter bufferedWriter;
	private StringBuilder stringBuilder;
	private OpenOption[] options;

	public CSVWriter() {

		stringBuilder = new StringBuilder();

		fileName = PropertyLoader.INSTANCE.getProperty(Constants.FILE_NAME);
		fileHeader = PropertyLoader.INSTANCE.getProperty(Constants.FILE_HEADER);
		fileOption = PropertyLoader.INSTANCE.getProperty(Constants.FILE_OPTION);

		if (fileName == null) {
			throw new RuntimeException("No File name specified");
		}

		// Default is open the file for writing, creating the file if it doesn't
		// exist, or initially truncating an existing regular-file.

		if (null == fileOption || fileOption.isEmpty() || !fileOption.equalsIgnoreCase("APPEND")) {
			options = new OpenOption[] { WRITE, CREATE_NEW };
		} else {
			options = new OpenOption[] { WRITE, CREATE };
		}

		path = Paths.get(fileName);
		try {

			bufferedWriter = Files.newBufferedWriter(path, options);

		} catch (IOException e) {
			throw new RuntimeException("Unable to write to file " + fileName, e);
		}
	}

	public void writeHeader(String city){
			try {
				// Header for each city with city name and csv column values
				
				bufferedWriter.write("#" + city.trim().toUpperCase());
				bufferedWriter.newLine();
				
				bufferedWriter.write("#" + fileHeader);
				bufferedWriter.newLine();
			} catch (IOException e) {
				throw new RuntimeException("Unable to write to the file", e);
			}
	}
	
	public void writeToFile(String line) {
		try {
			bufferedWriter.newLine();
			bufferedWriter.write(" " + line);
			bufferedWriter.newLine();
		} catch (IOException e) {
			throw new RuntimeException("Unable to write to the file", e);
		}

	}

	public void writeCSVLineToFile(Object... fields) {

		// Re-using the String builder to create a single line for the csv file.

		stringBuilder.setLength(0);
		for (Object field : fields) {
			stringBuilder.append(field.toString() + " , ");
		}
		stringBuilder.setLength(stringBuilder.length() - 2);
		writeToFile(stringBuilder.toString());
	}

	public void close() {
		try {
			
			bufferedWriter.flush();
			bufferedWriter.close();
			if(Files.size(path) == 0){
				Files.delete(path);
			}
		} catch (IOException e) {
			throw new RuntimeException("Unable to close File writer ", e);
		}
	}

}
