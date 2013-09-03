package mtechproject.smssender;

import com.way2sms.SMS;
class TestSMS {

public static void main(String[] args) {

SMS smsClient=new SMS();
smsClient.send( "99964534673", "asilutr", "9964534673", "Hi, how are you?","");
}
}