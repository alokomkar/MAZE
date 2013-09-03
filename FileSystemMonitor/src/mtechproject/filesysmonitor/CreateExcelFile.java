package mtechproject.filesysmonitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class CreateExcelFile implements Database_Credentials{
	//static int counter = 1;
	//static int counter1 = 1;

	public static void InsertLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/FloggerDB";
		int counter = 1;
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
			FileOutputStream fileOut = null;
			sql = "Select * from Flogger";
			String filename = "E:/Flogger.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Activity");
			rowhead.createCell((short) 1).setCellValue("Username");
			rowhead.createCell((short) 2).setCellValue("Time of Event");
			//rowhead.createCell((short) 3).setCellValue("Username");
			//rowhead.createCell((short) 4).setCellValue("E-mail");
			//rowhead.createCell((short) 5).setCellValue("Country");


			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Activity"));
				row.createCell((short) 1).setCellValue(rs.getString("Username"));
				row.createCell((short) 2).setCellValue(rs.getString("TIME_OF_EVENT"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public static void InsertChangeLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/DATARECOVERY";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from changelog";
			String filename = "E:/Changelog.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Filename");
			rowhead.createCell((short) 1).setCellValue("Date_of_Detection");
			rowhead.createCell((short) 2).setCellValue("Change Information");
			
			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Filename"));
				row.createCell((short) 1).setCellValue(rs.getString("Date_of_Detection"));
				row.createCell((short) 2).setCellValue(rs.getString("Changed_Information"));
				counter ++;
				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



	public static void AllFileLastAccessLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/ACCESSDB";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from Access_log";
			String filename = "E:/AllFilesAccessLog.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Filename");
			rowhead.createCell((short) 1).setCellValue("Last Access Time");
			//rowhead.createCell((short) 2).setCellValue("Time of Event");
			//rowhead.createCell((short) 3).setCellValue("Username");
			//rowhead.createCell((short) 4).setCellValue("E-mail");
			//rowhead.createCell((short) 5).setCellValue("Country");


			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Filename"));
				row.createCell((short) 1).setCellValue(rs.getString("Last_access_time"));
				//row.createCell((short) 2).setCellValue(rs.getString("TIME_OF_EVENT"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public static void SandTrapLastAccessLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/SANDTRAPACCESSDB";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from Access_log";
			String filename = "E:/SandTrapAccessLog.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Filename");
			rowhead.createCell((short) 1).setCellValue("Last Access Time");
			//rowhead.createCell((short) 2).setCellValue("Time of Event");
			//rowhead.createCell((short) 3).setCellValue("Username");
			//rowhead.createCell((short) 4).setCellValue("E-mail");
			//rowhead.createCell((short) 5).setCellValue("Country");


			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Filename"));
				row.createCell((short) 1).setCellValue(rs.getString("Last_access_time"));
				//row.createCell((short) 2).setCellValue(rs.getString("TIME_OF_EVENT"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}

	public static void BreachLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/BREACHLOGDB";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from breachlog";
			String filename = "E:/BreachLog.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Filename");
			rowhead.createCell((short) 1).setCellValue("Username");
			rowhead.createCell((short) 2).setCellValue("Access Time");
			//rowhead.createCell((short) 3).setCellValue("Username");
			//rowhead.createCell((short) 4).setCellValue("E-mail");
			//rowhead.createCell((short) 5).setCellValue("Country");


			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Filename"));
				row.createCell((short) 1).setCellValue(rs.getString("Username"));
				row.createCell((short) 2).setCellValue(rs.getString("ACCESS_TIME"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static void ForensicstoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/Floggerdb";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from user_document_access_table";
			String filename = "E:/Forensics.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("Filename");
			rowhead.createCell((short) 1).setCellValue("Accessed By");
			rowhead.createCell((short) 2).setCellValue("Action_Performed");
			rowhead.createCell((short) 3).setCellValue("Time_of_Action");
			rowhead.createCell((short) 4).setCellValue("Total_Access_times");
			rowhead.createCell((short) 5).setCellValue("User_Group");
			rowhead.createCell((short) 6).setCellValue("File_Group");
			rowhead.createCell((short) 7).setCellValue("Privileged_Access");

			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("Filename"));
				row.createCell((short) 1).setCellValue(rs.getString("Accessed_by"));
				row.createCell((short) 2).setCellValue(rs.getString("Action_Performed"));
				row.createCell((short) 3).setCellValue(rs.getString("Time_of_action"));
				row.createCell((short) 4).setCellValue(rs.getString("Total_Access_Times"));
				row.createCell((short) 5).setCellValue(rs.getString("User_Group"));
				row.createCell((short) 6).setCellValue(rs.getString("File_Group"));
				row.createCell((short) 7).setCellValue(rs.getString("Privileged_Access"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public static void IPLogtoExcel() {
		final String DB_URL = "jdbc:mysql://localhost:3306/USERACCOUNTSDB";
		int counter = 1;
		
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
			FileOutputStream fileOut = null;
			sql = "Select * from ipaddress";
			String filename = "E:/IPLog.xls" ;
			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet =  hwb.createSheet("new sheet");

			HSSFRow rowhead=   sheet.createRow((short)0);
			rowhead.createCell((short) 0).setCellValue("IP ADDRESS");
			//rowhead.createCell((short) 1).setCellValue("Username");
			//rowhead.createCell((short) 2).setCellValue("Access Time");
			//rowhead.createCell((short) 3).setCellValue("Username");
			//rowhead.createCell((short) 4).setCellValue("E-mail");
			//rowhead.createCell((short) 5).setCellValue("Country");


			ResultSet rs = stmt1.executeQuery(sql);

			while(rs.next()){
				HSSFRow row=   sheet.createRow((int)counter);
				row.createCell((short) 0).setCellValue(rs.getString("IPADDRESS"));
				//row.createCell((short) 1).setCellValue(rs.getString("Username"));
				//row.createCell((short) 2).setCellValue(rs.getString("ACCESS_TIME"));
				counter ++;

				//row.createCell((short) 3).setCellValue("roseindia");
				//row.createCell((short) 4).setCellValue("hello@roseindia.net");
				//row.createCell((short) 5).setCellValue("India");


				try {
					fileOut =  new FileOutputStream(filename);
					hwb.write(fileOut);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



			}
			fileOut.close();
			rs.close();
			stmt1.close();
			conn.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}




	public static int InsertLogtoExcel(String Activity, String Username, String Time, int counter1) {

		FileOutputStream fileOut = null;
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//Date date = new Date();

		String filename = "E:/Flogger"+Username+".xls" ;
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("new sheet");

		HSSFRow rowhead=   sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue("Activity");
		rowhead.createCell((short) 1).setCellValue("Username");
		rowhead.createCell((short) 2).setCellValue("Time of Event");
		//if(counter1 == 1){
		HSSFRow row=   sheet.createRow((int)counter1);
		row.createCell((short) 0).setCellValue(Activity);
		row.createCell((short) 1).setCellValue(Username);
		row.createCell((short) 2).setCellValue(Time);
		counter1 ++;
		//}
		try {
			fileOut =  new FileOutputStream(filename,true);
			hwb.write(fileOut);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return counter1;

	}

	public static void main(String[]args){
		//BreachLogtoExcel();
		//InsertLogtoExcel();
		//AllFileLastAccessLogtoExcel();
		//SandTrapLastAccessLogtoExcel();
		//IPLogtoExcel();
		ForensicstoExcel();
		
		System.out.println("Your excel file has been generated!");

	}
}