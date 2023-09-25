package design;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import auxiliary.AudioFloatInputStream;
import auxiliary.Yin;
import auxiliary.Yin.DetectedPitchHandler;

public class TunerWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public MyPanel tick;
	private JLabel logo, freqLabel;
	private JComboBox<Object> inputBox;
	private int xPos, yPos;
	private Boolean isTuned = false;

	class AudioInputProcessor implements Runnable {
		public MyPanel tick;
		private final int sampleRate;
		private final double audioBufferSize;
		private TargetDataLine line;

		public AudioInputProcessor(MyPanel tick){
			sampleRate = 22050; //Hz
			audioBufferSize = 0.1;//Seconds
			this.tick = tick;
		}

		public void run() {
			javax.sound.sampled.Mixer.Info selected = (javax.sound.sampled.Mixer.Info) inputBox.getSelectedItem();
			if (selected == null)
				return;
			try {
				Mixer mixer = AudioSystem.getMixer(selected);
				AudioFormat format = new AudioFormat(sampleRate, 16, 1, true,false);
				DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,format);
				line = (TargetDataLine) mixer.getLine(dataLineInfo);
				int numberOfSamples = (int) (audioBufferSize * sampleRate);
				line.open(format, numberOfSamples);
				line.start();
				AudioInputStream ais = new AudioInputStream(line);
				AudioFloatInputStream afis = AudioFloatInputStream.getInputStream(ais);
				processAudio(afis);
				line.stop();
				line.close();
				//line = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			aiprocessor = null;
		}

		volatile AudioInputProcessor aiprocessor;

		public void processAudio(AudioFloatInputStream afis) throws IOException, UnsupportedAudioFileException {
			Yin.processStream(afis,new DetectedPitchHandler() {
				@Override
				public void handleDetectedPitch(float time,float pitch) {
					if(pitch != -1.0) {
						freqLabel.setText((new StringBuilder("Freq: ")).append(pitch).toString());
						double spread = 15;
						double tempValue = (pitch - tick.freqOk) / ((tick.freqOk+spread) - (tick.freqOk-spread));
						double angle = tempValue -0.;
						xPos = (int)(80*Math.sin(angle) + 75);
						yPos = (int)(125 - (80*Math.cos(angle)));						
						tick.repaint();
					}
				}
			});
		}
	}

	public void startAction() {
		if (aiprocessor != null) {
			return;
		} else {
			aiprocessor = new AudioInputProcessor(tick);
			(new Thread(aiprocessor)).start();
			return;
		}
	}

	public void stopAction() {
		if (aiprocessor == null) {
			return;
		} else {
			Yin.stop();
			(new Thread(aiprocessor)).stop();
			return;
		}
	}

	volatile AudioInputProcessor aiprocessor;

	public class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		double freqOk=82.406;
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			if(freqLabel.getText() != "Freq: 00.000") {

				if(xPos > 70 && xPos <80) {
					isTuned = true;
					g.setColor(Color.GREEN);
					g.drawRect(0, 0, getWidth(), getHeight());
					this.setBackground(Color.WHITE);
					freqLabel.setForeground(Color.GREEN);
				}
				else {
					isTuned = false;
					g.setColor(Color.BLACK);
					g.drawRect(0, 0, getWidth(), getHeight());
					this.setBackground(Color.WHITE);
					freqLabel.setForeground(Color.WHITE);
				}
			}
			g.drawLine(75, 125, xPos, yPos);
		}
	}

	public TunerWindow() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		super();

		ArrayList<javax.sound.sampled.Mixer.Info> capMixers = new ArrayList<javax.sound.sampled.Mixer.Info>();
		javax.sound.sampled.Mixer.Info mixers[] = AudioSystem.getMixerInfo();
		for (int i = 0; i < mixers.length; i++) {
			javax.sound.sampled.Mixer.Info mixerinfo = mixers[i];
			if (AudioSystem.getMixer(mixerinfo).getTargetLineInfo().length != 0) {
				if(!mixers[i].toString().startsWith("Port")) {
					capMixers.add(mixerinfo);
				}
			}
		}

		setSize(310,500);
		setTitle("Youner - Guitar Tuner");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);

		createLabels();

		JButton btnStart = new JButton("Start");
		btnStart.setBounds(200,10,100,25);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					startAction();
				}
				catch(IllegalArgumentException ex){
					System.out.println("erro");
				}

			}
		});
		getContentPane().add(btnStart);

		JButton btncancel = new JButton("Cancel");
		btncancel.setBounds(getWidth()/2-50,450,100,25);
		btncancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopAction();
				
				dispose();
				new MainWindow();
			}
		});
		getContentPane().add(btncancel);
		
		JButton btnHelp = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//help.png"));
		btnHelp.setBounds(10, 450, 32, 32);
		btnHelp.setContentAreaFilled(false);
		btnHelp.addActionListener(this);
		btnHelp.setActionCommand("help");
		getContentPane().add(btnHelp);

		JButton btnE = new JButton("E");
		btnE.setBounds(getWidth()/2-115, 342, 100, 25);
		btnE.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 82.406;
			}});
		btnE.addActionListener(this);
		getContentPane().add(btnE);

		JButton btnA = new JButton("A");
		btnA.setBounds(getWidth()/2-115, 378, 100, 25);
		btnA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 110.0;
			}});
		getContentPane().add(btnA);

		inputBox = new JComboBox<Object>((Object[]) capMixers.toArray());
		inputBox.setBounds(10, 11, 128, 22);
		getContentPane().add(inputBox);

		tick = new MyPanel();
		tick.setBounds(this.getWidth()/2-75, 200, 150, 125);
		xPos = 150/2;
		yPos = 125/2;
		getContentPane().add(tick);

		JButton btnD = new JButton("D");
		btnD.setBounds(getWidth()/2-115, 414, 100, 25);
		btnD.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 146.832;
			}});
		getContentPane().add(btnD);

		JButton btnG = new JButton("G");
		btnG.setBounds(getWidth()/2+15, 343, 100, 23);
		btnG.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 195.997;
			}});
		getContentPane().add(btnG);

		JButton btnB = new JButton("B");
		btnB.setActionCommand("Cancel");
		btnB.setBounds(getWidth()/2+15, 378, 100, 25);
		btnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 246.941;
			}});
		getContentPane().add(btnB);
		tick.repaint();
		setVisible(true);

		JButton btnEAgudo = new JButton("e");
		btnEAgudo.setBounds(getWidth()/2+15, 414, 100, 25);
		btnEAgudo.setFont(new Font("Arial", Font.PLAIN, 17));
		btnEAgudo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tick.freqOk = 329.627;
			}});
		getContentPane().add(btnEAgudo);
	}

	public void createLabels(){
		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png"));
		logo.setBounds(getWidth()/2 - 50,39,100,100);
		getContentPane().add(logo);

		freqLabel = new JLabel("Freq: 00.000");
		freqLabel.setForeground(Color.white);
		freqLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		freqLabel.setBounds(getWidth()/2 - 60,97,250,100);
		getContentPane().add(freqLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("help")) {
			new warningWindow("<html><br><br><br> Each button corresponds to a guitar string: <br><br> E = 6º string <br> A = 5º string<br> D = 4º string<br> G = 3º string<br> B = 2º string<br> e = 1º string</html>", warningWindow.OK, null, this);
		}
	}
}