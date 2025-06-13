package adapters.utils;

import org.omg.sysml.lang.sysml.Feature;

import interfaces.utils.IFeature;

public class GenericFeatureAdapter implements IFeature {
	  private final Feature f;
	  public GenericFeatureAdapter(Feature f) { this.f = f; }
	  @Override 
	  public String getName() { return f.getDeclaredName(); }
	}
