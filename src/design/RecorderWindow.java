package design;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import auxiliary.LevelMeter;
import auxiliary.bgMenuBar;
import dataBase.Recorder;

public class RecorderWindow extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	
	private Recorder newRec;

	private JButton btnStart, btnStop;
	private JMenu menuFile, menuRecord, menuExit;
	private JMenuItem miNew, miOpen, miDir, miBack, miLeave;
	private FileNameExtensionFilter filter;
	private JFileChooser fileChooser, chooser;
	private String choosenDir = null, openedDir, date, fileName, message = "Choose the file name!";
	private final String defaultDir = "C:\\\\Users\\\\"+ System.getProperty("user.name") +"\\\\Music\\\\";
	private int min, sec;
	private JLabel lblCounter, lblMic;

	private File fileRecording;

	protected static final AudioFileFormat.Type FILE_TYPE = AudioFileFormat.Type.WAVE;
	private TargetDataLine line;

	private LocalDateTime myDateObj;

	private final int timerPeriod = 1000;
	private int count = 0;
	private Timer timer;
	private bgMenuBar menuBar;


	private boolean recording = false;

	private String sampleDir, sampleDuration;

	private LevelMeter meter;

	public RecorderWindow() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				start();
				newRec = new Recorder();

				setTitle("Youner - Recorder");
				setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(300,300);
				getContentPane().setBackground(Color.BLACK);
				getContentPane().setLayout(null);
				setUndecorated(true);

				createBackground();
				createButtons();
				createLabel();
				createMenu();
				createFileChooser();
				Date();

				setLocationRelativeTo(null);
				setVisible(true);
			}
		});
	}

	// CREATE BUTTONS
	public void createButtons() {		
		btnStart = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//start.png"));
		btnStart.setBounds(90, 216, 40, 40); //getWidth()/2 -20, 216, 40, 40
		btnStart.setVisible(true);
		btnStart.setBackground(Color.WHITE);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusable(false);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");
		getContentPane().add(btnStart);

		btnStop = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//stop.png"));
		btnStop.setBounds(170, 216, 40, 40); // 175, 220, 30, 30
		btnStop.setVisible(true);
		btnStop.setBackground(Color.WHITE);
		btnStop.setContentAreaFilled(false);
		btnStop.setFocusable(false);
		btnStop.addActionListener(this);
		btnStop.setActionCommand("stop");
		getContentPane().add(btnStop);
	}

	//ADD BACKGROUND IMAGE
	public void createBackground() {
		try {
			setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("Images/bgRecord.png")))));
			setBounds(0, 0, WIDTH, HEIGHT);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		pack();

	}
	
	/*public void createLevelMeter(TargetDataLine line) {
		meter = new LevelMeter();
        meter.setBounds(getWidth()/2 - 75, 100 ,150 ,150);
        getContentPane().add(meter, BorderLayout.CENTER);
        
       new Thread(new LevelMeter.Record(meter, line)).start();
	}*/

	// CREATE JMENUBAR
	public void createMenu() {
		menuBar = new bgMenuBar(new Color(0, 0, 0));
		menuBar.setBorderPainted(false);
		setJMenuBar(menuBar);

		menuFile = new JMenu();
		menuFile.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//menu.png"));
		menuFile.add(new JSeparator());
		menuBar.add(menuFile);

		miNew = new JMenuItem("New...");
		miNew.addActionListener(this);
		menuFile.add(miNew);

		miOpen = new JMenuItem("Open...");
		miOpen.addActionListener(this);
		menuFile.add(miOpen);

		menuFile.addSeparator();

		miDir = new JMenuItem("Change Saving Directory");
		miDir.addActionListener(this);
		menuFile.add(miDir);
		
		menuRecord = new JMenu("       RECORD       ");
		menuRecord.setForeground(Color.WHITE);
		menuRecord.addMouseListener(this);
		menuBar.add(menuRecord);

		menuBar.add(Box.createHorizontalGlue());

		menuExit = new JMenu();
		menuExit.setIcon(new ImageIcon(System.getProperty("user.dir") + "//Images//close.png"));
		menuBar.add(menuExit);
		
		miBack = new JMenuItem("Back...");
		miBack.addActionListener(this);
		menuExit.add(miBack);
		
		menuExit.addSeparator();

		miLeave = new JMenuItem("Leave...");
		miLeave.addActionListener(this);
		menuExit.add(miLeave);
	}

	// CREATE LABELS 
	public void createLabel() {
		lblCounter = new JLabel("00:00");
		lblCounter.setBounds(getWidth()/2 - 45,50,90,30);
		lblCounter.setFont(new Font("Arial", Font.PLAIN, 35));
		lblCounter.setHorizontalAlignment(SwingConstants.CENTER);
		lblCounter.setForeground(Color.BLACK);
		getContentPane().add(lblCounter);
		
		lblMic = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//mic.png"));
		lblMic.setBounds(getWidth()/2 - 50,getHeight()/2-50,100,100);
		lblMic.setFont(new Font("Arial", Font.PLAIN, 25));
		lblMic.setForeground(Color.BLACK);
		getContentPane().add(lblMic);
	}

	// GET FILE SIZE
	public String getFileSize(String fileName){
		String resultado = "";
		Path path = Paths.get(fileName);

		try {
			long bytes = Files.size(path);
			resultado = String.format("%,d", bytes / 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultado;
	}

	//GET DATE
	public void Date() {
		myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		date = myDateObj.format(myFormatObj);
	}

	// CREATE FILECHOOSER
	public void createFileChooser() {

		fileChooser = new JFileChooser();
		filter = new FileNameExtensionFilter("WAV File","wav");
		fileChooser.setFileFilter(filter);
		fileChooser.setCurrentDirectory(new java.io.File(defaultDir));

		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	//CHANGE DIRECTORY
	public void chooseDirectory() {
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
			choosenDir = chooser.getSelectedFile() + "\\";
		}
		else {
			choosenDir = null;
		}
	}

	// STOP RECORDING
	protected void stopRecording() {
		if (line != null) {
			line.stop();
			line.close();
			line = null;
		}
	}

	// START RECORDING
	protected void startRecording(String fileName) {
		if (line == null) {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						AudioFormat format = getAudioFormat();
						DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

						if (!AudioSystem.isLineSupported(info)) {
							System.out.println("Line not supported");

						}
						line = (TargetDataLine) AudioSystem.getLine(info);
						line.open(format);
						line.start();

						AudioInputStream ais = new AudioInputStream(line);

						if(fileName!=null){
							
							//createLevelMeter(line);
							timer.start();
							AudioSystem.write(ais, FILE_TYPE, fileRecording);
						}
					} catch (LineUnavailableException ex) {
						line.stop();
						line.close();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}catch (Exception e) {
						e.printStackTrace();

					}
				}
			});
			t.start();
		}
		else {
			System.out.println("Line is not null!");
		}
	}

	// STARTS TIMER
	public void start() {
		timer = new Timer(timerPeriod, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(recording){
					min = count/60;
					sec = count%60;

					if(min<10 && sec <10) {
						lblCounter.setText("0" + min + ":" + "0" + sec);
					}
					else if(min<10 && sec >= 10) {
						lblCounter.setText("0" + min + ":" + sec);
					}
					else if(min >= 10 && sec < 10) {
						lblCounter.setText(min + ":" + "0" + sec);
					}
					else {
						lblCounter.setText(min + ":" + sec);
					}

					count++;
				}else {
					count = 0;
					((Timer) e.getSource()).stop();
				}
			}
		});
	}

	// DEFINES AUDIOFORMAT
	protected static AudioFormat getAudioFormat() {
		  float sampleRate = 16000; // 8000,11025,16000,22050,44100,48000
		  int sampleSizeInBits = 16; // 8,16
		  int channels = 2; // 1,2
		  boolean signed = true; // true,false
		  boolean bigEndian = true; // true,false
		  AudioFormat audioFormat = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		  return audioFormat;
		}

	// ADD RECORD TO DATABASE
	public void addRec(){

		String email = LoginWindow.emailLogin;
		String sampleName = fileName;
		sampleDuration = lblCounter.getText();		
		String sampleDate = date;

		if(choosenDir != null) {
			sampleDir = choosenDir + fileName +".wav";
		}
		else {
			sampleDir = defaultDir + fileName +".wav";
		}

		String sampleSize = getFileSize(sampleDir);
		String instrument = "Guitarra";

		System.out.println("Email: " + email);
		System.out.println("Name: " + sampleName);
		System.out.println("Duration: " + sampleDuration);
		System.out.println("Size: " + sampleSize); 
		System.out.println("Date: " + sampleDate);
		System.out.println("Dir: " + sampleDir);
		System.out.println("Instrument: " + instrument);

		if(newRec.addRecord(email, sampleName, sampleDuration, sampleSize, sampleDate, sampleDir, instrument) == 0){
			//new LeaveWindow("Record successfully added", LeaveWindow.OK, null);
		}
		else{
			//new LeaveWindow("Failed to add record", LeaveWindow.OK, null);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub


		if(e.getActionCommand().equals("start")) {
			new warningWindow(message, this, warningWindow.OK, this  );
			recording = true;
		}
		else if(e.getActionCommand().equals("stop")) {
			recording = false;

			if(fileRecording!=null){
				addRec();
				stopRecording();
			}
			lblCounter.setText("00:00");
		}
		else {
			if(recording && fileRecording!=null){
				stopRecording();
				fileRecording.delete();
			}
			recording = false;
			lblCounter.setText("00:00");

			if(e.getSource().equals(miNew)) {
				btnStart.setVisible(true);
				btnStop.setVisible(true);
				lblCounter.setVisible(true);
			}
			else if(e.getSource().equals(miOpen)) {
				btnStart.setVisible(false);
				btnStop.setVisible(false);
				lblCounter.setVisible(false);
				setVisible(false);
				switch (fileChooser.showOpenDialog(this)) {
				case JFileChooser.APPROVE_OPTION:
					openedDir = fileChooser.getSelectedFile().toString();

					new AudioWindow(openedDir);

					break;
				case JFileChooser.CANCEL_OPTION:
					new RecorderWindow();
				}
			}
			else if(e.getSource().equals(miDir)) {
				chooseDirectory();
				btnStart.setVisible(true);
				btnStop.setVisible(true);
				lblCounter.setVisible(true);
			}
			
			else if(e.getSource().equals(miBack)) {
				dispose();
				new MainWindow();
			}

			else if(e.getSource().equals(miLeave)) {
				dispose();
			}
		}
	}

	public void receiveValue(String text) {
		fileName = text;
		if(fileName!=null){
			// start recording
			if(choosenDir != null) {
				fileRecording=new File(choosenDir + fileName +".wav");
			}
			else {
				fileRecording=new File(defaultDir + fileName +".wav");
			}

		}
		
		if(fileRecording.exists()) {
			message = "<html>File already exists!<br>Choose another one!</html>";
			 new warningWindow(message, this, warningWindow.OK, this);
		}else {
			startRecording(text);
		}

		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		menuRecord.setSelected(false);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		menuRecord.setSelected(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		menuRecord.setSelected(false);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}