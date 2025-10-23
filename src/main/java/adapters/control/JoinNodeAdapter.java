package adapters.control;

import java.util.ArrayList;
import java.util.List;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import interfaces.control.IJoinNode;

public class JoinNodeAdapter implements IJoinNode {

    private final JoinNode joinNode;
    private final Namespace actionNamespace;

    public JoinNodeAdapter(JoinNode joinNode, Namespace actionNamespace) {
        this.joinNode = joinNode;
        this.actionNamespace = actionNamespace;
    }

    @Override
    public String getName() {
        return joinNode.getDeclaredName() != null ? joinNode.getDeclaredName() : "<no-name>";
    }

 // Retorna todos os elementos que chegam ao JoinNode (target = joinNode)
    @Override
    public List<String> getIncomingSuccessions() {
        List<String> inputs = new ArrayList<>();
        for (Element elem : actionNamespace.getOwnedMember()) {
            if (!(elem instanceof SuccessionAsUsage su)) continue;

            for (Element tgt : su.getTarget()) {
                if (getName().equals(tgt.getDeclaredName())) {
                    for (Element src : su.getSource()) {
                        inputs.add(src.getDeclaredName() != null ? src.getDeclaredName() : "<no-name>");
                    }
                }
            }
        }
        return inputs;
    }

    // Retorna o elemento único que é o destino do JoinNode
    @Override
    public List<String> getOutgoingSuccessions() {
        List<String> outputs = new ArrayList<>();
        for (Element elem : actionNamespace.getOwnedMember()) {
            if (!(elem instanceof SuccessionAsUsage su)) continue;

            for (Element src : su.getSource()) {
                if (getName().equals(src.getDeclaredName())) {
                    for (Element tgt : su.getTarget()) {
                        outputs.add(tgt.getDeclaredName() != null ? tgt.getDeclaredName() : "<no-name>");
                    }
                }
            }
        }
        return outputs;
    }

    @Override
    public String describe() {
        StringBuilder sb = new StringBuilder();
        sb.append("JoinNode '").append(getName()).append("'\n");
        sb.append("  Entradas:\n");
        for (String in : getIncomingSuccessions()) {
            sb.append("    - ").append(in).append("\n");
        }
        sb.append("  Saídas:\n");
        for (String out : getOutgoingSuccessions()) {
            sb.append("    - ").append(out).append("\n");
        }
        return sb.toString();
    }
}

