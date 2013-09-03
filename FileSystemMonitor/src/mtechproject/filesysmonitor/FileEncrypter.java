package mtechproject.filesysmonitor;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class FileEncrypter{
    
    private String algo;
    private File file;

    public FileEncrypter(String algo,String path) {
    this.algo=algo; //setting algo
    this.file=new File(path); //settong file
    }
    
     public void encrypt() throws Exception{
        //opening streams
         FileInputStream fis =new FileInputStream(file);
         file=new File(file.getAbsolutePath()+".enc");
         FileOutputStream fos =new FileOutputStream(file);
         //generating key
         byte k[] = "HignDlPs".getBytes();   
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher encrypt =  Cipher.getInstance(algo);  
         encrypt.init(Cipher.ENCRYPT_MODE, key);  
         CipherOutputStream cout=new CipherOutputStream(fos, encrypt);
         
         byte[] buf = new byte[1024];
         int read;
         while((read=fis.read(buf))!=-1)  //reading data
             cout.write(buf,0,read);  //writing encrypted data
         //closing streams
         fis.close();
         cout.flush();
         cout.close();
     }
     
     public void decrypt() throws Exception{
        //opening streams
         FileInputStream fis =new FileInputStream(file);
         file=new File(file.getAbsolutePath()+".dec");
         FileOutputStream fos =new FileOutputStream(file);               
         //generating same key
         byte k[] = "HignDlPs".getBytes();   
         SecretKeySpec key = new SecretKeySpec(k,algo.split("/")[0]);  
         //creating and initialising cipher and cipher streams
         Cipher decrypt =  Cipher.getInstance(algo);  
         decrypt.init(Cipher.DECRYPT_MODE, key);  
         CipherInputStream cin=new CipherInputStream(fis, decrypt);
              
         byte[] buf = new byte[1024];
         int read=0;
         while((read=cin.read(buf))!=-1)  //reading encrypted data
              fos.write(buf,0,read);  //writing decrypted data
         //closing streams
         cin.close();
         fos.flush();
         fos.close();
     }
     
     public static void main (String[] args)throws Exception {
    	 long start = System.nanoTime(); // requires java 1.5
    	// Segment to monitor
    	 new FileEncrypter("DES/ECB/PKCS5Padding","c:\\sample.txt").encrypt();
    	 double elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
    	 long start1 = System.nanoTime();
         new FileEncrypter("DES/ECB/PKCS5Padding","c:\\sample.txt.enc").decrypt();
         double elapsedTimeInSec2 = (System.nanoTime() - start1) * 1.0e-9;
         
         System.out.println("Encryption Time:"+elapsedTimeInSec);
         System.out.println("Decryption Time:"+elapsedTimeInSec2);
         File f = new File("c:\\sample.txt");
         start = System.nanoTime();
        // Desktop.getDesktop().open(new File("c:\\sample.txt"));
         boolean exists = f.exists();
         
         elapsedTimeInSec = (System.nanoTime() - start) * 1.0e-9;
         System.out.println("FileAccessTime Time:"+elapsedTimeInSec);
}
}