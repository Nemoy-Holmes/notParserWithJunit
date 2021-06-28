package julietTestSuite;

import java.io.File;

import TestCase.TruePositiveTest;

public class JulietTestCase {
	boolean returnFileUri(final File folderUri, final File xmlFile) {
		TruePositiveTest tPt = new TruePositiveTest();
		boolean testResult = tPt.compareMaps(folderUri, xmlFile);
		return testResult;
	}

}
