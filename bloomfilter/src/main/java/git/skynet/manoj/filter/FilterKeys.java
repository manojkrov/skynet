package git.skynet.manoj.filter;

import java.util.function.Function;

public class FilterKeys {
	
	static class IntegerFilterKey implements Function<Integer, byte[]>{

		@Override
		public byte[] apply(Integer element) {
			
			return new byte[] {
		            (byte)(element >>> 24),
		            (byte)(element >>> 16),
		            (byte)(element >>> 8),
		            (byte)(element.intValue()) };
		}
		
	}
	
	static class LongFilterKey implements Function<Long, byte[]>{

		@Override
		public byte[] apply(Long element) {

			return new byte[] {
					(byte)(element >>> 56),
					(byte)(element >>> 48),
					(byte)(element >>> 40),
					(byte)(element >>> 32),
		            (byte)(element >>> 24),
		            (byte)(element >>> 16),
		            (byte)(element >>> 8),
		            (byte)(element.longValue()) };
		}
	}

	static class StringFilterKey implements Function<String,byte[]>{

		@Override
		public byte[] apply(String element) {
			
			return element.getBytes();
		}
	}

	
	public static Function<Integer,byte[]>  INTEGER_KEY_TYPE  = new IntegerFilterKey();
	public static Function<Long,byte[]> 	   LONG_KEY_TYPE   = new LongFilterKey();
	public static Function<String,byte[]>   STRING_KEY_TYPE   = new StringFilterKey();
	

}
