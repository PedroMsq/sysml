package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.actions.ActionDefinitionAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.nodes.IFlow;
import interfaces.nodes.IFlowEnd;
import interfaces.nodes.INode;
import interfaces.utils.INamedElement;
import interfaces.utils.IParameter;


class ActionDefinitionAdapterTest {

    private static SysMLV2Spec spec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void init() throws IOException {
    	spec = new SysMLV2Spec();
    	spec.parseFile("control/ForkJoinExample.sysml");
    	rootNamespace = (Namespace) spec.getRootNamespace();
    	assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }
    
    private void collectAllActionDefinitions(Element elt, List<ActionDefinition> out) {
    	if (elt == null) return;
    	
    	if (elt instanceof ActionDefinition ad) {
    		out.add(ad);
    	}
    	
    	if (elt instanceof Namespace ns) {
    		for (Element member : ns.getOwnedMember()) {
    				collectAllActionDefinitions(member, out);
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
    void testActionDefinitionAdapters() {
    	List<ActionDefinition> actionDefs = new ArrayList<>();
    	collectAllActionDefinitions(rootNamespace, actionDefs);
    	
    	assertFalse(actionDefs.isEmpty(), "Nenhuma ActionDefinition encontrada no modelo");
    	
    	// percorre cada ActionDefinition encontrada
    	for (ActionDefinition actionDef : actionDefs) {
    		Namespace container = (Namespace) actionDef.getOwner();
    		if (container == null) container = rootNamespace;
    		
    		ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(actionDef);
    		
    		System.out.println("\n=== TESTANDO ACTION DEFINITION ADAPTER PARA: " + adapter.getDeclaredName() + " ===");
    		System.out.println(adapter.getDeclaredName());
    		System.out.println("Parâmetros:");
    		for (IParameter parameter : adapter.getParameters()) {
    			if (adapter.getParameters() != null) {
    				System.out.print(parameter.getDirection() != null ? parameter.getDirection() + " " : "<null> ");
        			System.out.println(parameter.getDeclaredName() != null ? parameter.getDeclaredName() : "<null>");
    			} else {
    				System.out.println("Sem parâmetros."); // Não aparece...?
    			}
    		}
    		System.out.println("\nFlows:");
    		// Impressão dos flows internos
    		if (adapter.getFlows() != null) {
    			for (IFlow flow : adapter.getFlows()) {
        			System.out.println(flow.getDeclaredName());
        			System.out.println(toPath(flow.getSource()));
        		}
    		} else {
    			System.out.println("<no-flows>");
    		}
    		System.out.println("\nNodes:");
    		// Impressão dos nodes internos (ControlNode e FlowUsage)
    		if (adapter.getNodes() != null) {
    			for (INode node : adapter.getNodes()) {
    				System.out.println(node.getDeclaredName());
        		}
    		} else {
    			System.out.println("<no-nodes>");
    		}
    	}
    }
}