package interfaces.control;

import java.util.List;

import interfaces.nodes.INode;


public interface IJoinNode extends INode {

    String getName();
//
//    List<String> getIncomings(); //getPredecessor
//
//    List<String> getOutgoings(); //getSuccessor 

    String describe(); // toString
}