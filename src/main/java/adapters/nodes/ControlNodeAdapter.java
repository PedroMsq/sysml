package adapters.nodes;

import org.omg.sysml.lang.sysml.DecisionNode;
import org.omg.sysml.lang.sysml.Element;
import org.omg.sysml.lang.sysml.ForkNode;
import org.omg.sysml.lang.sysml.JoinNode;
import org.omg.sysml.lang.sysml.MergeNode;
import org.omg.sysml.lang.sysml.Namespace;

import interfaces.control.IControlNode;

public class ControlNodeAdapter extends NodeAdapter implements IControlNode {

    private final boolean isForkNode;
    private final boolean isJoinNode;
    private final boolean isDecisionNode;
    private final boolean isMergeNode;

    public ControlNodeAdapter(Element controlNodeElement) {
        super(controlNodeElement);

        this.isDecisionNode = controlNodeElement instanceof DecisionNode;
        this.isForkNode = controlNodeElement instanceof ForkNode;
        this.isJoinNode = controlNodeElement instanceof JoinNode;
        this.isMergeNode = controlNodeElement instanceof MergeNode;
    }

    @Override
    public boolean isDecisionNode() {
        return isDecisionNode;
    }

    @Override
    public boolean isForkNode() {
        return isForkNode;
    }

    @Override
    public boolean isJoinNode() {
        return isJoinNode;
    }

    @Override
    public boolean isMergeNode() {
        return isMergeNode;
    }
}