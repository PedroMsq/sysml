package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.ControlNode;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.nodes.ControlNodeAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.actions.ISuccession;

public class ControlNodeTest {
	
	private static SysMLV2Spec spec;
	private static Namespace rootNamespace;
	
	@BeforeAll
	static void init() {
		spec = new SysMLV2Spec();
		spec.parseFile("control/ControlNodeTest.sysml");
		rootNamespace = (Namespace) spec.getRootNamespace();
		assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
	}
	
	private void collectControlNodes(Element elt, List<ControlNode> out) {
        if (elt == null) return;

        if (elt instanceof ControlNode cn) {
            out.add(cn);
        }

        if (elt instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                collectControlNodes(member, out);
            }
        }
    }
	
	private String resolveNodeType(ControlNodeAdapter adapter) {
	    if (adapter.isDecisionNode()) return "DecisionNode";
	    if (adapter.isForkNode()) return "ForkNode";
	    if (adapter.isJoinNode()) return "JoinNode";
	    if (adapter.isMergeNode()) return "MergeNode";
	    return "Unknown";
	}
	
//	private void printSuccessions(ControlNodeAdapter adapter) {
//
//	    if (adapter.isJoinNode() || adapter.isMergeNode()) {
//
//	        System.out.println("Incoming successions:");
//	        for (ISuccession s : adapter.getIncomings()) {
//	            System.out.println(s.getName());
//	        }
//
//	    } else if (adapter.isForkNode() || adapter.isDecisionNode()) {
//
//	        System.out.println("Outgoing successions:");
//	        for (ISuccession s : adapter.getOutgoings()) {
//	            System.out.println(s.getName());
//	        }
//
//	    } else {
//	        System.out.println("No successions.");
//	    }
//	}
	
	@Test
	void testControlNodeAdapters() {
        List<ControlNode> nodes = new ArrayList<>();
        collectControlNodes(rootNamespace, nodes);

        assertFalse(nodes.isEmpty(), "Nenhum Control Node encontrado no modelo.");

        System.out.println("\n================ INICIANDO TESTE ==================\n");

        for (ControlNode node : nodes) {

            Namespace container = (Namespace) node.getOwner();
            if (container == null) container = rootNamespace;

            ControlNodeAdapter adapter = new ControlNodeAdapter(node, container);

            System.out.println("=== Node: " + node.getName() + " ===");
            System.out.println("Node Type: " + resolveNodeType(adapter) + ";\n");
            // printSuccessions(adapter);
        }

        System.out.println("\n================== FIM DO TESTE ==================\n");
    }
}
