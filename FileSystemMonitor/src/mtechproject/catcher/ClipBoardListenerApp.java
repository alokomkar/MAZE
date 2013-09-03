package mtechproject.catcher;

import java.awt.Toolkit;  
import java.awt.datatransfer.*;  
import java.io.IOException;  
  
class ClipboardListener extends Thread implements ClipboardOwner {
	
    Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();  
      
    public void run(){  
        Transferable selection = systemClipboard.getContents(this);  
        gainOwnership(selection);  
        while (true) {}
    }  
      
    public void gainOwnership(Transferable t){ 
    	try {this.sleep(100);} 
    	catch (InterruptedException e) {}
        systemClipboard.setContents(t, this);  
    }  
      
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        /*try {System.out.println((String) clipboard.getData(DataFlavor.stringFlavor));} 
        catch (UnsupportedFlavorException e) {} 
        catch (IOException e) {}
        gainOwnership(contents);*/  
    }  
}  

public class ClipBoardListenerApp {

	public static void main(String[] args){
		ClipboardListener listener = new ClipboardListener();
		listener.start();}
}

