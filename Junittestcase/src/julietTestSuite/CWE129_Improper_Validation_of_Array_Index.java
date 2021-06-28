package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE129_Improper_Validation_of_Array_Index {
	@Test

	void test() {
		final File javaFolder = new File(
				"/Junittestcase/resources/Juliet_Test_Suite_v1.2/src/testcases/CWE129_Improper_Validation_of_Array_Index");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE129_Improper_Validation_of_Array_Index.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}