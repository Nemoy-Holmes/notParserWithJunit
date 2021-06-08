package TestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


import org.junit.jupiter.api.Test;

public class TruepPositiveTest {
	JavaFileFilter jff = new JavaFileFilter();
	XmlFileParser xfp = new XmlFileParser();
	
	
	final File javaFolderPath = new File("C:\\Users\\Djones\\Desktop\\Java\\src\\testcases\\CWE89_SQL_Injection\\s01");
	final File xmlFilePath = new File("C:\\Users\\Djones\\Desktop\\-Findings-List.xml");
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
			
			System.out.println("Following files are in folder but not in xml file "+ javaExtraKeys);
			System.out.println("Following files are in xml file but not in folder "+ xmlExtraKeys);
			
			return false;
		}
		
		
	}
	
	@Test
	void test()
	{
		assertEquals(true,compareMaps());
	}
	
	
}
