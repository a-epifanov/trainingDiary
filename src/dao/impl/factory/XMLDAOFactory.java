package dao.impl.factory;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import dao.DAOFactory;
import dao.IConfigDAO;
import dao.IExerciseDAO;
import dao.IResultDAO;
import dao.ITrainingPlanDAO;
import dao.impl.xml.XMLConfigDAO;
import dao.impl.xml.XMLExerciseDAO;
import dao.impl.xml.XMLResultDAO;
import dao.impl.xml.XMLTrainingPlanDAO;

import javax.xml.parsers.*;

/**
* XML DAO Factory
*
* @version 1.0
* @author Nikita Levchenko
*/
public class XMLDAOFactory extends DAOFactory {

	/*public Document exerciseDoc;
	public Document resultDoc;
	public Document trainingPlanDoc;
	private File     exerciseFile;
	private File     resultFile;
	private File     trainingPlanFile;*/
	
	@Override
	public IExerciseDAO getExerciseDAO() {
		return new XMLExerciseDAO();
	}
	
	@Override
	public ITrainingPlanDAO getTrainingPlanDAO() {
		return new XMLTrainingPlanDAO();
	}
	
	@Override
	public IResultDAO getResultDAO() {
		return new XMLResultDAO();
	}

	@Override
	public IConfigDAO getConfigDAO() {
		return new XMLConfigDAO();
	}
	
	/*@Override
	public void createDataSource() {
		exerciseFile     = new File("src/resources/exercises.xml");
		resultFile       = new File("src/resources/results.xml");
		trainingPlanFile = new File("src/resources/plans.xml");
		
		DocumentBuilderFactory dbf = null;
		DocumentBuilder        db  = null;
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (exerciseFile.exists()) {
            	exerciseDoc = db.parse(exerciseFile);
            } else {

            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
        		try {
        			exerciseDoc = factory.newDocumentBuilder().newDocument();
        		} catch (ParserConfigurationException e) {
        			e.printStackTrace();
        		}
        		
        		Element root = exerciseDoc.createElement("root");
        		exerciseDoc.appendChild(root);
            }
            
            exerciseDoc.getDocumentElement().normalize();

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }

		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (resultFile.exists()) {
            	resultDoc = db.parse(resultFile);
            } else {
            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
        		try {
        			resultDoc = factory.newDocumentBuilder().newDocument();
        		} catch (ParserConfigurationException e) {
        			e.printStackTrace();
        		}
        		
        		Element root = resultDoc.createElement("root");
        		resultDoc.appendChild(root);
            }
            
            resultDoc.getDocumentElement().normalize();

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (trainingPlanFile.exists()) {
            	trainingPlanDoc = db.parse(trainingPlanFile);
            } else {
            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
        		try {
        			trainingPlanDoc = factory.newDocumentBuilder().newDocument();
        		} catch (ParserConfigurationException e) {
        			e.printStackTrace();
        		}
        		
        		Element root = trainingPlanDoc.createElement("root");
        		trainingPlanDoc.appendChild(root);
            }
            
            trainingPlanDoc.getDocumentElement().normalize();

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
	}

	@Override
	public void closeDataSource() {
		Transformer transformer = null;
		
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		exerciseDoc.normalize();
		resultDoc.normalize();
		trainingPlanDoc.normalize();
		
		try {
			transformer.transform(new DOMSource(exerciseDoc), new StreamResult(exerciseFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		try {
			transformer.transform(new DOMSource(resultDoc), new StreamResult(resultFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		try {
			transformer.transform(new DOMSource(trainingPlanDoc), new StreamResult(trainingPlanFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}*/

}
