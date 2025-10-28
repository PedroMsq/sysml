package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.*;

import adapters.nodes.NodeAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.actions.ISuccession;
//import interfaces.nodes.INode;


public class NodeAdapterTest {

    private static SysMLV2Spec spec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void init() {
        spec = new SysMLV2Spec();
        spec.parseFile("control/DecisionExample.sysml");
        rootNamespace = (Namespace) spec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }


    private void collectAllDecisionNodes(Element elt, List<DecisionNode> out) {
        if (elt == null) return;

        if (elt instanceof DecisionNode dn) {
            out.add(dn);
        }

        if (elt instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                collectAllDecisionNodes(member, out);
            }
        }
    }

    //Testa se o adaptador de Node encontra corretamente as incoming/outgoing successions
    @Test
    void testDecisionNodeConnections() {
        List<DecisionNode> decisionNodes = new ArrayList<>();
        collectAllDecisionNodes(rootNamespace, decisionNodes);

        assertFalse(decisionNodes.isEmpty(), "Nenhum DecisionNode encontrado no modelo");

        DecisionNode dn = decisionNodes.stream()
                .filter(d -> "decision1".equals(d.getDeclaredName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("DecisionNode 'decision1' não encontrado"));

        System.out.println("=== TESTANDO NODE ADAPTER PARA: " + dn.getDeclaredName() + " ===");

        NodeAdapter adapter = new NodeAdapter(dn, (Namespace) dn.getOwner());

        System.out.println("\n--- INCOMING SUCCESSIONS ---");
        for (ISuccession inc : adapter.getIncomings()) {
            String srcName = inc.getSource() != null ? inc.getSource().getName() : "<null>";
            System.out.println("De: " + srcName + "  | Guarda: " + inc.getGuard());
        }

        System.out.println("\n--- OUTGOING SUCCESSIONS ---");
        for (ISuccession out : adapter.getOutgoings()) {
            String tgtName = out.getTarget() != null ? out.getTarget().getName() : "<null>";
            System.out.println("Para: " + tgtName + "  | Guarda: " + out.getGuard());
        }
    }
}
