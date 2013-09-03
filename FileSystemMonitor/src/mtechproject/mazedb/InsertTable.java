package mtechproject.mazedb;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import mtechproject.useraccounts.Database_Credentials;

public class InsertTable implements Database_Credentials{

	@SuppressWarnings({ "unused", "null" })
	public InsertTable(ArrayList<FileBean> FileList){
		final String DB_URL = "jdbc:mysql://localhost:3306/accessdb";

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
			Iterator<FileBean> itr = FileList.iterator();
			while(itr.hasNext()){

				//FileBean fb = new FileBean();
				FileBean fb = (FileBean)itr.next();
				System.out.println(fb.getFilename());
				System.out.println(fb.getLAT());

				sql = "INSERT INTO access_log " +
				"VALUES ('"+fb.getFilename()+"','"+fb.getLAT()+"')";

				stmt1.executeUpdate(sql);



			}
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}

	public static void InsertTable(Path Filename, String accesstime){
		final String DB_URL = "jdbc:mysql://localhost:3306/accessdb";

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


			sql = "INSERT INTO access_log " +
			"VALUES ('"+Filename.getFileName()+"','"+accesstime+"')";

			stmt1.executeUpdate(sql);
			sql = "DELETE FROM access_log WHERE Filename LIKE '%.tmp'";
			
			stmt1.executeUpdate(sql);
			
			sql = "DELETE FROM access_log WHERE Filename LIKE '%~$%'";
			stmt1.executeUpdate(sql);
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




	}
	public InsertTable() {
		// TODO Auto-generated constructor stub
	}

	public void InsertDecoyFileTable(ArrayList<DecoyFileBean> FileList){
		final String DB_URL = "jdbc:mysql://localhost:3306/decoyfiledb";

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
			Iterator<DecoyFileBean> itr = FileList.iterator();
			while(itr.hasNext()){

				//FileBean fb = new FileBean();
				DecoyFileBean fb = (DecoyFileBean)itr.next();
				System.out.println(fb.getFilename());
				System.out.println(fb.getAbsolutePath());

				sql = "INSERT INTO decoyfilelog " +
				"VALUES ('"+fb.getFilename()+"','"+fb.getAbsolutePath()+"')";

				stmt1.executeUpdate(sql);



			}
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}



	}

}
