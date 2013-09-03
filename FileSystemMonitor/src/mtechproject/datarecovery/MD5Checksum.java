package mtechproject.datarecovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import mtechproject.useraccounts.Database_Credentials;

public class MD5Checksum implements Database_Credentials{

	public static byte[] createChecksum(String filename) throws Exception {
		InputStream fis =  new FileInputStream(filename);
		
		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("MD5");
		int numRead;

		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);

		fis.close();
		return complete.digest();
	}

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static void getMD5Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		File fname = new File(filename);
		String result = "";

		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		//Code for inserting MD5 Check sum into database.
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
			sql = "INSERT INTO MD5_File_Checksum_log " +
			"VALUES ('"+fname+"','"+result+"')";
			stmt1.executeUpdate(sql);
			stmt1.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return result;
	}
	
	public static void compareChecksum(String filename){
		byte[] b = null;
		try {
			b = createChecksum(filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File fname = new File(filename);
		String result = "";

		for (int i=0; i < b.length; i++) {
			result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
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
			sql = "select * from MD5_File_Checksum_log where filename like '%"+fname+"%'";
			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()){
				//System.out.println(rs.getString("checksum"));
				if(!result.equals(rs.getString("checksum"))){
					System.out.println(fname.getAbsolutePath()+" is modified.");
					//JTextArea textarea = new JTextArea();
					//textArea.append(fname.getAbsolutePath());
					//redirectSystemStreams();
					//updateTextArea(fname.getAbsolutePath()+" is modified.");
				}
				
			}

			stmt1.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	static JTextArea textArea = new JTextArea();
	private static void updateTextArea(final String text) {
		  SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      textArea.append(text);
		    }
		  });
		}
		 
		private static void redirectSystemStreams() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(int b) throws IOException {
		      updateTextArea(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      updateTextArea(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		 
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
		}	 
	
	
	public static void CheckMD5Checksum(File fname){
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
			sql = "select * from MD5_File_Checksum_log where filename like '%"+fname+"%'";
			ResultSet rs = stmt1.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString("checksum"));
				
			}

			stmt1.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void main(String args[]) {
		try {
			//System.out.println(getMD5Checksum("C:\\Insurance Details1.doc"));
			//CheckMD5Checksum(new File("C:\\Insurance Details1.doc"));
			compareChecksum("E:\\DVFS\\JBerger_DFS\\My Digital Editions\\Insurance Details.doc");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}