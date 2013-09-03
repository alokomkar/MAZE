package mtechproject.useraccounts;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.*;

import mtechproject.FCIntegratedFileCreator.FCFileAttributeSetter;
import mtechproject.datarecovery.FileDifferenceCheck;
import mtechproject.datarecovery.MD5Checksum;
import mtechproject.datarecovery.RaidRetrive;
import mtechproject.filesysmonitor.CreateExcelFile;
import mtechproject.mazedb.FloggerDB;
import mtechproject.mazedb.GetFileAttr;

import com.sun.glass.events.KeyEvent;

public class FullScreenJFrame extends JFrame implements KeyListener
{
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public FullScreenJFrame( String title ,final JFileChooser c, final String User, final boolean sandtrap)
	{
		super(title);

		this.setFocusTraversalKeysEnabled(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c.setFocusTraversalKeysEnabled(false);
		this.addKeyListener((KeyListener) this);
		setUndecorated(true);

		/*ImageIcon image1 = new ImageIcon("C:/Users/Alok Omkar/workspace/FileSystemMonitor/template.jpg");
		JLabel label = new JLabel("", image1, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add( label, BorderLayout.CENTER );
		panel.setFocusable(false);
		panel.setOpaque(true);
		getContentPane().add(panel);*/

		getContentPane().setBackground(new Color(240,245,250));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		//setBounds(0,0,120, 120);
		getContentPane().add(new JLabel("MAZE Cloud Server"), BorderLayout.NORTH);
		getContentPane().add(c);

		JLabel l_name,l_pass;
		final JTextField t_name;
		final JPasswordField t_pass;     //A special JTextField but hides input text


		l_name=new JLabel("Username");
		l_pass=new JLabel("Password");
		t_name=new JTextField(10);
		t_pass=new JPasswordField(16);

		getContentPane().setLayout(new FlowLayout());
		getContentPane().add(l_name);
		getContentPane().add(t_name);

		getContentPane().add(l_pass);
		getContentPane().add(t_pass);

		l_name.setVisible(true);
		l_pass.setVisible(true);
		t_name.setVisible(true);
		t_pass.setVisible(true);

		final SearchAccount sa = new SearchAccount();

		//getContentPane().add(panel);

		JButton closeButton = new JButton("Close");
		closeButton.setVisible(true);
		closeButton.addActionListener( new ActionListener()
		{	int tryagain = 1;
		public void actionPerformed( ActionEvent ae )
		{
			try {

				//System.out.println(t_pass.getText());
				//while(tryagain != 4){
				if(sa.searchbyexitPassword(t_name.getText(),t_pass.getText())){
					//tryagain++;
					//break label;
					System.exit(0);
				}
				else if(tryagain == 3){

					//System.out.println("Close button Pressed");
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date2 = new Date();
					FloggerDB.InsertLog("Session Logout - No Exit Key", User ,dateFormat.format(date2) );
					UserAccountDB.LockAccount(User);
					System.out.println("No Exit Key, Reverting back changes made..!!");
					FullScreenJFrame.this.setVisible(false);
					System.exit(0);

				}
				tryagain++;

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//swing_sample sample=new swing_sample();
			//FullScreenJFrame.this.add(sample);
		}
		});


		JButton RetrieveButton = new JButton("Retrieve");
		RetrieveButton.setVisible(true);
		RetrieveButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent ae )
			{
				//JFileChooser j = new JFileChooser();
				//j = c;
				JavaFXLogin.disableNewFolderButton(c);
				c.setMultiSelectionEnabled(true);
				getContentPane().add(c);
				c.showOpenDialog(null);
				//File chosenfile = c.getSelectedFile();
				File[] files = c.getSelectedFiles();
				boolean check = false;
				if(!files.equals(null) && sandtrap){
					int i = 0;
					while(i != files.length && files.length >= 3){
						File chosenfile = files[i];
						if(FCFileAttributeSetter.checkfcattrs(chosenfile.getName(), User)){
							check = true;
						}else {
							check = false;
							JOptionPane.showMessageDialog(null, "Access Restricted");
						}
						i++;
					}
				}

				if(check == true && sandtrap){
					if(!files.equals(null)){
						int i = 0;
						try {
							String User = JOptionPane.showInputDialog("Enter your Username:");
							String password = JOptionPane.showInputDialog("Exit Password for Retrieval:");
							//boolean verified = false;
							int length = 3;
							int retrieved = 0;
							if(sa.searchbyexitPassword(User,password)){
								if(FCFileAttributeSetter.RetrieveUnlocker(User)){
									FCFileAttributeSetter.SetLocked(User);
									while(i != files.length){
										File chosenfile = files[i];
										i++;
										//method to check the chosen fc file is owned by user
										if(!chosenfile.toString().isEmpty()){
											//chosenfile = null;

											if(FCFileAttributeSetter.checkfcattrs(chosenfile.getName(), User)){
												//verified = true;
												retrieved++;

											}
											else{
												retrieved--;

											}
										}
										else{
											//Do nothing
											JOptionPane.showMessageDialog(null, "No File Specified");
										}

									}//while
									if(retrieved >= length && files.length >= length){
										UserAccountDB.UnLockAccount(User);
										JOptionPane.showMessageDialog(null,"Authentication Success: Please relogin to acces files");

									}else{
										JOptionPane.showMessageDialog(null,"Authentication Failed: Exiting Session, Contact Admin");
									}


								}else{
									JOptionPane.showMessageDialog(null,"One time Authentication Utilized: Exiting Session, Contact Admin");
								}
							}
							else JOptionPane.showMessageDialog(null,"Wrong Username or Exit Key.");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}



					}//if(!files.equals(null)){


				}else if(sandtrap == false){
					if(!files.equals(null)){
						int i = 0;
						while(i != files.length){

							File chosenfile = files[i];
							i++;
							//Put a method to check the chosen file is owned by user
							if(!chosenfile.toString().isEmpty() && User.equals(GetFileAttr.GetFileAttrs(chosenfile.getName(),User))){
								try {
									String User = JOptionPane.showInputDialog("Enter your Username:");
									String password = JOptionPane.showInputDialog("Exit Password for Retrieval:");
									if(sa.searchbyexitPassword(User,password)){
										RaidRetrive retrival = new RaidRetrive(chosenfile.getAbsolutePath());
										retrival.retrieve();
										retrival.merge();
										JOptionPane.showMessageDialog(null, "Retrieval Success");	

									}
									else JOptionPane.showMessageDialog(null,"Wrong Username or Exit Key.");

									//if(chosenfile)

								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								chosenfile = null;
							}
							else if(!User.equals(GetFileAttr.GetFileAttrs(chosenfile.getName(),User))){
								//Do nothing
								JOptionPane.showMessageDialog(null, "Access Restricted for "+chosenfile.getName()+" to "+ User);
							}
							else{
								JOptionPane.showMessageDialog(null, "No File Specified");
							}

						}//while


					}//if(!files.equals(null)){

				}
			}
		});

		JButton FileCheckButton = new JButton("File Check");
		FileCheckButton.setVisible(true);
		FileCheckButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent ae )
			{
				JavaFXLogin.disableNewFolderButton(c);
				getContentPane().add(c);
				c.showOpenDialog(null);
				File chosenfile = c.getSelectedFile();
				if(!chosenfile.equals(null)){
					if(!chosenfile.toString().isEmpty()){
						try {
							String User = JOptionPane.showInputDialog("Enter your Username:");
							String password = JOptionPane.showInputDialog("Exit Password for Retrieval:");
							if(sa.searchbyexitPassword(User,password)){

								MD5Checksum.compareChecksum(chosenfile.getAbsolutePath());
								//FileDifferenceCheck f = new FileDifferenceCheck();
								FileDifferenceCheck.callmain(chosenfile.getAbsolutePath().toString());
								JOptionPane.showMessageDialog(null, "Check Completed - View log.");
								CreateExcelFile.InsertChangeLogtoExcel();
							}
							else JOptionPane.showMessageDialog(null,"Wrong Username or Exit Key.");
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						chosenfile = null;
					}
					else{
						//Do nothing
						JOptionPane.showMessageDialog(null, "No File Specified");
					}
				}

			}
		});


		getContentPane().add(closeButton, BorderLayout.EAST);
		getContentPane().add(RetrieveButton,BorderLayout.WEST);
		getContentPane().add(FileCheckButton,BorderLayout.NORTH);
		//this.show();
		//getContentPane().show(true);
	}

	public FullScreenJFrame( String title)
	{
		super(title);

		this.setFocusTraversalKeysEnabled(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//c.setFocusTraversalKeysEnabled(false);

		setUndecorated(true);
		/*ImageIcon image1 = new ImageIcon("C:/Users/Alok Omkar/workspace/FileSystemMonitor/template.jpg");
		JLabel label = new JLabel("", image1, JLabel.CENTER);
		JPanel panel = new JPanel(new BorderLayout());
		panel.setFocusTraversalKeysEnabled(false);
		panel.setOpaque(false);
		panel.add( label, BorderLayout.CENTER );


		getContentPane().add(panel);*/

		//getContentPane().setBackground(new Color(255, 255, 240));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		//setBounds(0,0,120, 120);
		getContentPane().add(new JLabel("MAZE Cloud Server"), BorderLayout.NORTH);
		//getContentPane().add(c);

		//getContentPane().add(panel);
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent ae )
			{
				System.out.println("Close button Pressed");
				FullScreenJFrame.this.setVisible(false);
				System.exit(0);
			}
		});
		final JFileChooser j = new JFileChooser();

		JButton RetrieveButton = new JButton("Retrieve");
		RetrieveButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent ae )
			{
				//j = c;
				JavaFXLogin.disableNewFolderButton(j);
				getContentPane().add(j);
				File chosenfile = j.getSelectedFile();
			}
		});

		getContentPane().add(closeButton, BorderLayout.EAST);
		getContentPane().add(RetrieveButton,BorderLayout.WEST);
		//JavaFXLogin.main(null);


		//this.show();
		//getContentPane().show(true);
	}

	public static void main( String[] args )
	{
		FullScreenJFrame frame = new FullScreenJFrame("");
		frame.setVisible(true);
	}

	@Override
	public void keyPressed(java.awt.event.KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_PRINTSCREEN){
			System.out.println("PrintScreen Pressed");
		}
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_PRINTSCREEN){
			System.out.println("PrintScreen Released");
		}

	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
