package interfaces.nodes;

import interfaces.utils.INamedElement;

public interface IFlow extends INamedElement {

    public IFlowEnd getSource();

    public IFlowEnd getTarget();

    // Payload (se existir)
    public INamedElement getPayload();
   
}