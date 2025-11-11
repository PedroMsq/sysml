package interfaces.nodes;

import interfaces.structure.IItemUsage;
import interfaces.utils.INamedElement;

public interface IFlow extends INamedElement {

    //FlowEnd source
    public IItemUsage getSource(); // IFlowEnd getSource(); - ItemUsage

    //FlowEnd target
    public IItemUsage getTarget(); // IFlowEnd getTarget(); - ItemUsage

    //Payload if it exists 
    public INamedElement getPayload(); // IElement getPayload();
    
}
