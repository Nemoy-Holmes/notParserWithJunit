package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE113_HTTP_Response_Splitting {
	@Test

	void test() {
		final File javaFolder = new File("/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE113_HTTP_Response_Splitting");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE113_HTTP_Response_Splitting.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}