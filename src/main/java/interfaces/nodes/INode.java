package interfaces.nodes;

import java.util.List;

public interface INode {
	
	List<String> getIncomingSuccessions();
	
	List<String> getOutgoingSuccessions();
	
//	List<String> getIncomings();
//	
//	List<String> getOutgoings();
}
