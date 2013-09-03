package mtechproject.mazedb;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Filewalker2sandtrap{

	

	ArrayList<FileBean> FileList =  new ArrayList<FileBean>();



	public ArrayList<FileBean> walk( String path ) {

		File root = new File( path );
		File[] list = root.listFiles();
		BasicFileAttributes attrs = null;

		for ( File f : list ) {
			if ( f.isDirectory() ) {

				walk( f.getAbsolutePath() );
				System.out.println( "Dir:" + f.getName() );

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
				System.out.println("  Last accessed at:" + dateFormat.format(date));
				
				FileList.add(fb);

			}
			else {

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
				System.out.println( "File:" + f.getName() );
				//System.out.println("  Last accessed at:" + attrs.lastAccessTime());
				System.out.println("  Last accessed at:" + dateFormat.format(date));
				//fb.setLAT(attrs.lastAccessTime().toString());

				FileList.add(fb);

			}
		}	
		
		return FileList;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Initializer2sandtrap initer = new Initializer2sandtrap();
		Filewalker2sandtrap fw = new Filewalker2sandtrap();
		ArrayList<FileBean> FileList1 = fw.walk("C:/DVFS");
		
		InsertTable2sandtrap inserter = new InsertTable2sandtrap(FileList1);
			

	}
}
