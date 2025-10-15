package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FeatureChainExpression;
import org.omg.sysml.lang.sysml.DecisionNode;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;
import org.omg.sysml.lang.sysml.Expression; // expressao de guarda

import br.ufrpe.dc.sysml.SysMLV2Spec;

class DecisionNodeTest {
	private static SysMLV2Spec sysmlSpec;
	private static Namespace rootNamespace;

	@BeforeAll
	static void init() {
		sysmlSpec = new SysMLV2Spec();
		sysmlSpec.parseFile("control/DecisionExample.sysml");
		rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
		assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
	}

	private void printElementStructure(Element element, int indent) {
		String prefix = " ".repeat(indent);
		String className = element.getClass().getSimpleName();
		String name = element.getDeclaredName() != null ? element.getDeclaredName() : "<no-name>";
		System.out.printf("%s%s - %s%n", prefix, className, name);

		if (element instanceof Namespace ns) {
			for (Element child : ns.getOwnedMember()) {
				printElementStructure(child, indent + 1);
			}
		}

		if (element instanceof DecisionNode dn) {
			if (!dn.getOwnedFeature().isEmpty()) {
				System.out.println("Decision Node Features:");
				for (Feature ffeat : dn.getOwnedFeature()) {
					String fclass = ffeat.eClass().getName();
					String fdecl = ffeat.getDeclaredName() != null ? ffeat.getDeclaredName() : (ffeat.getName() != null ? ffeat.getName() : "<no-name>");
				System.out.printf("%s	Owned Feature: %s - %s%n", prefix, fclass, fdecl);
				}
			} else {
				System.out.printf("Decision node %s nao possui features %n", dn.getDeclaredName());
			}
		}
		
		if (element instanceof SuccessionAsUsage su) {
			// usa getSource() e getTarget() para mostrar ligações reais
			System.out.println(prefix + "SuccessionAsUsage:");
			if(!su.getSource().isEmpty()) {
				for (Element src : su.getSource()) {
					String srcName = src.getDeclaredName() != null ? src.getDeclaredName() : "<no-name>";
					System.out.printf("%s	Source -> %s (%s)%n", prefix, srcName, src.getClass().getSimpleName());
				}
			} else {
				System.out.printf("%s	Nenhuma Source%n", prefix);
			}
			if(!su.getTarget().isEmpty()) {
				for (Element tgt : su.getTarget()) {
					String tgtName = tgt.getDeclaredName() != null ? tgt.getDeclaredName() : "<no-name>";
					System.out.printf("%s	Target -> %s (%s)%n", prefix, tgtName, tgt.getClass().getSimpleName());
				}
			} else {
				System.out.printf("%s	Nenhuma Target%n", prefix);
			}
		}
		
		if (element instanceof Expression expr) {
			 System.out.printf("%sExpression - %s (%s)%n",
				        prefix,
				        expr.getDeclaredName() != null ? expr.getDeclaredName() : "<no-name>",
				        expr.eClass().getName()
				    );

			 if (!expr.getOwnedFeature().isEmpty()) {
				System.out.printf("%s  Owned Features:%n", prefix);
				for (Feature feat : expr.getOwnedFeature()) {
					if(feat instanceof ReferenceUsage ru) {
						
					}
					if(feat instanceof FeatureChainExpression fce) {
						
					}
					
					String fname = feat.getDeclaredName() != null ? feat.getDeclaredName() : "<no-name>";
					System.out.printf("%s    Feature: %s (%s)%n", prefix, fname, feat.eClass().getName());
				}
			 }
			 
		}
	}
	
	@Test
	void testPrintFullModelStructure() {
		assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
		System.out.println("=== FULL MODEL STRUCTURE ===");
		printElementStructure(rootNamespace, 0);
	}
}
