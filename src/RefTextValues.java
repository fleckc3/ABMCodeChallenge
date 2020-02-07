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
			
			//xml document passed to extractRefText method
			extractRefText(document);
			
			//throw exception error
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
	
	}
	
	/**
	 * This method takes the XML document object and traverses the tree via for loop. 
	 * It then finds the RefText values associated with the Reference elements whose attributes
	 * equal 'MWB', 'TRV', and 'CAR'
	 * @param document is passed from the main method and used to find reftext values
	 */
	public static void extractRefText(Document document) {
		
		//gets the list of reference tags
		NodeList nList = document.getElementsByTagName("Reference");
		
		//loops through the reference tag list
		for (int i = 0; i < nList.getLength(); i++) {
			
			Node child = nList.item(i);
			//checks each element if it is a node
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				
				//sets the element to the current child item
				Element element = (Element) child;
				
				//gets refcode attribute of the element if it has one
				String checkAttribute = element.getAttribute("RefCode");
				
				/*checks the attribute code to see if it matches MWB, TRV, or CAR.
				 * If so it sets the value to the public String declared at top.
				 */
				if (checkAttribute.contentEquals("MWB")) {
					String refTextValue = element.getElementsByTagName("RefText").item(0).getTextContent();
					System.out.println(refTextValue);
					mwbRefTextValue = refTextValue;
				} else if (checkAttribute.contentEquals("TRV")) {
					String refTextValue = element.getElementsByTagName("RefText").item(0).getTextContent();
					System.out.println(refTextValue);
					trvRefTextValue = refTextValue;
				} else if (checkAttribute.contentEquals("CAR")) {
					String refTextValue = element.getElementsByTagName("RefText").item(0).getTextContent();
					System.out.println(refTextValue);
					carRefTextValue = refTextValue;
				}
			}
		}
		//Print reftext values
		System.out.println("MWB RefText value: " + mwbRefTextValue + "\n" + "TRV RefText value: " + trvRefTextValue + "\n" + "CAR RefText Value: " + carRefTextValue);
	}
	
}


