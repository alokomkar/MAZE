package mtechproject.datarecovery;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import mtechproject.datarecovery.XFile;

/**
 * @author Adithya & Alok
 * @brief The class contains the application for the FSRD RAID split .
 */
public class RaidSplit implements RAIDConstants{
	
	private FileInputStream  file;
	private FileOutputStream ofiles[];
	private XFile 			 directory;
	private File filename1;
	
	private FileOutputStream CreateTempFile(String filename)
	{
		FileOutputStream file = null;
		try
		{
			 file = new FileOutputStream(TEMP_DIR_NAME + "\\" + filename);
		} 
		catch (Exception e)
		{
		}
		return file;
	}
	
	/**
	 * 
	 * @param dirname the absolute path name of the directory
	 */
	private void makeDir(String dirname)
	{
		directory = new XFile(dirname);
		if (!directory.exists())
			directory.mkdir();
		else{
			directory.cleanDir(false);
		}
	}

	
	private void Initialize(String filename) throws FileNotFoundException
	{
		//Create a input file handle.
		file = new FileInputStream(filename);
		filename1 = new File(filename);
		
		//Create temporary directory if the directory does not exist.
		makeDir(TEMP_DIR_NAME);

		ofiles = new FileOutputStream[NUM_BAK_FILES];
		int idx = NUM_BAK_FILES;
		while (idx > 0)
			ofiles[--idx] = this.CreateTempFile(filename1.getName() + idx);
	}
	
	public RaidSplit(String filename) throws FileNotFoundException
	{
		Initialize(filename);
	}
	
	/**
	 * 
	 * @param file The file that is being read
	 * @param ofiles The output steam to where the files are read
	 * @throws IOException when unable to read or write 
	 * @throws IndexOutOfBoundsException
	 */
	
	void split2Files(FileInputStream file, FileOutputStream ofiles[]) throws IOException, IndexOutOfBoundsException
	{
		int idx;
		int size = 0;
		byte buffer[] = new byte[DISK_BLOCK_SIZE];
		for (idx = 0 ; size != -1 ; idx++)
		{
			size = file.read(buffer, 0, DISK_BLOCK_SIZE);
			if (size < 0 || size > DISK_BLOCK_SIZE)	{
				//System.err.println("size = " + size + " idx = " + idx);
				break;
			} else {
				//System.out.println("size = " + size + " idx = " + idx);
				ofiles[idx % NUM_BAK_FILES].write(buffer, 0, size);  
			}
		}

		for (size = 0 ; size < NUM_BAK_FILES ; size++ )
			 ofiles[size].close();
	}
	
	void distributefiles()
	{
		int idx=0;
		XFile xfile = new XFile(TEMP_DIR_NAME);
		String filename = new String();
		try
		{
			for (idx = 0 ; idx < NUM_BAK_FILES ; idx++)
			{
				makeDir(TEMP_DIR_NAME+"\\BackupDir" + idx);
			}
		
			for(idx = 0 ; idx < NUM_BAK_FILES ; idx++)
			{
				xfile.copyFile(TEMP_DIR_NAME+"\\"+filename1.getName()+idx,BACK_UP_DIR+idx+"\\"+filename1.getName()+idx);
				
				xfile.copyFile(TEMP_DIR_NAME+"\\"+filename1.getName()+((idx+1)%NUM_BAK_FILES),BACK_UP_DIR+idx+"\\"+filename1.getName()+((idx+1)%NUM_BAK_FILES));
			}
			
			for(idx = 0 ; idx < NUM_BAK_FILES ; idx++)
			{
				
				filename = TEMP_DIR_NAME+"\\"+filename1.getName()+idx;
				File file = new File(filename);
				file.delete();
			}
			
		}	
		catch(IOException ex)
		{
			System.err.println(ex.getCause());
		}
		// Creates NUM_BAK_FILES directories
	}
	
	private void mirror()
	{
		XFile srcFolder = new XFile("c:\\TempDir");
    	XFile destFolder = new XFile("e:\\TempDir");
    	
    	if(!srcFolder.exists())
    	{
    		 
            System.out.println("Directory does not exist.");
            //just exit
            System.exit(0);
  
         }
    	else
    	{
  
            try
            {
            	XFile.copyFolder(srcFolder,destFolder);
            }
            catch(IOException e)
            {
            	e.printStackTrace();
            	//error, just exit
                 System.exit(0);
            }
         }
	}
	
	void split()
	{
		try
		{
			System.out.println("Splitting in progress ..............");
			split2Files(file, ofiles);
			System.out.println("Files are split successfully");
			System.out.println("Redundent Distribution in progress............");
			distributefiles();
			System.out.println("Redundent distribution success");
			System.out.println("Mirrorring in progress........................");
			mirror();
			System.out.println("Mirrorring done successfully");
			
		} 
		catch (IOException e)
		{
		}
	}
	
	

	/**
	 * @param args Command line arguments for the application.
	 */
	public static void main(String[] args)
	{
		RaidSplit raidSplit = null;
		try 
		{
			raidSplit = new RaidSplit("E:\\DVFS\\EmityCorp_DFS\\Apoorva_Folder1\\Apoorva details1.doc");
		} 
		catch ( FileNotFoundException f )		
		{
			return;
		}
		raidSplit.split();
	}
}