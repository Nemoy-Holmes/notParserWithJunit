package TestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class TruePositiveTest {
	JavaFileFilter jff = new JavaFileFilter();
	XmlFileParser xfp = new XmlFileParser();

	private static final Logger LOGGER = Logger.getLogger(TruePositiveTest.class.getName());

	public final boolean compareMaps(final File javaFolderUri, final File xmlFileUri) {
		Map<String, String> javaFileFilterMap = new HashMap<String, String>();
		javaFileFilterMap.putAll(jff.folderEntry(javaFolderUri));

		Map<String, String> xmlFileMap = new HashMap<String, String>();
		xmlFileMap.putAll(xfp.readXML(xmlFileUri));

		boolean testResult = javaFileFilterMap.keySet().equals(xmlFileMap.keySet());
		if (testResult == true) {
			return true;
		} else {
			HashSet<String> xmlExtraKeys = new HashSet<>(javaFileFilterMap.keySet());
			xmlExtraKeys.addAll(xmlFileMap.keySet());
			xmlExtraKeys.removeAll(javaFileFilterMap.keySet());

			HashSet<String> javaExtraKeys = new HashSet<>(xmlFileMap.keySet());
			javaExtraKeys.addAll(javaFileFilterMap.keySet());
			javaExtraKeys.removeAll(xmlFileMap.keySet());

			Iterator<String> itrJavaFolder = javaExtraKeys.iterator();
			Iterator<String> itrXmlFile = xmlExtraKeys.iterator();

			LOGGER.info("Following files are in java folder but not in xml file :");
			while (itrJavaFolder.hasNext()) {
				LOGGER.info(itrJavaFolder.next());
			}
			LOGGER.info("Following files are in xml file but not in folder :");
			while (itrXmlFile.hasNext()) {
				LOGGER.info(itrXmlFile.next());
			}

			return false;
		}

	}

	@Test
	void test() {
		final File javaFolderUri = new File(
				"C:\\Users\\Djones\\Desktop\\Java\\src\\testcases\\CWE89_SQL_Injection\\s01");
		final File xmlFileUri = new File("C:\\Users\\Djones\\Downloads\\S01-Findings-List (1).xml");
		assertEquals(true, compareMaps(javaFolderUri, xmlFileUri));
	}

}
