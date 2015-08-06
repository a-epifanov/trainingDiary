package dao.impl.xml;

import java.io.File;
import java.util.ArrayList;
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

import dao.IExerciseDAO;
import dao.impl.factory.XMLDAOFactory;
import entities.Exercise;

/**
* XML Exercise DAO
*
* @version 1.0
* @author Nikita Levchenko
*/
public class XMLExerciseDAO implements IExerciseDAO  {

	private Document doc = null;
	private XMLDAOFactory xMLDAOFactory = null;
	private File     exerciseFile;
	
	public XMLExerciseDAO() {
	}
	
	@Override
	public void delete(int id) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("ex");
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
	public int create(Exercise exercise) {
		this.createDataSource();
		Element  root = doc.getDocumentElement();
		NodeList nl   = doc.getElementsByTagName("ex");
		Element  ex   = doc.createElement("ex");
		
		int id = 0;
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
				Element element = (Element) node;

	            int p = Integer.valueOf(element.getAttribute("id"));
	            if (p >= id) {
	            	id = p + 1;
	            }
            }
		}
		ex.setAttribute("id", String.valueOf(id));
		ex.setAttribute("name", exercise.getName());
		ex.setAttribute("desc", exercise.getDescription());
		ex.setAttribute("cat", exercise.getCategory());
		root.appendChild(ex);
		this.closeDataSource();
		return id;
	}

	@Override
	public Exercise findOne(int id) {
		this.createDataSource();
		Exercise result = null;
		
		NodeList nl = doc.getElementsByTagName("ex");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (id == Integer.valueOf(element.getAttribute("id"))) {
                	result = new Exercise(id, element.getAttribute("name"), element.getAttribute("desc"), element.getAttribute("cat"));
                	//result = new Exercise(id, element.getAttribute("name"), element.getAttribute("desc"), null, element.getAttribute("cat"));
                }
            }
		}
		this.closeDataSource();
		return result;
	}

	@Override
	public void update(Exercise execrcise) {
		this.createDataSource();
		NodeList nl = doc.getElementsByTagName("ex");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                if (execrcise.getId() == Integer.valueOf(element.getAttribute("id"))) {
                	element.removeAttribute("name");
                	element.setAttribute("name", execrcise.getName());
                	element.removeAttribute("desc");
                	element.setAttribute("desc", execrcise.getDescription());
                	element.removeAttribute("cat");
                	element.setAttribute("cat", execrcise.getCategory());
                }
            }
		}
		this.closeDataSource();
	}

	@Override
	public List<Exercise> findAll() {
		this.createDataSource();
		List<Exercise> result = new ArrayList<Exercise>();
		
		NodeList nl = doc.getElementsByTagName("ex");
		
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                result.add(new Exercise(Integer.valueOf(element.getAttribute("id")), 
                		                element.getAttribute("name"), 
                		                element.getAttribute("desc"), 
                		                element.getAttribute("cat")));
            }
		}
		this.closeDataSource();
		return result;
	}

	public void createDataSource() {
		exerciseFile     = new File("src/resources/exercises.xml");

		
		DocumentBuilderFactory dbf = null;
		DocumentBuilder        db  = null;
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (exerciseFile.exists()) {
            	doc = db.parse(exerciseFile);
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
		
		doc.normalize();
	
		try {
			transformer.transform(new DOMSource(doc), new StreamResult(exerciseFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
