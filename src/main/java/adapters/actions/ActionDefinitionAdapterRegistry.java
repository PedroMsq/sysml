package adapters.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;

public class ActionDefinitionAdapterRegistry {

    private Map<String, ActionDefinitionAdapter> actionsById = new HashMap<>();

    public ActionDefinitionAdapterRegistry(Namespace root) {
        List<ActionDefinition> defs = new ArrayList<>();
        collectAllActionDefinitions(root, defs);

        for (ActionDefinition def : defs) {
            ActionDefinitionAdapter adapter = new ActionDefinitionAdapter(def);
            actionsById.put(def.getElementId(), adapter);
        }
    }

    public ActionDefinitionAdapter getById(String id) {
        return actionsById.get(id);
    }

    public Collection<ActionDefinitionAdapter> getAll() {
        return actionsById.values();
    }

    public void collectAllActionDefinitions(Element elt,
                                             List<ActionDefinition> out) {
        if (elt instanceof ActionDefinition ad) {
            out.add(ad);
        }
        if (elt instanceof Namespace ns) {
            for (Element member : ns.getOwnedMember()) {
                collectAllActionDefinitions(member, out);
            }
        }
    }
}