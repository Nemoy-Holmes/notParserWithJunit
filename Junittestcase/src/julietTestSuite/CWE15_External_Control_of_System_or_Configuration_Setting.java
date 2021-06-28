package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE15_External_Control_of_System_or_Configuration_Setting {
	@Test

	void test() {
		final File javaFolder = new File(
				"/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE15_External_Control_of_System_or_Configuration_Setting");
		final File xmlFile = new File(
				"/Junittestcase/resources/xmlFile/CWE15_External_Control_of_System_or_Configuration_Setting.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}
