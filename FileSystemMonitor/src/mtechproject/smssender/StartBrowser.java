package mtechproject.smssender;

import org.apache.commons.httpclient.Cookie;  
import org.apache.commons.httpclient.HttpState;  
import org.apache.commons.httpclient.HttpClient;  
import org.apache.commons.httpclient.methods.GetMethod;  

public class StartBrowser {

	public static void main(String args[])
	{
		String url = "C:/Users/Alok Omkar/Desktop/p.html";
		HttpClient client = new HttpClient();  
	    client.getParams().setParameter("username", "9964534673"); 
	   // client.getParams("position.coords.latitude");
	    client.getParams().setParameter("password", "asilutr");  
	  
	    GetMethod method = new GetMethod("http://site2.way2sms.com/content/index.html");  
	    try{  
	      client.executeMethod(method);  
	      Cookie[] cookies = client.getState().getCookies();  
	      for (int i = 0; i < cookies.length; i++) {  
	        Cookie cookie = cookies[i];  
	        System.err.println(  
	          "Cookie: " + cookie.getName() +  
	          ", Value: " + cookie.getValue() +  
	          ", IsPersistent?: " + cookie.isPersistent() +  
	          ", Expiry Date: " + cookie.getExpiryDate() +  
	          ", Comment: " + cookie.getComment());  
	        }  
	      client.executeMethod(method);  
	    } catch(Exception e) {  
	      System.err.println(e);  
	    } finally {  
	      method.releaseConnection();  
	    }  
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();

		try{

			if (os.indexOf( "win" ) >= 0) {

				// this doesn't support showing urls in the form of "page.html#nameLink" 
				rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);

			} else if (os.indexOf( "mac" ) >= 0) {

				rt.exec( "open " + url);

			} else if (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0) {

				// Do a best guess on unix until we get a platform independent way
				// Build a list of browsers to try, in this order.
				String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
						"netscape","opera","links","lynx"};

				// Build a command string which looks like "browser1 "url" || browser2 "url" ||..."
				StringBuffer cmd = new StringBuffer();
				for (int i=0; i<browsers.length; i++)
					cmd.append( (i==0  ? "" : " || " ) + browsers[i] +" \"" + url + "\" ");

				rt.exec(new String[] { "sh", "-c", cmd.toString() });

			} else {
				return;
			}
		}catch (Exception e){
			return;
		}
		return;		

	}
}