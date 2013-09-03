package mtechproject.useraccounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserAccountDB implements Database_Credentials{

	public static void main(String[] args){

		//CreateDB();
		//CreateTable();
		//System.out.println(MobileLookup("Alok Omkar"));
		//LockAccount("Alok Omkar");
		//CreateIPTable();
		//InsertIP("127.0.0.1");
		Sample();
	}

	private static void CreateDB() {

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";

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

			final String sql = "CREATE DATABASE IF NOT EXISTS USERACCOUNTSDB";
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

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";
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

			String sql = "CREATE TABLE IF NOT EXISTS USER_ACCOUNTS " +
			"(Username VARCHAR(255)," + 
			" Prim_Password VARCHAR(255), " +
			" Sec_Password VARCHAR(255), Locked VARCHAR(1),Mobile_no VARCHAR(16), " +
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
	private static void CreateIPTable() {

		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

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

			String sql = "CREATE TABLE IF NOT EXISTS IPADDRESS " +
			"(IPADDRESS VARCHAR(255))"; 

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

	public static void InsertIP(String IP){
		//final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		//final String USER = "root";
		//final String PASS = "phoenix6832";
		Connection conn = null;
		Statement stmt = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "Insert into IPADDRESS values('"+IP+"')";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static boolean LookupIP(String IP){
		boolean result = false;

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";


		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT IPADDRESS FROM ipaddress" +
			" WHERE IPADDRESS LIKE '%"+IP+"%' ";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(IP.equals(rs.getString("IPADDRESS"))){

					result= true;
				}

				else {
					result = false;
				}
			}
			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return result;

	}

	public static void InsertMAC(String User,String MAC,String IP){

		if(!LookupMAC(User,MAC)){//USER AND MACHINE MAC ARE NOT PRESENT
			////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
			final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

			//  Database credentials
			////final String USER = "root";
			////final String PASS = "phoenix6832";
			Connection conn = null;
			Statement stmt = null;
			//STEP 2: Register JDBC driver
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
				String sql = "Insert into User_Machines values('"+User+"','"+MAC+"','"+IP+"')";
				stmt.executeUpdate(sql);
				stmt.close();
				conn.close();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public static boolean LookupMAC(String Username, String MAC){
		boolean result = false;

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";


		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM User_Machines" +
			" WHERE Username LIKE '%"+Username+"%' And mac LIKE '%"+MAC+"%' ";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				result= true;

			}
			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return result;

	}


	public static boolean compareSecPwd(String username,String Password){
		boolean result = false;

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";


		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT Username, Prim_Password, Sec_Password, Locked FROM user_accounts" +
			" WHERE Username LIKE '%"+username+"%' ";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(username.equals(rs.getString("Username")) && !Password.equals(rs.getString("Sec_Password"))){

					result= true;
				}

				else {
					result = false;
				}
			}
			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return result;
	}

	public static String MobileLookup(String username){
		boolean result = false;

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";
		String mobileno = null;

		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT Mobile_no FROM user_accounts" +
			" WHERE Username LIKE '%"+username+"%' ";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				mobileno = rs.getString("Mobile_no");
			}

			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return mobileno;
	}

	public static void LockAccount(String Username){
		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";
		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "UPDATE user_accounts SET Locked = 'Y'" +
			" WHERE Username LIKE '%"+Username+"%' ";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public static void UnLockAccount(String user) {
		// TODO Auto-generated method stub
		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";
		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "UPDATE user_accounts SET Locked = 'N'" +
			" WHERE Username LIKE '%"+user+"%' ";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}

	public static void InsertIntruderMAC(String maCaddress) {
		// TODO Auto-generated method stub
		
		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";
		Connection conn = null;
		Statement stmt = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "Insert into Intruder_Machines values('"+maCaddress+"')";
			stmt.executeUpdate(sql);
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		
	}
	
	public static boolean LookupIntruderMAC(String MAC){
		boolean result = false;

		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";


		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "SELECT * FROM intruder_machines" +
			" WHERE mac LIKE '%"+MAC+"%' ";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				result= true;

			}
			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return result;

	}
	
	public static void Sample(){
		
		////final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";

		//  Database credentials
		////final String USER = "root";
		////final String PASS = "phoenix6832";


		Connection conn = null;
		Statement stmt = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			String Username = "Alok Omkar";

			String sql = "Select distinct a.Username from user_accounts a, user_machines m where a.Username = m.Username and a.Username Like '%"+Username+"%'";

			ResultSet rs = null;

			rs = stmt.executeQuery(sql);
			while(rs.next()){
				//result= true;
				System.out.println(rs.getString("a.Username"));

			}
			stmt.close();
			rs.close();
			conn.close();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}





}
