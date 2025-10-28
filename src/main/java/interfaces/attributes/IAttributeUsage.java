package interfaces.attributes;

import interfaces.utils.IFeature;
import interfaces.utils.IParameter;

public interface IAttributeUsage extends IParameter {
    String getName();
    String getType();        // Retorna o tipo: Real, Integer, etc.
    String getUnit();        // Ex: SI::kg
    String getValue();       // Ex: "1350"
}