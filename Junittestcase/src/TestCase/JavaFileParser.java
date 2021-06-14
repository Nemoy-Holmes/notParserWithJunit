package TestCase;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.junit.jupiter.api.Test;

public class JavaFileParser {
	private final static Logger LOG = Logger.getLogger(JavaFileParser.class.getName());
	
	private final static List<String>  mkClassFQNsFromSourcecode(final InputStream is, final String desc) throws IOException{
		final CompilationUnit ast = mkCompilationUnitAstorNull(is, StandardCharsets.UTF_8, desc);
		if(null == ast) {
			return Collections.emptyList();
		}
		
		final String[] packagePath = new String[1];
		
		final List<String> topLevelTypeNamesMap = new ArrayList<String>();
		final ASTVisitor v = new ASTVisitor() {
			////TODO: implement visitor()
			@Override 
			public boolean visit(final PackageDeclaration node) {
				packagePath[0] = node.getName().getFullyQualifiedName();
				return true;
			}
			
			public boolean visit(TypeDeclaration node) {
				registerTypeName(node);
				return true;
				
				}
			
			public boolean visit(final EnumDeclaration node) {
				registerTypeName(node);
				return true;
			}
			
			public boolean visit(final AnnotationTypeDeclaration node) {
				registerTypeName(node);
				return true;
			}
			
			private void registerTypeName(final ASTNode decl) {
				final StringBuffer nestedBuffer = new StringBuffer();
				Object obj = decl;
				
				while(obj instanceof AbstractTypeDeclaration) {
					
					final AbstractTypeDeclaration tDecl = (AbstractTypeDeclaration) obj;
					if(nestedBuffer.length() == 0) {
						nestedBuffer.append(tDecl.getName().getFullyQualifiedName());
					} else {
						nestedBuffer.insert(0, tDecl.getName().getFullyQualifiedName() + "$");
					}
					obj = tDecl.getParent();
				}
				
				if(obj instanceof CompilationUnit) {
					final String typeName = nestedBuffer.toString();
					
					if(packagePath[0] == null) {
						topLevelTypeNamesMap.add(typeName);
					} else {
						topLevelTypeNamesMap.add(packagePath[0] + "." + typeName);
					}
				}
			}
		};
		
		ast.accept(v);
		return topLevelTypeNamesMap;
	}
	

	private static CompilationUnit mkCompilationUnitAstorNull(final InputStream is, final Charset cs, String desc) throws IOException {
		
		final String txt = mkString(is, cs);
		
		try {
			return mkCompilationUnitAst(txt);
		} catch(final Exception ex) {
			LOG.log(Level.WARNING, "Problem while parsing source file '" + desc + "'", ex);
			return null;
		}
	}


	private static String mkString(final InputStream is, final Charset encoding) 
	throws IOException {
		final StringBuilder sb = new StringBuilder();
		final InputStreamReader rdr = new InputStreamReader(new BufferedInputStream(is), encoding);
		int ch;
		while (-1 != (ch = rdr.read())) {
			sb.append((char) ch);
		}
		return sb.toString();
	}

	private static CompilationUnit mkCompilationUnitAst(final String txt) throws Exception {
		
		final ASTParser parser = ASTParser.newParser(AST.JLS3);
		final Map<String, String> options = new HashMap<String, String>(JavaCore.getOptions());
		// TODO: implement key-value pair
		options.put("org.eclipse.jdt.core.compiler.source", "13");
		options.put("org.eclipse.jdt.core.compiler.problem.unreachableCode", "ignore");
		options.put("org.eclipse.jdt.core.compiler.generateClassFiles", "disabled");
		
		parser.setCompilerOptions(options);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setSource(txt.toCharArray());
		
		final CompilationUnit  ast = (CompilationUnit) parser.createAST(null);
		return ast;
		
	}
	
	private final Map<String, String> listFilesForFolder(final File file) {
		Map<String, String> javaParserMap = new HashMap<String, String>();
		 List<String> topLevelTypeNames = new ArrayList<String>();
		for(final File fileEntry : file.listFiles()) {
			if(fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			}
			else {
				if(fileEntry.getName().toLowerCase().endsWith(".java")) {
					try {
						final String fileName = fileEntry.getName();
						System.out.println(fileEntry);
						final InputStream is = new FileInputStream(fileEntry);
						topLevelTypeNames = (mkClassFQNsFromSourcecode(is, fileName));
						//javaParserMap.putAll(mkClassFQNsFromSourcecode(is, fileName));
						} catch (IOException e) {
							e.printStackTrace();
						} 
				}
				System.out.println(topLevelTypeNames);
			}
		}
		if(!javaParserMap.isEmpty() ) {
			return javaParserMap;
		}
		else {
			return Collections.emptyMap();
		}
	}
	
	@Test
	void test() {
		final File javaFolderFile = new File("C:\\Users\\Djones\\Desktop\\Java\\src\\testcases\\CWE89_SQL_Injection\\s01");
		final ASTParser parser = ASTParser.newParser(AST.JLS3);
		Map<String, String> javaParserMap = new HashMap<String, String>();
		listFilesForFolder(javaFolderFile);
		//javaParserMap.putAll(listFilesForFolder(javaFolderFile));
		if(javaParserMap.isEmpty()) {
			System.out.println("No bad file in this folder");
		}
		else {
			javaParserMap.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " Map entry Output " + entry.getValue());
			});
		}
		
	}

}
