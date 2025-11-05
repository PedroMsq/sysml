package adapters.actions;

import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.Namespace;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

import adapters.nodes.NodeAdapter;
import interfaces.actions.ISuccession;
import interfaces.nodes.INode;
import interfaces.states.IGuard;

public class SuccessionAdapter implements ISuccession {

    private final SuccessionAsUsage succession;
    private INode source;
    private INode target;

    public SuccessionAdapter(SuccessionAsUsage succession, Namespace nodeNamespace) {
        this.succession = succession;
        if(!succession.getSource().isEmpty()) {
        	Element src = succession.getSource().get(0); //futuramente adicionar adaptador do elemento
        	source = new NodeAdapter((Namespace) src);
        }
        if(!succession.getTarget().isEmpty()) {
        	Element trg = succession.getTarget().get(0); //futuramente adicionar adaptador do elemento
        	source = new NodeAdapter((Namespace) trg);
        }
    }

    @Override
    public INode getSource() {
        return source;
    }

    @Override
    public INode getTarget() {
        return target;
        
    }

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGuard getGuard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeclaredName() {
		// TODO Auto-generated method stub
		return null;
	}
}
