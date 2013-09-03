package mtechproject.catcher;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.net.InetAddress;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mtechproject.mazedb.FloggerDB;

public class CopyListener extends Thread implements ClipboardOwner {
	Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
	String Username = null;
	String Filename = null;

	public void run() {
		Transferable trans = sysClip.getContents(this);
		regainOwnership(trans);
		//DataFlavor flavor = null;
		/*if(sysClip != null){
			if(sysClip.isDataFlavorAvailable(DataFlavor.imageFlavor)){
				try {System.out.println("Image flavor suppoted");
					Image i = (Image) trans.getTransferData(DataFlavor.imageFlavor);
					if(i != null){
						System.out.println("Print Screen usage");
						i = null;
					}
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
				
		}*/
		System.out.println("Listening to board...");
		//System.out.println("Clipboard contains:\n" + CopyListener.getClipboardContents() );
		while(true) {}
	}

	/*public void lostOwnership(Clipboard c, Transferable t) {
    Transferable contents = sysClip.getContents(this); //EXCEPTION
    processContents(contents);
    regainOwnership(contents);
  }*/

	public void lostOwnership(Clipboard c, Transferable t) {
		try {
			this.sleep(20);
		} catch(Exception e) {
			System.out.println("Exception: " + e);
		}
				Transferable contents = sysClip.getContents(this);
				processContents(contents);
				regainOwnership(contents);
	}
	
	void processContents(Transferable t) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		if(Filename != null){
			FloggerDB.InsertLog("Content Copy : "+CopyListener.getClipboardContents() +": From "+Filename, Username, dateFormat.format(date) );
		}
		else
		FloggerDB.InsertLog("Content Copy : "+CopyListener.getClipboardContents(), Username, dateFormat.format(date) );
		//System.out.println("Clipboard contains:\n" + CopyListener.getClipboardContents() );
		System.out.println("Processing: " + t);


	}

	

	void regainOwnership(Transferable t) {
		sysClip.setContents(t, this);
	}

	public static String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText =
			(contents != null) &&
			contents.isDataFlavorSupported(DataFlavor.stringFlavor)
			;
		if ( hasTransferableText ) {
			try {
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			}
			catch (UnsupportedFlavorException ex){
				//highly unlikely since we are using a standard DataFlavor
				System.out.println(ex);
				ex.printStackTrace();
			}
			catch (IOException ex) {
				System.out.println(ex);
				ex.printStackTrace();
			}
		}
		return result;
	}
	public CopyListener(String User){
		Username = User;
	}
	
	public void setFilename(String Filename){
		this.Filename = Filename;
	}
	public static void main(String[] args) {
		CopyListener b = new CopyListener(null);
		b.start();

	}
}