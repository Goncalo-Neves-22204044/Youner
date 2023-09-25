package design;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JSeparator;


public class ChordsWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private JButton[] button = new JButton[12];
	private String chords[] = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	private JButton[] buttonVar = new JButton[12];
	private String Var[] = {"major", "minor", "5", "7", "maj7", "m7", "sus4", "add9", "sus2", "7sus4", "7#9", "9"};
	private JLabel chords2;
	private JButton btnPrevious, btnNext, btnPrevious2, btnNext2, btnCancel, btnSound, btnHelp;
	private JSeparator separator, separator2;
	private String currentChord, currentVariation, currentChordVar;

	private Clip clip;

	public ChordsWindow(){
		super();

		setTitle("Youner - Chord");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(300,300);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		createButtons();	
		createSeparator();	
		createLabels();

		setVisible(true);
	}

	//CREATE BUTTONS
	public void createButtons() {

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("Cancel");
		btnCancel.setBounds(105, 237, 89, 23);
		getContentPane().add(btnCancel);

		btnHelp = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//help.png"));
		btnHelp.addActionListener(this);
		btnHelp.setActionCommand("help");
		btnHelp.setContentAreaFilled(false);
		btnHelp.setBounds(5, 265, 30, 30);
		getContentPane().add(btnHelp);

		btnSound = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//sound.png"));
		btnSound.addActionListener(this);
		btnSound.setActionCommand("sound");
		btnSound.setContentAreaFilled(false);
		btnSound.setBackground(Color.BLACK);
		btnSound.setBounds(235, 150, 32, 32);
		getContentPane().add(btnSound);

		btnNext = new JButton();
		btnNext.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Right_Arrow.png"));
		btnNext.setBounds(256, 0, 40, 28);
		btnNext.addActionListener(this);
		btnNext.setActionCommand("Next");
		btnNext.setFocusable(false);
		getContentPane().add(btnNext);

		btnPrevious = new JButton();
		btnPrevious.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Left_Arrow.png"));
		btnPrevious.setBounds(-2, 0, 40, 28);
		btnPrevious.addActionListener(this);
		btnPrevious.setActionCommand("Previous");
		btnPrevious.setFocusable(false);
		getContentPane().add(btnPrevious);

		btnNext2 = new JButton();
		btnNext2.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Right_Arrow.png"));
		btnNext2.setBounds(256, 25, 40, 28);
		btnNext2.addActionListener(this);
		btnNext2.setActionCommand("Next2");
		btnNext2.setFocusable(false);
		getContentPane().add(btnNext2);

		btnPrevious2 = new JButton();
		btnPrevious2.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//Left_Arrow.png"));
		btnPrevious2.setBounds(-2, 25, 40, 28);
		btnPrevious2.addActionListener(this);
		btnPrevious2.setActionCommand("Previous2");
		btnPrevious2.setFocusable(false);
		getContentPane().add(btnPrevious2);

		//CHORDS BUTTONS
		for(int i=0; i<button.length; i++){
			chords[i] = new String(chords[i]);
			button[i] = new JButton(chords[i]);
			button[i].setForeground(Color.white);
			button[i].setBounds(115+(i*80), 2, 75, 25);
			button[i].setContentAreaFilled(false);
			getContentPane().add(button[i]);
		}

		//CHORDS VARIATION BUTTONS
		for(int i=0; i<buttonVar.length; i++){
			Var[i] = new String(Var[i]);
			buttonVar[i] = new JButton(Var[i]);
			buttonVar[i].setForeground(Color.white);
			buttonVar[i].setBounds(115+(i*80), 26, 75, 25);
			buttonVar[i].setContentAreaFilled(false);
			getContentPane().add(buttonVar[i]);
		}		
	}

	//CREATE SEPARATORS
	public void createSeparator() {
		separator = new JSeparator();
		separator.setBounds(0, 25, 305, 2);
		getContentPane().add(separator);

		separator2 = new JSeparator();
		separator2.setBounds(0, 50, 305, 2);
		getContentPane().add(separator2);
	}


	//CREATE IMAGES
	public void createLabels() {

		chords2 = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//Cmajor.png"));
		chords2.setBounds(50, 50, 200, 200);
		getContentPane().add(chords2);

		for(int i = 0; i<chords.length; i++) {
			if(button[i].getX() == 115) {
			}

			for(int j = 0; j<buttonVar.length; j++) {

			}
		}
	}

	// PLAY THE CHORD SOUND
	public void playSound(String soundName) {
		if(soundName.equals("null.wav")) {
			soundName = "Cmajor.wav";
		}

		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(System.getProperty("user.dir") + "//Sounds//" + soundName ).getAbsoluteFile());
			clip = AudioSystem.getClip( );
			clip.open(audioInputStream);
			clip.start();
		}
		catch(FileNotFoundException fnf){
			new warningWindow("Sound Unavailable", warningWindow.OK, null, this);
		}
		catch(Exception ex) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("Cancel")) {
			try {
				if(clip.isRunning()) {
					clip.stop();
				}
			}catch(Exception ex) {

			}

			dispose();
			new MainWindow();

		}

		else if(ae.getActionCommand().equals("help")) {
			new warningWindow("<html><br><br>Upper Bar: Chords <br>Lower Bar: Chords Variations <br><br>" 
					+  "C = Dó <br>D = Ré <br>E = Mi <br>F = Fá <br>G = Sol <br>A = Lá <br>B = Si</html>", warningWindow.OK, null, this);
		}

		else if(ae.getActionCommand().equals("sound")) {
			if(!(clip!= null && clip.isRunning())) {
				playSound(currentChordVar + ".wav");
			}			
		}
		//MOVING CHORD BUTTONS TO THE NEXT BUTTON
		else if(ae.getActionCommand().equals("Next")){
			if(button[11].getX() != 115){

				for(int i=0; i<button.length; i++){
					button[i].setBounds(button[i].getX()-80, 2, 75, 25);
					getContentPane().add(button[i]);

					if(button[i].getX() == 115) {
						currentChord = chords[i];
					}
				}

				if(currentVariation == null) {
					currentVariation = Var[0];
				}

				currentChordVar = currentChord + currentVariation;
				updateLabel(currentChordVar);

			}
		}
		
		//MOVING CHORD BUTTONS TO THE PREVIOUS BUTTON
		else if (ae.getActionCommand().equals("Previous")){
			if(button[0].getX() != 115){

				for(int i=0; i<button.length; i++){
					button[i].setBounds(button[i].getX()+80, 2, 75, 25);
					getContentPane().add(button[i]);

					if(button[i].getX() == 115) {
						currentChord = chords[i];
					}
				}

				if(currentVariation == null) {
					currentVariation = Var[0];
				}
				currentChordVar = currentChord + currentVariation;
				updateLabel(currentChordVar);
			}
		}
		//MOVING VARIATION BUTTONS TO THE NEXT BUTTON
		else if(ae.getActionCommand().equals("Next2")){
			if(buttonVar[11].getX() != 115){

				for(int i=0; i<buttonVar.length; i++){
					buttonVar[i].setBounds(buttonVar[i].getX()-80, 26, 75, 25);
					getContentPane().add(buttonVar[i]);

					if(buttonVar[i].getX() == 115) {
						currentVariation = Var[i];
					}
				}

				if(currentChord == null) {
					currentChord = chords[0];
				}
				currentChordVar = currentChord + currentVariation;
				updateLabel(currentChordVar);
			}
			else {
			}
		}

		//MOVING VARIATION BUTTONS TO THE PREVIOUS BUTTON
		else if(ae.getActionCommand().equals("Previous2")){
			if(buttonVar[0].getX() != 115){

				for(int i=0; i<buttonVar.length; i++){
					buttonVar[i].setBounds(buttonVar[i].getX()+80, 26, 75, 25);
					getContentPane().add(buttonVar[i]);

					if(buttonVar[i].getX() == 115) {
						currentVariation = Var[i];
					}
				}

				if(currentChord == null) {
					currentChord = chords[0];
				}

				currentChordVar = currentChord + currentVariation;
				updateLabel(currentChordVar);
			}
			else {
			}
		}
	}

	// CHANGE THE CHORD IMAGE
	private void updateLabel(String currentChordVar) {
		getContentPane().remove(chords2);
		chords2 = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//" + currentChordVar + ".png"));
		chords2.setBounds(50, 50, 200, 200);
		getContentPane().add(chords2);
		repaint();
		revalidate();
	}
}
