package adapters.control;

import java.util.ArrayList;
import java.util.List;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import interfaces.control.IJoinNode;
import interfaces.actions.ISuccession;

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

	@Override
	public boolean isInitialNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFlowFinalNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinalNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isForkNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isJoinNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDecisionNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMergeNode() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ISuccession[] getIncomings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISuccession[] getOutgoings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
}
    /*
 // Retorna todos os elementos que chegam ao JoinNode (target = joinNode)
    @Override
    public List<String> getInputNames() {
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
    public List<String> getOutputNames() {
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
        for (String in : getInputNames()) {
            sb.append("    - ").append(in).append("\n");
        }
        sb.append("  Saídas:\n");
        for (String out : getOutputNames()) {
            sb.append("    - ").append(out).append("\n");
        }
        return sb.toString();
    }
}
     */

