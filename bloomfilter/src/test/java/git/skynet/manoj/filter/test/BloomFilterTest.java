package git.skynet.manoj.filter.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import git.skynet.manoj.filter.BloomFilter;

/**
 * @author manoj
 *
 */
public class BloomFilterTest {

	private static BloomFilter bloomFilter;
	private static Random random = new Random();
	private static int TOTAL_NUMBER_OF_ELEMENTS = 10000;
	private static double FALSE_POSITIVE_PROBABILITY = 0.01;
	private static int TEST_INPUT_SIZE = 1000;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		bloomFilter = new BloomFilter(TOTAL_NUMBER_OF_ELEMENTS, FALSE_POSITIVE_PROBABILITY);
	}

	@Before
	public void setUpBeforeTest(){
		bloomFilter.clearFilter();
	}
	
	@Test
	public void testInsertedElements() {
		int[] input = new int[TEST_INPUT_SIZE];
		IntStream.rangeClosed(0, TEST_INPUT_SIZE-1).forEach(i -> { input[i]= random.nextInt(1000000);bloomFilter.addElement(input[i]);});
		
		Arrays.stream(input).forEach(i -> assertTrue(bloomFilter.containsElement(i)));
	}
	
	@Test
	public void testFalsePositiveRate() {
		int[] input = new int[TEST_INPUT_SIZE];
		IntStream.rangeClosed(0, TEST_INPUT_SIZE-1).forEach(i -> { input[i]= random.nextInt(1000000);bloomFilter.addElement(input[i]);});
		
		boolean[] results = new boolean[1000];
		IntStream.rangeClosed(0, TEST_INPUT_SIZE-1).forEach(i -> { results[i] = bloomFilter.containsElement(-input[i]); });
		
		int positives = 0;
		for(boolean result : results) {
			if(result) positives++;
		}
		
		assertTrue(" False positive ratio should be less than  "+ FALSE_POSITIVE_PROBABILITY , positives/TEST_INPUT_SIZE < FALSE_POSITIVE_PROBABILITY);
	}
	
}
