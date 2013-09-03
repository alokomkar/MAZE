package mtechproject.faker;

import com.itextpdf.text.log.SysoLogger;

public class Luhn {
    public static void main(String[] args) {
        //System.out.println(luhnTest("49927398716"));
        //System.out.println(luhnTest("49927398717"));
        //System.out.println(luhnTest("1234567812345678"));
        //System.out.println(luhnTest("1234567812345670"));
    	//System.out.println(luhnTest("5586385851810719"));
    	//System.out.println(luhnTest("5586583881510719"));
    	//System.out.println(luhnTest("1211785601913428"));
    	//System.out.println(luhnTest("1234117856019128"));
    	//System.out.println(luhnTest("1211019876543128"));
    	//System.out.println(luhnTest("1234567891011128"));
    	
    	System.out.println(luhnTest("5587585806318119"));
    }
 
    public static boolean luhnTest(String number){
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        System.out.println(reverse);
        for(int i = 0 ;i < reverse.length();i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
                System.out.println("Odd digits = "+digit);
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                System.out.println("Even digits = "+digit);
                if(digit >= 5){
                    s2 -= 9;
                    System.out.println("Organizing" + (2*digit - 9));
                }
            }
        }
        System.out.println("Sum1 = "+s1);
        System.out.println("Sum2 = "+s2);
        return (s1 + s2) % 10 == 0;
    }
}
