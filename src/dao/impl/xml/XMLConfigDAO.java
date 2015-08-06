package dao.impl.xml;

import java.io.File;

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

import dao.IConfigDAO;
import dao.impl.factory.XMLDAOFactory;
import entities.Exercise;

/**
* XML Exercise DAO
*
* @version 1.0
* @author Nikita Levchenko
*/
public class XMLConfigDAO implements IConfigDAO{
	private Document doc = null;
	private XMLDAOFactory xMLDAOFactory = null;
	private File     configFile;
	
	
	public void createDataSource() {
		configFile     = new File("src/resources/config.xml");

		
		DocumentBuilderFactory dbf = null;
		DocumentBuilder        db  = null;
		
		try
        {
            dbf = DocumentBuilderFactory.newInstance();
            db  = dbf.newDocumentBuilder();
            if (configFile.exists()) {
            	doc = db.parse(configFile);
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
			transformer.transform(new DOMSource(doc), new StreamResult(configFile));
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeCurrentTrainingPlan() {
		this.createDataSource();
		System.out.println("Удаляем из конфига план");
		Element  root = doc.getDocumentElement();
		NodeList nl   = doc.getElementsByTagName("curtrpl");
		Element  curtrpl = null;
		if(nl.item(0).getNodeType() == Node.ELEMENT_NODE)
        {
			curtrpl = (Element) nl.item(0);
        }
		if (nl.getLength() > 0) {
			curtrpl.removeAttribute("id");
		} else {
			curtrpl   = doc.createElement("curtrpl");
		}
		curtrpl.setAttribute("id", "");
		root.appendChild(curtrpl);
		this.closeDataSource();
	}
	
	@Override
	public void setCurrentTrainingPlan(int id) {
		this.createDataSource();
		System.out.println("Пишем конфиг" + id);
		Element  root = doc.getDocumentElement();
		NodeList nl   = doc.getElementsByTagName("curtrpl");
		Element  curtrpl = null;
		if (nl.getLength() > 0) {
			if(nl.item(0).getNodeType() == Node.ELEMENT_NODE)
	        {
				curtrpl = (Element) nl.item(0);
	        }
		}
		if (nl.getLength() > 0) {
			curtrpl.removeAttribute("id");
		} else {
			curtrpl   = doc.createElement("curtrpl");
		}
		curtrpl.setAttribute("id", String.valueOf(id));
		root.appendChild(curtrpl);
		this.closeDataSource();
	}

	@Override
	public Integer getCurrentTrainingPlan() {
		this.createDataSource();
		Exercise result = null;
		
		Integer id = null;
		NodeList nl = doc.getElementsByTagName("curtrpl");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                String idString = element.getAttribute("id");
                if (idString != "") {
                	id = Integer.valueOf(element.getAttribute("id"));
                }
            }
		}
		this.closeDataSource();
		return id;
		
	}
	
	@Override
	public void setCurrentStyle(int id) {
		this.createDataSource();
		System.out.println("Пишем конфиг" + id);
		Element  root = doc.getDocumentElement();
		NodeList nl   = doc.getElementsByTagName("style");
		Element  curtrpl = null;
		if (nl.getLength() > 0) {
			if(nl.item(0).getNodeType() == Node.ELEMENT_NODE)
	        {
				curtrpl = (Element) nl.item(0);
	        }
		}
		if (nl.getLength() > 0) {
			curtrpl.removeAttribute("id");
		} else {
			curtrpl   = doc.createElement("style");
		}
		curtrpl.setAttribute("id", String.valueOf(id));
		root.appendChild(curtrpl);
		this.closeDataSource();
	}

	@Override
	public Integer getCurrentStyle() {
		this.createDataSource();
		Exercise result = null;
		
		Integer id = null;
		NodeList nl = doc.getElementsByTagName("style");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                String idString = element.getAttribute("id");
                if (idString != "") {
                	id = Integer.valueOf(element.getAttribute("id"));
                }
            }
		}
		this.closeDataSource();
		if (id == null) {
			this.setCurrentStyle(0);
			id = 0;
		}
		return id;
		
	}
}
