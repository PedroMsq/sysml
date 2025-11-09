package interfaces.nodes;

import interfaces.utils.INamedElement;

public interface IFlow extends INamedElement {

    //FlowEnd source
    public String getSource(); // IFlowEnd getSource(); - ItemUsage

    //FlowEnd target
    public String getTarget(); // IFlowEnd getTarget(); - ItemUsage

    //Payload if it exists 
    public String getPayload(); // IElement getPayload();
    
    // public String getName();
}
