package design;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import auxiliary.HintPasswordField;
import auxiliary.HintTextField;
import auxiliary.DateLabelFormatter;
import auxiliary.EmailSend;
import dataBase.SignUp;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;


public class SignUpWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private SignUp newSignUp;
	private HintTextField tfName, tfLastname, tfEmail, tfCountry;
	private HintPasswordField pfPassword, pfPassword2;

	private JLabel logo;

	private String email, name, birthday, country, password, password2;

	private JButton btnRegister, btnCancel, btnClose, btnTogglePassword, btnTogglePassword2;

	private JPanel panel1, panel2, panel3;

	private JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;

	private Date selectedDate;

	private SimpleDateFormat df;

	int PasswordScore = 0;

	private UtilDateModel model;

	private LocalDateTime myDateObj;

	private String date;

	private HintTextField tfBirthday;

	public SignUpWindow() {
		super();

		newSignUp = new SignUp();

		setTitle("Youner - Sign up");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(300, 475);
		setLocationRelativeTo(null);
		setResizable(false);
		setFocusable(true);
		setUndecorated(true);

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus(); 
			}
		});

		Date();
		createTextFields();
		createButtons();
		createLabels();
		createPanels();

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);
		setVisible(true);			 
	}

	public void createPanels() {
		panel1 = new JPanel();
		panel1.setBounds(30, 228, 240, 25);
		panel1.setBackground(Color.BLACK);
		panel1.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		panel1.add(pfPassword);
		panel1.add(btnTogglePassword);
		panel1.setLayout(null);
		getContentPane().add(panel1);

		panel2 = new JPanel();
		panel2.setBounds(30, 264, 240, 25);
		panel2.setBackground(Color.BLACK);
		panel2.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		panel2.add(pfPassword2);
		panel2.add(btnTogglePassword2);
		panel2.setLayout(null);
		getContentPane().add(panel2);

		panel3 = new JPanel();
		panel3.setBounds(30, 300, 240, 30);
		panel3.setBackground(Color.BLACK);
		panel3.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		panel3.setLayout(null);
		panel3.add(datePicker);	
		panel3.add(tfBirthday);
		getContentPane().add(panel3);
	}

	public void createTextFields(){

		tfName = new HintTextField("Name");
		tfName.setBackground(Color.BLACK);
		tfName.setBounds(30, 156, 115, 25);
		tfName.setBorder(new LineBorder(new Color(255, 255, 255), 1, true) );
		getContentPane().add(tfName);

		tfLastname = new HintTextField("Last Name");
		tfLastname.setForeground(Color.WHITE);
		tfLastname.setBackground(Color.BLACK);
		tfLastname.setBounds(155, 156, 115, 25);
		tfLastname.setBorder(new LineBorder(new Color(255, 255, 255), 1, true) );
		getContentPane().add(tfLastname);

		tfEmail = new HintTextField("Email");
		tfEmail.setForeground(Color.WHITE);
		tfEmail.setBackground(Color.BLACK);
		tfEmail.setBounds(30, 192, 240, 25);
		tfEmail.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		getContentPane().add(tfEmail);

		pfPassword = new HintPasswordField("Password");
		pfPassword.setBackground(Color.BLACK);
		pfPassword.setForeground(Color.WHITE);
		pfPassword.setBounds(0, 0, 200, 25);
		pfPassword.setBorder(null);

		pfPassword2 = new HintPasswordField("Confirm Password");
		pfPassword2.setBackground(Color.BLACK);
		pfPassword2.setForeground(Color.WHITE);
		pfPassword2.setBounds(0, 0, 200, 25);
		pfPassword2.setBorder(null);


		model = new UtilDateModel();
		model.setSelected(true);

		Properties p = new Properties();

		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");

		datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		datePicker.setOpaque(false);
		datePicker.setTextEditable(true);
		datePicker.setBounds(50, 1, 185, 30);
		datePicker.setFocusable(true);

		datePicker.getComponent(0).setBackground(Color.BLACK);
		datePicker.getComponent(0).setForeground(Color.BLACK);
		datePicker.getComponent(0).setFocusable(false);
		((JComponent) datePicker.getComponent(0)).setBorder(null);

		datePicker.getComponent(1).setBackground(Color.WHITE);
		datePicker.getComponent(1).setForeground(Color.BLACK);
		datePicker.getComponent(1).setFont(new Font("Tahoma", Font.PLAIN, 11));
		datePicker.getComponent(1).setFocusable(false);
		datePicker.getComponent(1).setCursor(new Cursor(Cursor.HAND_CURSOR));	
		datePicker.getComponent(1).addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tfBirthday.setBounds(0,  0,  0, 0);
				datePicker.setBounds(0, 1, 240, 30);
				model.setSelected(true);
				datePicker.getComponent(0).setForeground(Color.WHITE);
				datePicker.getComponent(0).setBounds(5, 0, 200, 30);
				datePicker.getComponent(1).setBounds(205, 1, 30, 25);
			}
		});

		tfBirthday = new HintTextField("Birthday ");
		tfBirthday.setBounds(0, 5, 47, 20);
		tfBirthday.setFocusable(true);
		tfBirthday.setForeground(Color.WHITE);
		tfBirthday.setBackground(Color.BLACK);
		tfBirthday.setBorder(null);
		tfBirthday.setFont(new Font("Tahoma", Font.ITALIC, 11));
		tfBirthday.addFocusListener(new FocusAdapter() {  

			@Override  
			public void focusGained(FocusEvent e) {  
				tfBirthday.setBounds(0,  0,  0, 0);
				datePicker.setBounds(0, 1, 240, 30);
				model.setSelected(true);
				datePicker.getComponent(0).setForeground(Color.WHITE);
				datePicker.getComponent(0).setBounds(5, 0, 200, 30);
				datePicker.getComponent(1).setBounds(205, 1, 30, 25);
			}
		}); 


		tfCountry = new HintTextField("Country");
		tfCountry.setForeground(Color.WHITE);
		tfCountry.setBackground(Color.BLACK);
		tfCountry.setBounds(30, 341, 240, 25);
		tfCountry.setBorder(new LineBorder(new Color(255, 255, 255), 1, true) );
		getContentPane().add(tfCountry);
		tfCountry.setColumns(10);
	}

	public void createButtons(){
		JRadioButton btn = new JRadioButton("text");
		getContentPane().add(btn);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(181, 400, 89, 23);
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setForeground(Color.BLACK);
		btnRegister.addActionListener(this);
		btnRegister.setActionCommand("Register");
		getContentPane().add(btnRegister);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(30, 400, 89, 23);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setForeground(Color.BLACK);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("Cancel");
		getContentPane().add(btnCancel);

		btnClose = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
		btnClose.setBounds(255, 10, 32, 32);
		btnClose.addActionListener(this);
		btnClose.setContentAreaFilled(false);
		btnClose.setActionCommand("Close");
		getContentPane().add(btnClose);

		btnTogglePassword = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//password.png"));
		btnTogglePassword.setBounds(215, 3, 20, 20);
		btnTogglePassword.setFocusable(false);
		btnTogglePassword.addActionListener(this);
		btnTogglePassword.setActionCommand("password");

		btnTogglePassword2 = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//password.png"));
		btnTogglePassword2.setBounds(215, 3, 20, 20);
		btnTogglePassword2.setFocusable(false);
		btnTogglePassword2.addActionListener(this);
		btnTogglePassword2.setActionCommand("password2");

		btn.requestFocusInWindow();
	}

	public void createLabels(){
		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//logo.png"));
		logo.setBounds(50, 50, 200, 75);
		getContentPane().add(logo);
	}

	// GET AND PRINT CURRENT DATE
	public void Date() {
		myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		date = myDateObj.format(myFormatObj);
	}

	public void createAccount(){

		System.out.println("Email: " + email);
		System.out.println("Name: " + name);
		System.out.println("Birthday: " + birthday);
		System.out.println("Country: " + country);
		System.out.println("Password: " + password);

		if(newSignUp.CreateAccount(email, name, birthday, country, password) == 0){
			dispose();
		}
		else{
			new warningWindow("Failed to create account!",warningWindow.OK , null, this);
		}
	}

	//EMAIL VALIDATION
	public boolean emailValidation(String email) {
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

	//Password VALIDATION
	public int passwordValidation(String password){

		if( password.length() < 8 )
			return 0;
		else if( password.length() >= 10 )
			PasswordScore += 2;
		else 
			PasswordScore += 1;

		//if it contains one digit, add 2 to total score
		if( password.matches("(?=.*[0-9]).*") )
			PasswordScore += 2;

		//if it contains one lower case letter, add 2 to total score
		if( password.matches("(?=.*[a-z]).*") )
			PasswordScore += 2;

		//if it contains one upper case letter, add 2 to total score
		if( password.matches("(?=.*[A-Z]).*") )
			PasswordScore += 2;    

		//if it contains one special character, add 2 to total score
		if( password.matches("(?=.*[~!@#$%^&*()_-]).*") )
			PasswordScore += 2;

		return PasswordScore;
	}

	// VALIDATE FIELDS
	public boolean validateFields(){
		boolean resultado=true;
		boolean answered=true;
		password =  new String (pfPassword.getPassword());
		password2 =  new String (pfPassword2.getPassword());


		if(tfName.getText().trim().equals("Name")){
			tfName.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			for(int i = 0; i < tfName.getText().length(); i++) {				
				if(!Character.isLetter(tfName.getText().charAt(i))){
					new warningWindow("Name can only contain letters!", warningWindow.OK, null, this);
					resultado = false;
					return false;
				}
			}
			
			tfName.setForeground(Color.WHITE); 
		}

		if(tfLastname.getText().trim().equals("Last Name")){
			tfLastname.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			
			for(int i = 0; i < tfLastname.getText().length(); i++) {
				if(!Character.isLetter(tfLastname.getText().charAt(i))) {
					new warningWindow("<html>LastName can only <br> contain letters!</html>", warningWindow.OK, null, this);
					resultado = false;
					return false;
				}
			}
			
			tfLastname.setForeground(Color.WHITE);
		}

		if(tfEmail.getText().trim().equals("Email")){
			tfEmail.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			tfEmail.setForeground(Color.WHITE);

			if(!emailValidation(email)){				
				System.out.println("Invalid Email");
				new warningWindow("Invalid Email!", warningWindow.OK, null, this);
				resultado = false;
				return false;
			}
		}

		if( password.trim().equals("Password")){
			pfPassword.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			pfPassword.setForeground(Color.WHITE);

			if(passwordValidation(password) < 6){
				new warningWindow("<html>Password is too weak! Try to Insert: <br> -Minimum 8 characters <br> -Numbers <br> -Special Characters ex: ~!@#$%^&*()_- <br> - Upper and lower case characters</html>", warningWindow.OK, null, this);
				resultado = false;
				return false;
			}
		}

		if(password2.trim().equals("Confirm Password")){
			pfPassword2.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			pfPassword2.setForeground(Color.WHITE);

			if(!password2.equals(password)) {
				new warningWindow("Passwords are different", warningWindow.OK, null, this);
				resultado = false;
				return false;
			}
		}

		if(birthday.trim().equals(date)) {
			datePicker.getComponent(0).setForeground(Color.RED);
			tfBirthday.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else {
			datePicker.getComponent(0).setForeground(Color.WHITE);
			if(Integer.parseInt(birthday.substring(0, 4)) > 2016 || Integer.parseInt(birthday.substring(0, 4)) < 1921) {
				new warningWindow("Invalid Birthday", warningWindow.OK, null, this);
				resultado = false;
				return false;
			}
		}
		if(tfCountry.getText().trim().equals("Country")){
			tfCountry.setForeground(Color.RED);
			resultado = false;
			answered=false;
		}
		else{
			
			for(int i = 0; i < tfCountry.getText().length(); i++) {
				if(!Character.isLetter(tfCountry.getText().charAt(i))) {
					new warningWindow("<html>Country can only <br> contain letters!</html>", warningWindow.OK, null, this);
					resultado = false;
					return false;
				}
			}
			
			tfCountry.setForeground(Color.WHITE);
		}

		if(answered == false) {
			new warningWindow("All fields must be filled!", warningWindow.OK, null, this);
		}

		return resultado;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		email = tfEmail.getText();
		name = tfName.getText() + " " + tfLastname.getText();

		selectedDate = (Date) datePicker.getModel().getValue(); 
		df = new SimpleDateFormat("yyyy-MM-dd");
		birthday = df.format(selectedDate);
		
		country = tfCountry.getText();
		password =  new String (pfPassword.getPassword());
		password2 =  new String (pfPassword2.getPassword());

		if(ae.getActionCommand().equals("password")){
			if(pfPassword.passwordVisible){
				if(!password.equals("Password")) {
					pfPassword.setEchoChar(pfPassword.echoChar);
					pfPassword.passwordVisible = false;
				}
			}
			else{
				pfPassword.setEchoChar((char) 0);
				pfPassword.passwordVisible = true;
			}
		}
		else if(ae.getActionCommand().equals("password2")){
			if(pfPassword2.passwordVisible){
				if(!password2.equals("Confirm Password")) {
					pfPassword2.setEchoChar(pfPassword2.echoChar);
					pfPassword2.passwordVisible = false;
				}
			}
			else{
				pfPassword2.setEchoChar((char) 0);
				pfPassword2.passwordVisible = true;
			}
		}
		else if(ae.getActionCommand().equals("Cancel")){
			dispose();
			new OptionWindow();
		}
		else if(ae.getActionCommand().equals("Close")) {
			new warningWindow("Are you sure you want to leave?", warningWindow.YESNO, null, this);
		}
		else {
			if(validateFields()){
				if(newSignUp.consultCreate(email)){
					new warningWindow("Email is already registered!", warningWindow.OK, null, this);
				}
				else{
					createAccount();
					new EmailSend().send(email, "Youner", "Congratulations!\nYour Account is now created and fully configured.\n");
					new LoginWindow();
					new warningWindow("<html>Account Successfully created! <br><br>An email has been sent to '"+ email +"'!</html>",warningWindow.OK , null, null);
					dispose();
				}
			}
		}
	}
}	



