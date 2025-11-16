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

public class ControlNodeTest {
	
	private static SysMLV2Spec spec;
	private static Namespace rootNamespace;
	private ControlNode ctrlNode;
	
	@BeforeAll
	static void init() {
		spec = new SysMLV2Spec();
		spec.parseFile("control/ControlNodeTest.sysml");
		rootNamespace = (Namespace) spec.getRootNamespace();
		assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
	}
	
	private void collectAllControlNodes(Element elt, List<ControlNode> out) {
		if (elt == null) return;
		
		if (elt instanceof ControlNode cn) {
			out.add(cn);
		}
		
		if (elt instanceof Namespace ns) {
			for (Element member : ns.getOwnedMember()) {
				collectAllControlNodes(member, out);
			}
		}
	}
	
	@Test
	void testControlNodeAdapters() {
		List<ControlNode> controlNodes = new ArrayList<>();
        collectAllControlNodes(rootNamespace, controlNodes);

        assertFalse(controlNodes.isEmpty(), "Nenhum ControlNode encontrado no modelo");

        for (ControlNode controlNode : controlNodes) {
            System.out.println("\n=== CONTROL NODE: " + controlNode.getDeclaredName() + " ===");

            Namespace container = (Namespace) controlNode.getOwner();
            if (container == null) container = rootNamespace;

            //ControlNodeAdapter adapter = new ControlNodeAdapter(controlNode, container);

            // System.out.println("isInitialNode     = " + adapter.isInitialNode());
            // System.out.println("isFlowFinalNode   = " + adapter.isFlowFinalNode());
            // System.out.println("isFinalNode       = " + adapter.isFinalNode());
            //System.out.println("isForkNode        = " + adapter.isForkNode());
            //System.out.println("isJoinNode        = " + adapter.isJoinNode());
            //System.out.println("isDecisionNode    = " + adapter.isDecisionNode());
            //System.out.println("isMergeNode       = " + adapter.isMergeNode());

            // Assert minimal sanity check
//            assertTrue(
//                //adapter.isInitialNode() ||
//                adapter.isFlowFinalNode() ||
//                adapter.isFinalNode() ||
//                adapter.isForkNode() ||
//                adapter.isJoinNode() ||
//                adapter.isDecisionNode() ||
//                adapter.isMergeNode(),
//                "Nenhum tipo reconhecido para ControlNode " + controlNode.getDeclaredName()
//            );
        }
	}
}
