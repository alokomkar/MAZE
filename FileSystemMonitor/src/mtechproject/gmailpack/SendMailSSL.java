package mtechproject.gmailpack;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailSSL {
	public static void main(String[] args) {
	String to = "alokomkar.gudikote@gmail.com";//Reciver Address
	Properties props = new Properties();
	/*props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
	"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");*/
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.auth", "true");
	props.setProperty( "mail.smtp.port", "465" );
	props.put("mail.smtp.starttls.enable", "true");
	Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(
						"alok.1si11scs02@gmail.com", "phoenix6832");//SenderID and Password.
			}
	});
	try {
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("alok.1si11scs02@gmail.com"));//Sender Id.
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(
				to));
		message.setSubject("Hello!");
		message.setText("Testing from Java Application.......");

		// send message.
		Transport.send(message);
		System.out.println("message sent successfully");
	} catch (MessagingException e) {
		throw new RuntimeException(e);
	}
}
}