package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import say.swing.JFontChooser;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;

public class FontChooseDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static FontChooseDialog dialog;
	/**
	 * Launch the application.
	 */
	
	public static FontChooseDialog getInstance () {
		if (dialog == null)
			dialog = new FontChooseDialog();
		return dialog;
	}
	/*public static void main(String[] args) {
		try {
			FontChooseDialog dialog = new FontChooseDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public FontChooseDialog() {
		setTitle("\u041D\u0430\u0441\u0442\u0440\u043E\u0439\u043A\u0438 \u0448\u0440\u0438\u0444\u0442\u0430");
		setBounds(100, 100, 450, 372);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JFontChooser fontChooser = new JFontChooser();
		fontChooser.setSelectedFont(new Font("Tahoma", 0, 12));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			contentPanel.add(fontChooser);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setUIFont(new FontUIResource(fontChooser.getSelectedFont()));
						SwingUtilities.updateComponentTreeUI(contentPanel.getParent());						
						SwingUtilities.updateComponentTreeUI(mainForm.frame);
						dialog.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("Cancel");
//						fontChooser.setSelectedFont(new Font ("Tahoma", 0, 11));
						setUIFont(new FontUIResource(fontChooser.getSelectedFont()));
						SwingUtilities.updateComponentTreeUI(contentPanel.getParent());						
						SwingUtilities.updateComponentTreeUI(mainForm.frame);
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
    public void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
}
