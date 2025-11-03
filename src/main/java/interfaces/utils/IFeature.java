package interfaces.utils;

// um elemento (dentro das definições do SysML ex: attribute, port, ...)
public interface IFeature extends INamedElement { // IGNORAR
	// IType getType();
	
    // IElement getOwningType();
    
	public boolean isReadOnly();
    
	public boolean isOrdered();
    
	public boolean isUnique();
    
	public boolean isComposite();
    
    // Multiplicity?
    
    // Trazer getDirection para cá?
    // FeatureDirectionKind
    
}
