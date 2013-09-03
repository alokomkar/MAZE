package mtechproject.catcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;

import mtechproject.filesysmonitor.DirectoryWatch;
import mtechproject.mazedb.BreachLogDB;
import mtechproject.rdcclient.ClientInitiator;
import mtechproject.smssender.*;
import mtechproject.useraccounts.Database_Credentials;
//a declaration of the events that can caught by a catcher
interface ThrowListener2sandtrap{
	public void Catch(String user, File chosenfile) throws IOException;

}

class Thrower2sandtrap extends Thread implements Database_Credentials{
	//a list of catchers
	List<ThrowListener2sandtrap> listeners = new ArrayList<ThrowListener2sandtrap>();
	//a way to add someone to the list of catchers

	static final String DB_URL = "jdbc:mysql://localhost:3306/SANDTRAPACCESSDB";

	Connection conn = null;
	Statement stmt = null;
	//DBExists db;

	public void addThrowListener2sandtrap(ThrowListener2sandtrap toAdd){
		listeners.add(toAdd);
	}
	
	
	public void Throw(String user,File chosenfile) throws Exception {

		//Path file_dir = Paths.get(Directory);
		//Path file = file_dir.resolve(Filename);
		Path file_dir = Paths.get(chosenfile.getParent());
		Path file = file_dir.resolve(chosenfile.getName());
		BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);


		//System.out.println(file.toString());
		String lastaccesstime = null;
		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			
			System.out.println("Fetching records with condition...");
			String sql = "SELECT Filename, Last_access_time FROM access_log" +
			" WHERE Filename LIKE '%"+file.getFileName().toString()+"%' ";
			ResultSet rs = stmt.executeQuery(sql);
			
			/*if(!rs.next()){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				
				sql = "Insert into accesslog values('"+file.getFileName()+"','"+dateFormat.format(date)+"')";
				stmt.executeUpdate(sql);
				
			}*/


			while(rs.next()){
				//Retrieve by column name
				String first = rs.getString("Filename");
				String last = rs.getString("Last_access_time");

				//Display values
				System.out.print("Filename: " + first);
				System.out.println("   Last access time: " + last);
				lastaccesstime = last;


			}
			rs.close();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//System.out.println(dateFormat.format(date));
			

			//System.out.println("Last Access Time of file : " + file + " at : " + attrs.lastAccessTime());

		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try



		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		
		System.out.println("Last accessed at:" + dateFormat.format(date));
		//System.out.println("Something thrown");

		//while(true){
		if(!lastaccesstime.toString().trim().isEmpty() && dateFormat.format(date).toString().equals(lastaccesstime.toString())){
			System.out.println("File is safe no one has accessed it.");
			//System.exit(0);
			//Thread.sleep(1000);
		}
		//}
		else{
			//1 or more times, a Notification that an event happened is thrown.
			for (ThrowListener2sandtrap hl : listeners) hl.Catch(user,chosenfile);
		}
	}
	//}

}

public class Catcher2sandtrap extends Thread implements ThrowListener2sandtrap,Database_Credentials {

	//static String Directory = "E:/JBerger_DFS";
	//static String Filename = "Credentials - Gmail.pdf";
	static String Directory;
	static String Filename;

	//implement added to class
	/*public static void main(String[] args) throws Exception {
		//instantiation of a Thrower2sandtrap object and use of it's addListener function

		//Catcher1 c = new Catcher1(Directory, Filename);
		Catcher1 c = new Catcher1();
	}*/

	/*public Catcher1(String Dir, String filen) 
	{
		this.Directory = Dir;
		this.Filename = filen;
		Callrun();

	}*/
	
	public Catcher2sandtrap(String User,File chosenfile) 
	{
			Callrun(User,chosenfile);

	}

	public void Callrun(String User,File chosenfile){
		
		Thrower2sandtrap Thrower2sandtrapInstance = new Thrower2sandtrap();
		Thrower2sandtrapInstance.addThrowListener2sandtrap(this);
		try {
			
			//System.out.println("Directory being accessed :"+Directory+"\nFile being accessed :"+Filename);
			
			//Thrower2sandtrapInstance.Throw(Directory, Filename);
			Thrower2sandtrapInstance.Throw(User,chosenfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//an override of 1 events that can caught by this type of catcher
	public void Catch(String user,File chosenfile) throws IOException {

		
		System.out.println("MAZE Security Alert :"+user+" is accessing the file : "+chosenfile+" or\n has already accessed the file");
		Way2SMS smssendagent = new Way2SMS();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date));
		final String DB_URL = "jdbc:mysql://localhost:3306/SANDTRAPACCESSDB";
		Connection conn = null;
		Statement stmt = null;

		Path file_dir = Paths.get(chosenfile.getParent());
		Path file = file_dir.resolve(chosenfile.getName());
		BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			

			//STEP 4: Execute a query
			//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//Date date = new Date();
			
			stmt = conn.createStatement();
			String access_time = dateFormat.format(date).toString();
			BreachLogDB.InsertLog(chosenfile.getName().toString(), user, access_time);
			
			String sql = "UPDATE ACCESS_LOG " +"SET Last_access_time = '"+access_time+"'"+
			" WHERE Filename LIKE '%"+chosenfile.getName()+"%' ";
			stmt.executeUpdate(sql);
			System.out.println("Updated Last Access time for detection of next access!!");
			if(ClientInitiator.getIpaddress()!=null){
				boolean smssent = smssendagent.SendMessage("MAZE SECURITY ALERT : File Access "+chosenfile.getName()+" by "+user+" at "+dateFormat.format(date)+"IP:"+ClientInitiator.getIpaddress(),user);	
			}
			else{
				boolean smssent = smssendagent.SendMessage("MAZE SECURITY ALERT : File Access "+chosenfile.getName()+" by "+user+" at "+dateFormat.format(date)+"IP:"+InetAddress.getLocalHost().getHostName(),user);
			}
			
		}catch(SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try

	}
}
