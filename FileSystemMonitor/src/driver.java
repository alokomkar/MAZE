import java.io.*;
import java.util.Scanner;
import java.util.Vector;
import Block.BlockContainer;
import Block.BlockEnvironment;

public class driver {

	protected final static int BlockSize = 4; // Size of Each Data/Parity Block
												// in bytes
	protected final static int DiskDriveCount = 4; // The Number of Hard Drives
	protected final static String PATH = "jpg"; // Defines the path for our
												// operations

	//protected final static int StaticSizeTotalBytes = 13;
	
	// Computes and returns the total size of our file in bytes
	public static int getFileSize(String parentPathName) {
		File myFile = new File("etc//" + parentPathName + "//InputFile."+PATH);
		return (int) myFile.length();
	}

	

	
	
	// Main function
	public static void main(String[] args) throws IOException {

		// Welcome Message
		System.out.println("************************************************");
		System.out.println("**                                            **");
		System.out.println("**Welcome to the Raid 5 Implementation Project**");
		System.out.println("**                                            **");
		System.out.println("************************************************\n");

		
		// We start by building our environment
		BlockEnvironment env = new BlockEnvironment(DiskDriveCount, PATH);
		for (int i = 0; i < DiskDriveCount; ++i) {
			env.AddBlock(new BlockContainer(DiskDriveCount, PATH));
		}
		
		Boolean alreadyLoaded = false;		
		
Boolean running = true;		
		
		while(running) {
		System.out.println("*********************Menu***********************");
		System.out.println("*****  Selection #1: Raid 5 Construction   *****");
		System.out.println("*****  Selection #2: Raid 5 Reconstruction *****");
		System.out.println("*****     Selection #3: Manipulate File    *****");
		System.out.println("*****     Selection #4: Manipulate Bit     *****");
		System.out.println("*****     Selection #5: Recovery           *****");
		System.out.println("*****     Selection #6: Save & Quit        *****");
		System.out.println("************************************************\n");

		System.out.print("Choice: ");
		Scanner in = new Scanner(System.in);
		int choice = in.nextInt();

		//1Boolean SessionRun = false;

		switch (choice) {
		
		case 1: //construction
			//SessionRun = true;
			
			System.out.println("Generating Mapping Structures...\n\n");
			
			try {
			env.fis(new FileInputStream("etc//"+ PATH + "//InputFile." + PATH));
			} catch(FileNotFoundException e) { System.out.println("Couldnt find input file...."); break;}
			
			env.execute(); //creates the map, and loads everything including parity into the BlockContainers
				
			env.setOperation("outputToSource"); //Sets the operation to output to source, which means sending to file(s).
			Vector<Thread> t = new Vector<Thread>();
			for (int i = 0; i < DiskDriveCount; ++i) {
				t.add(new Thread(env.getBlock(i))); //load our threads, with objects from our previously loaded environment
			}
			
			// Start our Threads
			for (int i = 0; i < t.size(); ++i) {
				t.get(i).start();
			}
			System.out.println("Sucessfully compiled list of OutputFile Documents with Parity Information");
			break;

		
			
		case 2: //re-construction
			if(alreadyLoaded == false) {
			System.out.println("Generating Mapping Structures...\n\n");
			System.out.println("Re-constucting file from OutputBlocks");
			//This part of the code will read from our output files and then generate our blocks back 
			  Vector<Thread> u = new Vector<Thread>();
			  for(int i = 0; i < DiskDriveCount; ++i) { 
				  u.add(new Thread(env.getBlock(i))); 
				  }			  
			  env.setOperation("sourceToInput"); //Start our Threads 
			  for(int i = 0; i < u.size(); ++i) { u.get(i).start(); }
			 
			  Thread.currentThread(); try { 
				  Thread.sleep(2000); 
				  } catch (InterruptedException e) {}
			}
			  env.reconstructFile(PATH);
			  
				  System.out.println("You must quit to finalize the output");
				  env.closeSession();
			  break;


		case 3: //file
			System.out.print("OutputFile:  ");
			int deleteOutputFileN = in.nextInt();
			if(deleteOutputFileN > 0 && deleteOutputFileN <= DiskDriveCount) {
				if(!env.manipulateFile(deleteOutputFileN)) {
				System.out.println("Error deleting file, please try to delete manually");	
				}
			} else {
				System.out.println("Selection not possible...");
			}
			break;

			
		case 4: //flip bit4
			
			alreadyLoaded = true;
			
			System.out.println("Loading structures in place, please be patient...");
			
			  Vector<Thread> u1 = new Vector<Thread>();
			  for(int i = 0; i < DiskDriveCount; ++i) { 
				  u1.add(new Thread(env.getBlock(i))); 
				  }			  
			  env.setOperation("sourceToInput"); //Start our Threads 
			  for(int i = 0; i < u1.size(); ++i) { u1.get(i).start(); }
			 
			  Thread.currentThread(); try { 
				  Thread.sleep(2000); 
				  } catch (InterruptedException e) {}
			
				 System.out.println("Structures load complete...");
			
					 				
			env.setFlip(2, 2);
		
			break;
			
		case 5: //recovery
		
			int counter = 0;
			File myFile = null;
			int FID = 0;
			for(int i = 1; i <= DiskDriveCount; ++i) {
				myFile = new File("etc//"+ PATH + "//OutputFile" + i);
				if(!myFile.exists()) { 
					++counter; 
					FID = i;
				}
			}


			
			if(counter == 1 && FID > 0) { 
				System.out.println("Detected error, missing OutputFile"+FID+", generating it now...");
				
				Vector<Thread> recovery = new Vector<Thread>();
				  for(int i = 0; i < DiskDriveCount; ++i) { 
					  if(!(env.getBlock(i).getBlockContainerID() == (FID))) {
						  recovery.add(new Thread(env.getBlock(i)));   
						  System.out.println("Loading OutputDisk " + (i+1) + "...");
					  } else {
						  
					  }
				  }			  
				  env.setOperation("sourceToInput"); //Start our Threads 
				  for(int i = 0; i < recovery.size(); ++i) { recovery.get(i).start(); }
				
				  System.out.println("Done generating data structures...");
				  System.out.println("Repairing desired Output File...");
				  
				  
				  
				  
				  Thread.currentThread(); try { 
					  Thread.sleep(3000); 
					  } catch (InterruptedException e) {}
				    
				  env.recoverBlockContainer(FID);
				  System.out.println("Output file repair complete, try rebuilding now...");
				
				  
			}					
			
			else if(counter > 1) {
				System.out.println("More than 1 file has been corrupted, cannot perform operation...");
				return;
			} else { //we must check the bits individually
				System.out.println("All drives present, no disk failure recovery necessary");
				System.out.println("Verifying integrity of disks");								
				env.recoverBits(PATH);
			}	
			//System.out.println("Recovery Successful...");
			
			break;
		case 6: //quit
			running = false;
			env.closeSession();
			System.out.println("Thank you for using the tool.");	
			break;
		}	
		
}

		
		
		
	} // end of main
} // end of driver