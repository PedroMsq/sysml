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
    private final Namespace containerNamespace;

    public SuccessionAdapter(SuccessionAsUsage succession, Namespace containerNamespace) {
        this.succession = succession;
        this.containerNamespace = containerNamespace;
    }

    @Override
    public INode getSource() {
        for (Element src : succession.getSource()) {
            // Nem todo source Ã© Namespace, mas qualquer Element pode ser adaptado
            return new NodeAdapter(src, containerNamespace);
        }
        return null;
    }

    @Override
    public INode getTarget() {
        for (Element tgt : succession.getTarget()) {
            return new NodeAdapter(tgt, containerNamespace);
        }
        return null;
    }

    @Override
    public IGuard getGuard() {
        
        return null;
    }

    @Override
    public String getDeclaredName() {
        return succession.getDeclaredName();
    }

    @Override
    public String getName() {
        return succession.getDeclaredName();
    }
}