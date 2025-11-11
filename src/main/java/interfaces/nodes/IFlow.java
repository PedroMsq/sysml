package interfaces.nodes;

import interfaces.structure.IItemUsage;
import interfaces.utils.INamedElement;

public interface IFlow extends INamedElement {

    //FlowEnd source
    public IItemUsage getSource(); // IFlowEnd 

    //FlowEnd target
    public IItemUsage getTarget(); // IFlowEnd 

    //Payload if it exists 
    public INamedElement getPayload(); // IExpression
   
}