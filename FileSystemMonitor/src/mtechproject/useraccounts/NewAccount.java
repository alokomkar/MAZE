package mtechproject.useraccounts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class NewAccount extends SecPasswdGen{
	
	static String Username = null;
	static String prim_pass = null;
	static String sec_pass = null;
	static String locked = null;
	static String mobileno = null;
	static UserBean ub = null;
	
	static ArrayList<UserBean> ublist = new ArrayList<UserBean>();
	
	public static  ArrayList<UserBean> CreateUserAccount(String uname, String pwd, String mobno){
		
		System.out.println(uname+pwd+mobno);
		ub = new UserBean();
		Username = uname;
		prim_pass = pwd;
		mobileno = mobno;
		sec_pass = PasswdGenerator();
		locked = "N";
		
		ub.setUsername(Username);
		ub.setPrim_password(prim_pass);
		ub.setSec_password(sec_pass);
		ub.setLocked(locked);
		ublist.add(ub);
		
		return ublist;
	}
	
	public static ArrayList<UserBean> CreateUserAccount(){
		
		//JOptionPane jp = new JOptionPane();
		FullScreenJFrame frame = null;
		frame = new FullScreenJFrame("MAZE Cloud Server File System");

		frame.setVisible(true);
		String uname = null;
		boolean query = false;
		try {
			do{
				uname = JOptionPane.showInputDialog("Please enter desired username");
		        SearchAccount sa = new SearchAccount();
		        
		        
			query = sa.searchbyUsername(uname);
			if(query == true){
				JOptionPane.showMessageDialog(null,"Username already exists, Enter another user name");
				
			}
			}while(query != false);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String password = JOptionPane.showInputDialog("Please enter desired password");
        
		String mobno = JOptionPane.showInputDialog("Please enter your mobile number");
		ub = new UserBean();
		ub.setUsername(uname);
		ub.setPrim_password(password);
		ub.setMobileno(mobno);
		
		System.out.println("Username : "+uname+"Primary Password : "+password);
		System.out.println("Your autogenerated secondary password for next login : ");
		sec_pass = PasswdGenerator();
		ub.setSec_password(sec_pass);
		
		locked = "N";
		
		ub.setLocked(locked);
		ublist.add(ub);
		System.out.println("Generated Password: "+sec_pass);
		return ublist;
		
	}

}
