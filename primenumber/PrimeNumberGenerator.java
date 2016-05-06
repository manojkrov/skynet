/**
 * 
 * @author manoj
 * 
 *  A Prime number generator following the algorithm described here  https://en.wikibooks.org/wiki/Some_Basic_and_Inefficient_Prime_Number_Generating_Algorithms
 *    
 */

public class PrimeNumberGenerator {

	public static void main(String[] args) {
		
		if(null == args[0] || args[0].isEmpty()){
			System.out.println("Enter a valid number greater than 6");
		}
		
		int n = 0;
		try{
			n = Integer.parseInt(args[0]);
		}catch(NumberFormatException e){
			System.out.println("Illegal input, Please enter a valid integer");
		}
		
		if(n < 6){
			System.out.println("Enter a valid number greater than 6");
		}
		
		/**
		 *  An upper bound on the number of primes not exceeding n can be found here https://primes.utm.edu/howmany.html
		 *  
		 *  We initialize the array to the size 
		 *   
		 */
		
		System.out.println("Primes less than " + n + "\n");
		
		int pi_x = (int) Math.round((n / (Math.log(n) - 1)));

		int[] prime_array = new int[pi_x + 1];  // Array containing the primes
		prime_array[0] = 2;						// Initializing with the first 3 primes 
		prime_array[1] = 3;
		prime_array[2] = 5;

		int current_prime_array_index = 3;		// Index of the prime_array

		/**
		 * 	The program will start with looking at numbers of the form (skip_multiplier) * N ( Natural Number ) + prime_array[ last_skip_factor_index_in_prime_array ]
		 * 	Eg. 6N + 1, 6N + 5 where N = 1..4,  Then 30N + 1 , 30N + 7 , 30N + 11 ...  30N + 29 where N = 1..6 then 210N + 11, 210N + 13 .... where N = 1..10   
		 */
		
		int skip_multiplier = 6;				// The multiplier 'S' in the above formula S * N + 1 , S * N + 5 .....	
		int skip_factors_count = 2;				// The skip_multiplier is calculated by multiplying subsequent primes . Initial value is 2 * 3 = 6. Then 2 * 3 * 5 = 30. Then 2 * 3 * 5 * 7 =210 ... and so on.
		
		int last_skip_factor_index_in_prime_array = 1;  // Index of the last factor in the skip_multiplier. Initially for a skip value of  2 * 3 = 6,  it is the index of 3 in the prime_array.         

		int candidatePrime = 0;					// This variable will contain the 'possible prime number' candidates for evaluation 

		
		/**
		 *  This is the value that will be added to the skip multiplier series. 6N + '1' , 6N + '5' , then 30N + '1', 30N + '5' , 30N + '7'  and so on. It is initialized to index '1'.
		 *  S * N + 1 is a special case we had to handle every time. The rest of the additives come from the prime_array.
		 */
		
		int skip_list_additive_index = last_skip_factor_index_in_prime_array;	

		long start = System.currentTimeMillis();   // Tracking the time taken by the code to measure performance.

		while (candidatePrime < n) {

			int skip_list_index = 1;

			candidatePrime = skip_multiplier + 1;   // The first candidate
			
			int next_skip_multiplier = skip_multiplier * prime_array[last_skip_factor_index_in_prime_array + 1];
			
			while (candidatePrime < (next_skip_multiplier)) {

				boolean isPrime = true;
				
				int sqrt = (int)(Math.sqrt((double)candidatePrime));
				
				for (int i = last_skip_factor_index_in_prime_array + 1; prime_array[i] <= sqrt ; i++) {
					
					if (candidatePrime % prime_array[i] == 0) {
						isPrime = false;
						break;
					}
				}

				if (isPrime) {
					prime_array[current_prime_array_index] = candidatePrime;
					System.out.print(candidatePrime+ " , ");
					if(current_prime_array_index % 10 == 0){
						System.out.println();
					}
					current_prime_array_index++;
				}

				skip_list_additive_index++;

				if (prime_array[skip_list_additive_index] > skip_multiplier) {
					skip_list_index++;
					skip_list_additive_index = skip_factors_count - 1;
					candidatePrime = skip_multiplier * skip_list_index + 1;
				} else {
					candidatePrime = skip_multiplier * skip_list_index + prime_array[skip_list_additive_index];
				}

		
				if (candidatePrime > n)
					break;
			}

			last_skip_factor_index_in_prime_array++;
			skip_multiplier *= prime_array[last_skip_factor_index_in_prime_array];
			skip_factors_count++;
			skip_list_index = last_skip_factor_index_in_prime_array;

		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\n");
		
		System.out.println("Time taken in MilliSeconds : "+ (end - start));

	}
}
