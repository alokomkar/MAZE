package mtechproject.mazedb;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DecoyFileWalker {

	ArrayList<DecoyFileBean> FileList =  new ArrayList<DecoyFileBean>();



	public ArrayList<DecoyFileBean> walk( String path ) {

		File root = new File( path );
		File[] list = root.listFiles();
		for ( File f : list ) {
			if ( f.isDirectory() ) {

				walk( f.getAbsolutePath() );
				Path file_dir = Paths.get(f.getParent());
				Path file = file_dir.resolve(f.getName());

				DecoyFileBean fb = new DecoyFileBean();
				fb.setFilename(f.getName());
				fb.setAbsolutePath(f.getAbsolutePath());
				
				FileList.add(fb);

			}
			else {
				Path file_dir = Paths.get(f.getParent());
				Path file = file_dir.resolve(f.getName());

				DecoyFileBean fb = new DecoyFileBean();
				fb.setFilename(f.getName());
				fb.setAbsolutePath(f.getAbsolutePath());
				System.out.println(f.getName()+f.getAbsolutePath());
				FileList.add(fb);

			}
		}	
		
		return FileList;
	}

	public static void main(String[] args){
		
		DecoyFileWalker fw = new DecoyFileWalker();
		ArrayList<DecoyFileBean> FileList1 = fw.walk("E:/Decoys");
		InsertTable inserter = new InsertTable();
		inserter.InsertDecoyFileTable(FileList1);
		
		
	}
}
