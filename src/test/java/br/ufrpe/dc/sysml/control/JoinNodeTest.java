package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import br.ufrpe.dc.sysml.SysMLV2Spec;


class JoinNodeTest {
    private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void init() {
        sysmlSpec = new SysMLV2Spec();
        sysmlSpec.parseFile("control/ForkJoinExample.sysml");
        rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }

    //percorre o Namespace e imprime a estrutura dos modelo com as classes dos elementos correspondentes
    private void printElementStructure(Element element, int indent) {
        String prefix = "  ".repeat(indent);
        String className = element.getClass().getSimpleName();
        String name = element.getDeclaredName() != null ? element.getDeclaredName() : "<no-name>";
        System.out.printf("%s%s - %s%n", prefix, className, name);

        if (element instanceof Namespace ns) {
            for (Element child : ns.getOwnedMember()) {
                printElementStructure(child, indent + 1);
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