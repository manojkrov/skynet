package git.skynet.manoj.filter;

import java.util.BitSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BloomFilter {

	private BloomFilterCalculation.FilterParams filterParams;

	private BitSet bitSet;
	private int elementsAdded;
	
	private final ReadWriteLock lock;
	private final Lock readLock ;
	private final Lock writeLock;

	
	
	public BloomFilter(int numberOfElemets, double falsePosProb) {
		this.filterParams = BloomFilterCalculation.calculateOptimalParams(numberOfElemets, falsePosProb);
		this.bitSet = new BitSet(filterParams.m);
		lock = new ReentrantReadWriteLock();
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}
	
	/** Converting an int into a byte array. This is the fastest way possible, instead of using Custom libraries or Byte buffers
	 * 
	 * @param element
	 */
	
	public void addElement(int element){
		
		addElement(new byte[] {
            (byte)(element >>> 24),
            (byte)(element >>> 16),
            (byte)(element >>> 8),
            (byte)(element) }
				);
	}

	
	/** Converting a long into a byte array. This is the fastest way possible, instead of using Custom libraries or Byte buffers
	 * 
	 * @param element
	 */


	public void addElement(long element){
		addElement(new byte[] {
				(byte)(element >>> 56),
				(byte)(element >>> 48),
				(byte)(element >>> 40),
				(byte)(element >>> 32),
	            (byte)(element >>> 24),
	            (byte)(element >>> 16),
	            (byte)(element >>> 8),
	            (byte)(element) }
					);
	}

	public void addElement(String element){
		addElement(element.getBytes());
	}

	private void addElement(byte[] element) {
		if (null == element) {
			throw new NullPointerException();
		}

		long hash1 = Integer.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed1));
		long hash2 = Integer.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed2));

		writeLock.lock();
		try {
			int offset = 0;
			long hash = hash1;
			int index;
			int mod;
			
			for (int i = 1; i <= filterParams.k; i++) {
				hash +=hash2;
				mod = ( int ) ( hash % filterParams.bucketLength );
				index = offset + mod;
				bitSet.set(index);
				offset += filterParams.bucketLength;
			}
			
			elementsAdded++;
		} finally {
			writeLock.unlock();
		}
		
	}

	public boolean containsElement(int element){
		
		return containsElement(new byte[] {
            (byte)(element >>> 24),
            (byte)(element >>> 16),
            (byte)(element >>> 8),
            (byte)(element) }
				);
	}

	public boolean containsElement(long element){
		return containsElement(new byte[] {
				(byte)(element >>> 56),
				(byte)(element >>> 48),
				(byte)(element >>> 40),
				(byte)(element >>> 32),
	            (byte)(element >>> 24),
	            (byte)(element >>> 16),
	            (byte)(element >>> 8),
	            (byte)(element) }
					);
	}

	public boolean containsElement(String element) {
		return containsElement(element.getBytes());
	}
	
	public boolean containsElement(byte[] element) {
		if (null == element) {
			throw new NullPointerException();
		}

		long hash1 = Integer.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed1));
		long hash2 = Integer.toUnsignedLong(Murmur3Hash.murmurhash3_x86_32(element, 0, element.length, filterParams.seed2));

		readLock.lock();
		try {
			long hash = hash1;
			int offset = 0;
			int index;
			int mod;

			for (int i = 1; i <= filterParams.k; i++) {
				hash += hash2;
				mod = ( int ) ( hash % filterParams.bucketLength );
				index = offset + mod;
				offset += filterParams.bucketLength;
				if (!bitSet.get(index)) {
					return false;
				}
			}
		} finally {
			readLock.unlock();
		}

		return true;
	}

	public int getElementsAdded() {
		return elementsAdded;
	}
	
	public void clearFilter(){
		bitSet.clear();
	}

	@Override
	public String toString() {
		return "BloomFilter [filterParams=" + filterParams + ", bitSet Cardinality=" + bitSet.cardinality() + "]";
	}
}
