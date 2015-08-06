package client;

import java.awt.Component;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.toedter.calendar.JCalendar;

import entities.Exercise;
import entities.Task;
import entities.Training;
import entities.TrainingPlan;

class MyDropTargetListener extends DropTargetAdapter {

    private DropTarget dropTarget;
    private JTable table;
    public static int id;
    public static String text;
    public MyDropTargetListener(JTable jtable) {
        table = jtable;
        dropTarget = new DropTarget(jtable, DnDConstants.ACTION_COPY, this, true, null);
    }

    @Override
    public void drop(DropTargetDropEvent event) {
    	if (mainForm.currentTrainingPlan != null) {
	        try {
	            DropTarget test = (DropTarget) event.getSource();
	            Component ca = (Component) test.getComponent();
	            Point dropPoint = ca.getMousePosition();
	            Transferable tr = event.getTransferable();
	
	            if (event.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	                text = (String)tr.getTransferData(DataFlavor.stringFlavor);
	
	                if (text != null) {
	                	id = Integer.valueOf(text.substring(text.lastIndexOf(" ") + 1, text.length()));
	                	EnterDetailsDialog.getInstance().setVisible(true);
;
	                  /*  if (mainForm.currentTraining == null) {
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
	                    newRow.add(text.substring(0, text.lastIndexOf(" ")));
	                    DefaultTableModel data = (DefaultTableModel)table.getModel();
	                    newRow.add(String.valueOf(task.getSetsCount()));
	                    newRow.add(String.valueOf(task.getRepetitionsCount()));
	                    newRow.add(String.valueOf(task.getWeight()));
	                    data.addRow(newRow);
	                    table.setModel(data);
	                    table.revalidate();
	                    table.repaint();*/
	                    
	                    event.dropComplete(true);
	                }
	            } else {
	                event.rejectDrop();
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            event.rejectDrop();
	        }
    	} else {
    		 JOptionPane.showMessageDialog(new JFrame(), "Текущий план не выбран", "Ошибка", JOptionPane.ERROR_MESSAGE);	
    	}
    }
}
