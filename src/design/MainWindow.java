package design;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;


public class MainWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private JButton btnGame, btnTuner, btnChord, btnRecord, btnClose;
	private JLabel logo;

	public MainWindow(){
		super();

		setTitle("Youner - Main");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(600,500);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setResizable(false);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		createLabels();
		createButtons();
		
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus(); 
			}
		});

		setVisible(true);
	}

	//CREATE BUTTONS
	public void createButtons(){
		btnGame = new JButton();
		btnGame.setBackground(Color.BLACK);
		btnGame.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Game.png"));
		btnGame.setBounds(50, 293, 219, 169);
		btnGame.setToolTipText("Youner Game");
		btnGame.setActionCommand("Game");
		btnGame.addActionListener(this);
		btnGame.setFocusable(false);
		getContentPane().add(btnGame);

		btnTuner = new JButton();
		btnTuner.setBackground(Color.BLACK);
		btnTuner.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Tuner.png"));
		btnTuner.setBounds(50, 115, 219, 169);
		btnTuner.setToolTipText("Guitar Tuner");
		btnTuner.setActionCommand("Tuner");
		btnTuner.addActionListener(this);
		btnTuner.setFocusable(false);
		getContentPane().add(btnTuner);

		btnChord = new JButton();		
		btnChord.setBackground(Color.BLACK);
		btnChord.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Chord.png"));
		btnChord.setToolTipText("Chord Library"); 
		btnChord.setBounds(324, 115, 219, 169);
		btnChord.setActionCommand("Chord");
		btnChord.addActionListener(this);
		btnChord.setFocusable(false);
		getContentPane().add(btnChord);

		btnRecord = new JButton();
		btnRecord.setBackground(Color.BLACK);
		btnRecord.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Record.png"));
		btnRecord.setBounds(324, 293, 219, 169);
		btnRecord.setToolTipText("Recorder");
		btnRecord.setActionCommand("Record");
		btnRecord.addActionListener(this);
		btnRecord.setFocusable(false);
		getContentPane().add(btnRecord);

		btnClose = new JButton("");
		btnClose.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
		btnClose.setBounds(550, 10, 32, 32);
		btnClose.addActionListener(this);
		btnClose.setContentAreaFilled(false);
		btnClose.setActionCommand("Close");
		getContentPane().add(btnClose);
	}

	// CREATE LABELS
	public void createLabels() {
		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//logo.png"));
		logo.setBounds(200, 10, 200, 75);
		getContentPane().add(logo);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getActionCommand().equals("Game")){
			dispose();
			new GameWindow();
		}
		else if(ae.getActionCommand().equals("Record")) {
			dispose();
			new RecorderWindow();
		}
		else if(ae.getActionCommand().equals("Chord")) {
			dispose();
			new ChordsWindow();
		}
		else if(ae.getActionCommand().equals("Tuner")) {
			dispose();
			try {
				new TunerWindow();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(ae.getActionCommand().equals("Close")) {
			new warningWindow("Do you really want to leave?", warningWindow.YESNO, null, this);
		}

	}
}
