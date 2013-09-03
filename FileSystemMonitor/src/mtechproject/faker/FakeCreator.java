package mtechproject.faker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FakeCreator {
	static List<Integer> numbersodd = new ArrayList<Integer>(3);
	static List<Integer> numberseven = new ArrayList<Integer>(3);
	static List<Integer> tnumbersodd = new ArrayList<Integer>(3);
	static List<Integer> tnumberseven = new ArrayList<Integer>(3);
	static List<Integer> numbers2 = new ArrayList<Integer>(3);
	static String CreditCardNumber = "5587585806318119";
	static String FakeCC = null;
	
	public static void main(String args[]){
		for(int i = 0 ;i < CreditCardNumber.length();i++){
            int digit = Character.digit(CreditCardNumber.charAt(i), 10);
            if(i == 0 || i == 1 || i == 14 || i == 15){
            	//System.out.println(digit);
            	continue;
            }
            //Extract Odd integers and add them to list
            if(i % 2 == 0){
            	numbersodd.add(digit);
            	//System.out.print(digit);
            }
            //Extract Even Integers
            else{
            	numberseven.add(digit);
            	//System.out.print(digit);
            }
		}
		Randomize(numbersodd);
		Randomize(numberseven);
		Merge(numbersodd,numberseven);
       
		
		
	}
	
	static void Merge(List<Integer> numbersodd2,
				List<Integer> numberseven2) {
		List<Integer> FakeList = new ArrayList<Integer>(16);
		String FakeCC = "";
		FakeList.add(Character.digit(CreditCardNumber.charAt(0), 10));
		FakeList.add(Character.digit(CreditCardNumber.charAt(1), 10));
		StringBuffer sb = new StringBuffer();
		Iterator<Integer> itrodd = numbersodd2.iterator();
		Iterator<Integer> itreven = numberseven2.iterator();
		
		while(itrodd.hasNext() || itreven.hasNext()){
			int d1 = itrodd.next();
			int d2 = itreven.next();
			FakeList.add(d1);
			FakeList.add(d2);
				
		}
		
		FakeList.add(Character.digit(CreditCardNumber.charAt(14), 10));
		FakeList.add(Character.digit(CreditCardNumber.charAt(15), 10));
		String Fake = null;
		Iterator<Integer> itr1 = FakeList.iterator();
		Integer randomval1;
		while(itr1.hasNext()){
			randomval1 = itr1.next();
			
			Fake = Integer.toString(randomval1);
			sb.append(Fake);
			
		}
		FakeCC = sb.toString();
		System.out.println(FakeCC);
	}
	
	static void Randomize(List<Integer> numbers1){
		List<Integer> numbers2=numbers1;
		Collections.shuffle(numbers1);
		
		if(numbers1.equals(numbers2)){
			Collections.shuffle(numbers1);	
		}
			
		Iterator<Integer> itr = numbers1.iterator();
		Integer randomval;
		while(itr.hasNext()){
			randomval = itr.next();
			//System.out.print(randomval);	
		}
		
	}
	
}
