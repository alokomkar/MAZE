package mtechproject.mazedb;

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
import mtechproject.useraccounts.Database_Credentials;

public class DecoyFileDB implements Database_Credentials{
	
	public static void main(String[] args){
		//CreateDB();
		//CreateTable();
		//boolean check = FileLookup("Salary Details.pdf");
		//System.out.println(check);
	}
	
	public static boolean FCFileLookup(String Filename, String User){
		boolean result = false;
		final String DB_URL = "jdbc:mysql://localhost:3306/faker";
		Connection conn = null;


		String sql = null;
		ResultSet rs = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//STEP 3: Open a connection
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();

			sql = "Select * from fc_file_attr where Filename = '"+Filename+"' ";
			rs = stmt1.executeQuery(sql);
			//System.out.println(rs.getString(3).toString());
			//String group = null;

			if(rs.next() != false){
				result = true;
			}
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(result == true){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//Send Alert to notify Read only login
			Way2SMS smssendagent = new Way2SMS();
			smssendagent.SendMessage("MAZE Security Alert : Decoy File Access :"+Filename+"at"+dateFormat.format(date)+" from IP Address :"+ClientInitiator.getIpaddress()+" by"+User,User);
		}


		return result;



		
	}
	
	public static boolean FileLookup(String Filename, String User){

		boolean result = false;
		final String DB_URL = "jdbc:mysql://localhost:3306/DECOYFILEDB";

		Connection conn = null;


		String sql = null;
		ResultSet rs = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//STEP 3: Open a connection
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();

			sql = "Select * from DecoyFileLog where Filename = '"+Filename+"' ";
			rs = stmt1.executeQuery(sql);
			//System.out.println(rs.getString(3).toString());
			//String group = null;

			if(rs.next() != false){
				result = true;
			}
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(result == true){
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			//Send Alert to notify Read only login
			Way2SMS smssendagent = new Way2SMS();
			smssendagent.SendMessage("MAZE Security Alert : Decoy File Access :"+Filename+"at"+dateFormat.format(date)+" from IP Address :"+ClientInitiator.getIpaddress()+" by"+User,User);
		}


		return result;



	}

	private static void CreateDB() {
		final String DB_URL = "jdbc:mysql://localhost:3306/";
		Connection conn = null;
		Statement stmt = null;

		try{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			//STEP 4: Execute a query
			System.out.println("Creating database...");
			stmt = conn.createStatement();

			final String sql = "CREATE DATABASE IF NOT EXISTS DECOYFILEDB";
			stmt.executeUpdate(sql);
			System.out.println("Database created successfully...");
		}catch(final SQLException se){
			//Handle errors for JDBC
			se.printStackTrace();
		}catch(final Exception e){
			//Handle errors for Class.forName
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					stmt.close();
			}catch(final SQLException se2){
			}// nothing we can do
			try{
				if(conn!=null)
					conn.close();
			}catch(final SQLException se){
				se.printStackTrace();
			}//end finally try
		}//end try


	}
	
	private static void CreateTable() {
		final String DB_URL = "jdbc:mysql://localhost:3306/decoyfiledb";
		Connection conn = null;
		Statement stmt = null;



		try{

			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			//STEP 4: Execute a query
			System.out.println("Creating table in given database...");
			stmt = conn.createStatement();

			String sql = "CREATE TABLE DECOYFILELOG " +
			"(Filename VARCHAR(255)," + 
			" Absolute_Path VARCHAR(1000))"; 

			stmt.executeUpdate(sql);
			System.out.println("Created table in given database...");
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
