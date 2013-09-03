package mtechproject.useraccounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.gmailpack.SendMailTLS;

public class UserMailDB implements Database_Credentials{

	public static void main(String[] args){

		//CreateDB();
		//CreateTable();
		//InsertRecord("Soumya");
		EmailSender("Soumya");

	}

	private static void CreateDB() {

		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/";

		//  Database credentials
		//final String USER = "root";
		//final String PASS = "phoenix6832";

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

			final String sql = "CREATE DATABASE IF NOT EXISTS USERMAILDB";
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

		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERMAILDB";

		//  Database credentials
		//final String USER = "root";
		//final String PASS = "phoenix6832";


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

			String sql = "CREATE TABLE IF NOT EXISTS USER_MAIL " +
			"(Username VARCHAR(255)," + 
			" Email VARCHAR(255), " +
			" Email_Password VARCHAR(255)," +
			" PRIMARY KEY ( Username ))"; 

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

	public static void InsertRecord(String Username){

		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERMAILDB";

		//  Database credentials
		//final String USER = "root";
		//final String PASS = "phoenix6832";

		Connection conn = null;


		String sql = null;
		//STEP 2: Register JDBC driver

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();
			String password = null;
			do{
				password = SecPasswdGen.PasswdGenerator();
			}while(!UserAccountDB.compareSecPwd(Username,password));

			sql = "INSERT INTO USER_MAIL " +
			"VALUES ('"+Username+"','"+Username.replaceAll(" ","").toLowerCase()+"maze"+"@gmail.com','"+password+"')";

			stmt1.executeUpdate(sql);


			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public static void EmailSender(String Username){
		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERMAILDB";

		//  Database credentials
		//final String USER = "root";
		//final String PASS = "phoenix6832";

		Connection conn = null;


		String sql = null;
		//STEP 2: Register JDBC driver

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//STEP 3: Open a connection
		System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();
			String password = null;
			do{
				password = SecPasswdGen.PasswdGenerator();
			}while(!UserAccountDB.compareSecPwd(Username,password));

			sql = "Select * from USER_MAIL where Username = '"+Username+"' ";
			

			ResultSet Rs = stmt1.executeQuery(sql);
			
			while(Rs.next()){
				SendMailTLS.SendMail(Rs.getString(1), Rs.getString(3), Rs.getString(2));
			}

			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		
		
	}

}
