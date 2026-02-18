package gamine.domain;

import java.util.ArrayList;
import java.util.List;
import org.omg.sysml.lang.sysml.SuccessionAsUsage;

public class SysMLV2Configuration {
	public List<SuccessionAsUsage> successions;
	// List<SuccessionAdapter> successions
	
	public SysMLV2Configuration(List<SuccessionAsUsage> succs) {
		successions = succs;
	}
	
	public SysMLV2Configuration clone() {
	    return new SysMLV2Configuration(new ArrayList<>(this.successions));
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
	    if (!(o instanceof SysMLV2Configuration that)) return false;
	    return successions.equals(that.successions);
	}
	
	@Override
    public int hashCode() {
        return successions.hashCode();
    }
	
	@Override
    public String toString() {
        return "Configuration" + successions;
    }
}