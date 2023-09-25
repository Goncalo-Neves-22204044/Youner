package design;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;

import dataBase.Game;

public class GameWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Game newGame;

	private String chords[] = {"C", "D", "A", "G"};
	private JLabel[] stars;
	private JLabel[] points;
	private JLabel[] imgs = new JLabel[4];
	private JLabel lblTime = new JLabel("");
	private JLabel lblScore = new JLabel("Score 0");
	private JLabel logo, game;
	private JButton[] button = new JButton[4];
	private JButton btnStart = new JButton("Start");
	private JButton btnCancel, btnPlayAgain, btnScore, btnPlayerScore, btnGlobalScore;
	private String date;
	private int selectedQuestion=-1, score=0;

	private boolean answered = false;

	private Timer timer;
	private Timer timerBtn;

	private LocalDateTime myDateObj;

	private Boolean inGame = false;


	private int count = 0;
	private static final int timerPeriod = 1000;
	private static final int maxCount = 30;
	private static final int timerPeriodBtn = 1000;

	public GameWindow(){
		super();
		start();

		newGame = new Game();

		setSize(310,300);
		setTitle("Youner - Game");
		setIconImage(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png").getImage());
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		getContentPane().setBackground(Color.black);
		getContentPane().setLayout(null);

		createImages();
		createLabels();
		createButtons();

		setVisible(true);
	}

	// CREATE BUTTONS
	public void createButtons() {
		btnStart.setBounds(90,200,125,25);
		btnStart.addActionListener(this);
		btnStart.setActionCommand("start");
		getContentPane().add(btnStart);

		btnScore = new JButton("Classifications");
		btnScore.setBounds(90, 230, 125, 25);
		btnScore.addActionListener(this);
		btnScore.setActionCommand("score");
		getContentPane().add(btnScore);

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(90, 260, 125, 25);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("Cancel");
		getContentPane().add(btnCancel);

		btnPlayAgain = new JButton("Play Again");
		btnPlayAgain.setBounds(90, 220, 125, 25);
		btnPlayAgain.addActionListener(this);
		btnPlayAgain.setActionCommand("Again");
		btnPlayAgain.setVisible(false);
		getContentPane().add(btnPlayAgain);

		btnPlayerScore = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//Right_Arrow.png"));
		btnPlayerScore.setBounds(275, 10, 30, 30);
		btnPlayerScore.addActionListener(this);
		btnPlayerScore.setActionCommand("player");
		btnPlayerScore.setVisible(false);
		getContentPane().add(btnPlayerScore);

		btnGlobalScore = new JButton(new ImageIcon(System.getProperty("user.dir") + "//Images//Left_Arrow.png"));
		btnGlobalScore.setBounds(5, 10, 30, 30);
		btnGlobalScore.addActionListener(this);
		btnGlobalScore.setActionCommand("global");
		btnGlobalScore.setVisible(false);
		getContentPane().add(btnGlobalScore);
	}


	// STARTS GAME
	public void startGame(){
		lblScore.setBounds(250, 10, 100, 15);
		lblScore.setForeground(Color.WHITE);
		getContentPane().add(lblScore);

		for(int i=0; i<button.length; i++){
			imgs[i] = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//" + chords[i] + ".png"));
			button[i] = new JButton(chords[i]);
			button[i].setBackground(Color.BLACK);
			button[i].setForeground(Color.WHITE);
			button[i].setBounds(18+(i*70),210,60,25);
			button[i].setFocusable(false);
			button[i].addActionListener(this);
			getContentPane().add(button[i]);
		}

		lblTime.setBounds(105,10,150,15);
		lblTime.setForeground(Color.white);
		getContentPane().add(lblTime);

		generateRandomQuestion();
		timer.start();
	}

	// CHOOSE A CHORD RANDOMLY
	public void generateRandomQuestion(){
		if(selectedQuestion>=0) {
			remove(imgs[selectedQuestion]);
		}

		Random generator = new Random();
		int random = generator.nextInt(imgs.length);
		selectedQuestion=random;
		imgs[random].setBounds(getWidth()/2 - 65,35,130,160);
		getContentPane().add(imgs[random]);
		revalidate();
		repaint();
	}

	// CREATE STARS IMAGES
	public void createImages(){
		stars=new JLabel[5];

		for(int i=0; i<stars.length;i++){
			stars[i]=new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//star.png"));
			stars[i].setBounds(55 + (42*i), 150, 32, 32);
			stars[i].setVisible(false);
			getContentPane().add(stars[i]);
		}
	}

	// CREATE LABELS
	public void createLabels(){
		points=new JLabel[5];
		int currentPoints = 0;

		for(int i=0; i<stars.length;i++){
			currentPoints = currentPoints + 10;
			String str1 = Integer.toString(currentPoints);

			points[i] = new JLabel(str1);
			points[i].setForeground(Color.white);
			points[i].setBounds(65 + (42*i), 182, 32, 32);
			points[i].setVisible(false);
			getContentPane().add(points[i]);
		}

		logo = new JLabel(new ImageIcon(System.getProperty("user.dir") + "//Images//icon.png"));
		logo.setBounds(102,35,100,100);
		getContentPane().add(logo);

		game = new JLabel("Youner Game");
		game.setForeground(Color.white);
		game.setFont(new Font("Arial", Font.PLAIN, 30));
		game.setBounds(60,100,250,100);
		getContentPane().add(game);
	}

	// GET AND PRINT CURRENT DATE
	public void Date() {
		JLabel label = new JLabel();
		label.setBounds(150, 10, 200, 20);
		getContentPane().add(label);

		myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		date = myDateObj.format(myFormatObj);

		label.setText("Dia: " + date);
		label.setBounds(10, 10, 200, 20);
		label.setForeground(Color.white);
	}

	// ADD SCORE TO DATABASE
	public void addScore(){

		System.out.println("Email: " + LoginWindow.emailLogin);
		System.out.println("Score: " + score);
		System.out.println("Data: " + date);

		if(newGame.addScore(date, score, LoginWindow.emailLogin, 1) == 0){
			new warningWindow("Score successfully added!", warningWindow.OK, null, this);
		}
		else{
			new warningWindow("Failed to add score!", warningWindow.OK, null, this);
		}
	}

	// GET GLOBAL SCORES
	public void getScore(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		Object[][] scores = newGame.getScore();
		String[] columns= new String[]{"Name","Score", "Country","Date"};

		JTable table = new JTable(scores, columns){
			private static final long serialVersionUID = 1L;
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}
		};

		table.setShowGrid(true);
		table.setSelectionBackground(Color.LIGHT_GRAY);
		table.getTableHeader().setBackground(new Color(0, 0, 255));

		table.getColumnModel().getColumn(3).setPreferredWidth(100);       

		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(5,40,300,200);   
		getContentPane().add(sp); 

		JLabel txtScore = new JLabel("Global Ranking");
		txtScore.setForeground(Color.WHITE);
		txtScore.setFont(new Font("Arial", Font.PLAIN, 25));
		txtScore.setBounds(75, 10, 175, 30);
		getContentPane().add(txtScore);
	}

	//GET PLAYER SCORE
	public void getPlayerScore(){
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		Object[][] scores = newGame.getPlayerScore(LoginWindow.emailLogin);
		String[] columns= new String[]{"Name","Score", "Country","Date"};

		JTable table = new JTable(scores, columns){
			private static final long serialVersionUID = 1L;
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}
		};
		table.setShowGrid(true);
		table.setSelectionBackground(Color.BLACK);

		table.getColumnModel().getColumn(3).setPreferredWidth(100);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);

		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(5,40,300,200);   
		getContentPane().add(sp); 

		JLabel txtScore = new JLabel("Your Ranking");
		txtScore.setForeground(Color.WHITE);
		txtScore.setFont(new Font("Arial", Font.PLAIN, 25));
		txtScore.setBounds(80, 10, 175, 30);
		getContentPane().add(txtScore);
	}

	// STARTS GAME TIMER
	public void start() {
		timer = new Timer(timerPeriod, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (count <= maxCount) {
					String text =(maxCount - count) + " seconds left";
					lblTime.setText(text);
					count++;
				} 
				else {
					inGame = false;
					((Timer) e.getSource()).stop();
					remove(imgs[selectedQuestion]);
					lblTime.setText("Game Over");
					lblTime.setFont (lblTime.getFont ().deriveFont (30.0f));
					lblTime.setBounds(75,10,300,200);
					Date();
					btnPlayAgain.setVisible(true);
					btnCancel.setBounds(getWidth()/2 - 50, 250, 100, 25);

					addScore();

					for(int i=0; i<button.length && i<imgs.length; i++){
						button[i].setVisible(false);
						imgs[i].setVisible(false);
					}

					int totalStars = 0;
					if(score >= 10 && score < 20){
						totalStars=1;
					}
					else if(score >= 20 && score < 30){
						totalStars=2;
					}
					else if(score >= 30 && score < 40){
						totalStars=3;
					}
					else if(score >= 40 && score < 50){
						totalStars=4;
					}
					else if(score > 10) {
						totalStars=5;
					}

					for(int i=totalStars-1; i>=0; i--){
						stars[i].setVisible(true);
						points[i].setVisible(true);
					}
				}
			}
		});

		// STARTS TIMER TO CHANGE QUESTIONS
		timerBtn = new Timer(timerPeriodBtn, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<button.length; i++)
					button[i].setBackground(Color.BLACK);
				generateRandomQuestion();
				answered=false;
				((Timer) e.getSource()).stop();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("start")) {
			inGame = true;
			remove(btnStart);
			remove(logo);
			remove(btnScore);
			remove(game);
			startGame();
			revalidate();
			repaint();
			btnCancel.setVisible(true);
		}
		else if(e.getActionCommand().equals("Cancel")) {
			if(inGame) {
				timer.stop();
				timerBtn.stop();
				dispose();
				new GameWindow();
			}
			else {
				dispose();
				new MainWindow();
			}

		}
		else if(e.getActionCommand().equals("score")) {
			getContentPane().removeAll();	
			getContentPane().add(btnPlayAgain);
			getContentPane().add(btnPlayerScore);
			btnPlayAgain.setBounds(btnCancel.getBounds());
			btnPlayAgain.setVisible(true);
			btnPlayAgain.setText("Cancel");
			btnPlayerScore.setVisible(true);
			getScore();
			repaint();
			revalidate();
		}
		else if(e.getActionCommand().equals("player")) {
			getContentPane().removeAll();	
			getContentPane().add(btnPlayAgain);
			getContentPane().add(btnGlobalScore);
			btnPlayAgain.setBounds(btnCancel.getBounds());
			btnPlayAgain.setVisible(true);
			btnPlayAgain.setText("Cancel");
			btnGlobalScore.setVisible(true);
			getPlayerScore();
			repaint();
			revalidate();
		}
		else if(e.getActionCommand().equals("global")) {
			btnScore.doClick();
		}
		else if(e.getActionCommand().equals("Again")) {
			dispose();
			new GameWindow();
		}

		for(int i=0; i<button.length; i++) {
			if(e.getSource()==button[i]){
				if(chords[selectedQuestion].equals(button[i].getText()) && !answered){
					answered=true;
					score = score + 2;
					lblScore.setText("Score " + score);
					button[i].setBackground(Color.GREEN);
					count-=1;
					if(count<0) count=0;
					timerBtn.start();
				}else if(!answered && !button[i].getBackground().equals(Color.RED)) {
					button[i].setBackground(Color.RED);
					count+=2;
				}
			}
		}
	}
}