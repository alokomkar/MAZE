package mtechproject.FCIntegratedFileCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import mtechproject.datarecovery.MD5Checksum;

class Test {
     public static void main(String args[]) {
          try {
               //RandomAccessFile f = new RandomAccessFile("t.doc", "rw");
               //f.setLength(1024 * 1024 * 1024); // 1 GB
               //f.setLength(1024 * 1024);
        	  String filepath = "E:\\DVFS\\JBerger_DFS\\sample\\sample.doc";
        	  RandomAccessFile f = new RandomAccessFile(filepath.replaceFirst("E", "C"), "rw");
	          f.setLength(new File(filepath).length());
				//walk("E:/DVFS");
               
          } catch (Exception e) {
               System.err.println(e);
          }
     }
     
     public static void walk( String path ) {

 		File root = new File( path );
 		File[] list = root.listFiles();
 		BasicFileAttributes attrs = null;

 		for ( File f : list ) {
 			if ( f.isDirectory() ) {
 				walk( f.getAbsolutePath() );
 				System.out.println( "Dir:" + f.getName() );
 				
 			}
 			else {
 				
 				RandomAccessFile f1;
				try {
					f1 = new RandomAccessFile(f.getAbsolutePath().replaceFirst("E", "C"), "rw");
					 f1.setLength(f.length());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
 		         
 					
 				
 			}
 		}	
 		
 		
 	}
 	

     
     
}

