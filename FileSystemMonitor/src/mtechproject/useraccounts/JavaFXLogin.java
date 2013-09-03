package mtechproject.useraccounts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import mtechproject.catcher.Catcher1;
import mtechproject.catcher.Catcher2sandtrap;
import mtechproject.catcher.ChrootFileSystemView;
import mtechproject.catcher.CopyListener;
import mtechproject.catcher.DirectoryRestrictedFileSystemView;
import mtechproject.filesysmonitor.CreateExcelFile;
import mtechproject.filesysmonitor.DirectoryWatch;
import mtechproject.mazedb.DecoyFileDB;
import mtechproject.mazedb.FloggerDB;
import mtechproject.mazedb.GetFileAttr;
import mtechproject.rdcclient.ClientInitiator;


public class JavaFXLogin extends Application implements Database_Credentials {
	
	
	//Parameters for logging into MAZE Server - Username, Password and Session Key
	String checkUser, checkPw, checkSk;
	
	//A condition to keep track of valid authentication procedure
	boolean proceed = false;
	
	//A condition to keep track of  unauthorised procedure
	boolean unauth = false;
	
	//To keep track of number of login attempts
	int tryagain = 0;
	
	//Information of the User machine
	static String Ipaddress = null;
	static String mac = null;
	
	//Class used for searching User account
	SearchAccount sa = new SearchAccount();
	
	public static void main(String[] args) {
		launch(args);
	}

	//Method for disabling the jchooser capability of creating a new folder
	public static void disableNewFolderButton(Container c) {
		int len = c.getComponentCount();
		for (int i = 0; i < len; i++) {
			Component comp = c.getComponent(i);
			if (comp instanceof JButton) {
				JButton b = (JButton) comp;
				Icon icon = b.getIcon();
				if (icon != null
						&& icon == UIManager.getIcon("FileChooser.newFolderIcon"))
					b.setEnabled(false);
			} else if (comp instanceof Container) {
				disableNewFolderButton((Container) comp);
			}
		}
	}

	//For starting Main login screen
	@Override
	public void start(final Stage primaryStage) {

		primaryStage.setTitle("MAZE Storage Server Login");
		primaryStage.setFullScreen(true);
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(10,50,50,50));

		//Adding HBox
		HBox hb = new HBox();
		hb.setPadding(new Insets(20,20,20,30));

		//Adding GridPane
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20,20,20,20));
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		//Implementing Nodes for GridPane
		Label lblUserName = new Label("Username");
		final TextField txtUserName = new TextField();
		
		Label lblPassword = new Label("Password");
		final PasswordField pf = new PasswordField();
		
		Label lblSessKey = new Label("Session Key");
		final PasswordField sk = new PasswordField();

		Button btnLogin = new Button("Login");
		final Label lblMessage = new Label();

		Button btnFrgtPwd = new Button("Forgot Password");
		final Label lblMessage1 = new Label();

		Button btnNewUser = new Button("Create Account");
		final Label lblMessage2 = new Label();


		//Adding Nodes to GridPane layout
		gridPane.add(lblUserName, 90, 50);
		gridPane.add(txtUserName, 91, 50);
		gridPane.add(lblPassword, 90, 51);
		gridPane.add(pf, 91, 51);
		gridPane.add(lblSessKey, 90, 52);
		gridPane.add(sk, 91, 52);
		gridPane.add(btnLogin, 92, 50);
		gridPane.add(lblMessage, 94, 52);
		gridPane.add(btnFrgtPwd, 92, 51);
		gridPane.add(lblMessage1, 95, 52);
		gridPane.add(btnNewUser, 92, 52);
		gridPane.add(lblMessage2, 96, 52);
		//gridPane.add(clock, 97, 53);

		//Reflection for gridPane
		Reflection r = new Reflection();
		r.setFraction(0.7f);
		gridPane.setEffect(r);

		//DropShadow effect
		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(5);
		dropShadow.setOffsetY(5);

		//Adding text and DropShadow effect to it
		Text text = new Text("MAZE Storage Server Login");
		text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
		text.setEffect(dropShadow);

		//Adding text to HBox
		hb.getChildren().add(text);

		//Add ID's to Nodes
		bp.setId("bp");
		gridPane.setId("root");
		btnLogin.setId("btnLogin");
		text.setId("text");
		btnFrgtPwd.setId("btnFrgrPwd");
		btnNewUser.setId("btnNewUser");

		//Action for btnLogin
		btnLogin.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings("deprecation")
			public void handle(ActionEvent event) {
				
				checkUser = txtUserName.getText().toString();
				checkPw = pf.getText().toString();
				checkSk = sk.getText().toString();
				
				//CopyListener class to check for content copy from files
				CopyListener cp = new CopyListener(checkUser);
				//MAC Address of the User/Intruder Machine
				String mac = ClientInitiator.getMACaddress();
				
				unsuccess:
					//Check if only username and password have been entered and login is pressed
					if(!checkUser.isEmpty() && !checkPw.isEmpty() && checkSk.isEmpty() && !UserAccountDB.LookupMAC(checkUser,mac)){
						boolean proceedup = false;
						System.out.println("No Session key");
						try {
							if(sa.searchbyUserPass(checkUser,checkPw)){
								lblMessage1.setText("Read Only login");
								lblMessage1.setTextFill(Color.BLUEVIOLET);
								primaryStage.close();
								DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								Date date = new Date();
								if(ClientInitiator.getIpaddress() != null && ClientInitiator.getMACaddress() != null){
									//Logging of Account Information for read only session
									UserAccountDB.InsertIP(ClientInitiator.getIpaddress());
									FloggerDB.InsertLog(" READ ONLY LOGIN FROM IP:"+ClientInitiator.getIpaddress()+" MAC :"+ClientInitiator.getMACaddress(), checkUser,dateFormat.format(date) );
								}
								else{
									try {
										//Login from local host.
										FloggerDB.InsertLog(" READ ONLY LOGIN FROM Localhost:"+InetAddress.getLocalHost().getHostName(), checkUser,dateFormat.format(date) );
									} catch (UnknownHostException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}	
								}

								proceedup = true;

								if(proceedup == true && unauth == false){

									try {
										//To Set Windows Look and Feel for the FileGUI opener
										UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
									} 
									catch (Exception e) {
										// handle exception
									}
									//Initiate Sandtrap Environment for assessing Intruder Activities.
									FileSystemView fsv = new DirectoryRestrictedFileSystemView(new File("C:\\DVFS"));
									JFileChooser chooser = new JFileChooser(fsv);
									chooser.setCurrentDirectory(new File("C:\\DVFS"));
									cp.start();
									boolean sandtrap = true;
									FullScreenJFrame frame = null;
									frame = new FullScreenJFrame("MAZE Cloud Server File System",chooser,checkUser,sandtrap);
									frame.setVisible(true);

									chooser.addKeyListener(new KeyAdapter()
									{
										public void keyReleased(KeyEvent e)
										{
											if (KeyEvent.VK_PRINTSCREEN == e.getKeyCode())
												Toolkit.getDefaultToolkit().getSystemClipboard().
												setContents(new StringSelection(""), null);
										}  
									});
									again:
										for(;;){

											disableNewFolderButton(chooser);
											int result = chooser.showOpenDialog(null);

											switch(result){

											case JFileChooser.APPROVE_OPTION:
												// Approve (Open or Save) was clicked
												File chosenfile = chooser.getSelectedFile();
												cp.setFilename(" SandTrap File : "+chosenfile.getName());
												String filename = chosenfile.getAbsolutePath();
												if (Desktop.isDesktopSupported()) {
													try {

														File myFile = new File(filename);
														Desktop.getDesktop().open(myFile);

														Catcher2sandtrap c = new Catcher2sandtrap(checkUser,chosenfile);

													} catch (IOException ex) {
														// no application registered for PDFs
													}
												}

												//break;
												continue again;
											case JFileChooser.CANCEL_OPTION:
												Date date2 = new Date();
												FloggerDB.InsertLog("READ ONLY SESSION LOGOUT", checkUser,dateFormat.format(date2) );
												break again;
											case JFileChooser.
											ERROR_OPTION:
												// The selection process did not complete successfully
												System.out.println("The selection process did not complete successfully, Try again");
												continue again;
											}//end Switch
										}//End for
								}//end if proceedup

							}
							else{
								lblMessage1.setText("Please Enter UserName and Password");
								lblMessage1.setTextFill(Color.RED);
								txtUserName.setText("");
								pf.setText("");

								break unsuccess;
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						txtUserName.setText("");
						pf.setText("");
					}
				
				
				try {
					String user = checkUser;

					//Three Attempts allowed for user to login, else the account is locked.
					if(tryagain == 2){
						//Call for method to enforce account locking
						UserAccountDB.LockAccount(checkUser);
						System.exit(0);
					}
					if(tryagain != 2 && user.equals(checkUser)){
						tryagain = tryagain + 1;
					}
					
					//Username, password and session key are entered
					if(sa.SearchByName(checkUser,checkPw,checkSk)){

						primaryStage.close();

						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						if(ClientInitiator.getIpaddress() != null){
							UserAccountDB.InsertMAC(checkUser, ClientInitiator.getMACaddress(), ClientInitiator.getIpaddress());
							FloggerDB.InsertLog("Session Login : "+ClientInitiator.getIpaddress()+" MAC : "+ClientInitiator.getMACaddress(), checkUser,dateFormat.format(date) );	
						}
						else{
							try {
								FloggerDB.InsertLog("Session Login : "+InetAddress.getLocalHost().getHostName(), checkUser,dateFormat.format(date) );
							} catch (UnknownHostException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						boolean proceed = true;

						if(proceed == true && unauth == false){

							try {
								//To Set Windows Look and Feel for the FileGUI opener
								UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
							} 
							catch (Exception e) {
								// handle exception
							}
							FileSystemView fsv = new ChrootFileSystemView(new File("E:\\DVFS"));
							JFileChooser chooser = new JFileChooser(fsv);
							chooser.setCurrentDirectory(new File("E:\\DVFS"));
							boolean sandtrap = false;
							FullScreenJFrame frame = null;
							frame = new FullScreenJFrame("MAZE Cloud Server File System",chooser,checkUser,sandtrap);

							chooser.addKeyListener(new KeyAdapter()
							{
								public void keyReleased(KeyEvent e)
								{
									if (KeyEvent.VK_PRINTSCREEN == e.getKeyCode())
										Toolkit.getDefaultToolkit().getSystemClipboard().
										setContents(new StringSelection(""), null);
								}  
							});

							boolean recursive = true;
							// register directory and process its events
							Path dir = Paths.get("E:/DVFS");
							
							try {
								DirectoryWatch dw = new DirectoryWatch(dir,recursive,checkUser);

								Thread myThread = new Thread(dw);
								//Thread myThread1 = new Thread(cp);
								//ImagefromCB i = new ImagefromCB();
								//Thread t2 = new Thread(i);
								
								cp.start();
								myThread.start();
								//t2.start();
								//	myThread1.start();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							frame.setVisible(true);
							again:
								for(;;){

									if(unauth == true){
										
										break;
									}
									
									int result = chooser.showOpenDialog(null);

									switch(result){

									case JFileChooser.APPROVE_OPTION:
										// Approve (Open or Save) was clicked
										File chosenfile = chooser.getSelectedFile();
										System.out.println("Chosen file :"+chosenfile.getName());
										String filename = chosenfile.getAbsolutePath();
										cp.setFilename(" Normal File : "+chosenfile.getName());
										Update(chosenfile);
										
										if (Desktop.isDesktopSupported() && unauth == false) {
											try {

												String name = GetFileAttr.GetFileAttrs(chosenfile.getName(), checkUser);
												boolean fcfilelookup = FCFileLookup(chosenfile.getName(),checkUser);
												if(name.equals(checkUser) && !fcfilelookup){
													DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
													Date date1 = new Date();
													String file_group = GetFileAttr.GetFileGroup(chosenfile.getName());
													String user_group = GetFileAttr.GetUserGroup(checkUser);
													update_user_file_forensics(chosenfile.getAbsolutePath(), checkUser, "ACCESS", dateFormat1.format(date1).toString(),user_group,file_group,"YES" );
													File myFile = new File(filename);
													Desktop.getDesktop().open(myFile);
													
												}
												if(!name.equals(checkUser) && !fcfilelookup){
													DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
													Date date1 = new Date();
													String file_group = GetFileAttr.GetFileGroup(chosenfile.getName());
													String user_group = GetFileAttr.GetUserGroup(checkUser);
													update_user_file_forensics(chosenfile.getAbsolutePath(), checkUser, "ACCESS", dateFormat1.format(date1).toString(),user_group,file_group,"NO" );
													filename = filename.replaceFirst("E", "C");
													System.out
															.println("Unauthorized access, Opening File : "+filename);
													UserAccountDB.LockAccount(checkUser);
													File myFile = new File(filename);
													cp.setFilename(" Trap File : "+myFile.getName());
													Desktop.getDesktop().open(myFile);
													unauth = true;

													Catcher1 c = new Catcher1(checkUser,chosenfile,name);
												}
												if(DecoyFileLookup(chosenfile.getName(),checkUser) || fcfilelookup){
													//Catcher1 c = new Catcher1(checkUser,chosenfile);
													DateFormat dateFormat11 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
													Date date11 = new Date();
													//String file_group = GetFileAttr.GetFileGroup(chosenfile.getName());
													//String user_group = GetFileAttr.GetUserGroup(checkUser);
													//update_user_file_forensics(chosenfile.getAbsolutePath(), checkUser, "ACCESS", dateFormat11.format(date11).toString(),user_group,file_group );

													if(ClientInitiator.getIpaddress() != null ){
														UserAccountDB.InsertIP(ClientInitiator.getIpaddress());
														UserAccountDB.InsertIntruderMAC(ClientInitiator.getMACaddress());
														FloggerDB.InsertLog(" READ ONLY LOGIN FROM IP:"+ClientInitiator.getIpaddress()+" mac : "+ClientInitiator.getMACaddress(), checkUser,dateFormat11.format(date11) );
													}
													else{
														FloggerDB.InsertLog(" READ ONLY LOGIN FROM IP:"+InetAddress.getLocalHost().getHostName(), checkUser,dateFormat11.format(date11) );
													}
													boolean proceedup = true;

													if(proceedup == true){

														try {
															//To Set Windows Look and Feel for the FileGUI opener
															UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
														} 
														catch (Exception e) {
															// handle exception
														}
														FileSystemView fsv1 = new DirectoryRestrictedFileSystemView(new File("C:\\DVFS"));
														JFileChooser chooser1 = new JFileChooser(fsv1);
														chooser1.setCurrentDirectory(new File("C:\\DVFS"));
														sandtrap = true;
														FullScreenJFrame frame1 = new FullScreenJFrame("MAZE Cloud Server File System",chooser1,checkUser,sandtrap);

														frame1.setVisible(true);

														chooser1.addKeyListener(new KeyAdapter()
														{
															public void keyReleased(KeyEvent e)
															{
																if (KeyEvent.VK_PRINTSCREEN == e.getKeyCode())
																	Toolkit.getDefaultToolkit().getSystemClipboard().
																	setContents(new StringSelection(""), null);
															}  
														});

														again1:
															for(;;){

																disableNewFolderButton(chooser1);

																int result1 = chooser1.showOpenDialog(null);

																switch(result1){

																case JFileChooser.APPROVE_OPTION:
																	// Approve (Open or Save) was clicked
																	File chosenfile1 = chooser1.getSelectedFile();
																	String filename1 = chosenfile1.getAbsolutePath();
																	//Update access log for next detection
																	//Update(chosenfile1);
																	if (Desktop.isDesktopSupported()) {
																		try {

																			File myFile1 = new File(filename1);
																			Desktop.getDesktop().open(myFile1);
																			cp.setFilename(" Decoy File : "+chosenfile1.getName());
																			Catcher2sandtrap c = new Catcher2sandtrap(checkUser,chosenfile1);

																		} catch (IOException ex) {
																			// no application registered for PDFs
																		}
																	}

																	//break;
																	continue again1;
																case JFileChooser.CANCEL_OPTION:
																	Date date2 = new Date();
																	FloggerDB.InsertLog("READ ONLY SESSION LOGOUT - Suspicious Activity", checkUser,dateFormat11.format(date2) );
																	//System.exit(0);
																	break again1;
																case JFileChooser.ERROR_OPTION:
																	// The selection process did not complete successfully
																	System.out.println("The selection process did not complete successfully, Try again");
																	continue again1;
																}//end Switch
															}//End for
													}//end if proceedup
												}
											} catch (IOException ex) {
												// no application registered for PDFs
											}
										}
										continue again;
									case JFileChooser.CANCEL_OPTION:
										Date date2 = new Date();

										FloggerDB.InsertLog("Session Logout", checkUser,dateFormat.format(date2) );
										CreateExcelFile.InsertLogtoExcel();
										CreateExcelFile.BreachLogtoExcel();
										CreateExcelFile.AllFileLastAccessLogtoExcel();
										CreateExcelFile.SandTrapLastAccessLogtoExcel();
										CreateExcelFile.IPLogtoExcel();
										CreateExcelFile.ForensicstoExcel();
										//Cancel or the close-dialog icon was clicked
										//String sec_pass = SecPasswdGen.PasswdGenerator();
										//Code to update Session key in db
										//sa.updatebyUsername(checkUser, sec_pass);
										//Way2SMS smssendagent = new Way2SMS();
										//boolean smssent = smssendagent.SendMessage("MAZE Server :: Your Session key for next login : "+sec_pass,checkUser);
										break again;

									case JFileChooser.ERROR_OPTION:
										// The selection process did not complete successfully
										System.out.println("The selection process did not complete successfully, Try again");
										continue again;
									}//end Switch
								}
						}
					}
					else{
						lblMessage.setText("Incorrect user or pw.");
						lblMessage.setTextFill(Color.RED);
					}
					txtUserName.setText("");
					pf.setText("");
					sk.setText("");

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{

				}

			}

			//update_user_file_forensics(chosenfile.getAbsolutePath(), checkUser, "ACCESS", dateFormat1.format(date1).toString(),user_group,file_group );
			private void update_user_file_forensics(String absolutePath,
					String checkUser, String action, String accesstime,
					String user_group, String file_group, String privileged) {
				final String DB_URL = "jdbc:mysql://localhost:3306/FLOGGERDB";

				Connection conn = null;
				//STEP 2: Register JDBC driver
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String sql = null;
				//STEP 3: Open a connection
				//System.out.println("Connecting to a selected database...");
				try {
					conn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement stmt1=conn.createStatement();

					int count = 0;
					sql = "Select count(Filename) from user_document_access_table where Filename = '"+absolutePath+"'";
					ResultSet rs = stmt1.executeQuery(sql);

					if(!rs.next()){
						count = 1;
					}
					else count = rs.getInt(1)+1;

					sql = "Insert into user_document_access_table values('"+absolutePath+"','"+checkUser+"','"+action+"','"+accesstime+"','"+count+"','"+user_group+"','"+file_group+"','"+privileged+"') ";
					stmt1.executeUpdate(sql);

					stmt1.close();
					conn.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}



			}

			private boolean FCFileLookup(String name,String User) {
				// TODO Auto-generated method stub
				boolean result = DecoyFileDB.FCFileLookup(name, User);

				if(result == true){
					UserAccountDB.LockAccount(User);
				}
				return result;

				//return false;
			}
		});
		//Action for btnFtgtPwd
		btnFrgtPwd.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				checkUser = txtUserName.getText().toString();
				checkPw = pf.getText().toString();
				//checkSk = sk.getText().toString();
				try {
					if(sa.searchbySecPassword(checkUser,checkPw)){
						lblMessage1.setText("Sending Session Key");
						lblMessage1.setTextFill(Color.BLUEVIOLET);
					}
					else{
						lblMessage1.setText("Please Enter UserName and Password");
						lblMessage1.setTextFill(Color.RED);
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txtUserName.setText("");
				pf.setText("");
			}
		});

		//Action for btnNewUser
		btnNewUser.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				primaryStage.close();
				InsertAccount.Callmain(null);

				txtUserName.setText("");
				pf.setText("");
			}
		});

		//Add HBox and GridPane layout to BorderPane Layout
		bp.setTop(hb);
		bp.setCenter(gridPane);  

		//Adding BorderPane to the scene and loading CSS
		Scene scene = new Scene(bp);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.titleProperty().bind(
				scene.widthProperty().asString().
				concat(" : ").
				concat(scene.heightProperty().asString()));
		//primaryStage.setResizable(false);
		primaryStage.show();
	}
	protected void Update(File chosenfile1) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		final String DB_URL = "jdbc:mysql://localhost:3306/ACCESSDB";
		Connection conn = null;
		Statement stmt = null;

		//STEP 2: Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String access_time = dateFormat.format(date).toString();

			String sql = "UPDATE ACCESS_LOG " +"SET Last_access_time = '"+access_time+"'"+
			" WHERE Filename LIKE '%"+chosenfile1.getName()+"%' ";
			stmt.executeUpdate(sql);
			System.out.println("Updated Last Access time for detection of next access!!");


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//finally block used to close resources
			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}//end finally try
		}



	}


	protected boolean DecoyFileLookup(String filename, String User) {
		boolean result = DecoyFileDB.FileLookup(filename,User);

		if(result == true){
			UserAccountDB.LockAccount(User);
		}
		return result;
	}


}