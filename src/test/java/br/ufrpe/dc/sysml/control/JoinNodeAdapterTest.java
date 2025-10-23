package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.control.JoinNodeAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;


class JoinNodeAdapterTest {

    private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;
    private static Namespace brakeAction;
    private static JoinNode joinNode;


    // Busca recursiva para encontrar um elemento por nome e tipo
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
        sysmlSpec.parseFile("control/ForkJoinExample.sysml");
        rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");

        System.out.println("=== DEBUG: Estrutura do modelo ===");
        for (Element e : rootNamespace.getOwnedMember()) {
            System.out.printf("  [%s] %s%n", e.getClass().getSimpleName(),
                    e.getDeclaredName() != null ? e.getDeclaredName() : "<no-name>");
        }

        // encontra a Action 'Brake' recursivamente
        brakeAction = findElementByNameRecursive(rootNamespace, "Brake", Namespace.class)
                .orElse(null);
        assertNotNull(brakeAction, "Action 'Brake' não encontrada no modelo. Verifique o nome.");

        // encontra o JoinNode 'joinNode' dentro da Action
        joinNode = findElementByNameRecursive(brakeAction, "joinNode", JoinNode.class)
                .orElse(null);
        assertNotNull(joinNode, "JoinNode 'joinNode' não encontrado dentro da Action 'Brake'.");
    }

    @Test
    void testJoinNodeAdapterDescribe() {
        JoinNodeAdapter adapter = new JoinNodeAdapter(joinNode, brakeAction);

        System.out.println("=== DESCRIÇÃO DO JOIN NODE ===");
        System.out.println(adapter.describe());

        assertNotNull(adapter.getName(), "Nome do JoinNode não deve ser nulo");
//        assertFalse(adapter.getIncomings().isEmpty(), "JoinNode deve ter entradas");
//        assertFalse(adapter.getOutgoings().isEmpty(), "JoinNode deve ter saídas");
    }
}
