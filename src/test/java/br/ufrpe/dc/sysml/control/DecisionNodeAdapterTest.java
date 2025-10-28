package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.DecisionNode;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.control.DecisionNodeAdapter;
import adapters.control.JoinNodeAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;

class DecisionNodeAdapterTest {
	
	private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;
    private static Namespace chargeBatteryAction;
    private static DecisionNode decisionNode;

    // -----------------------------------------------------------
    // Busca recursiva para encontrar um elemento por nome e tipo
    // -----------------------------------------------------------
    private static <T extends Element> Optional<T> findElementByNameRecursive(Element element, String name, Class<T> type) {
        if (type.isInstance(element) && name.equals(element.getDeclaredName())) {
            return Optional.of(type.cast(element));
        }
        if (element instanceof Namespace ns) {
            for (Element child : ns.getOwnedMember()) {
                Optional<T> result = findElementByNameRecursive(child, name, type);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }
    
    @BeforeAll
    static void init() {
        sysmlSpec = new SysMLV2Spec();
        sysmlSpec.parseFile("control/DecisionExample.sysml");
        rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");

        System.out.println("=== DEBUG: Estrutura do modelo ===");
        for (Element e : rootNamespace.getOwnedMember()) {
            System.out.printf("  [%s] %s%n", e.getClass().getSimpleName(),
                    e.getDeclaredName() != null ? e.getDeclaredName() : "<no-name>");
        }

        // encontra a Action 'ChargeBattery' recursivamente
        chargeBatteryAction = findElementByNameRecursive(rootNamespace, "ChargeBattery", Namespace.class)
                .orElse(null);
        assertNotNull(chargeBatteryAction, "Action 'ChargeBattery' não encontrada no modelo. Verifique o nome.");

        // encontra o DecisionNode 'decisionNode' dentro da Action
        decisionNode = findElementByNameRecursive(chargeBatteryAction, "decision1", DecisionNode.class)
                .orElse(null);
        assertNotNull(decisionNode, "DecisionNode 'decision1' não encontrado dentro da Action 'ChargeBattery'.");
    }
    
    @Test
    void testJoinNodeAdapterDescribe() {
        DecisionNodeAdapter adapter = new DecisionNodeAdapter(decisionNode, chargeBatteryAction);

        System.out.println("=== DESCRIÇÃO DO DECISION NODE ===");
        //System.out.println(adapter.describe()); //UPDATE

        assertNotNull(adapter.getName(), "Nome do DecisionNode não deve ser nulo");
        //assertFalse(adapter.getInputNames().isEmpty(), "DecisionNode deve ter entradas");
        //assertFalse(adapter.getOutputNames().isEmpty(), "DecisionNode deve ter saídas");
    }

}
