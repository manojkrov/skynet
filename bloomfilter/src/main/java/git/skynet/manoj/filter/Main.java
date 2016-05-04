package git.skynet.manoj.filter;

public class Main {

	public static void main(String[] args) {
		BloomFilter bloomFilter  = new BloomFilter(10, 0.1);
		
		bloomFilter.addElement("manoj");
		bloomFilter.addElement("manoj1");
		bloomFilter.addElement("manoj2");
		bloomFilter.addElement("manoj3");
		bloomFilter.addElement("manoj4");
		bloomFilter.addElement("manoj5");
		bloomFilter.addElement("manoj6");
		bloomFilter.addElement("manoj7");
		bloomFilter.addElement("manoj8");
		bloomFilter.addElement("manoj9");
		
		System.out.println(bloomFilter);
		System.out.println("contains element manoj : " +  bloomFilter.containsElement("manj"));
		System.out.println("contains element manoj1 : " + bloomFilter.containsElement("manj1"));
		System.out.println("contains element manoj2 : " + bloomFilter.containsElement("manj2"));
		System.out.println("contains element manoj3 : " + bloomFilter.containsElement("manj3"));
		System.out.println("contains element manoj4 : " + bloomFilter.containsElement("manj4"));
		System.out.println("contains element manoj5 : " + bloomFilter.containsElement("manj5"));
		System.out.println("contains element manoj5 : " + bloomFilter.containsElement("maj5"));
		
		
	}
}
