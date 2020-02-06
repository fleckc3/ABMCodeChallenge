import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Edifact Parser class takes an Edifact message text and parses out the LOC
 * segments and populates an array with the 2nd and 3rd element of each segment
 * @author Colin
 *
 */
public class EdifactParser {
	
	//public array populated with 2nd and 3rd LOC elements of EDIFACT message
	public static ArrayList<String> secondThirdArray = new ArrayList<String>();
	
	public static void main(String[] args) {
		
		//creates file with edifact text
		try {
			FileWriter edifactText = new FileWriter("edifact.txt");
			edifactText.write("UNA:+.? '\r\n" + 
					"UNB+UNOC:3+2021000969+4441963198+180525:1225+3VAL2MJV6EH9IX+KMSV7HMD+CU\r\n" + 
					"SDECU-IE++1++1'\r\n" + 
					"UNH+EDIFACT+CUSDEC:D:96B:UN:145050'\r\n" + 
					"BGM+ZEM:::EX+09SEE7JPUV5HC06IC6+Z'\r\n" + 
					"LOC+17+IT044100'\r\n" + 
					"LOC+18+SOL'\r\n" + 
					"LOC+35+SE'\r\n" + 
					"LOC+36+TZ'\r\n" + 
					"LOC+116+SE003033'\r\n" + 
					"DTM+9:20090527:102'\r\n" + 
					"DTM+268:20090626:102'\r\n" + 
					"DTM+182:20090527:102'");
			edifactText.close();
			
			//set up edifact.txt to be read as a file by scanner
			File edifact = new File("edifact.txt");
			Scanner fileReader = new Scanner(edifact);
			
			while (fileReader.hasNextLine()) {
				String lineData = fileReader.nextLine();
				parser(lineData);
			}
			
		} catch (IOException e) {
			//gives error if one occurs during the file writing
			System.out.println("Error occurred while writing file.");
			e.printStackTrace();
		}
		
		System.out.println(Arrays.toString(secondThirdArray.toArray()));
	}
	
	/**
	 * Method checks for LOC on ach line of the message read by the scanner.
	 * Adds the 2nd and 3rd parameters to an array
	 * @param lineData
	 */
	public static void parser(String lineData) {
		
		/* Check to see if start of line starts with LOC.
		 * If it does split the string after each + into an array */
		if (lineData.substring(0, 3).contentEquals("LOC")) {
			String[] dataSplit = lineData.split("\\+");
			
			//check output of split
			System.out.println(Arrays.deepToString(dataSplit));
			
			//loop through each line that is now an array 
			for (int i = 0; i < dataSplit.length; i++) {
				
				//find 2nd element and add it to the array list
				if(i == 1) {
					secondThirdArray.add(dataSplit[i]);
				
				//find 3rd element, get rid of the apostrophe, add it to the array list
				} else if (i == 2) {
					String third = dataSplit[i];
					int apos = third.length() - 1;
					String thirdNoApos = third.substring(0, apos);
					secondThirdArray.add(thirdNoApos);
				}
			}
		}
	}

}
