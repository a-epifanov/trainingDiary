package transfer;

import java.util.ArrayList;
import java.util.Locale;

import entities.Task;
import entities.Training;
import entities.TrainingPlan;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Exel {

	public static void createXLSPlan(TrainingPlan trainingPlan, File file) {
		
		WorkbookSettings ws = new WorkbookSettings();
		WritableWorkbook workbook = null;
		
		WritableCellFormat arial12BoldFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD));
		WritableCellFormat arial12NoBoldFormat = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD));
		
		try {
			workbook = Workbook.createWorkbook(file, ws);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ws.setLocale(new Locale("ru", "RU"));
		
		
		
		for (Training training : trainingPlan.getTrainings()) {
			
			ArrayList<Task> tasks = training.getTasks();
							
			
			
			String dateString = String.valueOf(training.getDate().getDate()) 
					  + "-" 
					  + String.valueOf(training.getDate().getMonth() + 1)
					  + "-"
					  + String.valueOf(training.getDate().getYear() + 1900);
			System.out.println(dateString);
			WritableSheet  sheet = workbook.createSheet(dateString, 0);
			try {
				sheet.addCell(new Label(0, 0, "Упражнение", arial12BoldFormat));
				sheet.addCell(new Label(1, 0, "Количество повторений", arial12BoldFormat));
				sheet.addCell(new Label(2, 0, "Количество подходов", arial12BoldFormat));
				sheet.addCell(new Label(3, 0, "Вес", arial12BoldFormat));
			} catch (RowsExceededException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < tasks.size(); i++) {
				try {
					sheet.addCell(new Label(0, i + 1, tasks.get(i).getExecrise().getName(), arial12NoBoldFormat));
					sheet.addCell(new Label(1, i + 1, String.valueOf(tasks.get(i).getRepetitionsCount()), arial12NoBoldFormat));
					sheet.addCell(new Label(2, i + 1, String.valueOf(tasks.get(i).getSetsCount()), arial12NoBoldFormat));
					sheet.addCell(new Label(3, i + 1, String.valueOf(tasks.get(i).getWeight()), arial12NoBoldFormat));
				} catch (RowsExceededException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		}
		
		try {
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}
}
