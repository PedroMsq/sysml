package adapters.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.omg.sysml.lang.sysml.ActionDefinition;
import org.omg.sysml.lang.sysml.ActionUsage;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.FeatureDirectionKind;
import org.omg.sysml.lang.sysml.FlowUsage;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.ReferenceUsage;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import interfaces.actions.IActionDefinition;
import interfaces.nodes.INode;
import interfaces.parts.IPartUsage;

public class ActionDefinitionAdapter implements IActionDefinition{

    private final ActionDefinition actionDef;

    public ActionDefinitionAdapter(ActionDefinition actionDef) {
        this.actionDef = actionDef;
    }

    @Override
    public String getName() {
        return actionDef.getDeclaredName() != null ? actionDef.getDeclaredName() : "<no-name>";
    }

    @Override
    public List<String> getParameters() {
        List<String> params = new ArrayList<>();

        // Extrai parâmetros diretamente definidos na ActionDefinition
        for (FeatureInfo f : extractIOFeaturesFromNamespace(actionDef)) {
            params.add(f.direction + ": " + f.name);
        }

        // Também verifica se há ActionUsage internas com parâmetros (subações)
        for (Element member : actionDef.getOwnedMember()) {
            if (member instanceof ActionUsage actUsage) {
                for (FeatureInfo f : extractIOFeatures(actUsage)) {
                    params.add(f.direction + ": " + f.name);
                }
            }
        }

        return params;
    }

    //Extrai parâmetros diretamente declarados na ActionDefinition
    private List<FeatureInfo> extractIOFeaturesFromNamespace(Namespace ns) {
        List<FeatureInfo> list = new ArrayList<>();

        for (Element owned : ns.getOwnedMember()) {
            if (owned instanceof ReferenceUsage ref) {
                String name = ref.getDeclaredName() != null ? ref.getDeclaredName() : "<no-name>";
                String dir;
                if (ref.getDirection() != null) {
                    dir = ref.getDirection().getName().toLowerCase();
                } else {
                    dir = "ref";
                }
                list.add(new FeatureInfo(dir, name));
            }
        }
        return list;
    }


    @Override
    public List<String> getFlows() {
        // TODO: usar FlowAdapter para descrever cada fluxo
        return new ArrayList<>();
    }

    @Override
    public List<String> getSuccessions() {
        // TODO: chamar o SuccessionAdapter
        return new ArrayList<>();
    }

    // Métodos auxiliares internos
    
    private static class FeatureInfo {
        String direction;
        String name;
        FeatureInfo(String direction, String name) {
            this.direction = direction;
            this.name = name;
        }
    }

    //Extrai parâmetros de entrada e saída de uma ActionUsage
    private List<FeatureInfo> extractIOFeatures(ActionUsage act) {
        List<FeatureInfo> list = new ArrayList<>();

        for (Element owned : act.getOwnedMember()) {
            if (owned instanceof ReferenceUsage ref) {

                String name = ref.getDeclaredName() != null ? ref.getDeclaredName() : "<no-name>";
                String dir;

                // Direção padrão: "ref" se não definida
                if (ref.getDirection() != null) {
                    FeatureDirectionKind directionKind = ref.getDirection();
                    dir = directionKind.getName().toLowerCase(); // converte enum para "in", "out", etc.
                } else {
                    dir = "ref";
                }

                list.add(new FeatureInfo(dir, name));
            }
        }
        return list;
    }

//
//    //Retorna uma descrição textual simples de um FlowUsage
//    private String describeFlow(FlowUsage flow) {
//        // Uma FlowUsage contém FlowEnd(s), que têm ReferenceUsage como origem/destino.
//        StringBuilder sb = new StringBuilder("flow ");
//        EList<Element> ends = flow.getOwnedMember();
//        if (ends.size() >= 2) {
//            Element from = ends.get(0);
//            Element to = ends.get(1);
//            sb.append("from ").append(nameOf(from)).append(" to ").append(nameOf(to));
//        } else {
//            sb.append("<incompleto>");
//        }
//        return sb.toString();
//    }
//
//    // Retorna uma descrição textual de uma SuccessionAsUsage
//    private String describeSuccession(SuccessionAsUsage su) {
//        List<String> sources = new ArrayList<>();
//        List<String> targets = new ArrayList<>();
//
//        for (Element src : su.getSource()) sources.add(nameOf(src));
//        for (Element tgt : su.getTarget()) targets.add(nameOf(tgt));
//
//        return String.join(",", sources) + " -> " + String.join(",", targets);
//    }

    //Nome de um elemento
    private String nameOf(Element e) {
        return e.getDeclaredName() != null ? e.getDeclaredName() : "<no-name>";
    }

//	@Override
//	public List<String> getIncomingFlows() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> getOutgoingFlows() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> getIncomingSuccessions() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<String> getOutgoingSuccessions() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void setActionDefinition(IActionDefinition actionDefinition) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public INode[] getNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPartUsage> getPartUsages() {
		// TODO Auto-generated method stub
		return null;
	}
}
