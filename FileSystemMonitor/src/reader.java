
public class reader {
	public static void main(String args[]){
		String str = "abc";
		
		//str.concat("abc");
		String str1 = str;
		System.out.println(str1);
		str1.concat("abc");
		//str.concat("def");
		System.out.println(str1);
	}
}
