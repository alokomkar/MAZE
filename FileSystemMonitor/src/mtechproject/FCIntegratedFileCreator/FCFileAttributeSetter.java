package mtechproject.FCIntegratedFileCreator;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.mazedb.InsertFileAttr;
import mtechproject.mazedb.RecursiveFileOwnerSetter;
import mtechproject.useraccounts.Database_Credentials;

public class FCFileAttributeSetter implements Database_Credentials{

	public static void main(String args[]){

		//FCFileAttributeSetter fw = new FCFileAttributeSetter();
		//fw.walk("E:/FC Files" );
		System.out.println(FCFileAttributeSetter.checkfcattrs("Alok Omkar_Password Change.docx", "Alok Omkar"));

	}

	public static boolean checkfcattrs(String Filename, String User){
		boolean result = false;
		final String DB_URL = "jdbc:mysql://localhost:3306/faker";

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

			stmt = conn.createStatement();

			String sql = "Select * from fc_file_attr where Filename like '%"+Filename+"%'";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				if(User.equals(rs.getString("Owner"))){
					result = true;
				}
			}

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
		return result;
	}

	public static void insertfcattrs(String Filename, String Owner){
		final String DB_URL = "jdbc:mysql://localhost:3306/faker";

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

			stmt = conn.createStatement();

			String sql = "Insert into fc_file_attr values('"+Filename+"','"+Owner+"')";
			stmt.executeUpdate(sql);

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

	public void walk( String path ) {

		File chosenfile = new File( path );
		File[] list = chosenfile.listFiles();
		for ( File f : list ) {
			//Path file_dir = Paths.get(f.getParent());
			//Path file = file_dir.resolve(f.getName());
			//UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();

			if ( f.isDirectory() ) {
				//     walk( f.getAbsolutePath() );
				//     InsertFileAttr.InsertFileAttrs(f.getName(),"Adithya","Users");
				//     System.out.println( "Dir:" + f.getAbsoluteFile() );
			}
			else {
				if(f.getName().contains("Alok Omkar")){
					FCFileAttributeSetter.insertfcattrs(f.getName(), "Alok Omkar");	
				}
				else if(f.getName().contains("Adithya")){
					FCFileAttributeSetter.insertfcattrs(f.getName(), "Adithya");
				}
				else if(f.getName().contains("Soumya")){
					FCFileAttributeSetter.insertfcattrs(f.getName(), "Soumya");
				}
				//InsertFileAttr.InsertFileAttrs(f.getName(),"Adithya","Users");
				//System.out.println( "File:" + f.getAbsoluteFile() );
			}
		}
	}

	public static boolean RetrieveUnlocker(String user) {
		// TODO Auto-generated method stub
		boolean result = false;
		final String DB_URL = "jdbc:mysql://localhost:3306/faker";

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

			stmt = conn.createStatement();

			String sql = "Select * from RetrieveUnlocker where Username like '%"+user+"%'";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				if("N".equals(rs.getString("OnetimeUnlock"))){
					result = true;
				}
			}

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
		return result;
	}

	public static void SetLocked(String user) {
		// TODO Auto-generated method stub
		final String DB_URL = "jdbc:mysql://localhost:3306/faker";

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

			stmt = conn.createStatement();

			String sql = "Update RetrieveUnlocker set OnetimeUnlock = 'Y' where Username like '%"+user+"%'";

			stmt.executeUpdate(sql);


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
