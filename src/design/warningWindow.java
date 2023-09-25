package design;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;

public class warningWindow extends JFrame implements ActionListener, KeyListener, WindowListener{
	private static final long serialVersionUID = 1L;
	private JFrame Window;
	private JButton btnYes, btnNo, btnOk, btnClose;
	private JLabel lblWarning, logo;
	private String message;
	static final int YESNO = 0;
	static final int OK = 1;
	private int type;
	private RecorderWindow recorderWindow;
	private LoginWindow loginWindow;
	private JTextField tfInput;

	public warningWindow(String message, int type, LoginWindow painel, JFrame window) {
		super();
		this.type = type;
		this.message = message;
		this.loginWindow = painel;
		this.Window = window;
		addWindowListener(this);
		setTitle("Youner - Help");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(300, 250);
		((JComponent) getContentPane()).setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		setResizable(false);
		setUndecorated(true);

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		createButtons();
		createLabel();
		createImage();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public warningWindow(String message, RecorderWindow painel, int type, JFrame window) {
		super();
		this.type = type;
		this.message = message;
		this.recorderWindow = painel;
		this.Window = window;
		setTitle("Youner - Help");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		((JComponent) getContentPane()).setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		setSize(300, 250);
		setResizable(false);
		setUndecorated(true);

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		createButtons();
		createLabel();
		createImage();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	//Buttons
	public void createButtons(){
		if(type == warningWindow.YESNO ) {
			btnYes = new JButton("Yes");
			btnYes.setBounds(96, 127, 89, 23);
			btnYes.setBackground(Color.WHITE);
			btnYes.setForeground(Color.BLACK);
			btnYes.addActionListener(this);
			btnYes.setActionCommand("Yes");
			getContentPane().add(btnYes);

			btnNo = new JButton("No");
			btnNo.setBounds(195, 127, 89, 23);
			btnNo.setBackground(Color.WHITE);
			btnNo.setForeground(Color.BLACK);
			btnNo.addActionListener(this);
			btnNo.setActionCommand("No");
			getContentPane().add(btnNo);
		}
		else if(type == warningWindow.OK) {
			btnOk = new JButton("Ok");
			btnOk.setBounds(145, 210, 89, 25);
			btnOk.setBackground(Color.WHITE);
			btnOk.setForeground(Color.BLACK);
			btnOk.addActionListener(this);
			btnOk.addKeyListener(this);
			btnOk.setActionCommand("Ok");
			getContentPane().add(btnOk);

			btnClose = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
			btnClose.setBounds(260, 10, 32, 32);
			btnClose.setContentAreaFilled(false);
			btnClose.addActionListener(this);
			btnClose.setActionCommand("close");
			getContentPane().add(btnClose);
		}

	}

	//Labels
	public void createLabel(){
		if(loginWindow != null || recorderWindow != null) {
			lblWarning = new JLabel(message);
			lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblWarning.setForeground(Color.WHITE);
			lblWarning.setBounds(126, 20, 165, 100);
			getContentPane().add(lblWarning);

			tfInput = new JTextField();
			tfInput.setBounds(126, 90, 164, 30);
			tfInput.requestFocus();
			getContentPane().add(tfInput);

			btnOk.setBounds(126, 150, 89, 25);
		}else {
			lblWarning = new JLabel(message);
			lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblWarning.setForeground(Color.WHITE);
			lblWarning.setBounds(125, 0, 165, 175);
			getContentPane().add(lblWarning);
		}
	}

	//Image With Label
	public void createImage(){
		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png"));
		logo.setBounds(20, 40, 90, 90);
		getContentPane().add(logo);

	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("Yes")){
			System.exit(EXIT_ON_CLOSE);
		}else if(ae.getSource() == btnOk) {
			try {
				this.Window.setEnabled(true);
				this.Window.setVisible(true);
			}
			catch(Exception e) {

			}

			if(recorderWindow != null) {
				recorderWindow.receiveValue(tfInput.getText());
			} else if(loginWindow != null) {
				loginWindow.receiveValue(tfInput.getText());
			}
			dispose();
		}	
		else{
			try {
				this.Window.setEnabled(true);
				this.Window.setVisible(true);
			}
			catch(Exception e) {

			}
			dispose();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode()==KeyEvent.VK_ENTER){
			btnOk.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		try {
			this.Window.setEnabled(false);
		}
		catch(Exception ex) {

		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
