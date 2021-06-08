package TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlFileParser {
	
	
	final Map<String, String> readXML(final File xmlFilString) {
		Document dom; 
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		if(xmlFilString.getName().toLowerCase().endsWith(".xml")) {
			String content = null;
			try {
				content = readFile(xmlFilString);
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse(content);
				Element doc = dom.getDocumentElement();
				Map<String, String> findingsMap = new HashMap<String, String>();  
				findingsMap.putAll(getXmlValue(doc, "finding"));
				return findingsMap;
			} catch (ParserConfigurationException pce) {
				System.out.println(pce.getMessage());
			} catch (SAXException se) {
				System.out.println(se.getMessage());
			} catch (IOException ioe) {
				System.err.println(ioe.getMessage());
			}
		}
		
		return null;
	}
	
	private final Map<String, String> getXmlValue(Element doc, String tag) {
		String valueFileName;
		String valueProblemType;
		NodeList nl;
		nl = doc.getElementsByTagName(tag);
		final int nodeLength = nl.getLength();
		Map<String, String> xmlFileMap = new HashMap<String, String>();
		if(nodeLength > 0  && nl.item(0).hasChildNodes() )  {
			for(int i = 0; i < nl.getLength(); ++i)
			{
				////// Cannot access XML elements
				valueFileName = nl.item(i).getChildNodes().item(12).getNodeValue();
				valueProblemType = nl.item(i).getChildNodes().item(0).getNodeValue();
				xmlFileMap.put(valueFileName, valueProblemType);
			}
			
		}
		return xmlFileMap;
	}
	
	private static String readFile( File xmlFilePath) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		InputStream inputFileData = new FileInputStream(xmlFilePath);
		String inputFileLine;
		BufferedReader inputFileBufferedReader = new BufferedReader(new InputStreamReader(inputFileData));
		try {
			while((inputFileLine = inputFileBufferedReader.readLine()) != null) {
				inputStringBuilder.append(inputFileLine + System.lineSeparator());
			}
		}
		finally {
			inputFileBufferedReader.close();
			
			
		}
		return inputStringBuilder.toString();
	}
	@Test
	void test() {
		final File xmlFilePath = new File("C:\\Users\\Djones\\Desktop\\-Findings-List.xml");
		Map<String, String> xmlFileMap = new HashMap<String, String>();
		xmlFileMap.putAll(readXML(xmlFilePath));
		if(xmlFileMap.size() == 0) {
			System.out.println("No bad file in this folder");
		}
		else {
			xmlFileMap.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " Map entry Output " + entry.getValue());
			});
		}
		
	}
}
