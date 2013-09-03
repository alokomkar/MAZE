/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mtechproject.smssender;

import java.io.*;
import  javax.mail.*;
import java.net.InetAddress;
import java.util.Date;
import javax.activation.*;
import java.util.Properties;





/**
 *
 * @author User: Alfonso Franco  middle  java  developer, who  is  helping
 * for  client  to  fixs  an  exception  problem  with  this  program
 *     
 */
public class SMTPSend extends Exception {

    /**
     * @param args the command line arguments
     */
    
    public SMTPSend(){
    }   
        
     public void msgsend(){
       String username = "alokomkar.gudikote";
       String password = "phoenix6832";
       String smtphost = " smtp.gmail.com ";
       String Compression = "My SMS Compression Information";
       String from = "username@gmail.com";
       //String to = " mobilenumber@serviceprovider.com";
       String to = " 9964534673";
       String body = "Hello SMS World !";
       String  myTransport = null;
         
       
       
          try{
              
              String Message="msg";
              String  msg="set";
              //java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
              Properties props = System.getProperties();
              props.put("mail.transport.protocol", "smtp");
              props.put("mail.smtp.starttls.enable     ", " true  ");
              props.put(" mail.smtp.host  ",  smtphost   );
              props.put("mail.smtp.auth", "true");
              //props.put("mail.smtp.port","465");
              //props.put("mail.smtp.starttls.required","true");
              props.put("Session mailSession = Session.getDefaultInstance"  , "props,null"); // here  I had been  a  fix  code
              Message.contentEquals("msg= new MimeMessage(mailSession)");
              msg.format("setFrom = new InternetAddress (from))), args");
              InetAddress[] InternetAddress;
              msg.format("setRecipients(Message.RecipientType.TO, address");
              msg.format("setSubject(compression)  ");
              msg.format(" setText(body) ");
              msg.format(" setSentDate(new Date()        ");
              
              myTransport.equals("mailSession.getTransport smtp");
              myTransport.valueOf("connect smtphost, username, password");
              msg.format("  saveChanges()  ");
              myTransport.equals(" sendMessage(msg,msg.getAllRecipients()      ");
              myTransport="close";
              
              
              
          }catch (Exception MessageRemovedException){
              
            // System.out.print(" javax.mail.MessagingException: 530 5.7.0   Must issue a STARTTLS command first. s29sm10871689wak.14          "); 
              
          
          }  
              
             
              
                
          
         
         
     }   
        
           
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        SMTPSend smtpSend =  new SMTPSend();
        smtpSend.msgsend();
        
         
        
    }

}
