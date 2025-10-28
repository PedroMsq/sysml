package adapters.actions;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import adapters.nodes.NodeAdapter;
import interfaces.control.ISuccession;
import interfaces.nodes.INode;
import interfaces.states.IGuard;

public class SuccessionAdapter implements ISuccession {

    private final SuccessionAsUsage succession;
    private final Namespace actionNamespace;

    public SuccessionAdapter(SuccessionAsUsage succession, Namespace actionNamespace) {
        this.succession = succession;
        this.actionNamespace = actionNamespace;
    }

    @Override
    public INode getSource() {
        for (Element src : succession.getSource()) {
            if (src instanceof Namespace ns)
                return new NodeAdapter(ns, actionNamespace);
        }
        return null;
    }

    @Override
    public INode getTarget() {
        for (Element tgt : succession.getTarget()) {
            if (tgt instanceof Namespace ns)
                return new NodeAdapter(ns, actionNamespace);
        }
        return null;
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGuard getGuard() {
		// TODO Auto-generated method stub
		return null;
	}
}
