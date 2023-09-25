package design;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import auxiliary.EmailSend;
import auxiliary.PasswordGenerator;
import dataBase.User;

public class LoginWindow extends JFrame implements ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;

	public static String emailLogin;

	private JTextField tfEmail;
	private JButton btnLogin, btnTogglePassword, btnForgot, btnSignUp;
	public JButton btnClose;

	private JPasswordField pfPassword;

	private String email, password;

	private JLabel lblMail, lblPassword, lblSignUp, lblForgot, logo, mailIcon, passwordIcon;

	private Font btnForgotFont;

	User user = new User();

	private char echoChar;
	private boolean passwordVisible;

	private JPanel panel;

	public LoginWindow() {
		super();

		setTitle("Youner - Login");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(300, 475);
		setResizable(false);
		setUndecorated(true);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus(); 
			}
		});

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);

		createLabels();
		createTextFields();
		createButtons();
		createImage();
		createPanel();

		setLocationRelativeTo(null);

		setVisible(true);
	}

	//CREATE LABELS
	public void createLabels(){
		lblMail = new JLabel("Email: ");
		lblMail.setForeground(Color.WHITE);
		lblMail.setHorizontalAlignment(SwingConstants.LEFT);
		lblMail.setBounds(127, 177, 75, 15);
		getContentPane().add(lblMail);

		lblPassword = new JLabel("Password: ");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(120, 245, 75, 15);
		getContentPane().add(lblPassword);

		lblSignUp = new JLabel("NO ACCOUNT?");
		lblSignUp.setForeground(Color.WHITE);
		lblSignUp.setBounds(82, 450, 152, 14);
		getContentPane().add(lblSignUp);

		lblForgot = new JLabel("FORGOT");
		lblForgot.setForeground(Color.WHITE);
		lblForgot.setBounds(89, 354, 75, 14);
		getContentPane().add(lblForgot);
	}

	// CREATE TEXTFIELDS
	public void createTextFields(){
		tfEmail = new JTextField();
		tfEmail.setHorizontalAlignment(SwingConstants.CENTER);
		tfEmail.setBackground(Color.BLACK);
		tfEmail.setForeground(Color.WHITE);
		tfEmail.addKeyListener(this);
		tfEmail.setBounds(45, 200, 210, 27);
		tfEmail.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		getContentPane().add(tfEmail);

		pfPassword = new JPasswordField();
		pfPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pfPassword.setBackground(Color.BLACK);
		pfPassword.setForeground(Color.WHITE);
		pfPassword.addKeyListener(this);
		echoChar = pfPassword.getEchoChar();
		passwordVisible=false;
		pfPassword.setBorder(null);
		pfPassword.setBounds(30, 0, 150, 27);
	}

	//CREATE BUTTONS
	public void createButtons(){
		btnLogin = new JButton("Login");
		btnLogin.setBounds(95, 314, 102, 25);
		btnLogin.addActionListener(this);
		btnLogin.addKeyListener(this);
		btnLogin.setBackground(Color.WHITE);
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setActionCommand("Login");
		getContentPane().add(btnLogin);

		btnTogglePassword = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//password.png"));
		btnTogglePassword.setBounds(185, 3, 20, 20);
		btnTogglePassword.setFocusable(false);
		btnTogglePassword.addActionListener(this);
		btnTogglePassword.setActionCommand("Password");
		getContentPane().add(btnTogglePassword);

		btnForgot = new JButton("PASSWORD?");
		btnForgot.setForeground(Color.WHITE);
		btnForgot.setBounds(95, 350, 175, 23);
		btnForgot.setContentAreaFilled(false);
		btnForgotFont = new Font(btnForgot.getFont().getName(),Font.BOLD,btnForgot.getFont().getSize());  
		btnForgot.setFont(btnForgotFont);
		btnForgot.addActionListener(this);
		btnForgot.setActionCommand("Forgot");
		getContentPane().add(btnForgot);

		btnSignUp = new JButton("SIGN UP");
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setBounds(154, 446, 89, 23);
		btnSignUp.setContentAreaFilled(false);
		btnSignUp.addActionListener(this);
		Font btnSignUpFont=new Font(btnSignUp.getFont().getName(),Font.BOLD,btnSignUp.getFont().getSize());    
		btnSignUp.setFont(btnSignUpFont);
		btnSignUp.setActionCommand("SignUp");
		getContentPane().add(btnSignUp);

		btnClose = new JButton("");
		btnClose.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
		btnClose.setBounds(255, 10, 32, 32);
		btnClose.addActionListener(this);
		btnClose.setContentAreaFilled(false);
		btnClose.setActionCommand("Close");
		getContentPane().add(btnClose);
	}

	//CREATE IMAGES
	public void createImage(){

		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//logo.png"));
		logo.setBounds(50, 70, 200, 75);
		getContentPane().add(logo);

		mailIcon = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//mailIcon.png"));
		mailIcon.setBounds(20, 203, 20, 20);
		getContentPane().add(mailIcon);

		passwordIcon = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//passwordIcon.png"));
		passwordIcon.setBounds(20, 267, 25, 25);
		getContentPane().add(passwordIcon);

	}

	// CREATE PASSWORD JPANEL
	public void createPanel() {
		panel = new JPanel();
		panel.setBounds(45, 265, 211, 27);
		panel.setBackground(Color.BLACK);
		panel.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		panel.add(pfPassword);
		panel.add(btnTogglePassword);
		panel.setLayout(null);
		getContentPane().add(panel);
	}

	//EMAIL VALIDATION
	public boolean EmailValidation(String email) {
		boolean isEmailIdValid = false;

		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);

			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	//UPDATE PASSWORD EVENT
	public void updateEvent(String email, String newPassword){

		System.out.println("Password: " + newPassword);

		if(user.update(email, newPassword) == 0){
			System.out.println("Password Updated Successfully");
		}
		else{
			new warningWindow("Update Failed!", warningWindow.OK, null, this);
		}
	}

	public void keyPressed(KeyEvent e){
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			btnLogin.doClick();
		}
	}
	public void keyReleased(KeyEvent e){

	}
	public void keyTyped(KeyEvent e){

	}

	//RECEIVES VALUE FROM WARNING WINDOW'S TEXTFIELD (RECOVER PASSWORD) 
	public void receiveValue(String mail) {
		if(mail != null){
			if(!mail.trim().equals("")) {

				if(!EmailValidation(mail)){				
					System.out.println("Invalid Email");

					new warningWindow("Invalid Email!", warningWindow.OK, null, this);
				}
				else{
					if(user.consultEmail(mail)) {
						String newPassword = new PasswordGenerator().generateRandomPassword(8);
						new EmailSend().send(mail, "New Password", "Here's your new Password: \n" + newPassword);
						new warningWindow("<html>An email with the new <br> password has been sent to '" + mail + "'!</html>", warningWindow.OK, null, this);
						updateEvent(mail, newPassword);
					}
					else{
						new warningWindow("Email is not registered!", warningWindow.OK, null, this);
					}
				}
			}
			else {
				new warningWindow("You must insert an email!", warningWindow.OK, null, this);
			}
		}
	}

	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		//LOGIN BUTTON
		if(ae.getActionCommand().equals("Login")){
			email = tfEmail.getText();
			password = new String(pfPassword.getPassword());

			if(email.equals("")){
				new warningWindow("Email cannot be empty!", warningWindow.OK, null, this);
			}
			else {

				if(password.equals("")){
					new warningWindow("Password cannot be empty!", warningWindow.OK, null, this);
				}
				else {
					if(EmailValidation(tfEmail.getText())){
						System.out.println("Valid Email");

						System.out.println(email);
						System.out.println(password);


						if(user.consultLogin(email, password)) {
							System.out.println("Successfully logged in!");
							emailLogin = email;
							dispose();
							new MainWindow();
						}
						else {
							new warningWindow("<html>Login Failed! <br><br>  Wrong Email or Password! <br><br>  Please Try Again!</html>", warningWindow.OK, null, this);
						}
					}
					else{
						System.out.println("Invalid Email");
						new warningWindow("Invalid Email!", warningWindow.OK, null, this);
					}
				}
			}
		}

		//TOGGLE PASSWORD BUTTON 
		if(ae.getActionCommand().equals("Password")){
			if(passwordVisible){
				pfPassword.setEchoChar(echoChar);
				passwordVisible = false;
			}
			else{
				pfPassword.setEchoChar((char) 0);
				passwordVisible = true;
			}
		}	

		//Cancel BUTTON
		if(ae.getActionCommand().equals("Cancel")){
			dispose();
			new OptionWindow();
		}

		//CREATE ACCOUNT BUTTON
		if(ae.getActionCommand().equals("SignUp")){
			dispose();
			new SignUpWindow();
		}

		//CLOSE BUTTON
		if(ae.getActionCommand() == "Close") {
			new warningWindow("Do you really want to leave?", warningWindow.YESNO, null, this);
		}

		//FORGOT PASSWORD BUTTON
		if(ae.getActionCommand().equals("Forgot")){
			//String mail = "";
			new warningWindow("Enter your email!", warningWindow.OK, this, this);

		}
	}
}


