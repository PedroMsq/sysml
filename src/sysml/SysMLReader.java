package sysml;

import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.PartDefinition;
import org.omg.sysml.lang.sysml.PartUsage;
import org.omg.sysml.lang.sysml.PortConjugation;
import org.omg.sysml.lang.sysml.PortDefinition;
import org.omg.sysml.lang.sysml.PortUsage;
import org.omg.sysml.lang.sysml.StateDefinition;
import org.omg.sysml.lang.sysml.StateUsage;
import org.omg.sysml.interactive.SysMLInteractive;
import org.omg.sysml.interactive.SysMLInteractiveResult;
import org.omg.sysml.lang.sysml.AcceptActionUsage;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.AttributeDefinition;
import org.omg.sysml.lang.sysml.AttributeUsage;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.FlowConnectionDefinition;
import org.omg.sysml.lang.sysml.FlowConnectionUsage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class SysMLReader {

	public static void main(String[] args) {
		// Get singleton instance.
		SysMLInteractive sysml = SysMLInteractive.getInstance();
		sysml.setVerbose(false);

		// Load the KerML and SysML library models. The argument gives the
		// absolute path to the root "system.library" directory.
		System.out.println("Loading libraries...");
		String systemLibPath = "C:\\Users\\pedro\\git\\new pilot install\\SySML-v2-Pilot-Implementation\\sysml.library";
		sysml.loadLibrary(systemLibPath);

		// Set the base path used for publishing via the API.
		sysml.setApiBasePath("http://sysml2.intercax.com:9000");

		try {
			// Evaluate (parse and validate) the SysML text read from a file
			// at the given absolute path.
			String filePath = "C:/Users/pedro/git/sysml/src/sysml/BatterySystem.sysml";
			System.out.println("Reading " + filePath);
			Path of = Path.of(filePath);
			String string = Files.readString(of, Charset.forName("UTF-8"));
			SysMLInteractiveResult result = sysml.process(string);

			// If there are warnings or errors, this will print them.
			// If the eval is successful, it will print the name and UUID of
			// the top-level element.
			System.out.println(result.toString());
			if (!result.hasErrors()) {
				Element root = result.getRootElement();
				if (root instanceof Namespace) {
					Namespace rootNamespace = (Namespace) root;
					System.out.println("Top-level Namespace: " + rootNamespace.getDeclaredName());

					rootNamespace.getOwnedMember();
					printElementStructure(rootNamespace, "  ");
				}
			}
			System.out.println("Finished!");

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void printElementStructure(Element element, String indent) {
	    String declaredName = element.getDeclaredName();
	    if (declaredName == null) {
	        declaredName = element.getName();
	    }
	    declaredName = (declaredName != null) ? declaredName : "Unnamed";

	    if (element instanceof PartDefinition) {
	        System.out.println(indent + "PartDefinition: " + declaredName);
	    } else if (element instanceof PartUsage) {
	        System.out.println(indent + "PartUsage: " + declaredName);
	    } else if (element instanceof AttributeDefinition) {
	        System.out.println(indent + "AttributeDefinition: " + declaredName);
	    } else if (element instanceof AttributeUsage) {
	        System.out.println(indent + "AttributeUsage: " + declaredName);
	    } else if (element instanceof StateUsage) {
	        System.out.println(indent + "StateUsage: " + declaredName);
	    } else if (element instanceof ActionDefinition) {
	        System.out.println(indent + "ActionDefinition: " + declaredName);
	    } else if (element instanceof ActionUsage) {
	        System.out.println(indent + "ActionUsage: " + declaredName);
	    } else if (element instanceof PortDefinition) {
	        System.out.println(indent + "PortDefinition: " + declaredName);
	    } else if (element instanceof PortUsage) {
	        System.out.println(indent + "PortUsage: " + declaredName);
	    } else if (element instanceof StateDefinition) {
	        System.out.println(indent + "StateDefinition: " + declaredName);
	    } else if (element instanceof AcceptActionUsage) {
	        System.out.println(indent + "AcceptActionUsage: " + declaredName);
	    } else if (element instanceof FlowConnectionDefinition) {
	        System.out.println(indent + "FlowConnectionDefinition: " + declaredName);
	    } else if (element instanceof FlowConnectionUsage) {
	        System.out.println(indent + "FlowConnectionUsage: " + declaredName);
	    } else if (element instanceof PortConjugation) {
	        System.out.println(indent + "PortConjugation: " + declaredName);
	    } else {
	        System.out.println(indent + "Other Element: " + declaredName);
	    }

	    // Recorre para imprimir membros internos se o elemento for um Namespace
	    if (element instanceof Namespace) {
	        for (Element member : ((Namespace) element).getOwnedMember()) {
	            printElementStructure(member, indent + "  ");
	        }
	    }
	}
}