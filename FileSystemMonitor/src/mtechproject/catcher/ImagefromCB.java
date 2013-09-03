package mtechproject.catcher;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;

public class ImagefromCB implements Runnable
{
// If an image is on the system clipboard, this method returns it;
// otherwise it returns null.
public static Image getImageFromClipboard()
{

    Clipboard systemClipboard = (Clipboard) AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() 
        {
            Clipboard tempClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         return tempClipboard;
        }
    });

    // get the contents on the clipboard in a 
    // Transferable object
    Transferable clipboardContents = systemClipboard.getContents(null);

    // check if contents are empty, if so, return null
    if (clipboardContents == null)
        return null;
    else
        try
        {
            // make sure content on clipboard is 
            // falls under a format supported by the 
            // imageFlavor Flavor
            if (clipboardContents.isDataFlavorSupported(DataFlavor.imageFlavor))
            {
                // convert the Transferable object
                // to an Image object
                Image image = (Image) clipboardContents.getTransferData(DataFlavor.imageFlavor);
                return image;
            }
        } catch (UnsupportedFlavorException ufe)
        {
            ufe.printStackTrace();
        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    return null;
}

public static Image getCBImage()
{
    //System.out.println("Copying image from system clipboard.");
    Image image = getImageFromClipboard();
    if (image != null)
    {
        //return image;
    	System.out.println("Image on Clipboard");
    	return null;
    } else
    {
        System.out.println("No Image found on Clipboard");
        return null;
    }
}

public static void main(String args[]){
	ImagefromCB i = new ImagefromCB();
	
}

@Override
public void run() {
	// TODO Auto-generated method stub
	for(;;){
		Image image = getCBImage();	
	}
	
	
}
}