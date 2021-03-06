package mtechproject.gmailpack;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.*;
import java.util.Properties;
import javax.mail.*;

public class ReadMail {
  public static void main(String args[]) throws IOException {
         Properties properties = System.getProperties();
         properties.setProperty("mail.store.protocol", "imaps");
             try {
                 Session session = Session.getDefaultInstance(properties, null);
                 //create session instance
                 Store store = session.getStore("imaps");//create store instance
                 store.connect("pop.gmail.com", "alok.1si11scs02@gmail.com", "phoenix6832");
                 //set your user_name and password
                 System.out.println(store);
                 Folder inbox = store.getFolder("inbox");
                 //set folder from where u wants to read mails
                 inbox.open(Folder.READ_ONLY);//set access type of Inbox
                 Message messages[] = inbox.getMessages();// gets inbox messages
                 for (int i = 0; i < messages.length; i++) {
                System.out.println("------------ Message " + (i + 1) + " ------------");
                System.out.println("SentDate : " + messages[i].getSentDate()); //print sent date
                System.out.println("From : " + messages[i].getFrom()[0]); //print email id of sender
                System.out.println("Sub : " + messages[i].getSubject()); //print subject of email
                try
                {
                      Multipart mulpart = (Multipart) messages[i].getContent();
                      int count = mulpart.getCount();
                      for (int j = 0; j+1 < count; j++)
                     {
                          storePart(mulpart.getBodyPart(j));
                     }
                }
                catch (Exception ex)
                {
                     System.out.println("Exception arise at get Content");
                     ex.printStackTrace();
                }
           }
           store.close();
      }
catch (Exception e) {
System.out.println(e); 
} 
}
  public static void storePart(Part part) throws Exception
     {   
          InputStream input = part.getInputStream();
          if (!(input instanceof BufferedInputStream))
         {
              input = new BufferedInputStream(input);
          }
          int i;
         System.out.println("msg : ");
          while ((i = input.read()) != -1)
         {
         System.out.write(i);
         }
     }
} 