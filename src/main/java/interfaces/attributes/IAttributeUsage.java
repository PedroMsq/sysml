package interfaces.attributes;

import interfaces.utils.IFeature;

public interface IAttributeUsage extends IFeature {
    String getName();
    String getType();        // Retorna o tipo: Real, Integer, etc.
    String getUnit();        // Ex: SI::kg
    String getValue();       // Ex: "1350"
}