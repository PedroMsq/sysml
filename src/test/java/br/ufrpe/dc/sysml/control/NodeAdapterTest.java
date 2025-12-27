package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.*;

import adapters.nodes.NodeAdapter;
import adapters.utils.AdapterUtils;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.actions.ISuccession;
import interfaces.nodes.INode;
//import interfaces.nodes.INode;


public class NodeAdapterTest {

    private static SysMLV2Spec spec;
    private static Namespace rootNamespace;
    // private ControlNode ctrlNode;

    @BeforeAll
    static void init() {
        spec = new SysMLV2Spec();
        spec.parseFile("control/DecisionExample.sysml");
        rootNamespace = (Namespace) spec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }
    
    private void collectAllActions(Element elt, List<ActionUsage> out) {
        if (elt == null) return;

        if (elt instanceof ActionUsage au) {
        	if (elt instanceof TransitionUsage tu) return;
            out.add(au);
        }
        if (elt instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                collectAllActions(member, out);
            }
        }
    }
    
    @Test
    void testActionConnections() {
        List<ActionUsage> actions = new ArrayList<>();
        collectAllActions(rootNamespace, actions);

        assertFalse(actions.isEmpty(), "Nenhuma ActionUsage encontrada no modelo");

        // percorre cada ação
        for (ActionUsage action : actions) {
        	if (action instanceof TransitionUsage) {
        		continue;
        	}
            System.out.println("\n=== TESTANDO NODE ADAPTER PARA: " + action.getDeclaredName() + " ===");

            // usa o namespace pai (geralmente o ActionDefinition ChargeBattery)
            Namespace container = (Namespace) action.getOwner();
            if (container == null) container = rootNamespace;

          
            NodeAdapter adapter = new NodeAdapter(action);

            // INCOMINGS
            System.out.println("\n--- INCOMING SUCCESSIONS ---");
            for (ISuccession inc : adapter.getIncomings()) {
                String srcName = (inc.getSource() != null) ? inc.getSource().getDeclaredName() : "<null>";
                String tgtName = (inc.getTarget() != null) ? inc.getTarget().getDeclaredName() : "<null>";
                System.out.println("De: " + srcName + " | Para: " + tgtName);
            }

            // OUTGOINGS
            System.out.println("\n--- OUTGOING SUCCESSIONS ---");
            for (ISuccession out : adapter.getOutgoings()) {
                String srcName = (out.getSource() != null) ? out.getSource().getDeclaredName() : "<null>";
                String tgtName = (out.getTarget() != null) ? out.getTarget().getDeclaredName() : "<null>";
                System.out.println("De: " + srcName + " | Para: " + tgtName + "  | Guarda: <sem guarda>");
            }
        }
        System.out.println(AdapterUtils.successions.keySet());
    }
}