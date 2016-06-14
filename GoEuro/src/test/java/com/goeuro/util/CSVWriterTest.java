package com.goeuro.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CSVWriterTest {

	private static CSVWriter csvWriter;
	private static String FILENAME = PropertyLoader.INSTANCE.getProperty(Constants.FILE_NAME);

	private static Path path = Paths.get(FILENAME);

	@BeforeClass
	public static void init() {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new RuntimeException("Unable to delete file " + FILENAME, e);
		}

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
	public void fileCreationTest(){
		csvWriter = new CSVWriter();
		Assert.assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
	}
	
	@Test
	public void emptyFileDeletionTest(){
		csvWriter = new CSVWriter();
		Assert.assertTrue(Files.exists(path, LinkOption.NOFOLLOW_LINKS));
		csvWriter.close();
		Assert.assertTrue(Files.notExists(path, LinkOption.NOFOLLOW_LINKS));
	}

}
