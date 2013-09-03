package mtechproject.mazedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;

public class GetFileAttr implements Database_Credentials{

	public static void main(String[] args){
		//boolean result = GetFileAttrs("chroot(2) - Linux manual page.pdf", "Alok Omkar");
		//System.out.println("Result : "+result);
	}

	public static String GetFileGroup(String Filename){
		String group = null;
		final String DB_URL = "jdbc:mysql://localhost:3306/FILEATTRIBUTEDB";
		Connection conn = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = null;
		ResultSet rs = null;
		//STEP 3: Open a connection
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();

			sql = "Select * from Fileattribute where Filename = '"+Filename+"' ";
			rs = stmt1.executeQuery(sql);
			//System.out.println(rs.getString(3).toString());


			while(rs.next()){
				group = rs.getString("Groupname");
			}
		
		rs.close();
		stmt1.close();
		conn.close();

	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	return group;
}

	public static String GetUserGroup(String Username){
		String group = null;
		final String DB_URL = "jdbc:mysql://localhost:3306/FILEATTRIBUTEDB";

		Connection conn = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = null;
		ResultSet rs = null;
		//STEP 3: Open a connection
		//System.out.println("Connecting to a selected database...");
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();

			sql = "Select * from Fileattribute where Owner = '"+Username+"' ";
			rs = stmt1.executeQuery(sql);
			//System.out.println(rs.getString(3).toString());


			while(rs.next()){
				group = rs.getString("Groupname");
			}
		
		rs.close();
		stmt1.close();
		conn.close();

	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	return group;
}

public static String GetFileAttrs(String Filename, String Username){

	boolean result = false;
	final String DB_URL = "jdbc:mysql://localhost:3306/FILEATTRIBUTEDB";

	Connection conn = null;

	String User = null;
	String sql = null, sql2 = null;
	ResultSet rs = null, rs2 = null;
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

		sql = "Select * from Fileattribute where Filename = '"+Filename+"' ";
		rs = stmt1.executeQuery(sql);
		//System.out.println(rs.getString(3).toString());
		String group = null;

		while(rs.next()){
			group = rs.getString("Groupname");
		}
		sql2 = "Select distinct Owner from Fileattribute where Groupname = '"+group+"'";
		rs2 = stmt1.executeQuery(sql2);


		while(rs2.next()){
			User = rs2.getString("Owner");
			if(Username.equals(User)){
				result = true;


			}

		}
		rs2.close();
		rs.close();
		stmt1.close();
		conn.close();

	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}


	if(result == true){
		return Username;
	}
	else
		return User;



}
}
