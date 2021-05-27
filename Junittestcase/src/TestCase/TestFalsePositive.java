package TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

public class TestFalsePositive {
	private static String readFile(File file) throws IOException{
		StringBuilder inputStringBuilder = new StringBuilder();
		InputStream inputFileData = new FileInputStream(file);
		String inputFileLine;
		BufferedReader inputFileBufferedReader = new BufferedReader(new InputStreamReader(inputFileData));
		try {	
			while((inputFileLine = inputFileBufferedReader.readLine()) != null) {
				inputStringBuilder.append(inputFileLine + System.lineSeparator());
				}
			}
		finally {
			inputFileBufferedReader.close();
		}
		
		
		return inputStringBuilder.toString();
	}
	
	private static void listFilesForFolder(final File folder) {
		int numberOfBad = 0;
		int numberOfBadSource = 0;
		int numberOfBadSink = 0;
		int numberOfBadWord = 0;
		int numberOfFiles = 0;
		int numberOfJavaFiles = 0;
		final String searchWordbad = "bad";
		final String searchKeywordbad = "bad()";
		final String searchKeywordbadSource = "badSource()";
		final String searchKeywordbadSink = "badSink()";
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
					
					if(content.indexOf(searchKeywordbad) != -1 ) {
						System.out.println(fileEntry.getName());
						numberOfBad += 1 ; 
					}	
					else if(content.indexOf(searchKeywordbadSource) != -1 ) {
								System.out.println(fileEntry.getName());
								numberOfBadSource += 1 ; 
								}
					else if(content.indexOf(searchKeywordbadSink) != -1 ) {
						System.out.println(fileEntry.getName());
						numberOfBadSink += 1 ; 
						}
					else if(content.indexOf(searchWordbad) != -1 ) {
						System.out.println(fileEntry.getName());
						numberOfBadWord += 1 ; 
						}
					
				}
				
			}
		}
		System.out.println("This directory has: "+numberOfBad+ " bad methods.");
		System.out.println("This directory has: "+numberOfBadSource+ " bad source methods.");
		System.out.println("This directory has: "+numberOfBadSink+ " bad sink methods.");
		System.out.println("This directory has: "+numberOfBadWord+ " bad keyword.");
		System.out.println("This directory has: "+numberOfJavaFiles+ " number Of JavaFiles.");
		System.out.println("This directory has: "+numberOfFiles+ " number Of Files.");
	}
	
	private static void folderEntry(){
		final File folder = new File("s01");
		listFilesForFolder(folder);
	}
	
	@Test
	void test() {
		folderEntry();
	}
}
