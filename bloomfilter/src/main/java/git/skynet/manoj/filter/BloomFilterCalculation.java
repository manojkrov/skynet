package git.skynet.manoj.filter;

import java.util.Random;

/**
 * 
 * @author manoj
 * 
 *		The Bloom filter calculations try to optimize the number of bits required (m) for the bloom filter 
 *		and the number of hash functions required ( k ) . 'm' and 'k' are rounded up to the nearest integers.
 *		The following formula is being used to calculate these values
 *
 *   	Given 'n' , the expected number of elements and 'p' the required maximum 'false probability' value
 *   	'm' is optimized as
 *   
 *    	m =  - n log(p) / (log(2) squared )
 *    
 *    	k = m log(2) / n
 *    
 *    	These calculations were taken from 
 *      http://pages.cs.wisc.edu/~cao/papers/summary-cache/node8.html
 *      
 */

public class BloomFilterCalculation {
	
	public static FilterParams calculateOptimalParams(int maximumElements, double maxFalsePosProb){
		
		int m = (int) Math.ceil( (- maximumElements * Math.log(maxFalsePosProb))/ Math.pow(Math.log(2), 2) );
		int k = (int) Math.ceil( m * Math.log(2) / maximumElements );
		return new FilterParams(maximumElements, maxFalsePosProb, m, k);
	}
	
	static class FilterParams {

		int n;      // maximum elements expected
		double p;   // maximum false positive probability desired
		int m;	    // No of bits of the filter
		int k;		// Number of hash functions
		int seed1;
		int seed2;
		
		int bucketLength; 
		
		static Random generator = new Random();
		
		public FilterParams(int n, double p, int m , int k) {
			this.n = n;
			this.p = p;
			
			/** Making m a multiple of k so that we can get k buckets of m/k bits each, where each hash function
			 *  will map into. E.g. h1(x) will map into the first m/k bits. h2(x) will map into the 2nd bucket and so on.
			 *  This will avoid the case where all or most of the 'k' hash functions mapping into the same index. This method will
			 *  ensure that each element inserted will affect 'k' different bits of the filter.
			 */
			
			this.m = m + m%k;
			this.k = k;
			
			this.bucketLength = m/k;
			this.seed1 = generator.nextInt();
			this.seed2 = generator.nextInt();
		}

		@Override
		public String toString() {
			return "FilterParams [n=" + n + ", p=" + p + ", m=" + m + ", k=" + k + ", seed1=" + seed1 + ", seed2="
					+ seed2 + ", bucketLength=" + bucketLength + "]";
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println(BloomFilterCalculation.calculateOptimalParams(10000, 0.001));
	}
}
