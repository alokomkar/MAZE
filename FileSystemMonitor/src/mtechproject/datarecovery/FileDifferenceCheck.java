package mtechproject.datarecovery;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mtechproject.useraccounts.Database_Credentials;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.mysql.jdbc.StringUtils;

public class FileDifferenceCheck implements Database_Credentials{

	public static void callmain(String filename){
		File file = null;
		WordExtractor extractor = null ;
		try {

			//file = new File("E:\\DVFS\\JBerger_DFS\\My Digital Editions\\Insurance Details.doc");
			file = new File(filename);
			FileInputStream fis=new FileInputStream(file.getAbsolutePath());
			HWPFDocument document=new HWPFDocument(fis);
			extractor = new WordExtractor(document);
			String [] fileData = extractor.getParagraphText();
			for(int i=0;i<fileData.length;i++){
				if(fileData[i] != null && !StringUtils.isEmptyOrWhitespaceOnly(fileData[i])){

					String Hashkey = SHAshing.hashcheck(fileData[i]);
					//System.out.println(Hashkey);
					//Retrieve Hash key stored in database and compare with the lines
					final String DB_URL = "jdbc:mysql://localhost:3306/DATARECOVERY";

					Connection conn = null;


					String sql = null;
					//STEP 2: Register JDBC driver
					try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						conn = DriverManager.getConnection(DB_URL, USER, PASS);
						Statement stmt1=conn.createStatement();
						sql = "Select * from file_data_logger where sentence like '%"+fileData[i]+"%' and filename like '%"+file+"%'";
						//sql = "Select * from file_data_logger";
						ResultSet rs = stmt1.executeQuery(sql);
						if(!rs.next()){
							//System.out.println("File modified at line : " + (i+1));
							DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date = new Date();

							InsertIntoLog(filename,dateFormat.format(date).toString(),"File modified at line : " + (i+1));
						}
						while(rs.next()){

							if(!Hashkey.equals(rs.getString("hashcode"))){
								//System.out.println("File modified at line : "+i);
								DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								Date date = new Date();

								InsertIntoLog(filename,dateFormat.format(date).toString(),"File modified at line : " + (i+1));
							}

						}

						stmt1.close();
						conn.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
			}
		}
		catch(Exception exep){}
	}

	private static void InsertIntoLog(String filename, String date, String activity) {
		final String DB_URL = "jdbc:mysql://localhost:3306/DATARECOVERY";
		Connection conn = null;


		String sql = null;
		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt1=conn.createStatement();
			sql = "INSERT INTO CHANGELOG " +
			"VALUES ('"+filename+"','"+date+"','"+activity+"')";

			stmt1.executeUpdate(sql);


			stmt1.close();
			conn.close();

		}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}



}
