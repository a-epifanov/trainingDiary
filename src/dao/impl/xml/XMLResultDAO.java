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

import dao.IResultDAO;
import dao.impl.factory.XMLDAOFactory;
import entities.Exercise;
import entities.Result;
import entities.Task;

/**
* XML Result DAO
*
* @version 1.0
* @author Nikita Levchenko
*/
public class XMLResultDAO implements IResultDAO {
	private Document doc = null;
	private XMLDAOFactory xMLDAOFactory = null;
	private Document exerciseDoc = null;
	private File     resultFile;
	private File     exerciseFile;
	
	public XMLResultDAO() {
	}

	@Override
	public void delete(int id) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("res");
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
	public void create(Result result) {
		this.createDataSource();
		Element  root = doc.getDocumentElement();
		NodeList nl   = doc.getElementsByTagName("res");
		Element  res   = doc.createElement("res");
		
		String dateString = String.valueOf(result.getDate().getDate()) 
				  + "." 
				  + String.valueOf(result.getDate().getMonth() + 1)
				  + "."
				  + String.valueOf(result.getDate().getYear() + 1900);
		
		res.setAttribute("id", String.valueOf(nl.getLength()));
		res.setAttribute("ex", String.valueOf(result.getExercise().getId()));
		res.setAttribute("rc", String.valueOf(result.getRepetitionsCount()));
		res.setAttribute("sc", String.valueOf(result.getSetsCount()));
		res.setAttribute("w", String.valueOf(result.getWeight()));
		res.setAttribute("dt", dateString);
		root.appendChild(res);
		this.closeDataSource();
	}

	@Override
	public Result findOne(int id) {
		this.createDataSource();
		Result result = null;
		NodeList nl = doc.getElementsByTagName("res");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getAttribute("id"))) {
                	
                	Integer exerciseId = Integer.valueOf(element.getAttribute("ex"));
                	Exercise resultExercise = null;
            		NodeList nodeListExercise = doc.getElementsByTagName("ex");
            		for (int j = 0; j < nodeListExercise.getLength(); j++) {
            			Node nodeExercise = nodeListExercise.item(j);
            			if(nodeExercise.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element elementExercise = (Element) nodeExercise;
                            if (exerciseId == Integer.valueOf(elementExercise.getAttribute("id"))) {
                            	resultExercise = new Exercise(exerciseId, elementExercise.getAttribute("name"), elementExercise.getAttribute("desc"), null);
                            }
                        }
            		}
            		String dateString = element.getAttribute("dt");
            		Date date = new Date(Integer.valueOf(dateString.substring(dateString.lastIndexOf("|") + 1)) - 1900,
                			Integer.valueOf(dateString.substring(dateString.indexOf("|") + 1, dateString.lastIndexOf("|"))) - 1,
                			Integer.valueOf(dateString.substring(0, dateString.indexOf("|"))));
                	result = new Result(id,
                						date, 
                						resultExercise, 
                						Integer.valueOf(element.getAttribute("rc")),
                						Integer.valueOf(element.getAttribute("sc")),
                						Integer.valueOf(element.getAttribute("w")));
                }
            }
		}
		this.closeDataSource();
		
		return result;
	}

	@Override
	public void update(Result result) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("res");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (result.getId() == Integer.valueOf(element.getAttribute("id"))) {
                	element.removeAttribute("ex");
                	element.setAttribute("ex", String.valueOf(result.getExercise().getId()));
                	element.removeAttribute("rc");
                	element.setAttribute("rc", String.valueOf(result.getRepetitionsCount()));
                	element.removeAttribute("sc");
                	element.setAttribute("sc", String.valueOf(result.getSetsCount()));
                	element.removeAttribute("w");
                	element.setAttribute("w", String.valueOf(result.getWeight()));
                	element.removeAttribute("dt");
                	
            		String dateString = String.valueOf(result.getDate().getDate()) 
          				  + "." 
          				  + String.valueOf(result.getDate().getMonth() + 1)
          				  + "."
          				  + String.valueOf(result.getDate().getYear() + 1900);
                	
                	element.setAttribute("w", dateString);
                }
            }
		}
		this.closeDataSource();
	}

	@Override
	public List<Result> findAll() {
		this.createDataSource();
		List<Result> result = new ArrayList<Result>();
		NodeList nl = doc.getElementsByTagName("res");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Integer exerciseId = Integer.valueOf(element.getAttribute("ex"));
                Exercise resultExercise = null;
            	NodeList nodeListExercise = exerciseDoc.getElementsByTagName("ex");
            	for (int j = 0; j < nodeListExercise.getLength(); j++) {
            		Node nodeExercise = nodeListExercise.item(j);
            		if(nodeExercise.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementExercise = (Element) nodeExercise;
                        if (exerciseId == Integer.valueOf(elementExercise.getAttribute("id"))) {
                        	resultExercise = new Exercise(exerciseId, elementExercise.getAttribute("name"), elementExercise.getAttribute("desc"), null);
                        //	System.out.println(elementExercise.getAttribute("name"));
                       }
                    }
            	}
        		String dateString = element.getAttribute("dt");
        		Date date = new Date(Integer.valueOf(dateString.substring(dateString.lastIndexOf(".") + 1)) - 1900,
            			Integer.valueOf(dateString.substring(dateString.indexOf(".") + 1, dateString.lastIndexOf("."))) - 1,
            			Integer.valueOf(dateString.substring(0, dateString.indexOf("."))));
                result.add(new Result(Integer.valueOf(element.getAttribute("id")),
                			 	  date, 
                				  resultExercise, 
                				  Integer.valueOf(element.getAttribute("rc")),
                				  Integer.valueOf(element.getAttribute("sc")),
                				  Integer.valueOf(element.getAttribute("w"))));
            }
		}
		this.closeDataSource();
		return result;
	}
	
	public void createDataSource() {
		resultFile       = new File("src/resources/results.xml");
		exerciseFile    = new  File("src/resources/exercises.xml");
		DocumentBuilderFactory dbf = null;
		DocumentBuilder        db  = null;
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (resultFile.exists()) {
            	doc = db.parse(resultFile);
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
		
		doc.normalize();
		exerciseDoc.normalize();

		try {
			transformer.transform(new DOMSource(doc), new StreamResult(resultFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		try {
			transformer.transform(new DOMSource(exerciseDoc), new StreamResult(exerciseFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
