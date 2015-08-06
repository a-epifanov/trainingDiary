package client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Exercise;
import javafx.scene.layout.Border;
import transfer.Transfer;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class EditExerciseDialog extends JDialog {

	private JPanel contentPanel = new JPanel();
	private static EditExerciseDialog dialog;
	private JTextField nameTextField;
	private JTextField selectedPicturePathField;
	private JLabel nameLabel;
	private JLabel descriptionLabel;
	private JLabel pictureLabel;
	private JTextArea descriptionArea;
	private JButton chooseButton;
	private GroupLayout gl_contentPanel;
	private JPanel buttonPane;
	private JButton okButton;
	private JButton cancelButton;
	private JFileChooser pictureChooser;
	private JTextField textField;
	
	/**
	 * Launch the application.
	 */
	
	public static EditExerciseDialog getInstance () {
		if (dialog == null) {
			dialog = new EditExerciseDialog();
		} else {
			dialog.dispose();
			dialog = new EditExerciseDialog();
		}
		return dialog;
	}

	
	public EditExerciseDialog() {
		setTitle("Редактирование упражнения");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		nameLabel        = new JLabel("Название:");
		descriptionLabel = new JLabel("Описание:");
		pictureLabel     = new JLabel("Картинка:");
		
		System.out.println(mainForm.currentExercise.getName());
		nameTextField = new JTextField(mainForm.currentExercise.getName());
		nameTextField.setColumns(10);
		
		descriptionArea = new JTextArea(mainForm.currentExercise.getDescription());
		descriptionArea.setLineWrap(true);
		descriptionArea.setBorder(UIManager.getBorder("ComboBox.border"));
		descriptionArea.setFont(getFont());
		
		selectedPicturePathField = new JTextField();
		selectedPicturePathField.setColumns(10);
		selectedPicturePathField.setEditable(false);
		
		pictureChooser = new JFileChooser();
		FileNameExtensionFilter filt = new FileNameExtensionFilter("Изображения png", "png");
		pictureChooser.setFileFilter(filt);
		
		chooseButton = new JButton("Выбрать...");
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int result = pictureChooser.showOpenDialog(dialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                	String pathString = pictureChooser.getSelectedFile().getAbsolutePath();
                	try {
	    		        if (pathString.substring(pathString.lastIndexOf('.'), pathString.length()).equalsIgnoreCase(".png")) {
	    		        	selectedPicturePathField.setText(pictureChooser.getSelectedFile().getAbsolutePath());   	
	    		        } else {
	    		        	JOptionPane.showMessageDialog(new JFrame(), "Выберите файл с расширением '.png'", "Ошибка", JOptionPane.ERROR_MESSAGE);
	    		        }
    				} catch (Exception ex) {
    					JOptionPane.showMessageDialog(new JFrame(), "Выберите файл с расширением '.png'", "Ошибка", JOptionPane.ERROR_MESSAGE);
    				}
                    
                }
               // else if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())); 
                   // JFileChooser 
                	//System.out.println("!!!!");
                /*	String pathString = pictureChooser.getSelectedFile().getAbsolutePath();
    				System.out.println(pathString.substring(pathString.lastIndexOf('.'), pathString.length()));
    		        if (pathString.substring(pathString.lastIndexOf('.'), pathString.length()).equals(".png")) {
    		        	if (result == JFileChooser.APPROVE_OPTION) {
    		        		//selectedPicturePathField.setText(pictureChooser.getSelectedFile().getAbsolutePath());
    		        	}
    		        } else {
    		        	JOptionPane.showMessageDialog(new JFrame(), "Выбран неверный файл", "Ошибка", JOptionPane.ERROR_MESSAGE);
    		        }*/
			}
		});
		
		JLabel lblNewLabel = new JLabel("Категория:");
		
		textField = new JTextField(mainForm.currentExercise.getCategory());
		textField.setColumns(10);
		gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(pictureLabel)
						.addComponent(lblNewLabel)
						.addComponent(nameLabel)
						.addComponent(descriptionLabel))
					.addGap(4)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(descriptionArea, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(selectedPicturePathField, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chooseButton))
						.addComponent(nameTextField, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(nameLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(descriptionLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(pictureLabel)
						.addComponent(selectedPicturePathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chooseButton))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
		
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
		okButton = new JButton("Сохранить");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!descriptionArea.getText().trim().equals("") && !textField.getText().trim().equals("") && !nameTextField.getText().trim().equals("")) {
					mainForm.currentExercise.setCategory(textField.getText());
					mainForm.currentExercise.setDescription(descriptionArea.getText());
					mainForm.currentExercise.setName(nameTextField.getText());
					mainForm.exerciseDAO.update(mainForm.currentExercise);
					mainForm.exercisesTabTextArea.setText(descriptionArea.getText());
					if (!selectedPicturePathField.getText().trim().equals("")) {
				        File file = new File(selectedPicturePathField.getText());
					        // Сменить бы проверку
					        File newFile = new File("img/" + String.valueOf(mainForm.currentExercise.getId()) + ".png");
					        ImageIcon imageIcon;
					        try {
					        	imageIcon = new ImageIcon(file.getAbsolutePath());
					        	if (imageIcon.getIconWidth() >= 100 && imageIcon.getIconHeight() >= 100) { 
							        try {
										newFile.createNewFile();
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
							        try {
										Files.copy(Paths.get(file.getPath()), Paths.get(newFile.getPath()), StandardCopyOption.REPLACE_EXISTING);
									} catch (IOException ex) {
										// TODO Auto-generated catch block
										ex.printStackTrace();
									} 
							        mainForm.currentExerciseJLabelWithId.setIcon(new ImageIcon("img/" + String.valueOf(mainForm.currentExercise.getId()) + ".png"));
						        	for (Component component : mainForm.trainingPlansTabExercisesPanel.getComponents()) {
						        		JLabelWithId jLabelWithId = (JLabelWithId) component;
						        		if (jLabelWithId.id == mainForm.currentExerciseJLabelWithId.id) {
						        			jLabelWithId.setIcon(new ImageIcon("img/" + String.valueOf(mainForm.currentExercise.getId()) + ".png"));
						        		}
						        	}
						        } else {
						        	JOptionPane.showMessageDialog(new JFrame(), "Размер изображения не должен превышать 100х100 пикселей", "Ошибка", JOptionPane.ERROR_MESSAGE);
						        }
					        } catch (Exception ex) {
		    					JOptionPane.showMessageDialog(new JFrame(), "Ошибка загрузки изображения", "Ошибка", JOptionPane.ERROR_MESSAGE);
					        }
					        
					}
					/////////изменить лейблы
					mainForm.currentExerciseJLabelWithId.setText(mainForm.currentExercise.getName());
					for (Component component : mainForm.trainingPlansTabExercisesPanel.getComponents()) {
		        		JLabelWithId jLabelWithId = (JLabelWithId) component;
		        		if (jLabelWithId.id == mainForm.currentExerciseJLabelWithId.id) {
		        			jLabelWithId.setText(mainForm.currentExercise.getName());
		        		}
		        	}
					SwingUtilities.updateComponentTreeUI(mainForm.currentExerciseJLabelWithId.getParent());
					mainForm.currentExerciseJLabelWithId.revalidate();
					mainForm.currentExerciseJLabelWithId.repaint();
					
					dialog.dispose();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
						
		cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dialog.dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);	
	}

}
