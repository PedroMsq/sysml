package interfaces.utils;

// incorpora PartUsage, AttributeUsage e ItemUsage
public interface IParameter extends IFeature {
	
	boolean isInput();
	
	boolean isOutput();
	
	IParameter getDirection(); // poderia ser um Enum?
	
	// public enum ParameterDirection {
	// 	IN, OUT, INOUT;
	// }
	
}
