import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This SimpleWebService provides the logic to validate the xml payload structure, the declaration command value, 
 * and the siteID value. The status code for validation is saved as an integer value that could be returned to a client.
 * @author Colin Fleck
 * @version 1.0
 * @since 10/02/20
 *
 */
public class WebService {

	/**
	 * Main method passes arguments to the command line.
	 * @param args
	 */
	public static void main(String[] args) {
		

		try {
			
			//int variables initialised with status codes after validation
			int structureStatus;
			int commandStatus;
			int siteIdStatus;
			
			//creates the payload file to be validated
			File xmlFile = new File("src/payload.xml");
			
			//create XML DOM object
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser;
			parser = factory.newDocumentBuilder();
			Document document = parser.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			//boolean variable checks the structure of the xml document against the xsd schema
			boolean structure = checkPayloadStructure("src/InputDocument.xsd", "src/payload.xml");
			
			//if true set structure status code to 0
			if (structure) {
				structureStatus = 0;
				System.out.println("Structure status code: " + structureStatus + " - Payload matches Schema.");
			}
			
			/* Calls the validateCommand method and returns an integer.
			 * If it is equal -1 then sommandStatus = -1. */
			if (validateCommand(document) == -1) {
				commandStatus = -1;
				System.out.println("Command status code: " + commandStatus + " - Invalid Command specified.");
			}
			
			/*Calls the validateSiteID method and returns an integer.
			 * If is equal to -2 then siteIdStatus = -2. */
			if(validateSiteID(document) == -2) {
				siteIdStatus = -2;
				System.out.println("SiteID status code: " + siteIdStatus + " - Invalid site specified.");
			}
		
			//throw errors if there are any with the document or file
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method checks the xml file structure against the xsd schema. 
	 * @param xsdPath xsd schema file passed.
	 * @param xmlPath xml file being validated.
	 * @return Boolean value depending on if the xml file conforms to the schema or not.
	 */
	public static boolean checkPayloadStructure(String xsdPath, String xmlPath) {
		
		try {
			
			//Creates the schema out of the xsd file created
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdPath));
			
			//sets the validator to the schema and checks the xml file against it
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xmlPath)));
			
		//if xml file does not follow the schema then an error is returned and the boolean is set to false.
		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * This method validates the value of the command attribute.
	 * @param document is the xml payload passed.
	 * @return an Integer of -1 if the command value = DEFAULT. 
	 */
	public static int validateCommand(Document document) {
		
		//status int to be returned
		int status = 0;
		
		//gets list of the declaration tags - should only be one if structure is correct
		NodeList declaration = document.getElementsByTagName("Declaration");
		Node decNode = declaration.item(0);
		
		//checks if the tag is an node element
		if (decNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) decNode;
			String checkCommand = element.getAttribute("Command");
			
			//Checks if the command value is equal to DEFAULT
			if (checkCommand.equals("DEFAULT")) {
				status = -1;
			}	
		}
		
		//return the status code for Command
		return status;
	}
	
	/**
	 * This method validates the SiteID value of the payload.
	 * @param document is the xml payload passed.
	 * @return an integer of -2 if the SiteID = 'DUB'.
	 */
	public static int validateSiteID(Document document) {
		
		//status int to be returned
		int status = 0;
		
		//gets list of the siteId tags - should only be one if structure is correct
		NodeList siteID = document.getElementsByTagName("SiteID");
		Node siteIdNode = siteID.item(0);
		
		//checks if the siteID is a node element
		if (siteIdNode.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) siteIdNode;
			String checkSiteId = element.getTextContent();

			//Checks if the value = 'DUB'
			if (checkSiteId.equals("DUB")) {
				status = -2;
			}
		}
		
		//siteID status returned
		return status;
	}
	
	
	

}
