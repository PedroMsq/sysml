package interfaces.control;

import java.util.List;

public interface IDecisionNode {
	
	String getName();
	
	List<String> getInputNames(); // getPredecessor
	
	List<String> getOutputNames(); // getSuccessor
	
	List<String> getGuards();
	
	String describe(); // toString readaptado
}
