package interfaces.structure;

import java.util.List;

import interfaces.utils.IFeature;

public interface IAttributeDefintion extends IFeature{
    // Nome declarado da attribute definition
    String getName();
    // Retornas as features (subelementos) contidas na definition
    List<IFeature> getOwnedFeatures();
}
