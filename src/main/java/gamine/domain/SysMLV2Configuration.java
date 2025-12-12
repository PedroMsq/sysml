package gamine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.omg.sysml.lang.sysml.SuccessionAsUsage;

public class SysMLV2Configuration {
	public List<SuccessionAsUsage> successions;
	
	public SysMLV2Configuration(List<SuccessionAsUsage> succs) {
		successions = succs;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysMLV2Configuration that = (SysMLV2Configuration) o;
        return Objects.equals(successions, that.successions);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(successions);
	}
	
	public SysMLV2Configuration clone() {
		return new SysMLV2Configuration(new ArrayList<SuccessionAsUsage>(successions));
	}
}
