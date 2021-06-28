package TestCase;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlFileParser {

	private static final Logger LOGGER = Logger.getLogger(XmlFileParser.class.getName());

	final Map<String, String> readXML(final File xmlFilString) {
		Document dom;
		Map<String, String> findingsMap = new HashMap<String, String>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		if (xmlFilString.getName().toLowerCase().endsWith(".xml")) {

			try {
				// content = readFile(xmlFilString);
				DocumentBuilder db = dbf.newDocumentBuilder();
				dom = db.parse(xmlFilString.toURI().toString());
				Element doc = dom.getDocumentElement();
				findingsMap.putAll(getXmlValue(doc));
				return findingsMap;
			} catch (ParserConfigurationException pce) {
				LOGGER.log(Level.SEVERE, "Exception occur" + pce.getMessage());
			} catch (SAXException se) {
				LOGGER.log(Level.SEVERE, "Exception occur" + se.getMessage());
			} catch (IOException ioe) {
				LOGGER.log(Level.SEVERE, "Exception occur" + ioe.getMessage());
			}
		}

		return findingsMap;
	}

	private final Map<String, String> getXmlValue(Element doc) {
		final String findingTag = "finding";
		final String fileTag = "file";
		final String methodTag = "method";
		String valueFileName;
		String valueMethodName;
		NodeList nl;
		nl = doc.getElementsByTagName(findingTag);
		final int nodeLength = nl.getLength();
		Map<String, String> xmlFileMap = new HashMap<String, String>();
		if (nodeLength > 0 && nl.item(0).hasChildNodes()) {
			for (int i = 0; i < nl.getLength(); ++i) {
				NodeList subNode = nl.item(i).getChildNodes();
				if (subNode != null && subNode.getLength() > 0) {
					NodeList nodeFile = ((Element) subNode).getElementsByTagName(fileTag);
					NodeList nodeMethod = ((Element) subNode).getElementsByTagName(methodTag);

					if (nodeFile != null && nodeFile.getLength() > 0) {
						valueFileName = nodeFile.item(0).getChildNodes().item(0).getNodeValue().toString();
					} else {
						valueFileName = "File name not found in XML file.";
					}
					if (nodeMethod != null && nodeMethod.getLength() > 0) {
						valueMethodName = nodeMethod.item(0).getChildNodes().item(0).getNodeValue().toString();
					} else {
						valueMethodName = "File name not found in XML file.";
					}

					xmlFileMap.put(valueFileName, valueMethodName);

				}

			}

		} else {
			xmlFileMap = Collections.emptyMap();
		}
		return xmlFileMap;
	}

	@Test
	void test() {
		final File xmlFilePath = new File("C:\\Users\\Djones\\Downloads\\S01-Findings-List (1).xml");
		Map<String, String> xmlFileMap = new HashMap<String, String>();
		xmlFileMap.putAll(readXML(xmlFilePath));
		if (xmlFileMap.size() == 0) {
			LOGGER.warning("No bad file in this folder");
		} else {
			xmlFileMap.entrySet().forEach(entry -> {
				LOGGER.info(entry.getKey() + " Map entry Output " + entry.getValue());
			});
		}

	}
}
