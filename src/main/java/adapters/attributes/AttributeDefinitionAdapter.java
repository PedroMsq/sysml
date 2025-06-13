package adapters.attributes;

import java.util.List;
import java.util.stream.Collectors;

import org.omg.sysml.lang.sysml.AttributeUsage;
import org.omg.sysml.lang.sysml.Feature;
import org.omg.sysml.lang.sysml.AttributeDefinition;

import interfaces.attributes.IAttributeDefintion;
import interfaces.attributes.IAttributeUsage;
import interfaces.utils.IFeature;
import adapters.attributes.AttributeUsageAdapter;
import adapters.utils.GenericFeatureAdapter;

public class AttributeDefinitionAdapter implements IAttributeDefintion {
	  private final AttributeDefinition def;
	  
	  public AttributeDefinitionAdapter(AttributeDefinition def) {
	    this.def = def;
	  }

	    @Override
	    public String getName() {
	        return def.getDeclaredName();
	    }

	    @Override
	    public List<IFeature> getOwnedFeatures() {
	        return def.getOwnedMember().stream()
	            .filter(e -> e instanceof Feature)
	            .map(e -> wrapFeature((Feature)e))
	            .collect(Collectors.toList());
	    }

	    // Métodos auxiliares para uso interno
	    private IFeature wrapFeature(Feature f) {
	    	
	        if (f instanceof AttributeUsage) {
	            return new AttributeUsageAdapter((AttributeUsage) f);
	        }
	        if (f instanceof AttributeDefinition) {
	            return new AttributeDefinitionAdapter((AttributeDefinition) f);
	        }
	        // outros adaptadores para outras subclasses de Feature
	        return new GenericFeatureAdapter(f);
	    }
	}

