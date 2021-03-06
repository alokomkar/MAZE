package mtechproject.mazedb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;


public class FloggerDB implements Database_Credentials{
	
	public static void main(String[] args){

		//DropDB();
		CreateDB();
		CreateattrTable();
		//InsertLog(Filename, Username, Access_time);

	}

	public static void InsertLog(String activity, String username2,
			String access_time2) {
		final String DB_URL = "jdbc:mysql://localhost:3306/FloggerDB";

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
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();
			
			sql = "INSERT INTO Flogger " +
			"VALUES ('"+activity+"','"+username2+"','"+access_time2+"')";

			stmt1.executeUpdate(sql);
			sql = "DELETE FROM Flogger WHERE Activity LIKE '%.tmp'";
			stmt1.executeUpdate(sql);
			sql = "DELETE FROM Flogger WHERE Activity LIKE '%~$%'";
			stmt1.executeUpdate(sql);
			
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

	}

	private static void DropDB() {
		final String DB_URL = "jdbc:mysql://localhost:3306/accessdb";

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
			System.out.println("Deleting database...");
			stmt = conn.createStatement();

			String sql = "DROP DATABASE FILEATTRIBUTEDB";
			stmt.executeUpdate(sql);
			System.out.println("Database deleted successfully...");
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
		//System.out.println("Goodbye!");



	}

	private static void CreateattrTable() {
		final String DB_URL = "jdbc:mysql://localhost:3306/Floggerdb";

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

			String sql = "CREATE TABLE Flogger " +
			"(Activity VARCHAR(500)," + 
			" Username VARCHAR(255),"+" TIME_OF_EVENT VARCHAR(255) " + 
			" )"; 

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

			final String sql = "CREATE DATABASE IF NOT EXISTS FloggerDB";
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
}