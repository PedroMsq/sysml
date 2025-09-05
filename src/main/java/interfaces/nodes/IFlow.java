package interfaces.nodes;

public interface IFlow {
    public String toString();

    //FlowEnd source
    public String getSource();

    //FlowEnd target
    public String getTarget();

    //Payload if it exists 
    public String getPayload();
    
    //FlowEnd name if it exists
    public String getName();
}
