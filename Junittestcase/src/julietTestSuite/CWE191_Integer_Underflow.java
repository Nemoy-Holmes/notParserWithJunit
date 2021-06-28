package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE191_Integer_Underflow {
	@Test

	void test() {
		final File javaFolder = new File(
				"/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE191_Integer_Underflow");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE191_Integer_Underflow.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}