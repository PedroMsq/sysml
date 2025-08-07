package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.AttributeDefinition;
import org.omg.sysml.lang.sysml.AttributeUsage;
import org.omg.sysml.lang.sysml.Classifier;
import org.omg.sysml.lang.sysml.ConnectorAsUsage;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureReferenceExpression;
import org.omg.sysml.lang.sysml.FlowEnd;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.PartDefinition;
import org.omg.sysml.lang.sysml.PartUsage;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.StateUsage;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.TransitionUsage;
import org.omg.sysml.lang.sysml.Usage;

import br.ufrpe.dc.sysml.SysMLV2Spec;

class DecisionControlTest {
	private static SysMLV2Spec sysmlSpec;
	private static Namespace rootNamespace;

	@BeforeAll
	static void init() {
		sysmlSpec = new SysMLV2Spec();
		sysmlSpec.parseFile("control/MergeExample.sysml");
		rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
		assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
	}
	
	//Imprime recursivamente qualquer elemento
	private void printSimpleStructure(Element element, int indent) {
	    String prefix = "  ".repeat(indent);

	    // DeclaredName > getName() > "Unnamed"
	    String declared = element.getDeclaredName() != null
	        ? element.getDeclaredName()
	        : element.getName() != null
	            ? element.getName()
	            : "Unnamed";

	    // Imprime classe + nome
	    System.out.println(prefix
	        + element.eClass().getName()
	        + " - "
	        + declared);

	    // recursão em Namespace
	    if (element instanceof Namespace ns) {
	        for (Element child : ns.getOwnedMember()) {
	            printSimpleStructure(child, indent + 1);
	        }
	    }
//	    if (element instanceof ReferenceUsage fe) {
//	    	fe.referencedFeatureTarget()
//	    	fe.getOwningMembership()
//	    	System.out.println("ReferenceUsage branch:" fe.getName());
//	    }
	}
	@Test
	void testPrintSimpleModelStructure() {
	    assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
	    System.out.println("=== SIMPLE MODEL STRUCTURE ===");
	    printSimpleStructure(rootNamespace, 0);
	}


	private void exploreSuccessionFlows(Element elt, int indent) {
		String prefix = "  ".repeat(indent);

		if (elt instanceof SuccessionAsUsage succ) {
			String succName = succ.getDeclaredName() != null ? succ.getDeclaredName() : "<no-name>";
			System.out.printf("%s[SuccessionAsUsage] %s%n", prefix, succName);

			// percorre flows diretos da succession
			for (Element member : succ.getOwnedMember()) {
				if (member instanceof FlowUsage flow) {
					printFlowDetails(flow, indent + 1);
				}
			}
		}
		// recursão
		if (elt instanceof Namespace ns) {
			for (Element child : ns.getOwnedMember()) {
				exploreSuccessionFlows(child, indent);
			}
		} else if (elt instanceof SuccessionAsUsage nested) {
			for (Element child : nested.getOwnedMember()) {
				exploreSuccessionFlows(child, indent + 1);
			}
		}
	}

	private void printFlowDetails(FlowUsage flow, int indent) {
		
		String prefix = "  ".repeat(indent);
		String flowName = flow.getDeclaredName() != null ? flow.getDeclaredName() : "<no-name>";
		System.out.printf("%s[FlowUsage] %s%n", prefix, flowName);

		// ReferenceUsages e FlowEnd em ownedFeature
		for (var feat : flow.getOwnedFeature()) {
			if (feat instanceof FlowEnd fe) {
				String feName = fe.getDeclaredName() != null ? fe.getDeclaredName() : "<no-name>";
				System.out.printf("%s  - FlowEnd: %s%n", prefix, feName);

			}
			if (feat instanceof ReferenceUsage ru) {

				String ruName = ru.getDeclaredName() != null ? ru.getDeclaredName() : "<no-name>";
				System.out.printf("%s  - ReferenceUsage: %s%n", prefix, ruName);
				for (Feature features : ru.getChainingFeature()) {
					System.out.println(features.getType().get(0) + features.getDeclaredName());
				}
				if (ru instanceof FeatureReferenceExpression) {

				}
			}
		}

		// FlowEnd - ongoing
		for (FlowEnd end : flow.getFlowEnd()) {
			String directionKind = end.getDirection().getName();
			String endName = end.getDeclaredName() != null ? end.getDeclaredName() : "<no-name>";
			System.out.printf("%s  [FlowEnd] %s kind %s %n", prefix, endName, directionKind);

			if (end.getOwnedReferenceSubsetting() != null) {
				String qName = end.getOwnedReferenceSubsetting().getQualifiedName();
				System.out.printf("%s    - ReferenceSubsetting QN: %s%n", prefix, qName);
			}

			// ReferenceUsages nas ownedFeature do FlowEnd
			for (var feat : end.getOwnedFeature()) {
				if (feat instanceof ReferenceUsage ru2) {
					String ru2Name = ru2.getDeclaredName() != null ? ru2.getDeclaredName() : "<no-name>";
					System.out.printf("%s    - ReferenceUsage: %s%n", prefix, ru2Name);
				}
			}
		}
	}
}