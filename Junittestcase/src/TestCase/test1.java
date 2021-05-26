package TestCase;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class test1 {
	public static String readFile(File file) throws IOException{
		StringBuilder inputStringBuilder = new StringBuilder();
		InputStream inputFileData = new FileInputStream(file);
		BufferedReader inputFileBufferedReader = new BufferedReader(new InputStreamReader(inputFileData));
		
		String inputFileLine;
		while((inputFileLine = inputFileBufferedReader.readLine()) != null) {
			inputStringBuilder.append(inputFileLine + System.lineSeparator());
		}
		
		inputFileBufferedReader.close();
		return inputStringBuilder.toString();
	}
	
	public static void listFilesForFolder(final File folder) {
		int numberOfBad = 0;
		int numberOfFiles = 0;
		int numberOfJavaFiles = 0;
		for(final File fileEntry : folder.listFiles()) {
			numberOfFiles += 1; 
			if(fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			}
			else {
				if(fileEntry.getName().toLowerCase().endsWith(".java"))
				{
					numberOfJavaFiles += 1;
					String content = null;
					try {
						content = readFile(fileEntry);
						} catch (IOException e) {
							e.printStackTrace();
						}
					String searchKeyword = "bad()";
					if(content.indexOf(searchKeyword) != -1) {
						System.out.println(fileEntry.getName());
						numberOfBad += 1 ; 
					}
					
				}
				
			}
		}
		System.out.println("This directory has: "+numberOfBad+ " bad methods.");
		System.out.println("This directory has: "+numberOfJavaFiles+ " number Of JavaFiles.");
		System.out.println("This directory has: "+numberOfFiles+ " number Of Files.");
	}
	
	public static void folderEntry(){
		final File folder = new File("C:\\Users\\Djones\\Desktop\\Java\\src\\testcases\\CWE89_SQL_Injection\\s01");
		listFilesForFolder(folder);
	}
	
	@Test
	void test() {
		folderEntry();
	}

}
