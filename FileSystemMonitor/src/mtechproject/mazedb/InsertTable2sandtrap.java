package mtechproject.mazedb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import mtechproject.useraccounts.Database_Credentials;

public class InsertTable2sandtrap implements Database_Credentials{

	@SuppressWarnings({ "unused", "null" })
	public InsertTable2sandtrap(ArrayList<FileBean> FileList){
		final String DB_URL = "jdbc:mysql://localhost:3306/sandtrapaccessdb";

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
}
