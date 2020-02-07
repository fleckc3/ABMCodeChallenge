import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class parses an xml document and extracts the RefText Values for References with
 * RefCodes that equal 'MWB', 'TRV', and 'CAR'.
 * @author Colin
 * @version 1.0
 * @since 07/02/20
 */
public class RefTextValues {

	//public string values for the extracted RefCodes
	public static String mwbRefTextValue;
	public static String trvRefTextValue;
	public static String carRefTextValue;
	
	/**
	 * Main method runs the code contained int his class
	 * @param args is passed to the command line to be ran
	 * @throws @throws java.io.IOException when there is an issue with creating the XML DOM object from the file
	 */
	public static void main(String[] args) {
		
		try {
			/* There was an error in the xml syntax. 
			 * A <Declaration> tag was missing near the bottom so that has been fixed and added into the file
			 */
			File xmlFile = new File("declaration.xml");
			
			//create XML DOM object
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(xmlFile);
			document.getDocumentElement().normalize();
			
			//throw exception error
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
	
	}
	
}


