package TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

public class JavaFileFilter {

	private static final Logger LOGGER = Logger.getLogger(JavaFileFilter.class.getName());

	private static String readFile(File file) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		InputStream inputFileData = new FileInputStream(file);
		String inputFileLine;
		BufferedReader inputFileBufferedReader = new BufferedReader(new InputStreamReader(inputFileData));
		try {
			while ((inputFileLine = inputFileBufferedReader.readLine()) != null) {
				inputStringBuilder.append(inputFileLine + System.lineSeparator());
			}
		} finally {
			inputFileBufferedReader.close();
		}

		return inputStringBuilder.toString();
	}

	private final Map<String, String> listFilesForFolder(final File folder) {

		final String searchWordbad = "bad";
		final String searchKeywordbad = "bad()";
		final String searchKeywordbadSource = "badSource()";
		Map<String, String> javaFileFilterMap = new HashMap<String, String>();
		int uniqueValue = 0;
		final File[] filesInFolder = folder.listFiles();
		if (filesInFolder != null) {
			for (final File fileEntry : filesInFolder) {

				if (fileEntry.isDirectory()) {
					listFilesForFolder(fileEntry);
				} else {
					if (fileEntry.getName().toLowerCase().endsWith(".java")) {
						uniqueValue += 1;

						String content = null;
						try {
							content = readFile(fileEntry);
						} catch (IOException e) {
							e.printStackTrace();
						}

						if (content.indexOf(searchKeywordbad) != -1) {
							// System.out.println(fileEntry.getName());
							// numberOfBad += 1 ;
							javaFileFilterMap.put(fileEntry.getName(), "bad()" + uniqueValue);
						} else if (content.indexOf(searchKeywordbadSource) != -1) {
							// System.out.println(fileEntry.getName());
							// numberOfBadSource += 1 ;
							javaFileFilterMap.put(fileEntry.getName(), "badSource()" + uniqueValue);
						}

						else if (content.indexOf(searchWordbad) != -1) {
							// System.out.println(fileEntry.getName());
							// numberOfBadWord += 1 ;
							javaFileFilterMap.put(fileEntry.getName(), "bad keyword" + uniqueValue);
						}

					}

				}
			}
		}

		if (javaFileFilterMap.size() == 0) {
			LOGGER.warning("This folder does not contain any bad methods");
		}

		return javaFileFilterMap;

	}

	final Map<String, String> folderEntry(final File folder) {

		Map<String, String> javaFilterMap = new HashMap<String, String>();
		javaFilterMap.putAll(listFilesForFolder(folder));
		return javaFilterMap;
	}

	@Test
	void test() {

		final File javaFolderPath = new File(
				"C:\\Users\\Djones\\git\\repository3\\Junittestcase\\resources\\Juliet_Test_Suite_v1.2\\src\\testcases\\CWE89_SQL_Injection\\s01");
		Map<String, String> javaFileFilterMap = new HashMap<String, String>();
		javaFileFilterMap.putAll(folderEntry(javaFolderPath));
		if (javaFileFilterMap.size() == 0) {
			LOGGER.warning("No bad files were found in this folder");

		} else {
			javaFileFilterMap.entrySet().forEach(entry -> {
				LOGGER.info((entry.getKey() + " Map entry Output " + entry.getValue()));
			});
		}
	}

}