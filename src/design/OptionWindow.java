package design;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class OptionWindow extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton btnLogin;
	private JButton btnSignUp;
	private JButton btnFocus;
	private JButton btnClose;

	private JLabel logo, slogan;

	public OptionWindow() {
		super();

		setTitle("Youner");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setResizable(false);
		setUndecorated(true);
		getContentPane().setLayout(null);

		createBackground();
		createButtons();
		createLabels();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	//ADD BACKGROUND
	public void createBackground() {
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Images/foto.png")))));
			setBounds(0, 0, WIDTH, HEIGHT);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		pack();

	}
	
	//CREATE LABELS
	public void createLabels() {
		slogan = new JLabel("<html>Become  the<br/> best guitarist <br/>you can be</html>");
		slogan.setBounds(25, 140, 200, 150);
		slogan.setForeground(Color.GRAY);
		slogan.setFont(new Font("Gabriola", Font.PLAIN, 22));
		getContentPane().add(slogan);
		
		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//logo2.png"));
		logo.setBounds(15, 45, 175, 44);
		getContentPane().add(logo);
	}

	//CREATE BUTTONS
	public void createButtons(){
		btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
		btnLogin.setBounds(20, 385, 125, 25);
		btnLogin.addActionListener(this);
		btnLogin.setActionCommand("Login");
		getContentPane().add(btnLogin);		

		btnSignUp = new JButton("SIGN UP");
		btnSignUp.setFont(new Font("Arial", Font.BOLD, 12));
		btnSignUp.setBounds(155, 385, 125, 25);
		btnSignUp.addActionListener(this);
		btnSignUp.setActionCommand("SignUp");
		getContentPane().add(btnSignUp);
		
		btnClose = new JButton("");
		btnClose.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
		btnClose.setBounds(260, 10, 32, 32);
		btnClose.addActionListener(this);
		btnClose.setContentAreaFilled(false);
		btnClose.setActionCommand("Close");
		getContentPane().add(btnClose);

		btnFocus = new JButton("");
		btnFocus.grabFocus();
		getContentPane().add(btnFocus);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if(ae.getActionCommand().equals("Login")){
			new LoginWindow();
			dispose();
		}
		else if(ae.getActionCommand().equals("SignUp")){
			new SignUpWindow();
			dispose();
		}
		else{
			new warningWindow("Are you sure you want to leave?", warningWindow.YESNO, null, this);
		}
	}	
}

