package mtechproject.useraccounts;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mtechproject.rdcclient.ClientInitiator;
import mtechproject.smssender.Way2SMS;

public class SearchAccount implements Database_Credentials{

	// JDBC driver name and database URL
	//static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

	//Database credentials
	//static final String USER = "root";
	//static final String PASS = "phoenix6832";

	Connection conn = null;
	Statement stmt = null;


	String Username, Password;

	boolean SearchByName(String name,String pass, String spass) throws ClassNotFoundException, SQLException{

		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		String sql = "SELECT Username, Prim_Password, Sec_Password, Locked FROM user_accounts" +
		" WHERE Username LIKE '%"+name+"%' ";
		ResultSet rs = stmt.executeQuery(sql);
		boolean query = false;
		while(rs.next()){
			if(name.equals(rs.getString("Username")) && pass.equals(rs.getString("Prim_Password")) && spass.equals(rs.getString("Sec_Password")) && "N".equals(rs.getString("Locked"))){

				query = true;
			}

			else {
				query = false;
			}
		}
		stmt.close();
		rs.close();
		conn.close();
		return query;
	}

	boolean searchbySecPassword(String name, String pass) throws ClassNotFoundException, SQLException{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		String sql = "SELECT Username, Prim_Password, Sec_Password, Locked FROM user_accounts" +
		" WHERE Username LIKE '%"+name+"%' ";
		ResultSet rs = stmt.executeQuery(sql);
		boolean query = false;
		while(rs.next()){

			if(name.equals(rs.getString("Username")) && pass.equals(rs.getString("Prim_Password")) && "N".equals(rs.getString("Locked"))){

				query = true;
				// LogoutCaller lout = new LogoutCaller(name,pass,rs.getString("Sec_Password"),rs.getString("Locked"));
				//Send Session key to Registered Mobile
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();

				Way2SMS smssendagent = new Way2SMS();
				boolean smssent = smssendagent.SendMessage("MAZE Server ::"+dateFormat.format(date)+":: Your Session Key ::  " +rs.getString("Sec_Password"),name);
			}

			else {
				query = false;
			}
		}
		stmt.close();
		rs.close();
		conn.close();
		return query;

	}

	boolean searchbyexitPassword(String name, String pass) throws ClassNotFoundException, SQLException{
		
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		String sql = "SELECT Username, exitkey FROM user_exitkey" +
		" WHERE Username LIKE '%"+name+"%'";
		ResultSet rs = stmt.executeQuery(sql);
		boolean query = false;
		while(rs.next()){

			if(name.equals(rs.getString("Username")) && pass.equals(rs.getString("exitkey")))
			{
				query = true;
			}

			else {
				query = false;
			}
		}
		stmt.close();
		rs.close();
		conn.close();
		return query;

	}

	boolean searchbyUserPass(String name, String pass) throws ClassNotFoundException, SQLException{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		//STEP 4: Execute a query
		stmt = conn.createStatement();
		String sql = "SELECT Username, Prim_Password, Sec_Password, Locked FROM user_accounts" +
		" WHERE Username LIKE '%"+name+"%' ";
		ResultSet rs = stmt.executeQuery(sql);
		boolean query = false;
		while(rs.next()){

			if(name.equals(rs.getString("Username")) && pass.equals(rs.getString("Prim_Password")) && "N".equals(rs.getString("Locked"))){

				query = true;
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				//Send Alert to notify Read only login
				Way2SMS smssendagent = new Way2SMS();
				if(ClientInitiator.getIpaddress()!=null){
					smssendagent.SendMessage("MAZE Server :: READ ONLY LOGIN AT :"+dateFormat.format(date)+" from IP Address :"+ClientInitiator.getIpaddress(),name);	
				} else{
					try {
						smssendagent.SendMessage("MAZE Server :: READ ONLY LOGIN AT :"+dateFormat.format(date)+" from IP Address :"+InetAddress.getLocalHost().getHostName(),name);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			else {
				query = false;
			}
		}
		stmt.close();
		rs.close();
		conn.close();
		return query;

	}

	boolean searchbyUsername(String name) throws ClassNotFoundException, SQLException{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection
		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		//STEP 4: Execute a query
		stmt = conn.createStatement();

		String sql = "SELECT Username, Prim_Password, Sec_Password, Locked FROM user_accounts" +
		" WHERE Username LIKE '%"+name+"%' ";
		ResultSet rs = stmt.executeQuery(sql);
		boolean query = false;
		while(rs.next()){

			if(name.equals(rs.getString("Username"))){

				query = true;
			}

			else {
				query = false;
			}
		}
		stmt.close();
		rs.close();
		conn.close();
		return query;

	}

	boolean updatebyUsername(String name, String sesspwd) throws ClassNotFoundException, SQLException{
		//STEP 2: Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

		//STEP 3: Open a connection

		conn = DriverManager.getConnection(DB_URL, USER, PASS);


		//STEP 4: Execute a query

		stmt = conn.createStatement();


		String sql = "UPDATE User_accounts SET Sec_Password = '"+sesspwd+"' WHERE Username = '"+name+"' ";
		stmt.executeUpdate(sql);
		boolean query = false;
		stmt.close();

		conn.close();
		return query;

	}



}
