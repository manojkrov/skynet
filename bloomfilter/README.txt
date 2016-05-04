BLOOM FILTER

This is a sample Bloom Filter Implementation in Java. It uses the Murmur3Hash for the hashing algorithm.

The BloomFilter Implementation is thread-safe. Input parameters include only the maximum expected number of elements and the 
false probability desired. The BloomFilter object is returned with optimized , filter length ( m )  and the ( k ) hash functions, 
based on the input parameters.

The filter uses bucketing for the operations as discussed here  http://pages.cs.wisc.edu/~cao/papers/summary-cache/node8.html

Any suggestions are welcome.

