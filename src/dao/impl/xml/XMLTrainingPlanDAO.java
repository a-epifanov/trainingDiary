package dao.impl.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import dao.ITrainingPlanDAO;
import dao.impl.factory.XMLDAOFactory;
import entities.Exercise;
import entities.Task;
import entities.Training;
import entities.TrainingPlan;

/**
* XML Task DAO
*
* @version 1.0
* @author Nikita Levchenko
*/
public class XMLTrainingPlanDAO implements ITrainingPlanDAO {
	
	private Document doc = null;
	private Document exerciseDoc = null;
	private XMLDAOFactory xMLDAOFactory = null;
	
	private File     exerciseFile;
	private File     trainingPlanFile;
	
	public XMLTrainingPlanDAO() {
	}

	@Override
	public void delete(int id) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getAttribute("id"))) {
                	element.getParentNode().removeChild(element);
                }
            }
		}
		this.closeDataSource();
	}

	@Override
	public int create(TrainingPlan trainingPlan) {
		this.createDataSource();
		
		Element  root = doc.getDocumentElement();
		NodeList trainingsNodeList   = doc.getElementsByTagName("trpl");
		Element  trpl   = doc.createElement("trpl");
		
		int id = 0;
		for (int i = 0; i < trainingsNodeList.getLength(); i++) {
			Node node = trainingsNodeList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
				Element element = (Element) node;

	            int p = Integer.valueOf(element.getAttribute("id"));
	            if (p >= id) {
	            	id = p + 1;
	            }
            }
		}
	/*	Node lastNode = trainingsNodeList.item(trainingsNodeList.getLength() - 1);
		if(lastNode.getNodeType() == Node.ELEMENT_NODE)
        {
            Element element = (Element) lastNode;

            id = Integer.valueOf(element.getAttribute("id")) + 1;
        }*/
		trpl.setAttribute("id", String.valueOf(id));
		trpl.setAttribute("name", trainingPlan.getName());
		
		for (Training training : trainingPlan.getTrainings()) {
			Element trn = doc.createElement("trn");
			
			String dateString = String.valueOf(training.getDate().getDate()) 
					  + "." 
					  + String.valueOf(training.getDate().getMonth() + 1)
					  + "."
					  + String.valueOf(training.getDate().getYear() + 1900);
			
			trn.setAttribute("dt", dateString);
			
			for (Task task : training.getTasks()) {
				Element tsk = doc.createElement("tsk");
	
				tsk.setAttribute("ex", String.valueOf(task.getExecrise().getId()));
				tsk.setAttribute("rc", String.valueOf(task.getRepetitionsCount()));
				tsk.setAttribute("sc", String.valueOf(task.getSetsCount()));
				tsk.setAttribute("w", String.valueOf(task.getWeight()));
				
				trn.appendChild(tsk);
			}
			
			trpl.appendChild(trn);
		}
		
		root.appendChild(trpl);
		this.closeDataSource();
		return id;
	}

	//@Override
	public TrainingPlan findOne(int id) {
		this.createDataSource();
		TrainingPlan result           = null;
		ArrayList<Training> trainings = new ArrayList<Training>();
		
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			
			

			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
				Element element = (Element) node;
				
				
				if (id == Integer.valueOf(element.getAttribute("id"))) {

					
					NodeList trainingsNodeList = element.getElementsByTagName("trn"); // doc.getElementsByTagName("trn");
					for (int j = 0; j < trainingsNodeList.getLength(); j++) {
						Node trainingNode = trainingsNodeList.item(j);

						if (trainingNode.getNodeType() == Node.ELEMENT_NODE) {
							Element trainingElement = (Element) trainingNode;
							ArrayList<Task> tasks = new ArrayList<Task>();
							
							NodeList tasksNodeList = trainingElement.getElementsByTagName("tsk");
							for (int k = 0; k < tasksNodeList.getLength(); k++) {
								Node taskNode = tasksNodeList.item(k);
								if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
									Element taskElement = (Element) taskNode;
									Task task = null;
									Exercise exercise = null;

									NodeList exercisesNl = exerciseDoc.getElementsByTagName("ex");
									for (int e = 0; e < exercisesNl.getLength(); e++) {
										Node exerciseNode = exercisesNl.item(e);
										if(exerciseNode.getNodeType() == Node.ELEMENT_NODE)
							            {
							                Element ExerciseElement = (Element) exerciseNode;
							                if (Integer.valueOf(taskElement.getAttribute("ex")) == Integer.valueOf(ExerciseElement.getAttribute("id"))) {
							                	exercise = new Exercise(Integer.valueOf(ExerciseElement.getAttribute("id")), ExerciseElement.getAttribute("name"), ExerciseElement.getAttribute("desc"), ExerciseElement.getAttribute("cat"));
							                }
							            }
									}
									
									task = new Task(exercise, 
														 Integer.valueOf(taskElement.getAttribute("rc")), 
														 Integer.valueOf(taskElement.getAttribute("sc")), 
														 Integer.valueOf(taskElement.getAttribute("w")));
									tasks.add(task);
								}
								
								
							}
							String dateString = trainingElement.getAttribute("dt");
							Date date = new Date(Integer.valueOf(dateString.substring(dateString.lastIndexOf(".") + 1, dateString.length())) - 1900, 
									Integer.valueOf(dateString.substring(dateString.indexOf(".") + 1, dateString.lastIndexOf("."))) - 1,
									Integer.valueOf(dateString.substring(0, dateString.indexOf("."))));
							
							trainings.add(new Training(date, tasks));
							
						}
					}
					
					
					result = new TrainingPlan(id, element.getAttribute("name"), trainings);
				}
				
            }
		}
		this.closeDataSource();
		return result;
	}

	@Override
	public List<TrainingPlan> findAll() {
		this.createDataSource();
		
		List<TrainingPlan> result = new ArrayList<TrainingPlan>();
		
		
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			
			

			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
				Element element = (Element) node;
				ArrayList<Training> trainings = new ArrayList<Training>();
					NodeList trainingsNodeList = element.getElementsByTagName("trn");
					for (int j = 0; j < trainingsNodeList.getLength(); j++) {
						Node trainingNode = trainingsNodeList.item(j);
						if (trainingNode.getNodeType() == Node.ELEMENT_NODE) {
							Element trainingElement = (Element) trainingNode;
							
							ArrayList<Task> tasks = new ArrayList<Task>();
							
							NodeList tasksNodeList = trainingElement.getElementsByTagName("tsk");
							for (int k = 0; k < tasksNodeList.getLength(); k++) {
								Node taskNode = tasksNodeList.item(k);
								if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
									Element taskElement = (Element) taskNode;
									Task task = null;
									Exercise exercise = null;

									NodeList exercisesNl = exerciseDoc.getElementsByTagName("ex");
									for (int e = 0; e < exercisesNl.getLength(); e++) {
										Node exerciseNode = exercisesNl.item(e);
										if(exerciseNode.getNodeType() == Node.ELEMENT_NODE)
							            {
											
							                Element ExerciseElement = (Element) exerciseNode;
							                if (Integer.valueOf(taskElement.getAttribute("ex")) == Integer.valueOf(ExerciseElement.getAttribute("id"))) {
							                	exercise = new Exercise(Integer.valueOf(ExerciseElement.getAttribute("id")), ExerciseElement.getAttribute("name"), ExerciseElement.getAttribute("desc"), ExerciseElement.getAttribute("cat"));
							                }
							            }
									}
									
									task = new Task(exercise, 
														 Integer.valueOf(taskElement.getAttribute("rc")), 
														 Integer.valueOf(taskElement.getAttribute("sc")), 
														 Integer.valueOf(taskElement.getAttribute("w")));
									tasks.add(task);
								}
								
								
							}
							String dateString = trainingElement.getAttribute("dt");
							Date date = new Date(Integer.valueOf(dateString.substring(dateString.lastIndexOf(".") + 1, dateString.length())) - 1900, 
									Integer.valueOf(dateString.substring(dateString.indexOf(".") + 1, dateString.lastIndexOf("."))) - 1,
									Integer.valueOf(dateString.substring(0, dateString.indexOf("."))));
							
							trainings.add(new Training(date, tasks));
							
						}
					}
					
					
					result.add(new TrainingPlan(Integer.valueOf(element.getAttribute("id")), element.getAttribute("name"), trainings));
				//}
				
            }
		}
		this.closeDataSource();
		return result;
	}

	@Override
	public void changeName(int id, String name) {
		this.createDataSource();
		
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getAttribute("id"))) {
                	element.removeAttribute("name");
                	element.setAttribute("name", name);
                }
            }
		}
		this.closeDataSource();
	}

	@Override
	public void addTraining(int planId, Training training) {
		this.createDataSource();
		System.out.println("Adding training");
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (planId == Integer.valueOf(element.getAttribute("id"))) {
        			Element trn = doc.createElement("trn");
        			
        			String dateString = String.valueOf(training.getDate().getDate()) 
        					  + "." 
        					  + String.valueOf(training.getDate().getMonth() + 1)
        					  + "."
        					  + String.valueOf(training.getDate().getYear() + 1900);
        			
        			trn.setAttribute("dt", dateString);
        			
        			for (Task task : training.getTasks()) {
        				System.out.println(task.getExecrise().getId());
        				Element tsk = doc.createElement("tsk");
        	
        				tsk.setAttribute("ex", String.valueOf(task.getExecrise().getId()));
        				tsk.setAttribute("rc", String.valueOf(task.getRepetitionsCount()));
        				tsk.setAttribute("sc", String.valueOf(task.getSetsCount()));
        				tsk.setAttribute("w", String.valueOf(task.getWeight()));
        				
        				trn.appendChild(tsk);
        			}
        			element.appendChild(trn);
                }
            }
		}
		this.closeDataSource();
	}

	@Override
	public void deleteTraining(int planId, Date trainingDate) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("trpl");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (planId == Integer.valueOf(element.getAttribute("id"))) {
                	//element.getParentNode().removeChild(element);
					NodeList trainingsNodeList = element.getElementsByTagName("trn"); // doc.getElementsByTagName("trn");
					for (int j = 0; j < trainingsNodeList.getLength(); j++) {
						Node trainingNode = trainingsNodeList.item(j);

						if (trainingNode.getNodeType() == Node.ELEMENT_NODE) {
							Element trainingElement = (Element) trainingNode;
							String dateString = String.valueOf(trainingDate.getDate()) 
									  + "." 
									  + String.valueOf(trainingDate.getMonth() + 1)
									  + "."
									  + String.valueOf(trainingDate.getYear() + 1900);
							System.out.println("!!! " + dateString + " = " + trainingElement.getAttribute("dt"));
			                if (dateString.equals(trainingElement.getAttribute("dt"))) {
			                	System.out.println("Deleting");
			                	trainingElement.getParentNode().removeChild(trainingElement);
			                }
						}
					}
                }
            }
		}
		this.closeDataSource();
	}
	

	public void createDataSource() {
		exerciseFile     = new File("src/resources/exercises.xml");
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
            if (trainingPlanFile.exists()) {
            	doc = db.parse(trainingPlanFile);
            } else {
            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
        		try {
        			doc = factory.newDocumentBuilder().newDocument();
        		} catch (ParserConfigurationException e) {
        			e.printStackTrace();
        		}
        		
        		Element root = doc.createElement("root");
        		doc.appendChild(root);
            }
            
            doc.getDocumentElement().normalize();

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
	}


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
		doc.normalize();
		
		try {
			transformer.transform(new DOMSource(exerciseDoc), new StreamResult(exerciseFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}


		try {
			transformer.transform(new DOMSource(doc), new StreamResult(trainingPlanFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
}
