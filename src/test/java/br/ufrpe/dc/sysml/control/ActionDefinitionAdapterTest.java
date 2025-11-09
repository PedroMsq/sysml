package br.ufrpe.dc.sysml.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

import adapters.actions.ActionDefinitionAdapter;
import br.ufrpe.dc.sysml.SysMLV2Spec;
import interfaces.actions.ISuccession;


class ActionDefinitionAdapterTest {

    private static SysMLV2Spec sysmlSpec;
    private static Namespace rootNamespace;

    // Busca recursiva genérica
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
    
    @Test
    void testChargeBrakeSuccessions() {
        // Busca a ActionDefinition ChargeBattery
    	ActionDefinition def = findElementByNameRecursive(rootNamespace, "Brake", ActionDefinition.class)
    	        .orElseThrow(() -> new AssertionError("ActionDefinition 'Brake' não encontrada"));
        ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(def, def);

        List<ISuccession> successions = adapter.getSuccessions();
        assertFalse(successions.isEmpty(), "Nenhuma succession encontrada dentro de 'ChargeBattery'");

        System.out.println("\n=== SUCCESSIONS DENTRO DE 'Brake' ===");
        for (ISuccession s : successions) {
            String src = s.getSource() != null && s.getSource().getDeclaredName() != null
                    ? s.getSource().getDeclaredName()
                    : "<unnamed>";
            String tgt = s.getTarget() != null && s.getTarget().getDeclaredName() != null
                    ? s.getTarget().getDeclaredName()
                    : "<unnamed>";
            System.out.printf("De: %s -> Para: %s%n", src, tgt);
        }
    }


    @BeforeAll
    static void init() {
        sysmlSpec = new SysMLV2Spec();
        sysmlSpec.parseFile("control/ForkJoinExample.sysml");
        rootNamespace = (Namespace) sysmlSpec.getRootNamespace();
        assertNotNull(rootNamespace, "Namespace raiz não deve ser nulo");
    }

    @Test
    void testMonitorBrakePedalParameters() {
        ActionDefinition def = findElementByNameRecursive(rootNamespace, "MonitorBrakePedal", ActionDefinition.class)
                .orElseThrow(() -> new AssertionError("ActionDefinition 'MonitorBrakePedal' não encontrada"));
        ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(def, def);

        assertEquals("MonitorBrakePedal", adapter.getName());
        assertTrue(adapter.getParameters().contains("out: pressure"), "MonitorBrakePedal deve ter parâmetro de saída 'pressure'");
        assertTrue(adapter.getFlows().isEmpty(), "Flows ainda não implementados");
        assertTrue(adapter.getSuccessions().isEmpty(), "Successions ainda não implementados");
    }

    @Test
    void testMonitorTractionParameters() {
        ActionDefinition def = findElementByNameRecursive(rootNamespace, "MonitorTraction", ActionDefinition.class)
                .orElseThrow(() -> new AssertionError("ActionDefinition 'MonitorTraction' não encontrada"));
        ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(def, def);

        assertEquals("MonitorTraction", adapter.getName());
        assertTrue(adapter.getParameters().contains("out: modFreq"), "MonitorTraction deve ter parâmetro de saída 'modFreq'");
        assertTrue(adapter.getFlows().isEmpty());
        assertTrue(adapter.getSuccessions().isEmpty());
    }

    @Test
    void testBrakingParameters() {
        ActionDefinition def = findElementByNameRecursive(rootNamespace, "Braking", ActionDefinition.class)
                .orElseThrow(() -> new AssertionError("ActionDefinition 'Braking' não encontrada"));
        ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(def, def);

        assertEquals("Braking", adapter.getName());
        assertTrue(adapter.getParameters().contains("in: brakePressure"), "Braking deve ter parâmetro de entrada 'brakePressure'");
        assertTrue(adapter.getParameters().contains("in: modulationFrequency"), "Braking deve ter parâmetro de entrada 'modulationFrequency'");
        assertTrue(adapter.getFlows().isEmpty());
        assertTrue(adapter.getSuccessions().isEmpty());
    }
}
