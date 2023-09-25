package auxiliary;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.event.FocusAdapter;  
import java.awt.event.FocusEvent;

import javax.swing.JPasswordField;

public class HintPasswordField extends JPasswordField {  
	private static final long serialVersionUID = 1L;

	public char echoChar;
	public boolean passwordVisible;

	Font gainFont = new Font("Tahoma", Font.PLAIN, 11);  
	Font lostFont = new Font("Tahoma", Font.ITALIC, 11);  

	public HintPasswordField(final String hint) {  
		echoChar = getEchoChar();
		passwordVisible = false;
		
		setText(hint);  
		setEchoChar((char)0);
		setFont(lostFont);  
		setForeground(Color.WHITE);  

		this.addFocusListener(new FocusAdapter() {  

			@Override  
			public void focusGained(FocusEvent e) {  
				if (new String(getPassword()).equals(hint)) {  
					setText("");  
					setFont(gainFont);
					setForeground(Color.WHITE);
					
					if(!passwordVisible)
						setEchoChar(echoChar);
					else
						setEchoChar((char) 0);
				} else {  
					setText(new String(getPassword()));  
					setFont(gainFont);  
				}  
			}  

			@Override  
			public void focusLost(FocusEvent e) {  
				if (new String(getPassword()).equals(hint)|| new String(getPassword()).length()==0) {  
					setText(hint);  
					setFont(lostFont);  
					setForeground(Color.RED); 
					setEchoChar((char) 0);
					
				} else {  
					setText(new String(getPassword()));  
					setFont(gainFont);  
				}  
			}  
		});  
	}  
}  