package br.ufrpe.dc.sysml.control;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.FlowEnd;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.nodes.FlowEndAdapter;
import adapters.nodes.FlowUsageAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.nodes.IFlowEnd;
import interfaces.utils.INamedElement;

public class FlowUsageTest {

    private static SysMLV2Spec spec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void init() throws IOException {
        spec = new SysMLV2Spec();
        spec.parseFile("control/FlowUsageExample.sysml");
        rootNamespace = (Namespace) spec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz nÃ£o deve ser nulo");
    }

    private void collectAllFlowUsages(Element elt, List<FlowUsage> out) {
        if (elt == null) return;

        if (elt instanceof FlowUsage fu) {
            out.add(fu);
        }

        if (elt instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                collectAllFlowUsages(member, out);
            }
        }

    }
    
    private String toPath(IFlowEnd end) {
    	StringBuilder sb = new StringBuilder();
    	if (end.getReferencedFeature() != null) {
    		sb.append(end.getReferencedFeature().getDeclaredName()).append(".");
    	}
    	for (INamedElement ine : end.getChainingFeatures()) {
            sb.append(ine.getName()).append(".");
        }
    	sb.append(end.getReferenceUsage().getName());
    	return sb.toString();
    }

    @Test
    void testFlowAdapters() {
        List<FlowUsage> flows = new ArrayList<>();
        collectAllFlowUsages(rootNamespace, flows);

        assertFalse(flows.isEmpty(), "Nenhum FlowUsage encontrado no modelo");

        // percorre cada flowUsage encontrado
        for (FlowUsage flow : flows) {

            Namespace container = (Namespace) flow.getOwner();
            if (container == null) container = rootNamespace;

            FlowUsageAdapter adapter = new FlowUsageAdapter(flow);
            // com base no getSource() e getTarget(), construir todo o caminho com StringBuilder
            IFlowEnd src = adapter.getSource();
            IFlowEnd tgt = adapter.getTarget();
            
            System.out.println("\n=== TESTANDO FLOW ADAPTER PARA: " + adapter.getDeclaredName() + " ===");

            System.out.println("getDeclaredName: " + (adapter.getDeclaredName() != null ? adapter.getDeclaredName() : "<no-name>"));
            System.out.println("getName: " + (adapter.getName() != null ? adapter.getName() : "<no-name>"));
            System.out.println("Payload: " + (adapter.getPayload() != null ? adapter.getPayload().getName() : "<no-payload>"));
            if (adapter.getSource() != null) {
                System.out.println("Source: " + toPath(src));
            } else {
                System.out.println("Source: <no-source>");
            }

            if (adapter.getTarget() != null) {
                System.out.println("Target: " + toPath(tgt));
            } else {
                System.out.println("Target: <no-target>");
            }
            // flow a of b from c to d
            StringBuilder sb = new StringBuilder();
        	sb.append("\nflow ");
        	if (adapter.getName() != "<no-name>") { // ajeitar
        		sb.append(adapter.getName());
        	}
        	if (adapter.getPayload() != null) {
        		sb.append(" of ").append(adapter.getPayload().getName());
        	}
        	sb.append("\nfrom ").append(toPath(src));
        	sb.append("\nto ").append(toPath(tgt));
        	System.out.println(sb.toString());

            System.out.println("=== Fim do teste para " + adapter.getName() + " ===");
        }
    }
}
