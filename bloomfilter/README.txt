BLOOM FILTER

This is a sample Bloom Filter Implementation in Java. It uses the Murmur3Hash for the hashing algorithm.

The BloomFilter Implementation is thread-safe. Input parameters include only the maximum expected number of elements and the 
false probability desired. The BloomFilter object is returned with optimized , filter length ( m )  and the ( k ) hash functions, 
based on the input parameters.

