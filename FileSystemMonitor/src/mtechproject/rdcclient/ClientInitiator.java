
package mtechproject.rdcclient;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mtechproject.useraccounts.JavaFXLogin;
import mtechproject.useraccounts.UserAccountDB;

/**
 *
 * This class is responsible for connecting to the server
 * and starting ScreenSpyer and ServerDelegate classes
 */
public class ClientInitiator implements KeyListener{

    Socket socket = null;
    static String Ipaddress = null;
    static String mac = null;
    public static void main(String[] args){
        String ip = JOptionPane.showInputDialog("Please enter server IP");
        Ipaddress = ip.toString();
        boolean result = UserAccountDB.LookupIP(Ipaddress);
        
        System.out.println(ip);
        String port = JOptionPane.showInputDialog("Please enter server port");
        if(result == false){
        new ClientInitiator().initialize(ip, Integer.parseInt(port));
        }
        else System.exit(0);
    }
    
    public static String getIpaddress(){
    	return Ipaddress;
    }
    
    public static String getMACaddress(){
    	return mac;
    }
    
    
    public void initialize(String ip, int port ){

        Robot robot = null; //Used to capture the screen
        Rectangle rectangle = null; //Used to represent screen dimensions
       
        try {
            System.out.println("Connecting to server ..........");
            socket = new Socket(ip, port);
            System.out.println("Connection Established.");
            BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            mac = r.readLine();
            System.out.println(mac);
            boolean intrudermaclookup = UserAccountDB.LookupIntruderMAC(mac);
            if(intrudermaclookup){
            	System.exit(0);
            }
            //Get default screen device
            GraphicsEnvironment gEnv=GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev=gEnv.getDefaultScreenDevice();

            //Get screen dimensions
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            rectangle = new Rectangle(dim);

            //Prepare Robot object
            robot = new Robot(gDev);
            
            //robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
            //draw client gui
            drawGUI();
            //ScreenSpyer sends screenshots of the client screen
            new ScreenSpyer(socket,robot,rectangle);
            //ServerDelegate recieves server commands and execute them
            new ServerDelegate(socket,robot);
            JavaFXLogin.main(null);
           // keyPressed(KeyEvent e);
             } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (AWTException ex) {
                ex.printStackTrace();
        }
    }

    private void drawGUI() {
        JFrame frame = new JFrame("Remote Admin");
        JButton button= new JButton("Terminate");
        
        frame.setBounds(100,100,150,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        button.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
      );
      frame.setVisible(true);
    }

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		  
            if (arg0.getKeyCode() == KeyEvent.VK_PRINTSCREEN ) {  
                // Do whatever...  
            	Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents( null );

                try
                {
                    if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor))
                    {
                        Image image = (Image)t.getTransferData(DataFlavor.imageFlavor);
                        //return image;
                        if (image != null){
                        	image = null;
                        }
                    }
                }
                catch (Exception e) {}
            }  
          
   
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_PRINTSCREEN ) {  
            // Do whatever...  
        	Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents( null );

            try
            {
                if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor))
                {
                    Image image = (Image)t.getTransferData(DataFlavor.imageFlavor);
                    //return image;
                    if (image != null){
                    	image = null;
                    }
                }
            }
            catch (Exception e) {}
        }  
      

		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
