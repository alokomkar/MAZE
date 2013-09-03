package mtechproject.useraccounts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class NewUser extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton SUBMIT;
	JPanel panel;
	JLabel label1,label2,label3;
	JTextField  text1,text2,text3;
	NewUser()
	{
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		label3 = new JLabel();
		label3.setText("Mobile Number:");
		text3 = new JTextField(15);


		SUBMIT=new JButton("SUBMIT");

		panel=new JPanel(new GridLayout(4,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(label3);
		panel.add(text3);
		
		panel.add(SUBMIT);
		add(panel,BorderLayout.CENTER);
		SUBMIT.addActionListener(this);
		setTitle("NEW USER FORM");
	}
	public void actionPerformed(ActionEvent ae)
	{
		String value1 = text1.getText();
		String value2 = text2.getText();
		String value3 = text3.getText();
		if (!value1.isEmpty() && !value2.isEmpty() && !value3.isEmpty()) {
			
			
			System.out.println(value1 +" "+ value2 +" "+ value3);
			//InsertAccount inserter = new InsertAccount();
			/*inserter.Callmain(value1, value2, value3);*/
			
			System.out.println("Success");
			
			//NextPage page=new NextPage();
			//page.setVisible(true);
			//JLabel label = new JLabel("Welcome:"+value1);
			//page.getContentPane().add(label);
			JOptionPane.showMessageDialog(this,"Registration Success, Session Key Sent to your mobile");
			text1.setText("");
			text2.setText("");
			text3.setText("");
			
			
		}
		else{
			System.out.println("enter the valid username and password and mobile number");
			JOptionPane.showMessageDialog(this,"Invalid username, password and mobile number",
					"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}
class NewUserGUI
{
	public static void main(String arg[])
	{
		try
		{
			NewUser frame=new NewUser();
			frame.setSize(300,100);
			frame.setVisible(true);
			
		}
		catch(Exception e)
		{JOptionPane.showMessageDialog(null, e.getMessage());}
	}
}