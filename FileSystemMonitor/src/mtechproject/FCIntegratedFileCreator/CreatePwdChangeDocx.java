package mtechproject.FCIntegratedFileCreator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;

import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class CreatePwdChangeDocx implements Database_Credentials{
	public static void main(String args[]){
		
		
		String Username = "Soumya";
		WordprocessingMLPackage wordMLPackage = null;
		try {
			wordMLPackage = WordprocessingMLPackage.createPackage();
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final String DB_URL = "jdbc:mysql://localhost:3306/FAKER";
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
			
			sql = "SELECT * from fc_table where Username like '%"+Username+"%'";

			ResultSet rs = stmt1.executeQuery(sql);
			if(rs.next()){
				String User = rs.getString("Username");
				String Password = rs.getString("F_Pwd");
				wordMLPackage.getMainDocumentPart().addParagraphOfText("Change of Password");
				wordMLPackage.getMainDocumentPart().addParagraphOfText("This is to notify that your password has been changed. Your new password for the account is:");
				wordMLPackage.getMainDocumentPart().addParagraphOfText("Username :"+User+"  Password : "+Password);
				
				try {
					wordMLPackage.save(new java.io.File("E:/"+User+"_Password Change.docx"));
					// 1) Load DOCX into WordprocessingMLPackage
					InputStream is = new FileInputStream(new File("E:/"+User+"_Password Change.docx"));
					wordMLPackage = WordprocessingMLPackage
							.load(is);

					// 2) Prepare Pdf settings
					PdfSettings pdfSettings = new PdfSettings();

					// 3) Convert WordprocessingMLPackage to Pdf
					OutputStream out = new FileOutputStream(new File(
							"E:/"+User+"_Password Change.pdf"));
					PdfConversion converter = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(
							wordMLPackage);
					converter.output(out, pdfSettings);

				} catch (Docx4JException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			stmt1.close();
			conn.close();
			
		


		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		

	}
}
