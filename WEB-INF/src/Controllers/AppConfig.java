package Controllers;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Models.ActionModels.ActionMapping;

/**
 * AppConfig class that parses config.xml, stores page mappings
 * for the Front Controller. 
 * @author Kevin Dugan
 */
public class AppConfig extends DefaultHandler {
	
	//Class Attributes
	private InputStream file;
	private String temp;
	private ActionMapping actionMapping;
	Map<String, ActionMapping> configMap;
	
	/**
	 * Constructor.
	 * @param file
	 */
	public AppConfig (InputStream file) {		
		this.file = file;
		configMap = new HashMap<String, ActionMapping>();
	}
	
	/**
	 * Method to initiate parsing the document using SAX
	 */
	public void parseDocument() {
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		
		try {
			SAXParser sp = spf.newSAXParser();
			sp.parse(file, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * SAX parse method override.
	 */
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {

		temp = "";
		
		if (qName.equalsIgnoreCase("ACTION")) {
			actionMapping = new ActionMapping();
		}
	}
	
	/**
	 * SAX parse method override.
	 */
	public void characters (char[] ch, int start, int length) throws SAXException {
		temp = new String(ch, start, length);
	}
	
	/**
	 * SAX parse method override.
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		//If at end element for the action, store the configurations in the map.
		if (qName.equalsIgnoreCase("ACTION")) {
			configMap.put(actionMapping.getName(), actionMapping);
		} else if (qName.equalsIgnoreCase("NAME")) {
			actionMapping.setName(temp);
		} else if (qName.equalsIgnoreCase("CLASS")) {
			actionMapping.setClassName(temp);
		} else if (qName.equalsIgnoreCase("SUCCESS")) {
			actionMapping.setSuccess(temp);
		} else if (qName.equalsIgnoreCase("FAILURE")) {
			actionMapping.setFailure(temp);
		} else if (qName.equalsIgnoreCase("PARAM")) {
			actionMapping.setParam(temp);
		}	
	}
	
	/**
	 * Sends the configurations map back to the FrontController.
	 * @return Map The map to the FrontController.
	 */
	public Map<String, ActionMapping> getConfigs() {
		return configMap;
	}
}
