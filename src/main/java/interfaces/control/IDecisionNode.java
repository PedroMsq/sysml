package interfaces.control;

import java.util.List;

// poderia usar o polimorfismo de ControlNode ou poderia criar uma interface para cada tipo (talvez reduza os if/instanceof)
public interface IDecisionNode extends IControlNode {
	
	// String getName(); --> recebe de IControlNode->INode->INamedElement
	
	// List<String> getInputNames(); --> recebe de IControlNode->INode
	
	// List<String> getOutputNames(); --> recebe de IControlNode->INode
	
	// List<String> getGuards(); --> recebe de ISuccession usado em INode
	
	// String describe(); --> desnecessário
}
