package TestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;


import org.junit.jupiter.api.Test;

public class TruepPositiveTest {
	JavaFileFilter jff = new JavaFileFilter();
	XmlFileParser xfp = new XmlFileParser();
	
	
	final File javaFolderPath = new File("C:\\Users\\Djones\\Desktop\\Java\\src\\testcases\\CWE89_SQL_Injection\\s01");
	final File xmlFilePath = new File("C:\\Users\\Djones\\Downloads\\S01-Findings-List (1).xml");
	final String javaFilePath = "";
	
	private final boolean compareMaps() {
		Map<String, String> javaFileFilterMap = new HashMap<String, String>();
		javaFileFilterMap.putAll(jff.folderEntry(javaFolderPath));
		
		Map<String, String> xmlFileMap = new HashMap<String, String>();
		xmlFileMap.putAll(xfp.readXML(xmlFilePath));
		
		boolean testResult = javaFileFilterMap.keySet().equals(xmlFileMap.keySet());
		if (testResult == true) {
			return true;
		}
		else {
			HashSet<String> xmlExtraKeys = new HashSet<>(javaFileFilterMap.keySet());
			xmlExtraKeys.addAll(xmlFileMap.keySet());
			xmlExtraKeys.removeAll(javaFileFilterMap.keySet());
			
			HashSet<String> javaExtraKeys = new HashSet<>(xmlFileMap.keySet());
			javaExtraKeys.addAll(javaFileFilterMap.keySet());
			javaExtraKeys.removeAll(xmlFileMap.keySet());
			
			Iterator<String> itrJavaFolder = javaExtraKeys.iterator();
			Iterator<String> itrXmlFile = xmlExtraKeys.iterator();
			
			System.out.println("Following files are in java folder but not in xml file :");
			while(itrJavaFolder.hasNext()) {
				System.out.println(itrJavaFolder.next()) ;
			}
			System.out.println("Following files are in xml file but not in folder :");
			while(itrXmlFile.hasNext()) {
				System.out.println(itrXmlFile.next()) ;
			}
			
			return false;
		}
		
		
	}
	
	@Test
	void test()
	{
		assertEquals(true,compareMaps());
	}
	
	
}
