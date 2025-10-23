package interfaces.actions;

import interfaces.nodes.INode;
import java.util.List;
import interfaces.utils.IFeature;

public interface IActionUsage extends INode {

	List<IFeature> getInputFeatures();

	List<IFeature> getOutputFeatures();

	IFeature getFeature(String name);

	IActionDefinition getActionDefinition();

	// boolean isCallBehaviorAction();

	// boolean isSendSignalAction();

	// boolean isAcceptEventAction();

}

