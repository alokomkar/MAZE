package mtechproject.mazedb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;


public class Initializer2sandtrap implements Database_Credentials{

	public Initializer2sandtrap(){


		DropDB();
		CreateDB();
		CreateTable();

	}

	private static void DropDB() {
		final String DB_URL = "jdbc:mysql://localhost:3306/sandtrapaccessdb";

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

			String sql = "DROP DATABASE SANDTRAPACCESSDB";
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

	private static void CreateTable() {
		final String DB_URL = "jdbc:mysql://localhost:3306/SANDTRAPACCESSDB";

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

			String sql = "CREATE TABLE IF NOT EXISTS ACCESS_LOG " +
			"(Filename VARCHAR(255)," + 
			" Last_access_time VARCHAR(255), " + 
			" PRIMARY KEY ( Filename ))"; 

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

			final String sql = "CREATE DATABASE IF NOT EXISTS SANDTRAPACCESSDB";
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