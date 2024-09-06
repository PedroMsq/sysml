package sysml;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.AttributeDefinition;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.PartDefinition;
//import org.omg.sysml.lang.sysml.ConnectionUsage;
import org.omg.sysml.xtext.SysMLStandaloneSetupGenerated;
//import org.omg.sysml.xtext.sysml.Namespace;
//import org.omg.sysml.xtext.sysml.ActionDefinition;
//import org.omg.sysml.xtext.sysml.AttributeDefinition;
//import org.omg.sysml.xtext.sysml.PartDefinition;

import com.google.inject.Injector;

public class SysMLReader {
	
	private static Injector injector;
	private static XtextResourceSet resourceSet;
	
	public static void Startup() throws Exception{
		injector = new SysMLStandaloneSetupGenerated().createInjectorAndDoEMFRegistration();
		resourceSet = injector.getInstance(XtextResourceSet.class);
	}
	
    public static void main(String[] args) throws Exception {
    	Startup();
    	try {
        	String filePath = "./src/sysml/BatterySystem.sysml";
        	URI fileURI = URI.createFileURI(filePath);
        	
        	Resource resource = resourceSet.getResource(fileURI, true);
        	Namespace model = (Namespace) resource.getContents().get(0);
        	printNamespace(model, 0);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static void printNamespace(Namespace namespace, int indent) {
        printIndent(indent);
        System.out.println("Namespace: " + namespace.eClass());

        // itera sobre os membros do Namespace
        List<Element> memberships = namespace.getOwnedMember();
        
        for (EObject member : memberships) {
            if (member instanceof Namespace) {
                printNamespace((Namespace) member, indent + 1);
            } else if (member instanceof PartDefinition) {
                printPartDefinition((PartDefinition) member, indent + 1);
            } else if (member instanceof ActionDefinition) {
                printActionDefinition((ActionDefinition) member, indent + 1);
            } else if (member instanceof AttributeDefinition) {
                printAttributeDefinition((AttributeDefinition) member, indent + 1);
            } else {
                printUnknownType(member, indent + 1);
            }
        }
    }

    private static void printPartDefinition(PartDefinition part, int indent) {
        printIndent(indent);
        System.out.println("Part: " + part.getName());
    }

    private static void printActionDefinition(ActionDefinition action, int indent) {
        printIndent(indent);
        System.out.println("Action: " + action.getName());
    }

    private static void printAttributeDefinition(AttributeDefinition attr, int indent) {
        printIndent(indent);
        System.out.println("Attribute: " + attr.getName());
    }

    private static void printUnknownType(Object obj, int indent) {
        printIndent(indent);
        System.out.println("Unknown type: " + obj.getClass().getSimpleName());
    }

    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }
}