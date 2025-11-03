package interfaces.utils;

// incorpora PartUsage, AttributeUsage e ItemUsage
public interface IParameter extends IFeature { // TROCAR PARA NAMED ELEMENT
	
	public boolean isInput();
	
	public boolean isOutput();
	
	public ParameterDirection getDirection();
	
	public enum ParameterDirection {
	 	IN, OUT, INOUT;
	}
	
}
