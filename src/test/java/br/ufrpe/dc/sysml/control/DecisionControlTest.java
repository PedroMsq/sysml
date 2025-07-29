package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import br.ufrpe.dc.sysml.SysMLV2Spec;

class DecisionControlTest {
    private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;

    @BeforeAll
    static void init() {
        sysmlSpec = new SysMLV2Spec();
        sysmlSpec.parseFile("control/MergeExample.sysml");  // Caminho ajustado

        rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }

    // Método auxiliar para busca recursiva de Namespace por nome
    private static Optional<Namespace> findNamespaceByName(Namespace namespace, String targetName) {
        for (Element element : namespace.getOwnedMember()) {
            if (element instanceof Namespace && targetName.equals(element.getDeclaredName())) {
                return Optional.of((Namespace) element);
            }
            if (element instanceof Namespace) {
                Optional<Namespace> found = findNamespaceByName((Namespace) element, targetName);
                if (found.isPresent()) {
                    return found;
                }
            }
        }
        return Optional.empty();
    }

    @Test
    void testMergeExampleNamespaceExists() {
        Optional<Namespace> mergeNamespace = findNamespaceByName(rootNamespace, "MergeExample");
        assertTrue(mergeNamespace.isPresent(), "Namespace 'MergeExample' deve estar presente no modelo");
    }
}