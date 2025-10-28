package interfaces.parts;

import interfaces.utils.IParameter;

public interface IPartUsage extends IParameter {

    String getName();
    // Nome da especialização (classifier/type) 
    String getSpecialization();
    
    String toString();
    
}