package mtechproject.gmailpack;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
	
	public static void main(String args[]){
		SendMail("AlokOmkar","phoenix6832","alokomkar.gudikote@gmail.com");
	}
 
	public static void SendMail(String Username, String Password, String Email) {
 
		final String username = "adithyamaze@gmail.com";
		final String password = "koundinya";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("adithyamaze@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(Email));
			message.setSubject("Session Password");
			message.setText("Dear "+Username+","
				+ "\n\n Your password for the MAZE Session is : "+Password);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}