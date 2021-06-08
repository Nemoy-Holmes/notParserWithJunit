package TestCase;

import java.io.BufferedInputStream;
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
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class JavaFileParser {
	private final static Logger LOG = Logger.getLogger(JavaFileParser.class.getName());
	
	public static List<String> mkclassFQNsFromSourcecode(final InputStream sourcecodeStream, final String desc) throws IOException{
		final CompilationUnit ast = mkCompilationUnitAstorNull(sourcecodeStream, StandardCharsets.UTF_8, desc);
		if(null == ast) {
			return Collections.emptyList();
		}
		
		final String[] packagePath = new String[1];
		
		final List<String> topLevelTypeNames = new ArrayList<String>();
		final ASTVisitor v = new ASTVisitor() {
			////TODO: implement visitor()
			@Override 
			public boolean visit(final PackageDeclaration node) {
				packagePath[0] = node.getName().getFullyQualifiedName();
				return true;
			}
			
			public boolean visit(MethodDeclaration node) {
				registerTypeName(node);
				return true;
				
				}
			
			private void registerTypeName(final ASTNode decl) {
				final StringBuffer nestedBuffer = new StringBuffer();
				Object obj = decl;
				
				while(obj instanceof AbstractTypeDeclaration) {
					final AbstractTypeDeclaration tDecl = (AbstractTypeDeclaration) obj;
					if(nestedBuffer.length() == 0) {
						
					}
				}
			}
			
		};
		
		ast.accept(v);
		return topLevelTypeNames;
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
		final ASTParser parser = ASTParser.newParser(AST.JLS13);
		final Map<String, String> options = new HashMap<String, String>(JavaCore.getOptions());
		// TODO: implement key-value pair
		
		parser.setCompilerOptions(options);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setSource(txt.toCharArray());
		
		final CompilationUnit  ast = (CompilationUnit) parser.createAST(null);
		return ast;
		
	}

}
