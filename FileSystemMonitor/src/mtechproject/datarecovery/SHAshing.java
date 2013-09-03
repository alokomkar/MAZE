package mtechproject.datarecovery;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import mtechproject.useraccounts.Database_Credentials;

public class SHAshing implements Database_Credentials{

	public static String hashcheck(String message){

		MessageDigest messageDigest;
		StringBuffer digestInHex = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(message.getBytes("UTF-16BE"));
			byte[] digest = messageDigest.digest();

			digestInHex = new StringBuffer();

			for (int i = 0, l = digest.length; i < l; i++) {
				// Preserve the bit representation when casting to integer.
				int intRep = digest[i] & 0xFF;
				// Add leading zero if value is less than 0x10.
				if (intRep < 0x10)  digestInHex.append('\u0030');
				// Convert value to hex.
				digestInHex.append(Integer.toHexString(intRep));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return digestInHex.toString();



	}
	public static void hashing(String filename,String message){

		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-512");
			messageDigest.update(message.getBytes("UTF-16BE"));
			byte[] digest = messageDigest.digest();

			StringBuffer digestInHex = new StringBuffer();

			for (int i = 0, l = digest.length; i < l; i++) {
				// Preserve the bit representation when casting to integer.
				int intRep = digest[i] & 0xFF;
				// Add leading zero if value is less than 0x10.
				if (intRep < 0x10)  digestInHex.append('\u0030');
				// Convert value to hex.
				digestInHex.append(Integer.toHexString(intRep));
			}

			//Code to insert into database
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
			//STEP 3: Open a connection
			try {
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt1=conn.createStatement();
				sql = "INSERT INTO file_data_logger " +
				"VALUES ('"+filename+"','"+message+"','"+digestInHex.toString()+"')";
				stmt1.executeUpdate(sql);

				stmt1.close();
				conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
