package mtechproject.catcher;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PrintScrnListener implements KeyListener {
    public void keyPressed( KeyEvent e ) {
        if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN ) {
            // Do whatever...
        }
    }
   @Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	   if (KeyEvent.VK_PRINTSCREEN == arg0.getKeyCode())  
		      Toolkit.getDefaultToolkit().getSystemClipboard().  
		        setContents(new StringSelection(""), null);  
		  }  
		
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
