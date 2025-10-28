package adapters.nodes;

import java.util.ArrayList;
import java.util.List;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import adapters.actions.SuccessionAdapter;
import interfaces.control.ISuccession;
import interfaces.nodes.INode;

public class NodeAdapter implements INode {

    private final Namespace nodeNamespace; // Representa o elemento SysML do nó
    private final Namespace actionNamespace; // Namespace pai, usado pra buscar Successions

    public NodeAdapter(Namespace nodeNamespace, Namespace actionNamespace) {
        this.nodeNamespace = nodeNamespace;
        this.actionNamespace = actionNamespace;
    }

    @Override
    public String getName() {
        return nodeNamespace.getDeclaredName();
    }

    @Override
    public ISuccession[] getIncomings() {
        List<ISuccession> incomings = new ArrayList<>();

        for (Element elem : actionNamespace.getOwnedMember()) {
            if (!(elem instanceof SuccessionAsUsage su)) continue;

            for (Element tgt : su.getTarget()) {
                if (getName().equals(tgt.getDeclaredName())) {
                    incomings.add(new SuccessionAdapter(su, actionNamespace));
                }
            }
        }

        return incomings.toArray(new ISuccession[0]);
    }

    @Override
    public ISuccession[] getOutgoings() {
        List<ISuccession> outgoings = new ArrayList<>();

        for (Element elem : actionNamespace.getOwnedMember()) {
            if (!(elem instanceof SuccessionAsUsage su)) continue;

            for (Element src : su.getSource()) {
                if (getName().equals(src.getDeclaredName())) {
                    outgoings.add(new SuccessionAdapter(su, actionNamespace));
                }
            }
        }

        return outgoings.toArray(new ISuccession[0]);
    }

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}
}


