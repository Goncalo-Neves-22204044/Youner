package design;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AudioWindow extends JFrame implements ActionListener, ChangeListener, LineListener, MouseListener{
	private static final long serialVersionUID = 1L;

	private JButton btnPlay, btnStop, btnPause, btnBack;
	private JToggleButton btnMute, btnVolume;
	private JSlider slider, slider2;
	private JProgressBar progressBar;
	private JLabel lblTitle, lblLogo, lblName;

	private String dir;
	private long seconds, currentTime = 0;
	private boolean isRunning = false, stopClicked = false;

	private boolean isDragging = false;
	
	private Thread t;
	private Clip clip;

	private int currentVolume;

	public AudioWindow(String dir) {
		super();
		this.dir = dir;

		setTitle("Youner - Player");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setSize(300, 475);
		setResizable(false);
		setUndecorated(true);


		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus(); 
			}
		});

		createLabels();
		createProgressBar();
		createSeparator();
		createButtons();
		createSlider();

		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);

		setVisible(true);
	}

	// CREATE LABELS
	public void createLabels() {
		File f = new File(dir);

		lblTitle = new JLabel("PLAYER");
		lblTitle.setBounds(20, 30, 100, 30);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Arial", Font.BOLD ,13));
		getContentPane().add(lblTitle);

		lblName = new JLabel();		
		lblName.setText(f.getName());
		lblName.setBounds(getWidth()/2-100, 215, 200, 100);
		lblName.setFont(new Font("Arial", Font.BOLD ,12));
		lblName.setHorizontalAlignment(JLabel.CENTER);
		lblName.setForeground(Color.WHITE);
		getContentPane().add(lblName);

		lblLogo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//icon2.png"));
		lblLogo.setBounds(getWidth()/2-75, 85, 150, 150);
		getContentPane().add(lblLogo);
	}

	// CREATE SEPARATORS
	public void createSeparator() {
		JSeparator separator = new JSeparator();
		separator.setBounds(20, 60, 250, 5);
		separator.setBackground(Color.WHITE);
		getContentPane().add(separator);

		JSeparator separator2 = new JSeparator();
		separator2.setBounds(20, 61, 250, 5);
		separator2.setBackground(Color.WHITE);
		getContentPane().add(separator2);
	}

	// CREATE BUTTONS
	public void createButtons() {		
		btnPause = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//Pause.png"));
		btnPause.setBounds(getWidth()/2-23, 325, 46, 46);
		btnPause.addActionListener(this);
		btnPause.setBackground(Color.WHITE);
		btnPause.setForeground(Color.BLACK);
		btnPause.setActionCommand("pause");
		getContentPane().add(btnPause);

		btnPlay = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//Play.png"));
		btnPlay.setBounds(btnPause.getX() - 65, 325, 46, 46);
		btnPlay.addActionListener(this);
		btnPlay.setBackground(Color.WHITE);
		btnPlay.setForeground(Color.BLACK);
		btnPlay.setActionCommand("play");
		getContentPane().add(btnPlay);

		btnStop = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//Stop.png"));
		btnStop.setBounds(btnPause.getX() + 65, 325, 46, 46);
		btnStop.addActionListener(this);
		btnStop.setBackground(Color.WHITE);
		btnStop.setForeground(Color.BLACK);
		btnStop.setActionCommand("stop");
		getContentPane().add(btnStop);

		btnMute = new JToggleButton();
		btnMute.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//mute.png"));
		btnMute.setBounds(15, 400, 32, 32);
		btnMute.setContentAreaFilled(false);
		btnMute.addActionListener(this);
		btnMute.setActionCommand("mute");
		getContentPane().add(btnMute);

		btnVolume = new JToggleButton(new ImageIcon(System.getProperty("user.dir") + "//Images//volume.png"));
		btnVolume.setBounds(255, 400, 32, 32);
		btnVolume.setContentAreaFilled(false);
		btnVolume.addActionListener(this);
		btnVolume.setActionCommand("volume");
		getContentPane().add(btnVolume);

		btnBack = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//seta2.png"));
		btnBack.setBounds(255, 5, 40, 40);
		btnBack.setContentAreaFilled(false);
		btnBack.addActionListener(this);
		btnBack.setActionCommand("back");
		getContentPane().add(btnBack);
	}

	// CREATE SLIDERS
	public void createSlider() {
		slider = new JSlider();
		slider.setBounds(getWidth()/2-100, btnStop.getY() + btnStop.getHeight()+20, 200, 50);
		slider.setMajorTickSpacing(10);
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.addChangeListener(this);
		getContentPane().add(slider);

		slider2 = new JSlider();
		slider2.setBounds(progressBar.getX()-5, progressBar.getY() + 20, progressBar.getWidth()+10, progressBar.getHeight());
		slider2.setMajorTickSpacing(1);
		slider2.setValue(0);
		slider2.setMinimum(0);
		slider2.setMaximum(100);
		slider2.setPaintTrack(false);
		slider2.addChangeListener(this);
		slider2.addMouseListener(this);
		getContentPane().add(slider2);
	}

	// CREATE PROGRESSBAR
	public void createProgressBar() {
		progressBar = new JProgressBar();
		progressBar.setBounds(50, 285, 200, 20);
		progressBar.setValue(0);  
		getContentPane().add(progressBar);
	}

	// PLAY THE SOUND FILE
	public void playSound() {
		try{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(dir).getAbsoluteFile());
			clip = AudioSystem.getClip();

			clip.open(audioInputStream);
			clip.addLineListener(this);
			clip.start();

			seconds = TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondLength());
		}
		catch(FileNotFoundException fnf){
			new warningWindow("Sound Unavailable", warningWindow.OK, null, this);
		}
		catch(Exception ex) {
		}
	}

	// CALCULATES CURRENT VOLUME
	public float getVolume() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		return (float) Math.pow(10f, gainControl.getValue() / 20f);
	}

	// SETS THE VOLUME
	public void setVolume(float volume) {
		if (volume < 0f || volume > 1f)
			throw new IllegalArgumentException("Volume not valid: " + volume);
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);        
		gainControl.setValue(20f * (float) Math.log10(volume));
	}

	@SuppressWarnings({ "removal", "deprecation" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("play")) {
			stopClicked = false;

			if(!isRunning) {
				if(currentTime == 0) {
					isRunning = true;
					playSound();
					
					t = new Thread(new Runnable() {
						@Override
						public void run() {
							while (clip.isRunning()){

								try {
									Thread.sleep((long) (seconds*10));
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								progressBar.paintImmediately(0, 0, 200, 25);
								
								double d = ((double)((double)clip.getMicrosecondPosition()/(double)clip.getMicrosecondLength())*100);
								progressBar.setValue((int)Math.round(d));
								
								if(!isDragging)
									slider2.setValue(progressBar.getValue());
							}
						}});

					t.start();
					slider.setValue(50);
					setVolume(0.50f);
				}
				else {
					isRunning = true;
					t.resume();
					clip.setMicrosecondPosition(currentTime);
					clip.start();
				}
			}

		}
		else if(e.getActionCommand().equals("pause")) {
			if(!stopClicked) {
				if(isRunning) {
					isRunning = false;
					currentTime = clip.getMicrosecondPosition();
					clip.stop();
					t.suspend();
				}
				else {
					isRunning=true;
					btnPause.doClick();
				}
			}
		}
		else if(e.getActionCommand().equals("stop")) {
			isRunning = false;
			stopClicked = true;
			clip.stop();
			t.stop();
			currentTime = 0;
		}
		else if(e.getActionCommand().equals("mute")) {
			if(btnMute.isSelected()) {
				currentVolume = slider.getValue();
				slider.setValue(0);
				btnMute.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//mute2.png"));
			}
			else {
				if(currentVolume == 0) {
					slider.setValue(50);
				}
				else {
					slider.setValue(currentVolume);
				}
				btnMute.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//mute.png"));
			}
		}
		else if(e.getActionCommand().equals("volume")) {
			if(btnMute.isSelected()) {
				btnMute.doClick();
			}

			if(btnVolume.isSelected()) {
				currentVolume = slider.getValue();
				slider.setValue(100);
			}
			else {
				if(currentVolume == 0) {
					slider.setValue(50);
				}
				else {
					slider.setValue(currentVolume);
				}
			}
		}
		else if(e.getActionCommand().equals("back")) {
			dispose();
			new RecorderWindow();
			if(isRunning) {
				clip.stop();
			}
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == slider) {
			if(slider.getValue() == 100) {
				setVolume(1.0f);
			}
			else {
				float volume = Float.parseFloat("0." + slider.getValue());
				setVolume(volume);
			}
		}
	}

	@Override
	public void update(LineEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == slider2) {
			isDragging = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == slider2) {
			isDragging = false;
			double d = clip.getMicrosecondLength()*((double)slider2.getValue()/100);
			currentTime = Math.round(d);
			clip.setMicrosecondPosition(currentTime);
			//progressBar.setValue(slider2.getValue());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
