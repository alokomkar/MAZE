/**
 * @author Adithya & Alok
 * @brief The class contains the generic XFILE, inherited from FILE.
 * 		  which is used for all file operations.
 */

package mtechproject.datarecovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

interface XFileConstants
{
	int BLOCK_SIZE = 4096;
}

public class XFile extends File implements XFileConstants{

	public XFile(String pathname) {
		super(pathname);
	}

	public XFile(URI uri) {
		super(uri);
	}

	public XFile(String parent, String child) {
		super(parent, child);
	}

	public XFile(File parent, String child) {
		super(parent, child);
	}

	/**
	 * The function deletes the files in the given directory
	 * @param cleanDirectories Clean directories recursively.
	 * @todo Implement recursive cleanijng of directories.
	 */
	public void cleanDir(boolean cleanDirectories)
	{
		if(!isDirectory()) 
			return;
		String files[] = list();
		int index = 0;
		for ( ; index < files.length ; index++)
		{
			XFile file = new XFile(files[index]);
			if ( cleanDirectories == true && file.isDirectory() )
				file.delete();
		}
	}
	
	/**
	 * @param destFilename The destination File name.
	 * @return XFile Object, reference to the destination File.
	 * @throws IOException If there will be an error in file creation.
	 */
	public XFile copyFile(String sourceFilename, String destFilename) throws IOException
	{
		byte buffer[] = new byte[BLOCK_SIZE];
		FileInputStream infile = new FileInputStream(sourceFilename);
		FileOutputStream outfile = new FileOutputStream(destFilename);
		
		while (infile.read(buffer)>0)
		{
			outfile.write(buffer); 
		}
		
		infile.close();
		outfile.close();
		
		return null;
	}
	
	public static void deleteFile(String filename)
	{
		File file = new File(filename);
		file.delete();
	}
	
	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	public static void deleteDir(File dir) 
	{
	    if (dir.isDirectory()) 
	    {
	        String[] children = dir.list();
	        for (int i=0; i<children.length; i++) 
	        {
	            deleteDir(new File(dir, children[i]));
	        }
	    }

	    // The directory is now empty so delete it
	    dir.delete();
	}
	
	public static void copyFolder(File src, File dest)
	throws IOException{

	if(src.isDirectory()){

		//if directory not exists, create it
		if(!dest.exists()){
		   dest.mkdir();
		}

		//list all the directory contents
		String files[] = src.list();

		for (String file : files) {
		   //construct the src and dest file structure
		   File srcFile = new File(src, file);
		   File destFile = new File(dest, file);
		   //recursive copy
		   copyFolder(srcFile,destFile);
		}

	}else{
		//if file, then copy it
		//Use bytes stream to support all file types
		FileInputStream in = new FileInputStream(src);
	    FileOutputStream out = new FileOutputStream(dest); 

	        byte[] buffer = new byte[1024];

	        int length;
	        //copy the file content in bytes 
	        while ((length = in.read(buffer)) > 0){
	    	   out.write(buffer, 0, length);
	        }

	        in.close();
	        out.close();
	}
}
}