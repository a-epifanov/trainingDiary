package program;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import javax.imageio.ImageIO;

import dao.DAOFactory;
import dao.IExerciseDAO;
import dao.IResultDAO;
import dao.ITrainingPlanDAO;
import entities.Exercise;
import entities.Result;
import entities.Task;
import entities.Training;
import entities.TrainingPlan;
import jxl.write.WriteException;
import transfer.Exel;
import transfer.Transfer;


public class test {

	public static void main(String[] args) {
		
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.XML);
		
		IExerciseDAO exerciseDAO = daoFactory.getExerciseDAO();
		IResultDAO resultDAO = daoFactory.getResultDAO();
		ITrainingPlanDAO trainingPlanDAO = daoFactory.getTrainingPlanDAO();
		
		for (TrainingPlan trpl : trainingPlanDAO.findAll()) {
			System.out.println("Plan id = " + trpl.getPlanId());
			for (Training tr : trpl.getTrainings()) {
				System.out.println(tr.getDate().toLocaleString());
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}



	}

}
