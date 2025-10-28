package interfaces.utils;

// um elemento (dentro das definições do SysML ex: attribute, port, ...)
public interface IFeature extends INamedElement {
	// IType getType();
	
    // IElement getOwningType();
    
    boolean isReadOnly();
    
    boolean isOrdered();
    
    boolean isUnique();
    
    boolean isComposite();
    
    // Multiplicity?
    
    // Trazer getDirection para cá?
    // FeatureDirectionKind
    
}
