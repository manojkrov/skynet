package com.goeuro.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.goeuro.controller.Controller;
import com.goeuro.util.Constants;
import com.goeuro.util.PropertyLoader;

public class ControllerTest {

	private static Controller controller;
	private static String BERLIN = "Berlin";
	private static String NEWYORK = "NEWYORK";
	private static String LONDON = "LONDON";
	private static String INVALID_INPUT = "INVALID";

	private static String FILENAME = PropertyLoader.INSTANCE.getProperty(Constants.FILE_NAME);

	private static Path path = Paths.get(FILENAME);

	@BeforeClass
	public static void init() {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new RuntimeException("Unable to delete file " + FILENAME, e);
		}

		controller = new Controller();
	}

	@After
	public void tearDown() {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new RuntimeException("Unable to delete file " + FILENAME, e);
		}
	}

	@Test
	public void createCSVMulti_CityTest() {
		controller.createCityCSV(new String[] { BERLIN, NEWYORK, LONDON });
		Assert.assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
	}

	@Test
	public void createCSVTest() {
		controller.createCityCSV(new String[] { LONDON });
		Assert.assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
	}

	@Test
	public void invalidInputTest() {
		controller.createCityCSV(new String[] { INVALID_INPUT });
		Assert.assertTrue(Files.notExists(path, LinkOption.NOFOLLOW_LINKS));
	}
	
}
