package mtechproject.mazedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;

public class InsertFileAttr implements Database_Credentials{
	public static void main(String args[]){
		String group = null;
		group = GetGroup("Alok Omkar");
	}
	public static String GetGroup(String user){
		final String DB_URL = "jdbc:mysql://localhost:3306/FILEATTRIBUTEDB";
		String group = null;
	
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
			
			sql = "SELECT Groupname from FILEATTRIBUTE " +
			"WHERE Owner LIKE '%"+user+"%'";

			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()){
				group = rs.getString("Groupname");	
			}
			
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(group);
		return group;

	}
	public static void InsertFileAttrs(final String filename, final String owner, final String group){
		final String DB_URL = "jdbc:mysql://localhost:3306/FILEATTRIBUTEDB";

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
			
			sql = "INSERT INTO FILEATTRIBUTE " +
			"VALUES ('"+filename+"','"+owner+"','"+group+"')";

			stmt1.executeUpdate(sql);
			
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
