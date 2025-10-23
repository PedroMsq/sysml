package interfaces.control;

import java.util.List;

import interfaces.nodes.INode;


public interface IJoinNode extends INode {

    String getName();

    List<String> getIncomingSuccessions(); //getPredecessor

    List<String> getOutgoingSuccessions(); //getSuccessor 

    String describe(); // toString
}