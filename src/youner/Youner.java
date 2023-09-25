package youner;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


import design.OptionWindow;

public class Youner{

	public Youner(){
		new OptionWindow();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub		

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				} else {
					UIManager.setLookAndFeel  ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				}
			}
		} catch (Exception e) {
			System.out.println("Failed");
		}

		new Youner();
	}
}
