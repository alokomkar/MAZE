package mtechproject.datarecovery;




import java.awt.TextArea;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class RecursiveFileChecker{

	

	ArrayList<FileBean> FileList =  new ArrayList<FileBean>();



	public ArrayList<FileBean> walk( String path ) {

		File root = new File( path );
		File[] list = root.listFiles();
		BasicFileAttributes attrs = null;

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				walk( f.getAbsolutePath() );
				//PipedOutputStream pOut = new PipedOutputStream();   
				//System.setOut(new PrintStream(pOut));   
				System.out.println( "Dir:" + f.getName() );
				//JTextArea textarea = new JTextArea();
				//textarea.append(f.getName());
				

				Path file_dir = Paths.get(f.getParent());
				Path file = file_dir.resolve(f.getName());

				try {
					attrs = Files.readAttributes(file, BasicFileAttributes.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				FileBean fb = new FileBean();
				fb.setFilename(f.getName());
				//fb.setLAT(attrs.lastAccessTime().toString());
				fb.setLAT(dateFormat.format(date));

				//System.out.println("  Last accessed at:" + attrs.lastAccessTime());
				//System.out.println("  Last accessed at:" + dateFormat.format(date));
				
				FileList.add(fb);

			}
			else {
				try {
					MD5Checksum.compareChecksum(f.getAbsolutePath());
					} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//FileBean fb = new FileBean();
				Path file_dir = Paths.get(f.getParent());
				Path file = file_dir.resolve(f.getName());

				try {
					attrs = Files.readAttributes(file, BasicFileAttributes.class);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}       
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				FileBean fb = new FileBean();
				fb.setFilename(f.getName());
				fb.setLAT(dateFormat.format(date));
				//System.out.println( "File:" + f.getName() );
				//System.out.println("  Last accessed at:" + attrs.lastAccessTime());
				//System.out.println("  Last accessed at:" + dateFormat.format(date));
				//fb.setLAT(attrs.lastAccessTime().toString());

				FileList.add(fb);

			}
		}	
		
		return FileList;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		RecursiveFileChecker fw = new RecursiveFileChecker();
		ArrayList<FileBean> FileList1 = fw.walk("E:/DVFS");
		
		
			

	}
}
