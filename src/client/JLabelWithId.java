package client;

import javax.swing.JLabel;

public class JLabelWithId extends JLabel {
	public int id;
	
	public JLabelWithId(String text, int Id) {
		super(text);
		id = Id;
	}
}
