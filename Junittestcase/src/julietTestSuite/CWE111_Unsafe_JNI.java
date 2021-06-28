package julietTestSuite;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

public class CWE111_Unsafe_JNI {
	@Test

	void test() {
		final File javaFolder = new File("/Junittestcase/src/julietTestSuite/CWE111_Unsafe_JNI.java");
		final File xmlFile = new File("/Junittestcase/resources/xmlFile/CWE111_Unsafe_JNI.xml");
		JulietTestCase jTc = new JulietTestCase();
		final boolean testResult = jTc.returnFileUri(javaFolder, xmlFile);
		assertTrue(testResult);

	}
}