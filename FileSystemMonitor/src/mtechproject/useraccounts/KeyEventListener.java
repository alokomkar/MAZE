package mtechproject.useraccounts;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyEventListener implements KeyListener {

	public static void main(String args[]){
		KeyEventListener k = new KeyEventListener();
		for(;;){
			
		}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_PRINTSCREEN ) {  
            // Do whatever...
			System.out.println("Print Screen pressed..!!");
        }  
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
