/**
 * @author Adithya & Alok
 * @brief The class contains the application for the RAID FSRD file 
 * 		  retrival Alogorithm.
 */

package mtechproject.datarecovery;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import mtechproject.catcher.DirectoryRestrictedFileSystemView;
import mtechproject.datarecovery.XFile;

public class RaidRetrive implements RAIDConstants 
{
	String Filename;
	
	public RaidRetrive(String name){
		this.Filename = name;
	}
	
	public boolean doesitexist(String dirname) throws FileNotFoundException
	{
		XFile directory = new XFile(dirname);
		
		if (directory.exists()) return true;
		else return false;
	}
	
	public void retrieve()
	{	
		File fname = new File(Filename);
		
		int idx;
		for (idx=0; idx < NUM_BAK_FILES; idx++)
		{
			try{
			if(doesitexist(BACK_UP_DIR+idx))
			{
				XFile xfile = new XFile(BACK_UP_DIR+idx);

				//System.out.println(BACK_UP_DIR+idx+"\\"+fname.getName()+idx+ " to " +TEMP_DIR_NAME+"\\"+fname.getName()+idx);
				xfile.copyFile(BACK_UP_DIR+idx+"\\"+fname.getName()+idx,TEMP_DIR_NAME+"\\"+fname.getName()+idx);
				
				//System.out.println(BACK_UP_DIR+(idx)%NUM_BAK_FILES+"\\"+fname.getName()+(idx+1)%NUM_BAK_FILES+" to "+TEMP_DIR_NAME+"\\"+fname.getName()+(idx+1)%NUM_BAK_FILES);
				xfile.copyFile(BACK_UP_DIR+(idx)%NUM_BAK_FILES+"\\"+fname.getName()+(idx+1)%NUM_BAK_FILES,TEMP_DIR_NAME+"\\"+fname.getName()+(idx+1)%NUM_BAK_FILES);
			}
			}catch(IOException ex)
			{
				System.err.println("File not found");
			}
		}
	}
	
	
	public void merge() throws FileNotFoundException, IOException
	{
		File fname = new File(Filename);
		byte buffer[] = new byte[DISK_BLOCK_SIZE];
		
		FileInputStream file0 = new FileInputStream(TEMP_DIR_NAME+"\\"+fname.getName()+"0");
		FileInputStream file1 = new FileInputStream(TEMP_DIR_NAME+"\\"+fname.getName()+"1");
		FileInputStream file2 = new FileInputStream(TEMP_DIR_NAME+"\\"+fname.getName()+"2");
		FileInputStream file3 = new FileInputStream(TEMP_DIR_NAME+"\\"+fname.getName()+"3");
		
		FileOutputStream ofile = new FileOutputStream(fname.getAbsolutePath());
		
		while(true)
		{	
			if ((file0.read(buffer)) < 0) break;
			else ofile.write(buffer);
			
			if ((file1.read(buffer)) < 0) break;
			else ofile.write(buffer);
			
			if ((file2.read(buffer)) < 0) break;
			else ofile.write(buffer);
			
			if ((file3.read(buffer)) < 0) break;
			else ofile.write(buffer);
		}
		ofile.close();
		file0.close();
		file1.close();
		file2.close();
		file3.close();		
	}
	
	public void cleanUp()
	{
		XFile Dfile = new XFile(TEMP_DIR_NAME);
		XFile.deleteDir(Dfile);
		
	}
	
	public static void main(String[] args) throws IOException, FileNotFoundException
	{	
		FileSystemView fsv = new DirectoryRestrictedFileSystemView(new File("E:\\DVFS"));
		JFileChooser chooser = new JFileChooser(fsv);
		
		chooser.showOpenDialog(null);
		//RaidRetrive retrival = new RaidRetrive("C:\\Insurance Details1.doc");
		RaidRetrive retrival = new RaidRetrive(chooser.getSelectedFile().getAbsolutePath());
		retrival.retrieve();

		retrival.merge();
		//retrival.cleanUp();
		System.out.println("The file is successfully retrieved");
	}

}