package interfaces.structure;

import java.util.List;

import interfaces.utils.IFeature;

public interface IPartDefinition {
    // Nome declarado da part definition
    String getName();
    // Retornas as features (subelementos) da definition
    List<String> getOwnedFeatures();
    // PartUsages dentro da PartDefinition (internas)
    List<String> getOwnedPartUsages();
    String toString();
    
}
