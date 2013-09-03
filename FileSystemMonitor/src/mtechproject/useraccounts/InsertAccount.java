package mtechproject.useraccounts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;


import mtechproject.useraccounts.UserBean;

public class InsertAccount extends NewAccount implements Database_Credentials{
	
	static ArrayList<UserBean> UserList = new ArrayList<UserBean>();
	
	//public void Callmain(String uname, String pwd, String mobno){
		public static void Callmain(String[] args){
		
		//System.out.println(mobno+" "+uname+" "+pwd);
		//UserList = CreateUserAccount(uname,pwd,mobno);
		UserList = CreateUserAccount();
	//	final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";
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
			Iterator<UserBean> itr = UserList.iterator();
			while(itr.hasNext()){

				//FileBean fb = new FileBean();
				UserBean ub = (UserBean)itr.next();
				System.out.println("Credentials : ");
				System.out.println(ub.getUsername());
				System.out.println(ub.getPrim_password());
				System.out.println(ub.getSec_password());
				System.out.println(ub.getMobileno());

				sql = "INSERT INTO USER_ACCOUNTS " +
				"VALUES ('"+ub.getUsername()+"','"+ub.getPrim_password()+"','"+ub.getSec_password()+"','"+ub.getLocked()+"','"+ub.getMobileno().toString().trim()+"')";

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
