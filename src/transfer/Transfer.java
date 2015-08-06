package transfer;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
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

import client.JLabelWithId;
import client.mainForm;
import dao.IExerciseDAO;
import dao.ITrainingPlanDAO;
import entities.Exercise;
import entities.Task;
import entities.Training;
import entities.TrainingPlan;

public class Transfer {
	public static void exportPlan(TrainingPlan trainingPlan, File file) {
		//File file = new File(fileName);
		Document doc = createDataSource(file);
		Element  root = doc.getDocumentElement();
		
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		
		for (Training training : trainingPlan.getTrainings()) {
			for (Task task : training.getTasks()) {
				boolean shouldAdd = true;
				for (Exercise exercise : exercises) {
					if (exercise.getId() == task.getExecrise().getId()) {
						shouldAdd = false;
					}
				}
				if (true == shouldAdd) {
					exercises.add(task.getExecrise());
				}
			}
		}
		NodeList nl   = doc.getElementsByTagName("ex");
		for (Exercise exercise : exercises) {
			
			Element  ex   = doc.createElement("ex");
			
			ex.setAttribute("id", String.valueOf(exercise.getId()));
			ex.setAttribute("name", exercise.getName());
			ex.setAttribute("desc", exercise.getDescription());
			ex.setAttribute("cat", exercise.getCategory());
			
			File newFile = new File("img/" + String.valueOf(exercise.getId() + ".png"));
			if (newFile.exists()) {
			    //  ENCODING
			        BufferedImage img = null;
					try {
						img = ImageIO.read(newFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}    
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        try {
						ImageIO.write(img, "png", baos);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}    
			        try {
						baos.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        Encoder encoder = Base64.getEncoder();
			        String encodedImage = encoder.encodeToString(baos.toByteArray());// .encodeToString(baos.toByteArray());
			        try {
						baos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        ex.setAttribute("img", encodedImage);
			} else {
				ex.setAttribute("img", "");
			}
			
			
			root.appendChild(ex);
		}
		
		
		
		
		NodeList trainingsNodeList   = doc.getElementsByTagName("trpl");
		Element  trpl   = doc.createElement("trpl");
		
		trpl.setAttribute("id", String.valueOf(trainingsNodeList.getLength()));
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
		closeDataSource(doc, file);
	}
	
	public static void importPlan(File file, IExerciseDAO exerciseDAO, ITrainingPlanDAO trainingPlanDAO) {
		//File file = new File(fileName);
		Document doc = createDataSource(file);
		ArrayList<int[]> conformities = new ArrayList<int[]>();
		NodeList nle = doc.getElementsByTagName("ex");
		
		ArrayList<Training> trainings = new ArrayList<Training>();

		
		for (int i = 0; i < nle.getLength(); i++) {
			Node node = nle.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                int[] mass = new int[2];
                mass[0] = Integer.valueOf(element.getAttribute("id"));
                
                Exercise exercise = new Exercise( element.getAttribute("name"), 
		                element.getAttribute("desc"), 
		                element.getAttribute("cat"));
                
                exercise.setId(exerciseDAO.create(exercise));
                mass[1] = exercise.getId();
               /* mass[1] = exerciseDAO.create(new Exercise( element.getAttribute("name"), 
                		                element.getAttribute("desc"), 
                		                element.getAttribute("cat")));*/
                

                String encodedImage = element.getAttribute("img");
                System.out.println(encodedImage);
                if (!encodedImage.equals("")) {
                
    		        Decoder decoder = Base64.getDecoder();
    		        byte[] bytes = decoder.decode(encodedImage);
    		        BufferedImage image = null;
    				try {
    					image = ImageIO.read(new ByteArrayInputStream(bytes));
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			//	mainForm.lblNewLabel_1.setIcon(new ImageIcon(image));
			       /* Decoder decoder = Base64.getDecoder();
			        byte[] bytes = decoder.decode(encodedImage);
			        BufferedImage image = null;
					try {
						image = ImageIO.read(new ByteArrayInputStream(bytes));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
			        lblNewLabel.setIcon(new ImageIcon(image));*/
					BufferedImage bi = (BufferedImage) image;
			        File outputfile = new File("img/" + String.valueOf(mass[1]) + ".png");

			        try {
						ImageIO.write(bi, "png", outputfile);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

			        try {
			        	outputfile.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }
                conformities.add(mass);
                
                
				mainForm.exercises.add(exercise);
				JLabelWithId lblNewLabel = new JLabelWithId(exercise.getName(), exercise.getId());
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
				JLabelWithId lblNewLabel1 = new JLabelWithId(exercise.getName(), exercise.getId());
				lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
				lblNewLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
				lblNewLabel1.setVerticalTextPosition(SwingConstants.BOTTOM);
				///Запилить другую проверку
				File newFile = new File("img/" + String.valueOf(exercise.getId() + ".png"));
				if (newFile.exists()) {
					lblNewLabel.setIcon(new ImageIcon(newFile.getPath()));
					lblNewLabel1.setIcon(new ImageIcon(newFile.getPath()));
				} else {
					lblNewLabel.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
					lblNewLabel1.setIcon(new ImageIcon(mainForm.class.getResource("/resources/placeholder.png")));
				}
				
				lblNewLabel1.addMouseListener(mainForm.exercisesMouseListener);
		        DragSource ds0 = new DragSource();
		        ds0.createDefaultDragGestureRecognizer(lblNewLabel, DnDConstants.ACTION_COPY, mainForm.dlistener);
		        mainForm.trainingPlansTabExercisesPanel.add(lblNewLabel);
		        mainForm.exercisesTabExercisesPanel.add(lblNewLabel1);
            }
		}

		
		
		NodeList nl = doc.getElementsByTagName("trpl");
		
		
		for (int i = 0; i < nl.getLength(); i++) {
			
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
				Element element = (Element) node;
		
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
		
								for (int[] m : conformities) {
									if (m[0] == Integer.valueOf(taskElement.getAttribute("ex"))) {
										exercise = exerciseDAO.findOne(m[1]);
										
										break;
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
				TrainingPlan trainingPlan = new TrainingPlan(element.getAttribute("name"), trainings);
				
				trainingPlan.setPlanId(trainingPlanDAO.create(trainingPlan));
				
				trainingPlan.setPlanId(trainingPlanDAO.create(trainingPlan));
				JLabelWithId label = new JLabelWithId(trainingPlan.getName(), trainingPlan.getPlanId());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setHorizontalTextPosition(SwingConstants.CENTER);
				label.setVerticalTextPosition(SwingConstants.BOTTOM);
				mainForm.trainingPlans.add(trainingPlan);
				mainForm.panelPlanList.add(label);
			
				label.addMouseListener(mainForm.trainingPlanItemMouseListener);

				//mainForm.panelPlanList.revalidate();
				//mainForm.panelPlanList.repaint();

            }
		}
		closeDataSource(doc, file);
	}
	
	private static Document createDataSource(File file) {

		Document document = null;
		DocumentBuilderFactory dbf = null;
		DocumentBuilder        db  = null;
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (file.exists()) {
            	document = db.parse(file);
            } else {

            	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        		factory.setNamespaceAware(true);
        		try {
        			document = factory.newDocumentBuilder().newDocument();
        		} catch (ParserConfigurationException e) {
        			e.printStackTrace();
        		}
        		
        		Element root = document.createElement("root");
        		document.appendChild(root);
            }
            
            document.getDocumentElement().normalize();

        }
        catch(Exception ex){
        	ex.printStackTrace();
        }
			
		return document;
	}
	
	private static void closeDataSource(Document document, File file) {
		Transformer transformer = null;
		
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		document.normalize();
		
		try {
			transformer.transform(new DOMSource(document), new StreamResult(file));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
