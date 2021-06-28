package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE134_Uncontrolled_Format_String {
	@Test

	void test() {
		final File javaFolder = new File(
				"/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE134_Uncontrolled_Format_String");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE134_Uncontrolled_Format_String.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}