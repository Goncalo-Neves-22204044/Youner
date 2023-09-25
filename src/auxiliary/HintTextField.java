package auxiliary;
import java.awt.Color;  
import java.awt.Font;  
import java.awt.event.FocusAdapter;  
import java.awt.event.FocusEvent;  
import javax.swing.JTextField;  

public class HintTextField extends JTextField { 

	private static final long serialVersionUID = 1L;

	Font gainFont = new Font("Tahoma", Font.PLAIN, 11);  
	Font lostFont = new Font("Tahoma", Font.ITALIC, 11);  

	public HintTextField(final String hint) {  

		setText(hint);  
		setFont(lostFont);  
		setForeground(Color.WHITE);  

		this.addFocusListener(new FocusAdapter() {  

			@Override  
			public void focusGained(FocusEvent e) {  
				if (getText().equals(hint)) {  
					setText("");  
					setFont(gainFont);
					setForeground(Color.WHITE);
				} else {  
					setText(getText());  
					setFont(gainFont);  
				}  
			}  

			@Override  
			public void focusLost(FocusEvent e) {  
				if (getText().equals(hint)|| getText().length()==0) {  
					setText(hint);  
					setFont(lostFont);  
					setForeground(Color.RED);
				} else {  
					setText(getText());
					setFont(gainFont);   
				}  
			}  
		});  

	}  
}  