package git.skynet.manoj.filter;

import java.util.Arrays;
import java.util.BitSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;
import java.util.stream.IntStream;

public class BloomFilter<T> {

	private BloomFilterCalculation.FilterParams filterParams;

	private BitSet bitSet;
	private int elementsAdded;
	private Function<T, byte[]> filterKey;
	private final ReadWriteLock lock;
	private final Lock readLock;
	private final Lock writeLock;

	public BloomFilter(Function<T, byte[]> key, int numberOfElemets, double falsePosProb) {

		this.filterKey = key;
		this.filterParams = BloomFilterCalculation.calculateOptimalParams(numberOfElemets, falsePosProb);
		this.bitSet = new BitSet(filterParams.m);
		lock = new ReentrantReadWriteLock();
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}

	/**
	 * Converting into a byte array. This is the fastest way possible, instead
	 * of using Custom libraries or Byte buffers
	 * 
	 * @param element
	 */

	public void addElement(T element) {

		addElement(filterKey.apply(element));
	}

	private void addElement(byte[] element) {
		if (null == element) {
			throw new NullPointerException();
		}

		long hash1 = Integer
				.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed1));
		long hash2 = Integer
				.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed2));

		final long hash = hash1;
		int[] indexArray = new int[filterParams.k];

		IntStream.range(1, filterParams.k + 1)
					.parallel().forEach(
					i -> indexArray[i - 1] = i * filterParams.bucketLength + (int) ((hash + i * hash2) % filterParams.bucketLength)
				);

		writeLock.lock();
		try {
			Arrays.stream(indexArray).parallel().forEach(bitSet::set);
			elementsAdded++;
		} finally {
			writeLock.unlock();
		}
	}

	public boolean containsElement(T element) {

		return containsElement(filterKey.apply(element));
	}

	public boolean containsElement(byte[] element) {
		if (null == element) {
			throw new NullPointerException();
		}

		long hash1 = Integer
				.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed1));
		long hash2 = Integer
				.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed2));

		final long hash = hash1;
		boolean result;
		int[] indexArray = new int[filterParams.k];

		IntStream.range(1, filterParams.k + 1)
		.parallel().forEach(
		i -> indexArray[i - 1] = i * filterParams.bucketLength + (int) ((hash + i * hash2) % filterParams.bucketLength)
	);

		readLock.lock();
		try {
			result = Arrays.stream(indexArray).parallel().allMatch(bitSet::get);

		} finally {
			readLock.unlock();
		}

		return result;
	}

	public int getElementsAdded() {
		return elementsAdded;
	}

	public int getCardinality() {
		int cardinality = -1;
		readLock.lock();
		try {
			cardinality = bitSet.cardinality();
		} finally {
			readLock.unlock();
		}
		return cardinality;
	}

	public void clearFilter() {
		writeLock.lock();
		try {
			bitSet.clear();
		} finally {
			writeLock.unlock();
		}

	}

	@Override
	public String toString() {
		return "BloomFilter [filterParams=" + filterParams + ", bitSet Cardinality=" + bitSet.cardinality() + "]";
	}
}
