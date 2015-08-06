package client;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entities.Task;
import entities.Training;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EnterDetailsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField quantityTextField;
	private JTextField quantityRepeatsTextField;
	private JTextField wheightTextField;
	private static EnterDetailsDialog dialog;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EnterDetailsDialog dialog = new EnterDetailsDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	public static EnterDetailsDialog getInstance () {
		if (dialog == null) {
			dialog = new EnterDetailsDialog();
		} else {
			dialog.dispose();
			dialog = new EnterDetailsDialog();
		}
		return dialog;
	}

	/**
	 * Create the dialog.
	 */
	public EnterDetailsDialog() {
		setTitle("\u0414\u043E\u0431\u0430\u0432\u043B\u0435\u043D\u0438\u0435 \u0443\u043F\u0440\u0430\u0436\u043D\u0435\u043D\u0438\u044F");
		setBounds(100, 100, 450, 223);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u043B\u0430\u043D\u0438\u0440\u0443\u0435\u043C\u044B\u0439 \u0440\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
		);
		JLabel quantityLabel = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0434\u0445\u043E\u0434\u043E\u0432:");
		JLabel quantityRepeatsLabel = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E \u043F\u043E\u0432\u0442\u043E\u0440\u0435\u043D\u0438\u0439:");
		JLabel wheightLabel = new JLabel("\u0412\u0435\u0441:");
		quantityTextField = new JTextField();
		quantityTextField.setText("1");
		quantityTextField.setColumns(10);
		quantityRepeatsTextField = new JTextField();
		quantityRepeatsTextField.setText("1");
		quantityRepeatsTextField.setColumns(10);
		wheightTextField = new JTextField();
		wheightTextField.setText("0");
		wheightTextField.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(quantityRepeatsLabel)
						.addComponent(quantityLabel)
						.addComponent(wheightLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(wheightTextField)
						.addComponent(quantityRepeatsTextField)
						.addComponent(quantityTextField, GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(quantityLabel)
						.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(quantityRepeatsLabel)
						.addComponent(quantityRepeatsTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(wheightLabel)
						.addComponent(wheightTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\u041E\u041A");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						boolean taskAdded = false;
						if (!quantityTextField.getText().trim().equals("") && !quantityRepeatsTextField.getText().trim().equals("") && !wheightTextField.getText().trim().equals("")) {
							
							try {
								if (Integer.valueOf(quantityTextField.getText()) > 0
										&& Integer.valueOf(quantityRepeatsTextField.getText()) > 0
										&& Integer.valueOf(wheightTextField.getText()) >= 0) {
									
								
									Task task = new Task(mainForm.findExercise(MyDropTargetListener.id), Integer.valueOf(quantityTextField.getText()), Integer.valueOf(quantityRepeatsTextField.getText()), Integer.valueOf(wheightTextField.getText()));
							        if (mainForm.currentTraining == null) {
							        	mainForm.currentTraining = new Training(mainForm.calendar.getDate(), task);
							        	System.out.println("mainForm.currentTraining == null");
							        	
							        	ArrayList<Training> trgs = mainForm.currentTrainingPlan.getTrainings();
							        	trgs.add(mainForm.currentTraining);
							        	mainForm.currentTrainingPlan.setTrainings(trgs);
							        } else {
							        	ArrayList<Task> tsks = mainForm.currentTraining.getTasks();
							        	tsks.add(task);
							        	mainForm.currentTraining.setTasks(tsks);
							        	mainForm.currentTask = task;
							        }
							        mainForm.trainingPlanDAO.deleteTraining(mainForm.currentTrainingPlan.getPlanId(), mainForm.currentTraining.getDate());
							        mainForm.trainingPlanDAO.addTraining(mainForm.currentTrainingPlan.getPlanId(), mainForm.currentTraining);
							        
							        Vector<String> newRow = new Vector<String>();
							        newRow.add(MyDropTargetListener.text.substring(0, MyDropTargetListener.text.lastIndexOf(" ")));
							        DefaultTableModel data = (DefaultTableModel) mainForm.tableSchedule.getModel();
							        newRow.add(String.valueOf(task.getSetsCount()));
							        newRow.add(String.valueOf(task.getRepetitionsCount()));
							        newRow.add(String.valueOf(task.getWeight()));
							        data.addRow(newRow);
							        mainForm.tableSchedule.setModel(data);
							        mainForm.tableSchedule.revalidate();
							        mainForm.tableSchedule.repaint();
							        taskAdded = true;
						        
								} else {
									JOptionPane.showMessageDialog(new JFrame(), "Введите корректные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
								}
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(new JFrame(), "Введены неверные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(new JFrame(), "Заполните все поля", "Ошибка", JOptionPane.ERROR_MESSAGE);
						}
						if (taskAdded) {
							dialog.dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialog.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

}
