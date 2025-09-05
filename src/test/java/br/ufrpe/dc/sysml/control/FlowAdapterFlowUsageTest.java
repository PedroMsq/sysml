package br.ufrpe.dc.sysml.control;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.nodes.FlowAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;

public class FlowAdapterFlowUsageTest {

    @Test
    void testFlowUsageExampleLoadsNamedFlows() throws FileNotFoundException, IOException {
        SysMLV2Spec spec = new SysMLV2Spec();
        
        spec.parseFile("control/FlowUsageExample.sysml");

        Namespace root = (Namespace) spec.getRootNamespace();
        assertNotNull(root, "Namespace raiz não deve ser nulo");

        // localizar flow1 e flow2
        FlowUsage flow1 = findFlowUsageByName(root, "flow1");
        FlowUsage flow2 = findFlowUsageByName(root, "flow2");

        assertNotNull(flow1, "Esperava encontrar FlowUsage 'flow1' no modelo");
        assertNotNull(flow2, "Esperava encontrar FlowUsage 'flow2' no modelo");

        FlowAdapter adapter1 = new FlowAdapter(flow1);
        FlowAdapter adapter2 = new FlowAdapter(flow2);

        // Debug prints
        System.out.println("=== FlowUsageExample.flow1 Debug ===");
        System.out.println("raw declaredName: " + (flow1.getDeclaredName() != null ? flow1.getDeclaredName() : "<no-name>"));
        System.out.println("adapter.getName(): " + adapter1.getName());
        System.out.println("adapter.getPayload(): " + adapter1.getPayload());
        System.out.println("adapter.getSource(): " + adapter1.getSource());
        System.out.println("adapter.getTarget(): " + adapter1.getTarget());
        System.out.println("adapter.toString(): " + adapter1.toString());
        System.out.println("=== End flow1 ===");

        System.out.println("=== FlowUsageExample.flow2 Debug ===");
        System.out.println("raw declaredName: " + (flow2.getDeclaredName() != null ? flow2.getDeclaredName() : "<no-name>"));
        System.out.println("adapter.getName(): " + adapter2.getName());
        System.out.println("adapter.getPayload(): " + adapter2.getPayload());
        System.out.println("adapter.getSource(): " + adapter2.getSource());
        System.out.println("adapter.getTarget(): " + adapter2.getTarget());
        System.out.println("adapter.toString(): " + adapter2.toString());
        System.out.println("=== End flow2 ===");

        // Testes
        assertEquals("Fuel", adapter1.getPayload(), "Esperava payload 'Fuel' para flow1");
        assertEquals("Fuel", adapter2.getPayload(), "Esperava payload 'Fuel' para flow2");

        assertEquals("flow1", adapter1.getName(), "getName deveria devolver declaredName para flow1");
        assertEquals("flow2", adapter2.getName(), "getName deveria devolver declaredName para flow2");

        assertTrue(adapter1.toString().contains("of Fuel"), "toString deve indicar 'of Fuel' para flow1");
        assertTrue(adapter2.toString().contains("of Fuel"), "toString deve indicar 'of Fuel' para flow2");

        assertNotNull(adapter1.getSource(), "Source não deve ser nulo para flow1");
        assertNotNull(adapter1.getTarget(), "Target não deve ser nulo para flow1");
        assertNotNull(adapter2.getSource(), "Source não deve ser nulo para flow2");
        assertNotNull(adapter2.getTarget(), "Target não deve ser nulo para flow2");
    }

    // procura recursivamente um FlowUsage
    private FlowUsage findFlowUsageByName(Element elt, String declaredName) {
        if (elt == null) return null;

        if (elt instanceof FlowUsage fu) {
            if (declaredName == null || declaredName.isEmpty()) return fu;
            if (declaredName.equals(fu.getDeclaredName())) return fu;
        }

        if (elt instanceof Namespace ns) {
            for (Element child : ns.getOwnedMember()) {
                FlowUsage found = findFlowUsageByName(child, declaredName);
                if (found != null) return found;
            }
        } else {
            // Também tenta procurar dentro de features (algumas FlowUsage aparecem como ownedFeature)
            try {
                if (elt instanceof org.omg.sysml.lang.sysml.Feature f) {
                    for (org.omg.sysml.lang.sysml.Feature child : f.getOwnedFeature()) {
                        FlowUsage found = findFlowUsageByName(child, declaredName);
                        if (found != null) return found;
                    }
                }
            } catch (Exception ex) {
                // ignore
            }
        }

        return null;
    }
}