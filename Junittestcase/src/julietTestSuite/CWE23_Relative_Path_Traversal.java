package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE23_Relative_Path_Traversal {
	@Test

	void test() {
		final File javaFolder = new File(
				"/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE23_Relative_Path_Traversal");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE23_Relative_Path_Traversal.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}